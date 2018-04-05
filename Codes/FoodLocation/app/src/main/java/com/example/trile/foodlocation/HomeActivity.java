package com.example.trile.foodlocation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Select home page fragment
                    HomeFragment fragmentHome = new HomeFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content,fragmentHome,"Fragment");
                    fragmentTransaction1.commit();

                    return true;
                case R.id.navigation_flace:

                    // Select list page fragment
                    PlaceFragment fragmentList = new PlaceFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content,fragmentList,"Fragment");
                    fragmentTransaction2.commit();

                    return true;
                case R.id.navigation_area:

                    // Select area page fragment
                    AreaFragment fragmentArea = new AreaFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content,fragmentArea,"Fragment");
                    fragmentTransaction3.commit();

                    return true;
                case R.id.navigation_user:

                    // Select user page fragment
                    UserFragment fragmentUser = new UserFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content,fragmentUser,"Fragment");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Select home page fragment
        HomeFragment fragmentHome = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content,fragmentHome,"Fragment");
        fragmentTransaction1.commit();
    }

}
