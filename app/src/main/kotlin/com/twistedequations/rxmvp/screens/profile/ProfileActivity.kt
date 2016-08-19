package com.twistedequations.rxmvp.screens.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twistedequations.rxmvp.app.RxMvpApp
import com.twistedequations.rxmvp.screens.profile.dagger.DaggerProfileComponent
import com.twistedequations.rxmvp.screens.profile.dagger.ProfileModule
import com.twistedequations.rxmvp.screens.profile.mvp.ProfilePresenter
import com.twistedequations.rxmvp.screens.profile.mvp.ProfileView
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context, username : String) {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("username", username)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var profileView: ProfileView

    @Inject
    lateinit var profilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerProfileComponent.builder()
                .profileModule(ProfileModule(this))
                .rxMvpAppComponent(RxMvpApp.get(this).component())
                .build().inject(this)

        profilePresenter.onCreate()
        setContentView(profileView.getView())
    }

    override fun onDestroy() {
        super.onDestroy()
        profilePresenter.onDestroy()
    }

}
