package com.stoyan.weatherful.rx;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by stoyan.ivanov on 4/2/2018.
 */
@Singleton
public class RxBus {
    private PublishSubject<Object> mBus = PublishSubject.create();

    @Inject
    public RxBus() {}

    public void post(Object event) {
        mBus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }
}
