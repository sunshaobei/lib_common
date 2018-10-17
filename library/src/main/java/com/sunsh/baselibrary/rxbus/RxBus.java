package com.sunsh.baselibrary.rxbus;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by sunsh on 2018/6/21.
 */
public class RxBus {

    private FlowableProcessor<Object> mBus;
    private static RxBus defaultInstance;
    private final SubscriberMethodFinder subscribermethodFinder;
    private final CompositeDispose compositeDispose;
    private final Map<Class<?>, Object> mStickyEventMap;


    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
        subscribermethodFinder = new SubscriberMethodFinder();
        compositeDispose = new CompositeDispose();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    /**
     * Convenience singleton for apps using a process-wide RxBus instance.
     */
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    public void register(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();
        List<Method> subscriberMethods = subscribermethodFinder.findSubscriberMethods(subscriberClass);
        synchronized (this) {
            for (Method subscriberMethod : subscriberMethods) {
                // fix backpressure
                if (((Subscribe) subscriberMethod.getAnnotations()[0]).isSticky()) {
                    compositeDispose.put(subscriber, toFlowableSticky(subscriberMethod.getParameterTypes()[0]).subscribe(o -> {
                        if (subscriber != null) {
                            subscriberMethod.setAccessible(true);
                            subscriberMethod.invoke(subscriber, o);
                        }
                    }));
                } else {
                    compositeDispose.put(subscriber, toFlowable(subscriberMethod.getParameterTypes()[0]).subscribe(o -> {
                        if (subscriber != null) {
                            subscriberMethod.setAccessible(true);
                            subscriberMethod.invoke(subscriber, o);
                        }
                    }));
                }
            }
        }
    }

    /**
     * fix leak memory
     *
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        compositeDispose.remove(subscriber);
    }

    public void post(Object obj) {
        post(obj, false);
    }


    /**
     * Stciky 相关
     * 发送一个新Sticky事件
     */
    public void post(Object event, boolean isSticky) {
        if (isSticky)
            synchronized (mStickyEventMap) {
                mStickyEventMap.put(event.getClass(), event);
            }
        mBus.onNext(event);
    }

    private <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }


    public void removeSticky(Class eventType){
        mStickyEventMap.remove(eventType);
    }


    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    private  <T> Flowable<T> toFlowableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Flowable<T> flowable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return flowable.mergeWith(Flowable.create(e -> e.onNext(eventType.cast(event)), BackpressureStrategy.LATEST));
            } else {
                return flowable;
            }
        }
    }
}