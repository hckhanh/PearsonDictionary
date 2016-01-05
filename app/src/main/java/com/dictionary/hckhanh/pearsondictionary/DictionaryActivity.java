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

import com.dictionary.hckhanh.pearsondictionary.fragment.PagerManager;
import com.dictionary.hckhanh.pearsondictionary.fragment.word.WordPager;
import com.dictionary.hckhanh.pearsondictionary.fragment.word.WordPagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.DefinitionFilter;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonApiConfig;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonServiceManager;

import java.net.UnknownHostException;

import rx.functions.Action1;

public class DictionaryActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.pearson.com";
    public static final String DICTIONARY = "ldoce5";
    public static final String CONSUMER_KEY = null; // Enter your consumer key here...
    private static final String STATE_CURRENT_WORD = "current_word";

    PearsonServiceManager pearsonServiceManager;

    PagerManager pagerManager;

    ViewPager dictPager;

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(final String query) {
            queryWord(query);

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private void queryWord(final String query) {
        pagerManager.showLoadingIndicator();
        pearsonServiceManager.getDefinition(query)
                .subscribe(new Action1<Definition>() {
                    @Override
                    public void call(Definition definition) {
                        if (definition.getCount() > 0) {
                            DefinitionFilter filteredWords = new DefinitionFilter(definition, query);
                            pagerManager.setWords(filteredWords);
                            pagerManager.notifyDataSetChanged();
                            pagerManager.hideLoadingIndicator();
                        } else {
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof UnknownHostException) {
                            Snackbar.make(dictPager, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            queryWord(query);
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PearsonApiConfig apiConfig = new PearsonApiConfig(
                BASE_URL,
                CONSUMER_KEY,
                DICTIONARY
        );
        pearsonServiceManager = new PearsonServiceManager(apiConfig);

        WordPager meaningWordPager = new WordPager("Meaning", null, WordPagerFragment.class);
        WordPager moreWordPager = new WordPager("More", null, WordPagerFragment.class);

        // Add pager to pager manager
        pagerManager = new PagerManager(getSupportFragmentManager());
        pagerManager.addPager(meaningWordPager);
        pagerManager.addPager(moreWordPager);


        // Add adapter to pager
        dictPager = (ViewPager) findViewById(R.id.dict_pager);
        dictPager.setAdapter(pagerManager);

        // Add pager to Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(dictPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
