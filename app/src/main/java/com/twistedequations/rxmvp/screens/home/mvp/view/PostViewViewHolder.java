package com.twistedequations.rxmvp.screens.home.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

public class PostViewViewHolder extends RecyclerView.ViewHolder {

  private final ListItemViewPost listItemViewPost;

  public PostViewViewHolder(ListItemViewPost listItemViewPost, AdapterView.OnItemClickListener itemClickListener) {
    super(listItemViewPost);
    listItemViewPost.setOnClickListener((view) -> itemClickListener.onItemClick(null, view, getAdapterPosition(), getItemId()));
    this.listItemViewPost = listItemViewPost;
  }

  public void setRedditItem(RedditItem redditItem, Picasso picasso) {
    listItemViewPost.setRedditItem(redditItem, picasso);
  }

}
