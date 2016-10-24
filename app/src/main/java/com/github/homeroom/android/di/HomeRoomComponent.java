package com.github.homeroom.android.di;


import com.github.homeroom.android.views.HomeActivity;
import com.github.homeroom.android.views.OnboardingActivity;
import com.github.homeroom.android.views.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vinay on 29/09/16.
 */
@Singleton
@Component(modules = {HomeRoomModule.class})
public interface HomeRoomComponent {
    void inject(HomeActivity target);
    void inject(OnboardingActivity target);
    void inject(SplashActivity target);
}
