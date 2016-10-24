package com.github.homeroom.android.views;

import android.os.Bundle;

import com.github.homeroom.android.HomeRoom;
import com.github.homeroom.android.R;
import com.github.homeroom.android.data.UserIdRepository;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import javax.inject.Inject;

public class OnboardingActivity extends IntroActivity {

    @Inject UserIdRepository userIdRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeRoom) getApplication()).component().inject(this);
        setFullscreen(true);
        addSlide(new SimpleSlide.Builder()
                .title("Example Onboarding Screen")
                .description("Example Value Proposition")
                .image(android.R.drawable.ic_menu_add)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .build());
    }

    @Override public void finish() {
        userIdRepository.userId().subscribe(SplashActivity::disable);
        HomeActivity.start(this);
        super.finish();
    }
}
