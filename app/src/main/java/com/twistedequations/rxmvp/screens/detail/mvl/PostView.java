package com.twistedequations.rxmvp.screens.detail.mvl;

import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

public interface PostView {
    void setRedditItem(RedditItem redditItem);

    void setCommentList(List<RedditItem> comments);
}
