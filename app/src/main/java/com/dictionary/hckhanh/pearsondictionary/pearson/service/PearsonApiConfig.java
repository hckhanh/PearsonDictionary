package com.dictionary.hckhanh.pearsondictionary.pearson.service;

public class PearsonApiConfig {

    public PearsonApiConfig(String baseUrl, String consumerKey, String dictionary) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.dictionary = dictionary;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getDictionary() {
        return dictionary;
    }

    String baseUrl;

    String consumerKey;

    String dictionary;
}
