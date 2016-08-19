package com.twistedequations.rxmvp.app

import android.app.Activity
import android.app.Application
import android.app.Service
import android.os.Build
import android.webkit.WebView

import com.twistedequations.rxmvp.app.builder.DaggerRxMvpAppComponent
import com.twistedequations.rxmvp.app.builder.RxMvpAppComponent
import com.twistedequations.rxmvp.app.builder.RxMvpAppModule

class RxMvpApp : Application() {

    companion object {

        @JvmStatic
        fun get(activity: Activity): RxMvpApp {
            return activity.application as RxMvpApp
        }

        @JvmStatic
        fun get(service: Service): RxMvpApp {
            return service.application as RxMvpApp
        }
    }

    private val rxMvpAppComponent: RxMvpAppComponent by lazy {
        DaggerRxMvpAppComponent.builder().rxMvpAppModule(RxMvpAppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    fun component(): RxMvpAppComponent {
        return rxMvpAppComponent
    }
}
