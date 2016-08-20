package com.twistedequations.rxmvp.screens.detail.mvp;

import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;

public interface PostView {
    void setRedditItem(RedditItem redditItem);

    Observable<Void> observableUpClicks();

    void setCommentList(List<RedditItem> comments);
}
