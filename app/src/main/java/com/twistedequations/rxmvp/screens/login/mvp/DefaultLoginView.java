package com.twistedequations.rxmvp.screens.login.mvp;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.twistedequations.rxmvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class DefaultLoginView extends FrameLayout implements LoginView {

    @BindView(R.id.username)
    EditText usernameText;

    @BindView(R.id.password)
    EditText passwordText;

    @BindView(R.id.login_button)
    Button loginButton;

    public DefaultLoginView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_login, this);
        ButterKnife.bind(this);
    }

    @Override
    public Observable<Void> loginObs() {
        return RxView.clicks(loginButton);
    }

    @Override
    public Observable<String> usernameTextObs() {
        return RxTextView.textChanges(usernameText)
                .map(CharSequence::toString);
    }

    @Override
    public Observable<String> passwordTextObs() {
        return RxTextView.textChanges(passwordText)
                .map(CharSequence::toString);
    }

    public View getView() {
        return this;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
