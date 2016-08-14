package com.twistedequations.rxmvp.screens.home.dagger;


import com.twistedequations.rxmvp.screens.home.HomeActivity;
import com.twistedequations.rxmvp.app.builder.RxMvpAppComponent;

import dagger.Component;

@HomeScope
@Component(modules = HomeModule.class, dependencies = RxMvpAppComponent.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);
}
