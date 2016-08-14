package com.twistedequations.rxmvp.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.twistedequations.rxmvp.app.RxMvpApp;
import com.twistedequations.rxmvp.screens.detail.dagger.DaggerPostActivityComponent;
import com.twistedequations.rxmvp.screens.detail.dagger.PostModule;
import com.twistedequations.rxmvp.screens.detail.mvl.DefaultPostView;
import com.twistedequations.rxmvp.screens.detail.mvl.PostPresenter;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import javax.inject.Inject;

public class PostActivity extends AppCompatActivity {

    public static final String REDDIT_ITEM_KEY = "redditItem";

    public static void start(Context context, RedditItem redditItem) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(REDDIT_ITEM_KEY, redditItem);
        context.startActivity(intent);
    }

    @Inject
    DefaultPostView defaultPostView;

    @Inject
    PostPresenter postPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerPostActivityComponent.builder()
                .rxMvpAppComponent(RxMvpApp.get(this).component())
                .postModule(new PostModule(this))
                .build().inject(this);

        postPresenter.onCreate();

        //registerLifecycle(postPresenter);
        setContentView(defaultPostView.getView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        postPresenter.onDestroy();
    }
}
