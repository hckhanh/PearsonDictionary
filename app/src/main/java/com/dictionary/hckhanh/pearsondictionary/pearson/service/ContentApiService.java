package com.dictionary.hckhanh.pearsondictionary.pearson.service;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class ContentApiService {
  private final ContentApis contentApis;

  /**
   * Get the unique instance of {@link ContentApiService}
   *
   * @return The {@link ContentApiService} object
   */
  public static ContentApiService getContentApiService() {
    return ContentServiceLoader.CONTENT_SERVICE_INSTANCE;
  }

  private ContentApiService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ContentApis.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    contentApis = retrofit.create(ContentApis.class);
  }

  /**
   * Get definition of a specificed word
   *
   * @param word The Word which is searched
   * @return {@link Observable} of {@link Word} object
   */
  public Observable<Definition> getDefinition(final String word) {
    return Observable.create(new Observable.OnSubscribe<Definition>() {
      @Override public void call(Subscriber<? super Definition> subscriber) {
        Call<Definition> definitionCall =
            contentApis.findWord(ContentApis.DICTIONARY, word, ContentApis.CONSUMER_KEY);
        try {
          Response<Definition> response = definitionCall.execute();
          subscriber.onNext(response.body());
          subscriber.onCompleted();
        } catch (IOException e) {
          subscriber.onError(e);
        }
      }
    }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * The Loader class is created for thread-safe singleton implementation
   */
  private static class ContentServiceLoader {
    /**
     * A unique instance of {@link ContentApiService}
     */
    public static final ContentApiService CONTENT_SERVICE_INSTANCE = new ContentApiService();
  }
}
