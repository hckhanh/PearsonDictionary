package com.dictionary.hckhanh.pearsondictionary.service;

import com.dictionary.hckhanh.pearsondictionary.data.Definition;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PearsonService {

    @GET("/dictionaries/{dictionary}/entries")
    Call<Definition> findWordWithKey(
            @Path("dictionary") String dictionary,
            @Query("headword") String word,
            @Query("apikey") String comsumerKey
    );

    @GET("/dictionaries/{dictionary}/entries")
    Call<Definition> findWord(
            @Path("dictionary") String dictionary,
            @Query("headword") String word
    );

}
