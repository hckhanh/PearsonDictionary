package com.dictionary.hckhanh.pearsondictionary;

import com.dictionary.hckhanh.pearsondictionary.pearson.DefinitionFilter;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonApiConfig;
import com.dictionary.hckhanh.pearsondictionary.pearson.service.PearsonServiceManager;

import org.junit.Test;

import rx.functions.Action1;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void testResultFilterMeanings() {
        final String findingWord = "record";

        PearsonApiConfig apiConfig = new PearsonApiConfig(
                DictionaryActivity.BASE_URL,
                DictionaryActivity.CONSUMER_KEY,
                DictionaryActivity.DICTIONARY
        );

        PearsonServiceManager serviceManager = new PearsonServiceManager(apiConfig);

        serviceManager.getDefinition("record")
                .subscribe(new Action1<Definition>() {
                    @Override
                    public void call(Definition definition) {
                        if (definition == null) {
                            fail("Cannot get the definition from server");
                        }

                        DefinitionFilter definitionFilter = new DefinitionFilter(definition, findingWord);
                        assertEquals(3, definitionFilter.getMeanings().size());
                        assertEquals(8, definitionFilter.getSynonyms().size());
                    }
                });
    }
}