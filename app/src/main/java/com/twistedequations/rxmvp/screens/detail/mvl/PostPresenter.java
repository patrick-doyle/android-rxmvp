package com.twistedequations.rxmvp.screens.detail.mvl;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.reddit.models.RedditListing;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class PostPresenter {

    private final PostModel postModel;
    private final DefaultPostView defaultPostView;
    private final AndroidRxSchedulers androidSchedulers;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public PostPresenter(DefaultPostView defaultPostView, PostModel postModel, AndroidRxSchedulers androidSchedulers) {
        this.postModel = postModel;
        this.defaultPostView = defaultPostView;
        this.androidSchedulers = androidSchedulers;
    }

    public void onCreate() {
        compositeSubscription.add(loadCommentsSubscriptions());
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    private Subscription loadCommentsSubscriptions() {

        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final PublishSubject<List<RedditListing>> listPublishSubject = PublishSubject.create();

        //get the posts from the network request
        final Subscription postSub = listPublishSubject.map(redditListings -> redditListings.get(0))
                .map(redditListing -> redditListing.data.children.get(0).data)
                .onErrorResumeNext(throwable -> Observable.empty())
                .observeOn(androidSchedulers.mainThread())
                .subscribe(defaultPostView::setRedditItem);

        //get the commentns from the network request
        final Observable<List<RedditItem>> networkItems = listPublishSubject.map(redditListings -> redditListings.get(1))
                .map(redditListing -> redditListing.data.children)
                .onErrorResumeNext(throwable -> Observable.just(Collections.emptyList()))
                .flatMap(children -> Observable.from(children)
                        .map(child -> child.data)
                        .toList());

        //get the comments from the saved state
        final Subscription commentSub = Observable.concat(postModel.getCommentsFromState(), networkItems)
                .observeOn(androidSchedulers.mainThread())
                .doOnNext(postModel::saveComentsState)
                .subscribe(defaultPostView::setCommentList);

        final Subscription subscription = Observable.just(postModel.getIntentRedditItem())
                .flatMap(redditItem -> postModel.getCommentsForPost(redditItem.subreddit, redditItem.id))
                .subscribeOn(androidSchedulers.network())
                .subscribe(listPublishSubject);

        compositeSubscription.add(subscription);
        compositeSubscription.add(postSub);
        compositeSubscription.add(commentSub);
        return compositeSubscription;
    };
}
