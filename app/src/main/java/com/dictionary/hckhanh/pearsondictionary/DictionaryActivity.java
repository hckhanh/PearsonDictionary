package com.dictionary.hckhanh.pearsondictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonApiConfig;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonServiceManager;

import rx.functions.Action1;

public class DictionaryActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.pearson.com";
    public static final String DICTIONARY = "ldoce5";
    public static final String CONSUMER_KEY = null; // Enter your co

    PearsonServiceManager pearsonServiceManager;

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
        pearsonServiceManager.getDefinition("happy")
            .subscribe(new Action1<Definition>() {
                @Override
                public void call(Definition definition) {
                    Toast.makeText(DictionaryActivity.this, String.format("%d", definition.getStatus()), Toast.LENGTH_SHORT)
                            .show();

                }
            });
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
