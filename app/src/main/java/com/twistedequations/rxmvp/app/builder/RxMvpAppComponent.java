package com.twistedequations.rxmvp.app.builder;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.RedditService;

import dagger.Component;

@AppScope
@Component(modules = {RxMvpAppModule.class, NetworkModule.class, RestServiceModule.class, GsonModule.class, RxModule.class})
public interface RxMvpAppComponent {

    Context context();

    Picasso picasso();

    AndroidRxSchedulers rxSchedulers();

    RedditService redditService();
}
