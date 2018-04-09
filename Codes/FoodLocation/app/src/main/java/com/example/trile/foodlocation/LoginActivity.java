package com.example.trile.foodlocation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.trile.foodlocation.Adapter.AdapterTabLogin;

public class LoginActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewPagerLogin);
        mViewPager.setAdapter(new AdapterTabLogin(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutLogin);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
