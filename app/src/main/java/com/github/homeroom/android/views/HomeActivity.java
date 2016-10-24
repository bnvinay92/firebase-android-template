package com.github.homeroom.android.views;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.homeroom.android.HomeRoom;
import com.github.homeroom.android.R;
import com.github.homeroom.android.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {


    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ((HomeRoom) getApplication()).component().inject(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}