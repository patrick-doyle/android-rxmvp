package com.twistedequations.rxmvp.screens.login.builder;

import com.twistedequations.rxmvp.screens.login.LoginActivity;

import dagger.Component;

@LoginScope
@Component(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
