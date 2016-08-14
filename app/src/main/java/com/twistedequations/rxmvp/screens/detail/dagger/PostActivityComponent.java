package com.twistedequations.rxmvp.screens.detail.dagger;


import com.twistedequations.rxmvp.screens.detail.PostActivity;
import com.twistedequations.rxmvp.app.builder.RxMvpAppComponent;

import dagger.Component;

@PostScope
@Component(modules = PostModule.class, dependencies = RxMvpAppComponent.class)
public interface PostActivityComponent {

    void inject(PostActivity postActivity);
}
