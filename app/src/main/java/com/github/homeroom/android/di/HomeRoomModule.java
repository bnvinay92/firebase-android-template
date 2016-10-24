package com.github.homeroom.android.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vinay on 29/09/16.
 */
@Module
public class HomeRoomModule {

    private final Application application;

    public HomeRoomModule(Application homeRoom) {
        application = homeRoom;
    }

    @Provides Application application() {
        return application;
    }

    @Provides Context context() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides DatabaseReference databaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
