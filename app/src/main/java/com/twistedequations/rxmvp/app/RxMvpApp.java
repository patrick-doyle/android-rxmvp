package com.twistedequations.rxmvp.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Build;
import android.webkit.WebView;

import com.twistedequations.rxmvp.app.builder.DaggerRxMvpAppComponent;
import com.twistedequations.rxmvp.app.builder.RxMvpAppComponent;
import com.twistedequations.rxmvp.app.builder.RxMvpAppModule;

public class RxMvpApp extends Application {

    RxMvpAppComponent rxMvpAppComponent;

    public static RxMvpApp get(Activity activity) {
        return (RxMvpApp) activity.getApplication();
    }

    public static RxMvpApp get(Service service) {
        return (RxMvpApp) service.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    public RxMvpAppComponent component() {
        if(rxMvpAppComponent == null) {
            rxMvpAppComponent = DaggerRxMvpAppComponent.builder()
                    .rxMvpAppModule(new RxMvpAppModule(this))
                    .build();
        }
        return rxMvpAppComponent;
    }
}
