package com.twistedequations.rxmvp.screens.login.mvp;

import android.app.Activity;

public class LoginModel {

    private final Activity activity;

    public LoginModel(Activity activity) {
        this.activity = activity;
    }

    public void finish() {
        activity.finish();
    }
}
