package com.github.homeroom.android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.homeroom.android.HomeRoom;
import com.github.homeroom.android.data.UserIdRepository;

import javax.inject.Inject;

import jonathanfinerty.once.Once;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

    public static final String ONBOARDING_TAG = "_onboarding";
    @Inject UserIdRepository userIdRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeRoom) getApplication()).component().inject(this);
        userIdRepository.userId()
                .map(this::getNavigationIntent)
                .doOnUnsubscribe(this::finish)
                .subscribe(this::startActivity,
                        throwable -> Timber.e(throwable, throwable.getMessage()));
    }

    private Intent getNavigationIntent(String userId) {
        Class activityClass = isOnboarded(userId) ? HomeActivity.class : OnboardingActivity.class;
        return new Intent(this, activityClass);
    }

    private boolean isOnboarded(String userId) {
        return Once.beenDone(Once.THIS_APP_INSTALL, userId + ONBOARDING_TAG);
    }

    public static void disable(String userId) {
        Once.markDone(userId + SplashActivity.ONBOARDING_TAG);
    }
}
