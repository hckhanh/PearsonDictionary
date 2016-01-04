package com.dictionary.hckhanh.pearsondictionary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dictionary.hckhanh.pearsondictionary.fragment.PagerManager;
import com.dictionary.hckhanh.pearsondictionary.fragment.word.WordPager;
import com.dictionary.hckhanh.pearsondictionary.fragment.word.WordPagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.DefinitionFilter;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonApiConfig;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonServiceManager;

import java.util.ArrayList;

import rx.functions.Action1;

public class DictionaryActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.pearson.com";
    public static final String DICTIONARY = "ldoce5";
    public static final String CONSUMER_KEY = null; // Enter your consumer key here...

    PearsonServiceManager pearsonServiceManager;

    PagerManager pagerManager;

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(final String query) {
            pearsonServiceManager.getDefinition(query)
                    .subscribe(new Action1<Definition>() {
                        @Override
                        public void call(Definition definition) {
                            if (definition.getCount() > 0) {
                                DefinitionFilter filter = new DefinitionFilter(definition, query);
                                WordPager meaningPager = (WordPager) pagerManager.getPager(0);
                                WordPager synonymsPager = (WordPager) pagerManager.getPager(1);

                                meaningPager.addWords(filter.getMeanings());
                                synonymsPager.addWords(filter.getSynonyms());

                                meaningPager.getPagerFragment().notifyDataSetChanged();
                                synonymsPager.getPagerFragment().notifyDataSetChanged();
                            } else {
                            }
                        }
                    });

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

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

        // Add adapter to pager
        ViewPager dictPager = (ViewPager) findViewById(R.id.dict_pager);
        pagerManager = new PagerManager(getSupportFragmentManager());
        dictPager.setAdapter(pagerManager);

        // Add pager to Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(dictPager);

        WordPager meaningWordPager = new WordPager("Meaning", new ArrayList<Word>(), WordPagerFragment.class);
        WordPager synonymWordPager = new WordPager("Synonyms", new ArrayList<Word>(), WordPagerFragment.class);
        pagerManager.addPager(meaningWordPager);
        pagerManager.addPager(synonymWordPager);

        pagerManager.notifyDataSetChanged();
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
