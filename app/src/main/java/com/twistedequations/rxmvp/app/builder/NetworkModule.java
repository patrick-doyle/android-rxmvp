package com.twistedequations.rxmvp.app.builder;

import android.content.Context;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

    @AppScope
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return  new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @AppScope
    @Provides
    public Cache cache(Context context, @Named("OkHttpCacheDir") String cacheDir, @Named("OkHttpCacheSize") int cacheSize) {
        return new Cache(new File(context.getFilesDir(), cacheDir), cacheSize);
    }


    @AppScope
    @Provides
    @Named("OkHttpCacheDir")
    public String cacheDir() {
        return "OkHttpCache";
    }


    @AppScope
    @Provides
    @Named("OkHttpCacheSize")
    public int cacheSize() {
        return 10 * 1024 * 1024; //10MB cache
    }

    @AppScope
    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.i("HttpLoggingInterceptor", message))
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @AppScope
    @Provides
    public Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
