package com.twistedequations.rxmvp.app.builder;


import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.DefaultAndroidRxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public AndroidRxSchedulers rxSchedulers() {
        return new DefaultAndroidRxSchedulers();
    }
}
