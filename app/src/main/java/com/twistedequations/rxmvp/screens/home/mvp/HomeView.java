package com.twistedequations.rxmvp.screens.home.mvp;

import android.view.View;

import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;

public interface HomeView {

    Observable<Void> refreshMenuClick();

    Observable<Void> profileMenuClick();

    Observable<Void> loginClick();

    Observable<Void> errorRetryClick();

    Observable<RedditItem> listItemClicks();

    void setRedditItems(List<RedditItem> items);

    void setLoading(boolean loading);

    void showError();

    View getView();

}




