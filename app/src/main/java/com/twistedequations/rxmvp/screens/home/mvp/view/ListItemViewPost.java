package com.twistedequations.rxmvp.screens.home.mvp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListItemViewPost extends FrameLayout {

    public ListItemViewPost(Context context) {
        super(context);
        init();
    }

    public ListItemViewPost(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListItemViewPost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListItemViewPost(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @BindView(R.id.post_title)
    TextView title;

    @BindView(R.id.post_description)
    TextView description;

    @BindView(R.id.post_upvotes_count)
    TextView upVotes;

    @BindView(R.id.post_user)
    TextView postUser;

    @BindView(R.id.post_subreddit)
    TextView subreddit;

    @BindView(R.id.post_image)
    ImageView postImage;

    private void init() {
        inflate(getContext(), R.layout.list_item_post, this);
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        Drawable selectableItemBackground = ta.getDrawable(0);
        ta.recycle();

        setForeground(selectableItemBackground);
        ButterKnife.bind(this);
    }

    public void setOnAuthorClickListener(OnClickListener l) {
        postUser.setOnClickListener(l);
    }

    public void setRedditItem(RedditItem redditItem, Picasso picasso) {
        title.setText(redditItem.title);
        description.setText(redditItem.selftext);
        upVotes.setText(String.format("%s", redditItem.score));
        subreddit.setText(redditItem.subreddit);
        postUser.setText(redditItem.author);

        if (URLUtil.isValidUrl(redditItem.thumbnail)) {
            postImage.setVisibility(VISIBLE);
            picasso.load(redditItem.thumbnail).centerCrop()
                    .placeholder(R.drawable.ic_image).fit().into(postImage);
        } else {
            postImage.setVisibility(GONE);
        }
    }
}
