package com.hckhanh.pearson.dict;

import android.test.ActivityInstrumentationTestCase2;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.hckhanh.pearson.dict.pearson.service.ContentApiService;
import com.robotium.solo.Solo;
import java.util.List;

public class DictionaryActivityTest extends ActivityInstrumentationTestCase2<DictionaryActivity> {

    ContentApiService serviceManager;
    private Solo solo;

    public DictionaryActivityTest() {
        super(DictionaryActivity.class);
        serviceManager = ContentApiService.getContentApiService();
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testSearchAndRotateScreen() {
        String findingWord = "record";

        // Unlock the screen
        solo.unlockScreen();

        // Click on search icon
        View searchItem = solo.getView(R.id.search);
        solo.clickOnView(searchItem);

        // Enter the "record" word
        solo.enterText(0, findingWord);

        // Press the search button on softkeyboard
        solo.pressSoftKeyboardSearchButton();

        // Click on the first item of the RecylerView
        List<TextView> textViews1 = solo.clickInRecyclerView(0);

        // Rotate the screen to landscape
        solo.setActivityOrientation(Solo.LANDSCAPE);

        // Check the title 1 of the word: "record"
        assertEquals(findingWord, textViews1.get(0).getText().toString());

        // Check the ipa 1 of the word: "record"
        assertEquals("rɪˈkɔːd (verb)", textViews1.get(1).getText().toString());

        // Check the definition 1 of the word: "record"
        assertEquals("to write information down or store it in a computer or on film so that it can be looked at in the future\n\nHer husband made her record every penny she spent.\n\n\n", textViews1.get(2).getText().toString());

        // Click on the second item of the RecylerView
        solo.scrollDownRecyclerView(0);
        List<TextView> textViews2 = solo.clickInRecyclerView(1);

        // Check the title 2 of the word: "record"
        assertEquals("record", textViews2.get(0).getText().toString());

        // Check the ipa 2 of the word: "record"
        assertEquals("ˈrekɔːd (noun)", textViews2.get(1).getText().toString());

        // Check the definition 2 of the word: "record"
        assertEquals(Html.fromHtml("<h3>information about something that is written down or stored on computer, film etc so that it can be looked at in the future</h3><p>According to official records, five people were killed last year near that road junction.</p><br />").toString(), textViews2.get(2).getText().toString());
    }

    public void testRotateAndSearchScreen() {
        String findingWord = "record";

        // Rotate the screen to landscape
        solo.setActivityOrientation(Solo.LANDSCAPE);

        // Click on search icon
        View searchItem = solo.getView(R.id.search);
        solo.clickOnView(searchItem);

        // Enter the "record" word
        solo.enterText(0, findingWord);

        // Rotate the screen to portrait mode
        solo.setActivityOrientation(Solo.PORTRAIT);

        // Press the search button on softkeyboard
        solo.pressSoftKeyboardSearchButton();

        // Click on the first item of the RecylerView
        List<TextView> textViews1 = solo.clickInRecyclerView(0);

        // Check the title 1 of the word: "record"
        assertEquals(findingWord, textViews1.get(0).getText().toString());

        // Check the ipa 1 of the word: "record"
        assertEquals("rɪˈkɔːd (verb)", textViews1.get(1).getText().toString());

        // Check the definition 1 of the word: "record"
        assertEquals("to write information down or store it in a computer or on film so that it can be looked at in the future\n\nHer husband made her record every penny she spent.\n\n\n", textViews1.get(2).getText().toString());

        // Click on the second item of the RecylerView
        solo.scrollDownRecyclerView(0);
        List<TextView> textViews2 = solo.clickInRecyclerView(1);

        // Check the title 2 of the word: "record"
        assertEquals("record", textViews2.get(0).getText().toString());

        // Check the ipa 2 of the word: "record"
        assertEquals("ˈrekɔːd (noun)", textViews2.get(1).getText().toString());

        // Check the definition 2 of the word: "record"
        assertEquals(Html.fromHtml("<h3>information about something that is written down or stored on computer, film etc so that it can be looked at in the future</h3><p>According to official records, five people were killed last year near that road junction.</p><br />").toString(), textViews2.get(2).getText().toString());
    }

    public void testMoreTab() {
        String findingWord = "record";

        // Click on search icon
        View searchItem = solo.getView(R.id.search);
        solo.clickOnView(searchItem);

        // Enter the "record" word
        solo.enterText(0, findingWord);

        // Press the search button on softkeyboard
        solo.pressSoftKeyboardSearchButton();

        // Click on MORE tab
        solo.clickOnText("More", 0);

        // Wait for the recording definition
        solo.waitForText("recording", 9, 2000, false);

        // Click on the first item of the RecylerView
        List<TextView> textViews1 = solo.clickInRecyclerView(0, 1);

        // Check the title of the word: "record"
        assertEquals("recording", textViews1.get(0).getText().toString());

        // Check the ipa of the word: "record"
        assertEquals("rɪˈkɔːdɪŋ (noun)", textViews1.get(1).getText().toString());

        // Check the definition of the word: "record"
        assertEquals(Html.fromHtml("<h3>music, speech, or images that have been stored on tape or discs</h3>").toString(), textViews1.get(2).getText().toString());
    }

    public void testSearchWithoutInternet() {
        String findingWord = "record";

        // Turn off WIFI
        solo.setWiFiData(false);
        solo.setMobileData(false);

        // Click on search icon
        View searchItem = solo.getView(R.id.search);
        solo.clickOnView(searchItem);

        // Enter the "record" word
        solo.enterText(0, findingWord);

        // Press the search button on softkeyboard
        solo.pressSoftKeyboardSearchButton();

        // Search the error message
        boolean hasErrorMessage = solo.waitForText("Please check your internet connection");

        // Search the retry button
        boolean hasRetryButton = solo.searchButton("RETRY");

        // Turn on WIFI connection again
        solo.setWiFiData(true);
        solo.setMobileData(true);

        assertTrue("Don't contain error message", hasErrorMessage);

        assertTrue("Don't contain RETRY button", hasRetryButton);
    }

    public void testSearchAfterReconnectingInternet() {
        String findingWord = "record";

        // Turn off WIFI and Mobile
        solo.setWiFiData(false);
        solo.setMobileData(false);

        // Click on search icon
        View searchItem = solo.getView(R.id.search);
        solo.clickOnView(searchItem);

        // Enter the "record" word
        solo.enterText(0, findingWord);

        // Press the search button on softkeyboard
        solo.pressSoftKeyboardSearchButton();

        // Wait and check the RETRY button is existed
        boolean hasRetryButton = solo.searchButton("RETRY");
        assertTrue("Don't contain RETRY button", hasRetryButton);

        // Turn on the WIFI and Mobile data again
        solo.setWiFiData(true);
        solo.setMobileData(true);

        solo.waitForText("");

        // Click on the RETRY button after reconnecting to the internet
        solo.clickOnButton("RETRY");

        solo.waitForText(findingWord);

        // Click on the first item of the RecylerView
        List<TextView> textViews1 = solo.clickInRecyclerView(0);

        // Check the title 1 of the word: "record"
        assertEquals(findingWord, textViews1.get(0).getText().toString());

        // Check the ipa 1 of the word: "record"
        assertEquals("rɪˈkɔːd (verb)", textViews1.get(1).getText().toString());

        // Check the definition 1 of the word: "record"
        assertEquals("to write information down or store it in a computer or on film so that it can be looked at in the future\n\nHer husband made her record every penny she spent.\n\n\n", textViews1.get(2).getText().toString());

        // Scroll down to the item 2
        solo.scrollDownRecyclerView(0);

        // Click on the second item of the RecylerView
        List<TextView> textViews2 = solo.clickInRecyclerView(1);

        // Check the title 2 of the word: "record"
        assertEquals("record", textViews2.get(0).getText().toString());

        // Check the ipa 2 of the word: "record"
        assertEquals("ˈrekɔːd (noun)", textViews2.get(1).getText().toString());

        // Check the definition 2 of the word: "record"
        assertEquals(Html.fromHtml("<h3>information about something that is written down or stored on computer, film etc so that it can be looked at in the future</h3><p>According to official records, five people were killed last year near that road junction.</p><br />").toString(), textViews2.get(2).getText().toString());
    }

}
