package com.dictionary.hckhanh.pearsondictionary.pearson.service;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PearsonServiceManager {

    public PearsonServiceManager(PearsonApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pearsonService = retrofit.create(PearsonService.class);

    }

    public Observable<Definition> getDefinition(final String word) {
        return Observable.create(new Observable.OnSubscribe<Definition>() {
            @Override
            public void call(Subscriber<? super Definition> subscriber) {
                String consumerKey = apiConfig.getConsumerKey();
                Call<Definition> definitionCall = null;
                if (consumerKey != null) {
                    definitionCall = pearsonService.findWordWithKey(apiConfig.getDictionary(), word, apiConfig.getConsumerKey());
                } else {
                    definitionCall = pearsonService.findWord(word);
                }

                try {
                    Response<Definition> response = definitionCall.execute();
                    subscriber.onNext(response.body());
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    PearsonApiConfig apiConfig;

    PearsonService pearsonService;

}
