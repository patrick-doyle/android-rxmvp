package com.twistedequations.rxmvp.web;

import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;

import rx.Observable;
import rx.Subscriber;

public class RxJavaWebInterface implements Observable.OnSubscribe<String> {

    @Nullable
    private Subscriber<? super String> subscriber = null;

    @JavascriptInterface
    public void onNext(String data) {
        if (subscriber != null) {
            subscriber.onNext(data);
        }
    }

    @JavascriptInterface
    public void onError(String error) {
        Exception exception = new Exception(error);
        if (subscriber != null) {
            subscriber.onError(exception);
        }
    }

    @JavascriptInterface
    public void onCompleted() {
        if (subscriber != null) {
            subscriber.onCompleted();
        }
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
    }
}
