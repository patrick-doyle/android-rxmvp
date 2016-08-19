package com.twistedequations.rxmvp.screens.profile.dagger

import com.twistedequations.mvl.rx.AndroidRxSchedulers
import com.twistedequations.rxmvp.app.builder.RxMvpAppComponent
import com.twistedequations.rxmvp.reddit.RedditService
import com.twistedequations.rxmvp.screens.profile.ProfileActivity
import com.twistedequations.rxmvp.screens.profile.mvp.DefaultProfileView
import com.twistedequations.rxmvp.screens.profile.mvp.ProfileModel
import com.twistedequations.rxmvp.screens.profile.mvp.ProfilePresenter
import com.twistedequations.rxmvp.screens.profile.mvp.ProfileView
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class ProfileScope

@ProfileScope
@Component(modules = arrayOf(ProfileModule::class), dependencies = arrayOf(RxMvpAppComponent::class))
interface ProfileComponent {

    fun inject(profileActivity: ProfileActivity)
}

@Module
class ProfileModule(private val profileActivity: ProfileActivity)  {

    @Provides
    @ProfileScope
    fun profileView() : ProfileView = DefaultProfileView(profileActivity)

    @Provides
    @ProfileScope
    fun profilePresenter(profileView: ProfileView, profileModel: ProfileModel, androidRxSchedulers: AndroidRxSchedulers)
            = ProfilePresenter(profileModel, profileView, androidRxSchedulers)

    @Provides
    @ProfileScope
    fun profileModel(redditService: RedditService) = ProfileModel(profileActivity, redditService)
}
