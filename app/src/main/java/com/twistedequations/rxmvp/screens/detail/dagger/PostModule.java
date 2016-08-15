package com.twistedequations.rxmvp.screens.detail.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.screens.detail.PostActivity;
import com.twistedequations.rxmvp.screens.detail.mvp.DefaultPostView;
import com.twistedequations.rxmvp.screens.detail.mvp.PostModel;
import com.twistedequations.rxmvp.screens.detail.mvp.PostPresenter;
import com.twistedequations.rxmvp.reddit.RedditService;

import dagger.Module;
import dagger.Provides;

@Module
public class PostModule {

    private final PostActivity activity;

    public PostModule(PostActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PostScope
    public DefaultPostView homeView(Picasso picasso) {
        return new DefaultPostView(activity, picasso);
    }

    @Provides
    @PostScope
    public PostPresenter postPresenter(DefaultPostView view, PostModel model, AndroidRxSchedulers androidSchedulers) {
        return new PostPresenter(view, model, androidSchedulers);
    }

    @Provides
    @PostScope
    public PostModel homeModel(RedditService redditService) {
        return new PostModel(redditService, activity);
    }
}
