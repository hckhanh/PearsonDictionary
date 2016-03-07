package com.hckhanh.pearson.dict.pearson.service;

import com.hckhanh.pearson.dict.pearson.data.Definition;
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
   * Find a word in pearson
   *
   * @param dictionary The name of pearson
   * @param headword The word which is searched
   * @param consumerKey the API key of service
   * @return {@link Observable}<{@link Definition}>
   */
  @GET("{pearson}/entries") Observable<Definition> findWord(
      @Path("pearson") String dictionary, @Query("headword") String headword,
      @Query("apikey") String consumerKey);
}
