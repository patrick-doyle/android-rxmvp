package com.twistedequations.rxmvp.screens.detail.mvl.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {

  private final List<RedditItem> redditItems = new ArrayList<>(0);
  private final Context context;

  public CommentListAdapter(Context context) {
    this.context = context;
  }

  public RedditItem getRedditItem(int position) {
    return redditItems.get(position);
  }

  public void setRedditItems(List<RedditItem> redditItems) {
    this.redditItems.clear();
    this.redditItems.addAll(redditItems);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return redditItems.size();
  }

  @Override
  public RedditItem getItem(int i) {
    return redditItems.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ListItemCommentView commentView = null;
    if(view == null) {
      commentView = new ListItemCommentView(context);
    } else {
      commentView = (ListItemCommentView) view;
    }
    commentView.setRedditItem(redditItems.get(i));
    return commentView;
  }
}
