package com.twistedequations.rxmvp.screens.login.mvp;


import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter {

    private static final String TAG = "LoginPresenter";
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void onCreate() {
        compositeSubscription.add(subscribeLoginButton());
        compositeSubscription.add(subscribeUsernameText());
        compositeSubscription.add(subscribePasswordText());
    }

    public void onDestroy() {
        compositeSubscription.clear();
    }

    private Subscription subscribeLoginButton() {
        return view.loginObs()
                .subscribe(aVoid -> view.showMessage("Login Web button clicked"));
    }

    private Subscription subscribeUsernameText() {
        return view.usernameTextObs()
                .subscribe(username -> {
                    Log.i(TAG, "Username web event " + username);
                });
    }

    private Subscription subscribePasswordText() {
        return view.passwordTextObs()
                .subscribe(password -> {
                    Log.i(TAG, "Password web event " + password);
                });
    }

}
