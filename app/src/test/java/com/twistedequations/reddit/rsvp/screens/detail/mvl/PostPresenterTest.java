package com.twistedequations.reddit.rsvp.screens.detail.mvl;

import com.google.gson.reflect.TypeToken;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.TestAndroidRxSchedulers;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.reddit.models.RedditListing;
import com.twistedequations.rxmvp.screens.detail.mvp.DefaultPostView;
import com.twistedequations.rxmvp.screens.detail.mvp.PostModel;
import com.twistedequations.rxmvp.screens.detail.mvp.PostPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import testUtils.ResorcesLoader;

import static org.mockito.Mockito.*;

public class PostPresenterTest {

    //Class to test
    private PostPresenter presenter;

    //To be mocked
    private PostModel postModel;
    private DefaultPostView postView;

    //TestAndroidSchedulers that force everything onto the current thread
    private AndroidRxSchedulers schedulers = new TestAndroidRxSchedulers();

    //Test data to be loaded from the resources folder
    private List<RedditListing> testPostJson;
    private RedditItem testRedditItem;

    @Before
    public void setUp() throws Exception {
        postModel = mock(PostModel.class);
        postView = mock(DefaultPostView.class);
        presenter = new PostPresenter(postView, postModel, schedulers);

        testPostJson = ResorcesLoader.loadResources(this, "/test_post.json", new TypeToken<List<RedditListing>>(){}.getType());
        testRedditItem = ResorcesLoader.loadResources(this, "/test_reddit_item.json", RedditItem.class);

        //Mock the model with blank observables by default
        when(postModel.getCommentsForPost(anyString(), anyString())).thenReturn(Observable.empty());
        when(postModel.getCommentsFromState()).thenReturn(Observable.empty());
        when(postModel.getIntentRedditItem()).thenReturn(testRedditItem);
    }

    @Test
    public void loadPostsFromNetworkWithNoRestoredStateTest() throws Exception {
        //When model can make a network call and there is no restored state

        //Mock the network call for the post
        when(postModel.getCommentsForPost(anyString(), anyString())).thenReturn(Observable.just(testPostJson));
        when(postModel.getCommentsFromState()).thenReturn(Observable.empty());

        //On create call
        presenter.onCreate();

        //Verify the postmodel methods were called and the data was set on model correctly
        verify(postModel).getCommentsForPost(testRedditItem.subreddit, testRedditItem.id);
        verify(postModel).getCommentsFromState();
        verify(postModel).getIntentRedditItem();

        List<RedditItem> redditItems = Observable.from(testPostJson.get(0).data.children)
                .map(child -> child.data)
                .toList()
                .toBlocking().first();

        List<RedditItem> comments = Observable.from(testPostJson.get(1).data.children)
                .map(child -> child.data)
                .toList()
                .toBlocking().first();
        //Verify the tets data was set on the view
        verify(postView).setRedditItem(redditItems.get(0));
        verify(postView).setCommentList(comments);

        presenter.onDestroy();
        verify(postModel).saveComentsState(comments);
    }

    @Test
    public void loadPostsFromNetworkWithRestoredStateTest() throws Exception {
        //When model can make a network call and there is restored state

        List<RedditItem> comments = Observable.from(testPostJson.get(1).data.children)
                .map(child -> child.data)
                .toList()
                .toBlocking().first();

        //Mock the network call for the post
        when(postModel.getCommentsForPost(anyString(), anyString())).thenReturn(Observable.just(testPostJson));
        when(postModel.getCommentsFromState()).thenReturn(Observable.just(comments));

        //On create call
        presenter.onCreate();

        //Verify the postmodel methods were called and the data was set on model correctly
        verify(postModel).getCommentsForPost(testRedditItem.subreddit, testRedditItem.id);
        verify(postModel).getCommentsFromState();
        verify(postModel).getIntentRedditItem();

        List<RedditItem> redditItems = Observable.from(testPostJson.get(0).data.children)
                .map(child -> child.data)
                .toList()
                .toBlocking().first();
        //Verify the tets data was set on the view
        verify(postView).setRedditItem(redditItems.get(0));
        verify(postView, times(2)).setCommentList(comments); // called twice, once for the state and once for the network

        presenter.onDestroy();
        verify(postModel, atLeastOnce()).saveComentsState(comments);
    }
}