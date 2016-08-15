package com.twistedequations.rxmvp.screens.login.mvp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.twistedequations.rxmvp.web.RxJavaWebInterface;

import rx.Observable;

public class DefaultLoginView extends FrameLayout implements LoginView {

    public DefaultLoginView(Context context) {
        super(context);
        init();
    }

    public DefaultLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private WebView webView;
    private RxJavaWebInterface usernameBridge = new RxJavaWebInterface();
    private RxJavaWebInterface passwordBridge = new RxJavaWebInterface();
    private RxJavaWebInterface loginButtonBridge = new RxJavaWebInterface();

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webView = new WebView(getContext());
        addView(webView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable Javascript.
        webSettings.setAllowFileAccessFromFileURLs(true); // Enable HTML Imports to access file://.
        webView.loadUrl("file:///android_asset/polymer/login.html");
        webView.addJavascriptInterface(usernameBridge, "UsernameBridge");
        webView.addJavascriptInterface(passwordBridge, "PasswordBridge");
        webView.addJavascriptInterface(loginButtonBridge, "LoginButtonBridge");
    }

    @Override
    public Observable<Void> loginObs() {
        return Observable.create(loginButtonBridge)
                .map(s -> null);
    }

    @Override
    public Observable<String> usernameTextObs() {
        return Observable.create(usernameBridge);
    }

    @Override
    public Observable<String> passwordTextObs() {
        return Observable.create(passwordBridge);
    }

    public View getView() {
        return this;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
