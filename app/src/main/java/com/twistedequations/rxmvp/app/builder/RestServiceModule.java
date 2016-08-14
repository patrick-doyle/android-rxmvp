package com.twistedequations.rxmvp.app.builder;

import com.google.gson.Gson;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.RedditService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestServiceModule {

    @AppScope
    @Provides
    @Named("RedditServiceRetrofit")
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson, AndroidRxSchedulers androidSchedulers) {
        return new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(androidSchedulers.network()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    public RedditService redditService(@Named("RedditServiceRetrofit") Retrofit retrofit) {
        return retrofit.create(RedditService.class);
    }
}
