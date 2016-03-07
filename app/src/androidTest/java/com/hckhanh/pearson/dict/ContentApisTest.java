package com.hckhanh.pearson.dict;

import android.test.ActivityInstrumentationTestCase2;
import com.hckhanh.pearson.dict.pearson.DefinitionFilter;
import com.hckhanh.pearson.dict.pearson.data.Definition;
import com.hckhanh.pearson.dict.pearson.service.ContentApiService;
import com.robotium.solo.Solo;
import rx.functions.Action1;

public class ContentApisTest extends ActivityInstrumentationTestCase2<DictionaryActivity> {

  ContentApiService serviceManager;
  private Solo solo;

  public ContentApisTest() {
    super(DictionaryActivity.class);
    serviceManager = ContentApiService.getContentApiService();
  }

  @Override protected void setUp() throws Exception {
    solo = new Solo(getInstrumentation(), getActivity());
  }

  @Override protected void tearDown() throws Exception {
    solo.finishOpenedActivities();
  }

  public void testResultFilterMeanings() {
    final String findingWord = "record";

    serviceManager.getDefinition(findingWord).subscribe(new Action1<Definition>() {
      @Override public void call(Definition definition) {
        if (definition == null) {
          fail("Cannot get the definition from server");
        }

        DefinitionFilter definitionFilter = new DefinitionFilter(definition, findingWord);
        assertEquals(2, definitionFilter.getMeanings().size());
        assertEquals(8, definitionFilter.getSynonyms().size());
      }
    }, new Action1<Throwable>() {
      @Override public void call(Throwable throwable) {

      }
    });
  }
}
