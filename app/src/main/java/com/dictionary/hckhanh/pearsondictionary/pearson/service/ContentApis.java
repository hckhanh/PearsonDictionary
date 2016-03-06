package com.dictionary.hckhanh.pearsondictionary.pearson.service;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * The Content APIs interface of Pearson service
 */
public interface ContentApis {
  /**
   * Base Url of Dictionaries API
   */
  String BASE_URL = "https://api.pearson.com/v2/dictionaries/";

  /**
   * Dictionary name
   */
  String DICTIONARY = "ldoce5";

  /**
   * The comsummer key of API (It's must be secured)
   */
  String CONSUMER_KEY = "8GSH5VnAF6MAarcneSo088RgA0K7U5SH";

  /**
   * Find a word in dictionary
   *
   * @param dictionary The name of dictionary
   * @param headword The word which is searched
   * @param consumerKey the API key of service
   * @return {@link Observable} of {@link Word} Object
   */
  @GET("{dictionary}/entries") Call<Definition> findWord(
      @Path("dictionary") String dictionary, @Query("headword") String headword,
      @Query("apikey") String consumerKey);
}
