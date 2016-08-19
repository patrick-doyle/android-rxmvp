package com.twistedequations.rxmvp.screens.profile.mvp

import android.view.View
import com.twistedequations.rxmvp.reddit.models.UserInfo
import rx.Observable

interface ProfileView {

    fun getView() : View

    fun setLoading(loading: Boolean)

    fun setProfileData(userInfo: UserInfo)

    fun showError(throwable: Throwable)

    fun observeBackClicks(): Observable<Unit>
}