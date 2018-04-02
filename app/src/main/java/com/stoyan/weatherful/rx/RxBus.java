package com.stoyan.weatherful.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by stoyan.ivanov on 4/2/2018.
 */

public class RxBus {
    private static RxBus instance;
    private PublishSubject<Object> bus = PublishSubject.create();

    public static RxBus getInstance() {
        if(instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }
}
