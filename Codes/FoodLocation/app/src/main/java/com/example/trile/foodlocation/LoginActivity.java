package com.example.trile.foodlocation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.trile.foodlocation.Adapter.AdapterTabLogin;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    // ANIMATION BACKGROUND
    FrameLayout frameLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mAuth = FirebaseAuth.getInstance();

        frameLayout = (FrameLayout) findViewById(R.id.frame);
        animationDrawable = (AnimationDrawable)frameLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        mViewPager = (ViewPager) findViewById(R.id.viewPagerLogin);
        mViewPager.setAdapter(new AdapterTabLogin(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutLogin);
        tabLayout.setupWithViewPager(mViewPager);
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
