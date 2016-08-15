package twistedequations.rxmvp.screens.home.mvp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.twistedequations.rxmvp.R;
import com.twistedequations.rxmvp.reddit.models.RedditItem;
import com.twistedequations.rxmvp.reddit.models.RedditListing;
import com.twistedequations.rxmvp.screens.home.HomeActivity;
import com.twistedequations.rxmvp.screens.home.mvp.DefaultHomeView;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import twistedequations.rxmvp.testUtils.ResorcesLoader;

@RunWith(AndroidJUnit4.class)
public class DefaultHomeViewTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    DefaultHomeView defaultHomeView;
    private List<RedditItem> testPosts;

    @Before
    public void setUp() throws Exception {
        HomeActivity activity = activityRule.getActivity();
        Picasso picasso = Picasso.with(activity);
        defaultHomeView = new DefaultHomeView(activity, picasso);

        RedditListing testPostJson = ResorcesLoader.loadResources(this, "/test_post_listing.json", RedditListing.class);
        testPosts = Observable.from(testPostJson.data.children)
                .map(child -> child.data).filter(redditItem -> !redditItem.over18)
                .toList().toBlocking().first();
    }

    @Test
    public void testSettingRedditDataCount() throws Throwable {
        activityRule.runOnUiThread(() -> {
            defaultHomeView.setRedditItems(testPosts);
            onView(withId(R.id.post_listview)).check(new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException noViewFoundException) {
                    RecyclerView recyclerView = (RecyclerView) view;
                    int count = recyclerView.getAdapter().getItemCount();
                    Assert.assertEquals(testPosts.size(), count);
                }
            });
        });

    }

    @After
    public void tearDown() throws Exception {

    }
}