package com.twistedequations.rxmvp.screens.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twistedequations.rxmvp.screens.login.builder.DaggerLoginComponent;
import com.twistedequations.rxmvp.screens.login.builder.LoginModule;
import com.twistedequations.rxmvp.screens.login.mvp.LoginPresenter;
import com.twistedequations.rxmvp.screens.login.mvp.LoginView;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginPresenter presenter;

    @Inject
    LoginView view;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this))
                .build().inject(this);

        setContentView(view.getView());
        presenter.onCreate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
