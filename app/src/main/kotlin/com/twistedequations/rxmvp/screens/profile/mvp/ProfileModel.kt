package com.twistedequations.rxmvp.screens.profile.mvp

import com.twistedequations.rxmvp.reddit.RedditService
import com.twistedequations.rxmvp.reddit.models.UserInfo
import com.twistedequations.rxmvp.screens.profile.ProfileActivity
import com.twistedequations.rxstate.RxSaveState
import rx.Observable

class ProfileModel(private val profileActivity: ProfileActivity,
                   private val redditService: RedditService) {

    fun getUserNameFromIntent() : String {
        val username: String? = profileActivity.intent.getStringExtra("username")
        return username ?: throw IllegalArgumentException("intent username cant be null")
    }

    fun getProfile(username : String) : Observable<UserInfo> {
        return redditService.userInfo(username)
        .map { userInfoData -> userInfoData.data }
    }

    fun getProfileFromState() : Observable<UserInfo> {
        return RxSaveState.getSavedState(profileActivity)
        .map { bundle -> bundle.getParcelable<UserInfo>("UserInfo") }
    }

    fun saveUserInfoState(userInfo: UserInfo) {
        RxSaveState.updateSaveState(profileActivity, { bundle ->
            bundle.putParcelable("UserInfo", userInfo)
        })
    }

    fun finish() {
        profileActivity.finish()
    }
}