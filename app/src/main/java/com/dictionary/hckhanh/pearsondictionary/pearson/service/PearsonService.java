package com.dictionary.hckhanh.pearsondictionary.pearson.service;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PearsonService {

    @GET("/v2/dictionaries/{dictionary}/entries")
    Call<Definition> findWordWithKey(
            @Path("dictionary") String dictionary,
            @Query("headword") String headword,
            @Query("apikey") String comsumerKey
    );

    @GET("/v2/dictionaries/{dictionary}/entries")
    Call<Definition> findWord(
            @Path("dictionary") String dictionary,
            @Query("headword") String headword
    );

}
