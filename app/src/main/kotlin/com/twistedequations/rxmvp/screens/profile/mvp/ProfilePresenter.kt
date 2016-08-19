package com.twistedequations.rxmvp.screens.profile.mvp

import com.twistedequations.mvl.rx.AndroidRxSchedulers
import com.twistedequations.rxmvp.reddit.models.UserInfo
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription

class ProfilePresenter(private val profileModel: ProfileModel,
                       private val profileView: ProfileView,
                       private val andoridRxSchedulers: AndroidRxSchedulers) {

    private val compositeSubscription = CompositeSubscription()

    fun onCreate() {
        compositeSubscription.add(getProfileSubscription())
        compositeSubscription.add(backButtonSubscription())
    }

    fun onDestroy() {
        compositeSubscription.clear()
    }

    private fun getProfileSubscription(): Subscription {

        //Inner function for getting the profile from the network
        fun getNetworkProfileUserInfo(username: String): Observable<UserInfo> {
            return Observable.fromCallable { profileView.setLoading(true) }
                    .observeOn(andoridRxSchedulers.network())
                    .flatMap { profileModel.getProfile(username) }
                    .observeOn(andoridRxSchedulers.mainThread())
                    .doOnEach { profileView.setLoading(false)  }
        }

        return Observable.fromCallable { profileModel.getUserNameFromIntent() }
                .flatMap { username ->
                    //Get userinfo from saved state and if that observable is empty then switch to the network
                    profileModel.getProfileFromState()
                            .switchIfEmpty(getNetworkProfileUserInfo(username))
                }
                .doOnNext { userInfo -> profileModel.saveUserInfoState(userInfo) } //save the userinfo
                .subscribe({ userInfo ->
                    profileView.setProfileData(userInfo)
                }, { throwable ->
                    profileView.showError(throwable)
                })
    }

    private fun backButtonSubscription() : Subscription {
        return profileView.observeBackClicks()
            .subscribe { profileModel.finish() }
    }

}