package com.twistedequations.rxmvp.screens.home.mvp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

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

    private TextView title;
    private TextView description;
    private TextView upVotes;
    private TextView subreddit;
    private ImageView postImage;

    private void init() {
        inflate(getContext(), R.layout.list_item_post, this);
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        Drawable selectableItemBackground = ta.getDrawable(0);
        ta.recycle();

        setForeground(selectableItemBackground);

        title = (TextView) findViewById(R.id.post_title);
        description = (TextView) findViewById(R.id.post_description);
        upVotes = (TextView) findViewById(R.id.post_upvotes_count);
        subreddit = (TextView) findViewById(R.id.post_subreddit);
        postImage = (ImageView) findViewById(R.id.post_image);
    }

    public void setRedditItem(RedditItem redditItem, Picasso picasso) {
        title.setText(redditItem.title);
        description.setText(redditItem.selftext);
        upVotes.setText(String.format("%s", redditItem.score));
        subreddit.setText(redditItem.subreddit);

        if (URLUtil.isValidUrl(redditItem.thumbnail)) {
            postImage.setVisibility(VISIBLE);
            picasso.load(redditItem.thumbnail).centerCrop()
                    .placeholder(R.drawable.ic_image).fit().into(postImage);
        } else {
            postImage.setVisibility(GONE);
        }
    }
}
