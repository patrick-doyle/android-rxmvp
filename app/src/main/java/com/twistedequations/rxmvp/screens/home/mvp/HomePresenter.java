package com.twistedequations.rxmvp.screens.home.mvp;

import android.util.Log;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.Collections;
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
        compositeSubscription.add(loadStartPostsSubscription());
        compositeSubscription.add(refreshPostsSubscription());
        compositeSubscription.add(loadCommentsSubscription());
        compositeSubscription.add(loginClickSubscription());
        compositeSubscription.add(profileClickSubscription());
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    //Loads posts from both the saved state and then the network
    private Observable<List<RedditItem>> loadRedditItems() {
        return Observable.just(null)
                .doOnNext(notification -> homeView.setLoading(true))

                .switchMap(aVoid -> homeModel.getSavedRedditListing() //get the listing from the saved state
                        .switchIfEmpty(homeModel.postsForAll() //switch to the network and get the listing from there if
                                //there is no saved state
                                .subscribeOn(androidSchedulers.network())
                                .observeOn(androidSchedulers.mainThread())
                                .map(homeModel::saveRedditListing)))// update the saved state with the network data

                //Mapping reddit data to the correct form for display
                .observeOn(androidSchedulers.io())
                .map(redditData -> redditData.data.children)
                .flatMap(redditItems -> Observable.from(redditItems)
                        .map(child -> child.data)
                        .filter(redditItem -> !redditItem.over18)
                        .toList())

                .observeOn(androidSchedulers.mainThread())
                .doOnNext(notification -> homeView.setLoading(false))
                .doOnError(throwable -> {
                    throwable.printStackTrace(); //Log errors
                    homeView.showError(); // show error dialog on error
                })
                .onErrorReturn(throwable -> Collections.emptyList()); // eat the error and return an empty list
    }

    private Subscription loadStartPostsSubscription() {
        return Observable.just(null) //inital load
                .flatMap(aVoid -> loadRedditItems()) //get the reddit data from network
                .doOnError(throwable -> {
                    throwable.printStackTrace(); //Log errors
                    homeView.showError(); // show error dialog on error
                })
                .subscribe(homeView::setRedditItems); // display data
    }

    private Subscription refreshPostsSubscription() {
        return homeView.refreshMenuClick()//merged with menu clicks
                .mergeWith(homeView.errorRetryClick()) //merged with retry clicks
                .flatMap(aVoid -> loadRedditItems()) //get the reddit data from network
                .doOnEach(notification -> Log.i("TAG", "notification = " + notification))
                .subscribe(homeView::setRedditItems); // display data
    }

    private Subscription loadCommentsSubscription() {
        return homeView.listItemClicks()
                .subscribe(homeModel::startDetailActivity);
    }

    private Subscription profileClickSubscription() {
        return homeView.profileMenuClick()
                .subscribe(aVoid -> homeModel.startProfileActivity());
    }

    private Subscription loginClickSubscription() {
        return homeView.loginClick()
                .subscribe(aVoid -> homeModel.startLoginActivity());
    }

}


