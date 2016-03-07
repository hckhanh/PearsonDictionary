package com.hckhanh.pearson.dict.pearson.service;

import com.hckhanh.pearson.dict.pearson.data.Definition;
import com.hckhanh.pearson.dict.pearson.data.Word;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class ContentApiService {
  private final ContentApis contentApis;

  private ContentApiService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ContentApis.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    contentApis = retrofit.create(ContentApis.class);
  }

  /**
   * Get the unique instance of {@link ContentApiService}
   *
   * @return The {@link ContentApiService} object
   */
  public static ContentApiService getContentApiService() {
    return ContentServiceLoader.CONTENT_SERVICE_INSTANCE;
  }

  /**
   * Get definition of a specified word
   *
   * @param word The Word which is searched
   * @return {@link Observable} of {@link Word} object
   */
  public Observable<Definition> getDefinition(final String word) {
    return contentApis.findWord(ContentApis.DICTIONARY, word, ContentApis.CONSUMER_KEY)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
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
