package com.sunsh.baselibrary.base;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseManager {
    protected void dataHandling(ObservableOnSubscribe observableOnSubscribe, Observer observer) {
        Observable.create(observableOnSubscribe).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
