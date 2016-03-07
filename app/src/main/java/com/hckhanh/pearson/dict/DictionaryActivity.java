package com.hckhanh.pearson.dict;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hckhanh.pearson.dict.history.HistoryPager;
import com.hckhanh.pearson.dict.history.HistoryPagerFragment;
import com.hckhanh.pearson.dict.history.dao.DaoMaster;
import com.hckhanh.pearson.dict.history.dao.DaoSession;
import com.hckhanh.pearson.dict.history.dao.History;
import com.hckhanh.pearson.dict.history.dao.HistoryDao;
import com.hckhanh.pearson.dict.pager.PagerManager;
import com.hckhanh.pearson.dict.pearson.DefinitionFilter;
import com.hckhanh.pearson.dict.pearson.data.Definition;
import com.hckhanh.pearson.dict.pearson.service.ContentApiService;
import com.hckhanh.pearson.dict.rx.RxEventBus;
import com.hckhanh.pearson.dict.word.WordPager;
import com.hckhanh.pearson.dict.word.WordPagerFragment;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * The main activity of the pearson app.
 */
public class DictionaryActivity extends AppCompatActivity {
  private static final long MAX_HISTORY = 50;
  private static final String INIT_WORD = "a";
  @Bind(R.id.dict_pager) ViewPager dictPager;
  private ContentApiService contentApiService;
  private PagerManager pagerManager;
  private CompositeSubscription subscriptions = new CompositeSubscription();
  private HistoryDao historyDao;
  private DefinitionFilter filteredWords;
  private List<History> histories;
  private SearchView.OnQueryTextListener onQueryTextListener =
      new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(final String query) {
          queryWord(query);
          saveToDatabase(query);
          return false;
        }

        @Override public boolean onQueryTextChange(String newText) {
          return false;
        }
      };

  private void queryWord(final String query) {
    dictPager.setCurrentItem(0);
    pagerManager.showLoadingIndicator();

    Subscription subscription =
        contentApiService.getDefinition(query).subscribe(new Action1<Definition>() {
          @Override public void call(Definition definition) {
            if (definition.getCount() > 0) {
              filteredWords = new DefinitionFilter(definition, query);
              pagerManager.setWords(filteredWords);
              //pagerManager.notifyDataSetChanged();
            } else {
              Snackbar.make(dictPager, R.string.error_no_result, Snackbar.LENGTH_LONG).show();
            }

            pagerManager.hideLoadingIndicator();
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            pagerManager.hideLoadingIndicator();
            if (throwable instanceof UnknownHostException) {
              Snackbar.make(dictPager, R.string.error_network_connection,
                  Snackbar.LENGTH_INDEFINITE)
                  .setAction(R.string.action_retry, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                      queryWord(query);
                      saveToDatabase(query);
                    }
                  })
                  .show();
            }
          }
        });

    subscriptions.add(subscription);
  }

  private void saveToDatabase(String query) {
    History history = new History(null, query);
    if (historyDao.count() < MAX_HISTORY && !checkExitWord(query)) {
      long id = historyDao.insert(history);
      Log.d(DictionaryActivity.class.getSimpleName(),
          "History is created with Id: " + id);

      history.setId(id);
      histories.add(history);
      //pagerManager.notifyDataSetChanged();
    }
  }

  private boolean checkExitWord(String query) {
    for (History history : histories) {
      if (history.getWord().equals(query)) return true;
    }
    return false;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dictionary);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    contentApiService = ContentApiService.getContentApiService();

    WordPager meaningWordPager =
        new WordPager(getString(R.string.tab_meaning), new WordPagerFragment());
    WordPager moreWordPager = new WordPager(getString(R.string.tab_more), new WordPagerFragment());
    HistoryPager historyWordPager =
        new HistoryPager(getString(R.string.tab_history), new HistoryPagerFragment());

    // Add pager to pager manager
    pagerManager = new PagerManager(getSupportFragmentManager());
    pagerManager.addPager(meaningWordPager);
    pagerManager.addPager(moreWordPager);
    pagerManager.addPager(historyWordPager);

    // Add adapter to pager
    dictPager.setAdapter(pagerManager);

    // Add pager to Tab Layout
    TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tab);
    tabLayout.setupWithViewPager(dictPager);

    // Initialize the database
    initDatabase();

    // Init Event bus
    initEventBus();
  }

  private void initEventBus() {
    Subscription subscription =
        RxEventBus.getEventBus().toObservable().subscribe(new Action1<Object>() {
          @Override public void call(Object o) {
            if (o instanceof HistoryPagerFragment.OnHistoryStartEvent) {
              pagerManager.setHistories(histories);
              //pagerManager.notifyDataSetChanged();
            } else if (o instanceof WordPagerFragment.OnWordStartEvent) {
              if (filteredWords != null) {
                pagerManager.setWords(filteredWords);
                //pagerManager.notifyDataSetChanged();
                pagerManager.hideLoadingIndicator();
              }
            }
          }
        });

    subscriptions.add(subscription);
  }

  private void initDatabase() {
    DaoMaster.OpenHelper dbHelper = new DaoMaster.DevOpenHelper(this, "dict-db", null);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    DaoMaster daoMaster = new DaoMaster(db);
    DaoSession daoSession = daoMaster.newSession();
    historyDao = daoSession.getHistoryDao();

    histories = historyDao.queryBuilder().list();
    histories = new ArrayList<>(histories);
  }

  @Override protected void onStart() {
    super.onStart();
    queryWord(INIT_WORD);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_dictionary, menu);

    SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
    searchView.setOnQueryTextListener(onQueryTextListener);
    return true;
  }

  // Release data and unsubscribe any subscriptions
  @Override protected void onDestroy() {
    super.onDestroy();
    subscriptions.unsubscribe();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.search) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
