package com.twistedequations.rxmvp.screens.login.builder;

import android.app.Activity;

import com.twistedequations.rxmvp.screens.login.mvp.DefaultLoginView;
import com.twistedequations.rxmvp.screens.login.mvp.LoginModel;
import com.twistedequations.rxmvp.screens.login.mvp.LoginPresenter;
import com.twistedequations.rxmvp.screens.login.mvp.LoginView;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final Activity activity;

    public LoginModule(Activity activity) {
        this.activity = activity;
    }

    @LoginScope
    @Provides
    public LoginView view() {
        return new DefaultLoginView(activity);
    }

    @LoginScope
    @Provides
    public LoginModel model() {
        return new LoginModel(activity);
    }

    @LoginScope
    @Provides
    public LoginPresenter presenter(LoginView view, LoginModel model) {
        return new LoginPresenter(model, view);
    }
}
