package com.twistedequations.rxmvp.screens.profile.mvp

import android.app.ProgressDialog
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding.support.v7.widget.navigationClicks
import com.twistedequations.rxmvp.R
import com.twistedequations.rxmvp.reddit.models.UserInfo
import com.twistedequations.rxmvp.screens.profile.ProfileActivity
import org.joda.time.format.DateTimeFormat

class DefaultProfileView : ProfileView {

    private val profileActivity: ProfileActivity
    private val rootView: FrameLayout
    private val progressDialog: ProgressDialog by lazy { createProgressDialog() } //Lazy dialog creation

    //Views
    @BindView(R.id.toolbar)
    lateinit internal var toolbar: Toolbar

    @BindView(R.id.username)
    lateinit internal var usernameText: TextView

    @BindView(R.id.user_is_friend)
    lateinit internal var isFiendText: TextView

    @BindView(R.id.user_created_utc)
    lateinit internal var userSince: TextView

    @BindView(R.id.user_link_karma)
    lateinit internal var userLinkKarma: TextView

    @BindView(R.id.user_comment_karma)
    lateinit internal var userCommentKarma: TextView

    @BindView(R.id.user_is_mod)
    lateinit internal var userIsMod: TextView

    @BindView(R.id.user_is_gold)
    lateinit internal var userIsGold: TextView

    constructor(profileActivity: ProfileActivity) {
        this.profileActivity = profileActivity
        rootView = FrameLayout(profileActivity)
        LayoutInflater.from(profileActivity).inflate(R.layout.activity_profile, rootView, true)
        ButterKnife.bind(this, rootView)
    }

    override fun setLoading(loading: Boolean) {
        if (loading) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    override fun setProfileData(userInfo: UserInfo) {
        val userCreatedDate = DateTimeFormat.mediumDate().print(userInfo.createdUtc * 1000)
        usernameText.text = userInfo.name
        isFiendText.text = profileActivity.getString(R.string.user_is_friend, userInfo.isFriend)
        userSince.text = profileActivity.getString(R.string.user_created_utc, userCreatedDate)
        userLinkKarma.text = profileActivity.getString(R.string.user_link_karma, userInfo.linkKarma)
        userCommentKarma.text = profileActivity.getString(R.string.user_comment_karma, userInfo.commentKarma)
        userIsMod.text = profileActivity.getString(R.string.user_is_mod, userInfo.isMod)
        userIsGold.text = profileActivity.getString(R.string.user_is_gold, userInfo.isGold)
    }

    override fun observeBackClicks() = toolbar.navigationClicks()

    override fun showError(throwable: Throwable) {
        AlertDialog.Builder(profileActivity)
                .setTitle("Error Loading reddit posts")
                .setPositiveButton(android.R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
    }

    override fun getView() = rootView

    private fun createProgressDialog(): ProgressDialog {
        val progressDialog = ProgressDialog(profileActivity)
        progressDialog.setMessage("Loading")
        return progressDialog
    }
}