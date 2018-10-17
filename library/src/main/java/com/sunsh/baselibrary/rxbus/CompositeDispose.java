package com.sunsh.baselibrary.rxbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CompositeDispose {
    private Map<Object, CompositeDisposable> compositeDisposableMap = new HashMap<>();

    public void put(Object o, Disposable disposable) {
        CompositeDisposable compositeDisposable = compositeDisposableMap.get(o);
        if (compositeDisposable == null)
            compositeDisposableMap.put(o, compositeDisposable = new CompositeDisposable());
        compositeDisposable.add(disposable);
    }

    public void remove(Object o) {
        CompositeDisposable compositeDisposable = compositeDisposableMap.get(o);
        if (compositeDisposable != null) compositeDisposable.dispose();
        compositeDisposableMap.remove(o);
    }
}
