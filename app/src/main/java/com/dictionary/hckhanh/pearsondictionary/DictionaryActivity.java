package com.dictionary.hckhanh.pearsondictionary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dictionary.hckhanh.pearsondictionary.pearson.DefinitionFilter;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.ContentApiService;
import com.dictionary.hckhanh.pearsondictionary.word.PagerManager;
import com.dictionary.hckhanh.pearsondictionary.word.WordPager;
import com.dictionary.hckhanh.pearsondictionary.word.WordPagerFragment;
import java.net.UnknownHostException;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * The main activity of the dictionary app.
 */
public class DictionaryActivity extends AppCompatActivity {
  @Bind(R.id.dict_pager) ViewPager dictPager;
  private ContentApiService contentApiService;
  private PagerManager pagerManager;
  private CompositeSubscription subscriptions = new CompositeSubscription();
  private SearchView.OnQueryTextListener onQueryTextListener =
      new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(final String query) {
          queryWord(query);
          return false;
        }

        @Override public boolean onQueryTextChange(String newText) {
          return false;
        }
      };

  private void queryWord(final String query) {
    pagerManager.showLoadingIndicator();

    Subscription subscription =
        contentApiService.getDefinition(query).subscribe(new Action1<Definition>() {
          @Override public void call(Definition definition) {
            if (definition.getCount() > 0) {
              DefinitionFilter filteredWords = new DefinitionFilter(definition, query);
              pagerManager.setWords(filteredWords);
              pagerManager.notifyDataSetChanged();
              pagerManager.hideLoadingIndicator();
              dictPager.setCurrentItem(0);
            } else {
              Snackbar.make(dictPager, R.string.error_no_result, Snackbar.LENGTH_LONG).show();
              pagerManager.hideLoadingIndicator();
            }
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            if (throwable instanceof UnknownHostException) {
              Snackbar.make(dictPager, R.string.error_network_connection,
                  Snackbar.LENGTH_INDEFINITE).setAction(R.string.action_retry, new View.OnClickListener() {
                @Override public void onClick(View v) {
                  queryWord(query);
                }
              }).show();
            }
          }
        });

    subscriptions.add(subscription);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dictionary);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    contentApiService = ContentApiService.getContentApiService();

    WordPager meaningWordPager = new WordPager(getString(R.string.tab_meaning), null, new WordPagerFragment());
    WordPager moreWordPager = new WordPager(getString(R.string.tab_more), null, new WordPagerFragment());

    // Add pager to pager manager
    pagerManager = new PagerManager(getSupportFragmentManager());
    pagerManager.addPager(meaningWordPager);
    pagerManager.addPager(moreWordPager);

    // Add adapter to pager
    dictPager.setAdapter(pagerManager);

    // Add pager to Tab Layout
    TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tab);
    tabLayout.setupWithViewPager(dictPager);
  }

  @Override protected void onStart() {
    super.onStart();
    queryWord(getString(R.string.init_word));
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
