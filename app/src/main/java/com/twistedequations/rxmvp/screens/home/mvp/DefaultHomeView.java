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
import com.twistedequations.rxmvp.screens.home.HomeActivity;
import com.twistedequations.rxmvp.screens.home.mvp.view.PostListAdapter;
import com.twistedequations.rxmvp.reddit.models.RedditItem;

import java.util.List;

import rx.Observable;

public class DefaultHomeView extends FrameLayout implements HomeView {

    private final PostListAdapter postListAdapter;
    private final ProgressDialog progressDialog;
    private final Toolbar toolbar;
    private final AlertDialog alertDialog;
    private final PublishRelay<Void> errorRetryRelay = PublishRelay.create();
    private final Observable<MenuItem> menuClicksObs;

    public DefaultHomeView(HomeActivity homeActivity, Picasso picasso) {
        super(homeActivity);
        inflate(getContext(), R.layout.activity_home, this);

        postListAdapter = new PostListAdapter(homeActivity, picasso);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.post_listview);
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity, LinearLayoutManager.VERTICAL, false));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        menuClicksObs = RxToolbar.itemClicks(toolbar).publish().autoConnect();

        progressDialog = new ProgressDialog(homeActivity);
        progressDialog.setMessage("Loading");

        alertDialog = new AlertDialog.Builder(homeActivity)
                .setTitle("Error Loading reddit posts")
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    errorRetryRelay.call(null);
                })
                .create();
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
    public void setRedditItems(List<RedditItem> items) {
        postListAdapter.swapData(items);
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        alertDialog.show();
        progressDialog.dismiss();
    }

    @Override
    public View getView() {
        return this;
    }
}
