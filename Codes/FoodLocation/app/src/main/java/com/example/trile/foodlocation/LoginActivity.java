package com.example.trile.foodlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.trile.foodlocation.Adapter.AdapterTabLogin;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mAuth = FirebaseAuth.getInstance();

        mViewPager = (ViewPager) findViewById(R.id.viewPagerLogin);
        mViewPager.setAdapter(new AdapterTabLogin(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutLogin);
        tabLayout.setupWithViewPager(mViewPager);
<<<<<<< HEAD
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
=======
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
>>>>>>> f470897c7982987f1a8b0e7654e816b314158fd5
            startActivity(intent);
        }
    }
}
