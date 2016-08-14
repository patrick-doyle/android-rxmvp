package com.twistedequations.rxmvp.screens.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twistedequations.rxmvp.app.RxMvpApp;
import com.twistedequations.rxmvp.screens.home.dagger.DaggerHomeActivityComponent;
import com.twistedequations.rxmvp.screens.home.dagger.HomeModule;
import com.twistedequations.rxmvp.screens.home.mvp.HomePresenter;
import com.twistedequations.rxmvp.screens.home.mvp.HomeView;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

  @Inject
  HomeView homeView;

  @Inject
  HomePresenter homePresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DaggerHomeActivityComponent.builder()
            .rxMvpAppComponent(RxMvpApp.get(this).component())
            .homeModule(new HomeModule(this))
            .build().inject(this);

    homePresenter.onCreate();
    setContentView(homeView.getView());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    homePresenter.onDestroy();
  }
}
