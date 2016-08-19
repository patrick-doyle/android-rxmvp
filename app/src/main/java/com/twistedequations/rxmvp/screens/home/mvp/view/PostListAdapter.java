package com.twistedequations.rxmvp.screens.home.mvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class PostListAdapter extends RecyclerView.Adapter<PostViewViewHolder> {

    private final List<RedditItem> redditItems = new ArrayList<>(0);
    private final Context context;
    private final Picasso picasso;
    private final ClicksSubscriber clicksSubscriber = new ClicksSubscriber();
    private final Observable<Integer> clicks = Observable.create(clicksSubscriber).publish().autoConnect();

    public PostListAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    @Override
    public PostViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewViewHolder(new ListItemViewPost(context), clicksSubscriber);
    }

    @Override
    public void onBindViewHolder(PostViewViewHolder holder, int position) {
        holder.setRedditItem(redditItems.get(position), picasso);
    }

    @Override
    public int getItemCount() {
        return redditItems.size();
    }

    public Observable<Integer> observeClicks() {
        return clicks;
    }

    public RedditItem getRedditItem(int position) {
        return redditItems.get(position);
    }

    public void swapData(List<RedditItem> redditItems) {
        this.redditItems.clear();
        this.redditItems.addAll(redditItems);
        notifyDataSetChanged();
    }

    private static class ClicksSubscriber implements Observable.OnSubscribe<Integer>, AdapterView.OnItemClickListener {

        private Subscriber<? super Integer> subscriber;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (subscriber != null && !subscriber.isUnsubscribed()) {
                subscriber.onNext(position);
            }
        }

        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
        }
    }
}
