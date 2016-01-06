package com.dictionary.hckhanh.pearsondictionary;

import android.test.ActivityInstrumentationTestCase2;

import com.dictionary.hckhanh.pearsondictionary.pearson.DefinitionFilter;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonApiConfig;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonServiceManager;
import com.robotium.solo.Solo;

import rx.functions.Action1;


public class PearsonServiceTest extends ActivityInstrumentationTestCase2<DictionaryActivity> {

    private Solo solo;

    PearsonServiceManager serviceManager;

    public PearsonServiceTest() {
        super(DictionaryActivity.class);
        serviceManager = new PearsonServiceManager(new PearsonApiConfig(
                DictionaryActivity.BASE_URL,
                DictionaryActivity.CONSUMER_KEY,
                DictionaryActivity.DICTIONARY
        ));
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testResultFilterMeanings() {
        final String findingWord = "record";

        serviceManager.getDefinition(findingWord)
                .subscribe(new Action1<Definition>() {
                    @Override
                    public void call(Definition definition) {
                        if (definition == null) {
                            fail("Cannot get the definition from server");
                        }

                        DefinitionFilter definitionFilter = new DefinitionFilter(definition, findingWord);
                        assertEquals(2, definitionFilter.getMeanings().size());
                        assertEquals(8, definitionFilter.getSynonyms().size());
                    }
                });
    }

}
