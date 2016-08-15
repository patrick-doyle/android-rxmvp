package com.twistedequations.rxmvp.screens.login.mvp;

import android.view.View;


import rx.Observable;

public interface LoginView {

    Observable<Void> loginObs();

    Observable<String> usernameTextObs();

    Observable<String> passwordTextObs();

    View getView();

    void showMessage(String message);
}
