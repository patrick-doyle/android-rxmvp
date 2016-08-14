package com.twistedequations.reddit.rsvp.screens.home.mvl;

import com.twistedequations.mvl.rx.AndroidSchedulers;
import com.twistedequations.mvl.rx.TestAndroidSchedulers;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.reddit.models.RedditListing;
import com.twistedequations.rxmvp.screens.home.mvp.HomeModel;
import com.twistedequations.rxmvp.screens.home.mvp.HomePresenter;
import com.twistedequations.rxmvp.screens.home.mvp.HomeView;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import testUtils.ResorcesLoader;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {

    //Class to test
    private HomePresenter presenter;

    //To be mocked
    private HomeModel model;
    private HomeView view;

    //TestAndroidSchedulers that force everything onto the current thread
    private AndroidSchedulers schedulers = new TestAndroidSchedulers();

    //Test data to be loaded from the resources folder
    private RedditListing testPostJson;

    @Before
    public void setUp() throws Exception {
        model = mock(HomeModel.class);
        view = mock(HomeView.class);
        presenter = new HomePresenter(view, model, schedulers);

        testPostJson = ResorcesLoader.loadResources(this, "/test_post_listing.json", RedditListing.class);

        //Mock the model with blank observables by default
        when(model.getSavedRedditListing()).thenReturn(Observable.empty());
        when(model.postsForAll()).thenReturn(Observable.empty());

        //Mock the view with never observables to simulate no clicks,
        // empty causes an on complete event which can cause problems
        when(view.errorRetryClick()).thenReturn(Observable.never());
        when(view.listItemClicks()).thenReturn(Observable.never());
        when(view.refreshMenuClick()).thenReturn(Observable.never());
    }

    @Test
    public void loadPostsFromNetworkWithNoRestoredStateTest() throws Exception {
        //When model can make a network call and there is no restored state

        //Mock the network call for the post
        when(model.getSavedRedditListing()).thenReturn(Observable.empty());
        when(model.postsForAll()).thenReturn(Observable.just(testPostJson));
        when(model.saveRedditListing(any())).thenReturn(testPostJson);
        //Call on create
        presenter.onCreate();

        //Verify the postmodel methods were called and the data was set on model correctly
        verify(model).getSavedRedditListing();
        verify(model).postsForAll();

        List<RedditItem> redditPosts = Observable.from(testPostJson.data.children)
                .map(child -> child.data).filter(redditItem -> !redditItem.over18)
                .toList().toBlocking().first();

        //Verify the tets data was set on the view
        verify(view).setRedditItems(redditPosts);
        verify(view, times(2)).setLoading(anyBoolean());
        verify(model).saveRedditListing(testPostJson);

        presenter.onDestroy();
    }

    @Test
    public void loadPostsFromNetworkWithRestoredStateTest() throws Exception {
        //When model can make a network call and there is no restored state

        //Mock the network call for the post
        when(model.getSavedRedditListing()).thenReturn(Observable.just(testPostJson));
        when(model.postsForAll()).thenReturn(Observable.just(testPostJson));
        when(model.saveRedditListing(any())).thenReturn(testPostJson);
        //Call on create
        presenter.onCreate();

        //Verify the postmodel methods were called and the data was set on model correctly
        verify(model).getSavedRedditListing();
        verify(model).postsForAll();

        List<RedditItem> redditPosts = Observable.from(testPostJson.data.children)
                .map(child -> child.data).filter(redditItem -> !redditItem.over18)
                .toList().toBlocking().first();

        //Verify the tets data was set on the view
        verify(view, times(2)).setRedditItems(redditPosts);
        verify(view, times(3)).setLoading(anyBoolean()); // two events emitted from the merge
        verify(model, times(1)).saveRedditListing(testPostJson);

        presenter.onDestroy();
    }

    @Test
    public void loadPostsFromNetworkWithRestoredStateAndViewClicksTest() throws Exception {
        //When model can make a network call and there is no restored state

        //Mock the network call for the post
        when(model.getSavedRedditListing()).thenReturn(Observable.just(testPostJson));
        when(model.postsForAll()).thenReturn(Observable.just(testPostJson));
        when(model.saveRedditListing(any())).thenReturn(testPostJson);
        when(view.errorRetryClick()).thenReturn(Observable.just(null));
        when(view.refreshMenuClick()).thenReturn(Observable.just(null));
        //Call on create
        presenter.onCreate();

        //Verify the postmodel methods were called and the data was set on model correctly
        verify(model, times(3)).getSavedRedditListing();
        verify(model, times(3)).postsForAll();

        List<RedditItem> redditPosts = Observable.from(testPostJson.data.children)
                .map(child -> child.data).filter(redditItem -> !redditItem.over18)
                .toList().toBlocking().first();

        //Verify the tets data was set on the view
        verify(view, times(6)).setRedditItems(redditPosts);
        verify(view, times(9)).setLoading(anyBoolean()); // two events emitted from the merge
        verify(model, times(3)).saveRedditListing(testPostJson);

        presenter.onDestroy();
    }

    @Test
    public void listItemClickStartDetailActivityTest() throws Exception {
        //When model can make a network call and there is no restored state

        //Mock the network call for the post
        when(view.listItemClicks()).thenReturn(Observable.just(testPostJson.data.children.get(0).data));
        //Call on create
        presenter.onCreate();

        //Verify activity was started
        verify(model).startDetailActivity(testPostJson.data.children.get(0).data);

        presenter.onDestroy();
    }

}