package com.twistedequations.rxmvp.screens.detail.mvp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

public class ListItemCommentView extends FrameLayout {

  public ListItemCommentView(Context context) {
    super(context);
    init();
  }

  public ListItemCommentView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ListItemCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ListItemCommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private TextView description;
  private TextView upVotes;

  private void init() {
    inflate(getContext(), R.layout.list_item_comment, this);
    description = (TextView) findViewById(R.id.post_description);
    upVotes = (TextView) findViewById(R.id.post_upvotes_count);
  }

  public void setRedditItem(RedditItem redditItem) {
    description.setText(redditItem.body);
    upVotes.setText(String.format("%s",redditItem.score));
  }
}
