package com.twistedequations.rxmvp.screens.home.mvp;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final HomeView homeView;
    private final HomeModel homeModel;
    private final AndroidRxSchedulers androidSchedulers;

    public HomePresenter(HomeView homeView, HomeModel homeModel, AndroidRxSchedulers androidSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.androidSchedulers = androidSchedulers;
    }

    public void onCreate() {
        compositeSubscription.add(loadPostsSubscription());
        compositeSubscription.add(loadCommentsSubscription());
        compositeSubscription.add(loginClickSubscription());
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    //Loads posts from both the saved state and then the network
    private Observable<List<RedditItem>> loadRedditItems() {
        return Observable.just(null)
                .doOnNext(notification -> homeView.setLoading(true))

                .switchMap(aVoid -> homeModel.getSavedRedditListing()
                        .concatWith(homeModel.postsForAll()
                                .subscribeOn(androidSchedulers.network())
                                .observeOn(androidSchedulers.mainThread())
                                .map(homeModel::saveRedditListing)))

                .map(redditData -> redditData.data.children)
                .flatMap(redditItems -> Observable.from(redditItems)
                        .map(child -> child.data)
                        .filter(redditItem -> !redditItem.over18)
                        .toList())

                .observeOn(androidSchedulers.mainThread())
                .doOnNext(notification -> homeView.setLoading(false));
    }

    private Subscription loadPostsSubscription() {
        return Observable.just(null)
                .mergeWith(homeView.refreshMenuClick())
                .mergeWith(homeView.errorRetryClick())
                .flatMap(aVoid -> loadRedditItems())
                .doOnError(Throwable::printStackTrace)
                .subscribe(homeView::setRedditItems, throwable -> homeView.showError());
    }

    private Subscription loadCommentsSubscription() {
        return homeView.listItemClicks()
                .subscribe(homeModel::startDetailActivity);
    }

    private Subscription loginClickSubscription() {
        return homeView.loginClick()
                .subscribe(aVoid -> homeModel.startLoginActivity());
    }

}


