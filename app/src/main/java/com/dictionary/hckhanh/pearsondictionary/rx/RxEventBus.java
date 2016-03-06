package com.dictionary.hckhanh.pearsondictionary.rx;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * The Event Bus implementation from RxJava library
 */
public final class RxEventBus {

  private final PublishSubject<Object> eventBus = PublishSubject.create();

  private RxEventBus() {
  }

  /**
   * Get the {@link RxEventBus} object, Only one instance is created in the app
   *
   * @return The unique instance of {@link RxEventBus}
   */
  public static RxEventBus getEventBus() {
    return RxEventBusLoader.RX_EVENT_BUS_INSTANCE;
  }

  public void send(Object o) {
    eventBus.onNext(o);
  }

  /**
   * Get {@link Observable} to subscribe the event is sent to {@link RxEventBus}
   *
   * @return The {@link Observable} object
   */
  public Observable<Object> toObservable() {
    return eventBus;
  }

  /**
   * The Loader class is created for thread-safe singleton implementation
   */
  private static class RxEventBusLoader {
    /**
     * A unique instance of {@link RxEventBus}
     */
    public static final RxEventBus RX_EVENT_BUS_INSTANCE = new RxEventBus();
  }
}
