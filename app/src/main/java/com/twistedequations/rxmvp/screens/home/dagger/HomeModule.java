package com.twistedequations.rxmvp.screens.home.dagger;

import com.squareup.picasso.Picasso;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.screens.home.HomeActivity;
import com.twistedequations.rxmvp.screens.home.mvp.DefaultHomeView;
import com.twistedequations.rxmvp.screens.home.mvp.HomeModel;
import com.twistedequations.rxmvp.screens.home.mvp.HomePresenter;
import com.twistedequations.rxmvp.screens.home.mvp.HomeView;
import com.twistedequations.rxmvp.reddit.RedditService;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeActivity homeActivity;

    public HomeModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeScope
    public HomeView homeView(Picasso picasso) {
        return new DefaultHomeView(homeActivity, picasso);
    }

    @Provides
    @HomeScope
    public HomePresenter homePresenter(HomeView homeView, HomeModel homeModel, AndroidRxSchedulers androidSchedulers) {
        return new HomePresenter(homeView, homeModel, androidSchedulers);
    }

    @Provides
    @HomeScope
    public HomeModel homeModel(RedditService redditService) {
        return new HomeModel(redditService, homeActivity);
    }
}
