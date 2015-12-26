package com.dictionary.hckhanh.pearsondictionary;

import com.dictionary.hckhanh.pearsondictionary.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.service.PearsonService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PearsonServciceManager {

    public PearsonServciceManager(PearsonApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pearsonService = retrofit.create(PearsonService.class);
    }

    public Definition getDefinition(String word) {
        String consumerKey = apiConfig.getConsumerKey();
        if (consumerKey != null) {
            return pearsonService.findWordWithKey(apiConfig.getDictionary(), word, apiConfig.getConsumerKey());
        } else {
            return pearsonService.findWord(apiConfig.getDictionary(), word);
        }
    }

    PearsonApiConfig apiConfig;

    PearsonService pearsonService;

}
