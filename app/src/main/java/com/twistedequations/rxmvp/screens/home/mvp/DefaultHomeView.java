package com.twistedequations.rxmvp.screens.home.mvp;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxrelay.PublishRelay;
import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.screens.home.HomeActivity;
import com.twistedequations.rxmvp.screens.home.mvp.view.PostListAdapter;

import java.util.List;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import rx.Observable;

public class DefaultHomeView extends FrameLayout implements HomeView {

    private final PostListAdapter postListAdapter;
    private final Toolbar toolbar;
    private final Lazy<AlertDialog> alertDialog;
    private final Lazy<ProgressDialog> progressDialog;
    private final PublishRelay<Void> errorRetryRelay = PublishRelay.create();
    private final Observable<MenuItem> menuClicksObs;

    public DefaultHomeView(HomeActivity homeActivity, Picasso picasso) {
        super(homeActivity);
        //Inflate the layout into the viewgroup
        inflate(getContext(), R.layout.activity_home, this);

        postListAdapter = new PostListAdapter(homeActivity, picasso);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.post_listview);
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity, LinearLayoutManager.VERTICAL, false));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        menuClicksObs = RxToolbar.itemClicks(toolbar).publish().autoConnect();

        //Using kotin lazy in java to create this at a later time
        progressDialog = LazyKt.lazy(() -> {
            ProgressDialog progressDialog = new ProgressDialog(homeActivity);
            progressDialog.setMessage("Loading");
            return progressDialog;
        });

        //Using kotin lazy in java to create this at a later time
        alertDialog = LazyKt.lazy(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity)
                    .setTitle("Error Loading reddit posts")
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                    .setNegativeButton("retry", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        errorRetryRelay.call(null);
                    });
            return builder.create();
        });

    } //View init stuff

    @Override
    public Observable<Void> refreshMenuClick() {
        return menuClicksObs.filter(menuItem -> menuItem.getItemId() == R.id.menu_refresh)
                .map(menuItem -> null);
    }

    @Override
    public Observable<Void> loginClick() {
        return menuClicksObs.filter(menuItem -> menuItem.getItemId() == R.id.menu_login)
                .map(menuItem -> null);
    }

    @Override
    public Observable<Void> errorRetryClick() {
        return errorRetryRelay;
    }

    @Override
    public Observable<RedditItem> listItemClicks() {
        return postListAdapter.observeClicks()
                .map(postListAdapter::getRedditItem);
    }

    @Override
    public Observable<String> postAuthorClick() {
        return postListAdapter.authorObserveClicks();
    }

    @Override
    public void setRedditItems(List<RedditItem> items) {
        postListAdapter.swapData(items);
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            progressDialog.getValue().show();
        } else {
            progressDialog.getValue().dismiss();
        }
    }

    @Override
    public void showError() {
        alertDialog.getValue().show();
        progressDialog.getValue().dismiss();
    }

    @Override
    public View getView() {
        return this; //Because android
    }
}
