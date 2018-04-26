package com.stoyan.weatherful.rx;


import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by stoyan.ivanov2 on 3/28/2018.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> applySchedulersObservable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> MaybeTransformer<T, T> applySchedulersMaybe() {
        return maybe -> maybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> applySchedulerSingle() {
        return single -> single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
