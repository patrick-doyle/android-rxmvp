package com.twistedequations.rxmvp.screens.detail.mvl;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.screens.detail.PostActivity;
import com.twistedequations.rxmvp.screens.detail.mvl.view.CommentListAdapter;
import com.twistedequations.rxmvp.screens.detail.mvl.view.RedditPostView;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

public class DefaultPostView extends FrameLayout implements PostView {

    private final Picasso picasso;
    private RedditPostView postView;
    private ListView listView;
    private CommentListAdapter commentListAdapter;

    public DefaultPostView(PostActivity context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
        inflate(context, R.layout.activity_post, this);

        postView = new RedditPostView(context);

        listView = (ListView) findViewById(R.id.comments_list_view);
        commentListAdapter = new CommentListAdapter(context);
        listView.addHeaderView(postView, null, false);
        listView.setAdapter(commentListAdapter);
    }

    @Override
    public void setRedditItem(RedditItem redditItem) {
        postView.setRedditItem(redditItem, picasso);
    }

    @Override
    public void setCommentList(List<RedditItem> comments) {
        commentListAdapter.setRedditItems(comments);
    }

    public View getView() {
        return this;
    }
}
