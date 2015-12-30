package com.dictionary.hckhanh.pearsondictionary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dictionary.hckhanh.pearsondictionary.fragment.word.WordPager;
import com.dictionary.hckhanh.pearsondictionary.fragment.PagerManager;
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
        pearsonServiceManager.getDefinition("record")
            .subscribe(new Action1<Definition>() {
                @Override
                public void call(Definition definition) {
                    DefinitionFilter filter = new DefinitionFilter(definition, "record");
                    WordPager wordPager = (WordPager) pagerManager.getPager(0);

                    wordPager.addWords(filter.getMeanings());
                    wordPager.getPagerFragment().notifyDataSetChanged();
                }
            });


        // Add adapter to pager
        ViewPager dictPager = (ViewPager) findViewById(R.id.dict_pager);
        pagerManager = new PagerManager(getSupportFragmentManager());
        dictPager.setAdapter(pagerManager);

        // Add pager to Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(dictPager);

        WordPager meaningWordPager = new WordPager("Meaning", new ArrayList<Word>(), WordPagerFragment.class);
        pagerManager.addPager(meaningWordPager);
        pagerManager.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
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
