package com.twistedequations.rxmvp.screens.detail.mvp;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.screens.detail.PostActivity;
import com.twistedequations.rxmvp.screens.detail.mvp.view.CommentListAdapter;
import com.twistedequations.rxmvp.screens.detail.mvp.view.RedditPostView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class DefaultPostView implements PostView {

    private final Picasso picasso;
    private RedditPostView postView;
    private CommentListAdapter commentListAdapter;
    private View view;

    @BindView(R.id.comments_list_view)
    ListView listView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public DefaultPostView(PostActivity context, Picasso picasso) {
        this.picasso = picasso;
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view = LayoutInflater.from(context).inflate(R.layout.activity_post, frameLayout, true);

        ButterKnife.bind(this, view);
        postView = new RedditPostView(context);
        commentListAdapter = new CommentListAdapter(context);
        listView.addHeaderView(postView, null, false);
        listView.setAdapter(commentListAdapter);
    }

    @Override
    public void setRedditItem(RedditItem redditItem) {
        postView.setRedditItem(redditItem, picasso);
    }

    @Override
    public Observable<Void> observableUpClicks() {
        return RxToolbar.navigationClicks(toolbar);
    }

    @Override
    public void setCommentList(List<RedditItem> comments) {
        commentListAdapter.setRedditItems(comments);
    }

    public View getView() {
        return view;
    }
}
