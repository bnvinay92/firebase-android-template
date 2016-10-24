package com.github.homeroom.android;

import android.app.Application;

import com.github.homeroom.android.di.DaggerHomeRoomComponent;
import com.github.homeroom.android.di.HomeRoomComponent;
import com.github.homeroom.android.di.HomeRoomModule;

import hu.supercluster.paperwork.Paperwork;
import jonathanfinerty.once.Once;
import timber.log.Timber;

/**
 * Created by Vinay on 29/09/16.
 */
public class HomeRoom extends Application {

    public static final String GIT_SHA = "GIT_SHA";
    public static final String BUILD_TIME = "BUILD_TIME";

    private HomeRoomComponent component;

    @Override public void onCreate() {
        super.onCreate();
        Paperwork paperwork = new Paperwork(this);
        String gitSha = paperwork.get(GIT_SHA);
        String buildTime = paperwork.get(BUILD_TIME);
        buildComponent();
        Once.initialise(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //TODO: Crash monitoring
        }
    }

    private void buildComponent() {
        component = DaggerHomeRoomComponent.builder()
                .homeRoomModule(new HomeRoomModule(this))
                .build();
    }

    public HomeRoomComponent component() {
        if (component == null) {
            buildComponent();
        }
        return component;
    }
}
