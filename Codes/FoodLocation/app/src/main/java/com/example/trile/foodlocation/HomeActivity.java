package com.example.trile.foodlocation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    DatabaseReference mData;
    FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mData = FirebaseDatabase.getInstance().getReference().child("Users");
            mAuth = FirebaseAuth.getInstance();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Select home page fragment
                    HomeFragment fragmentHome = new HomeFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragmentHome, "Fragment");
                    fragmentTransaction1.commit();

                    return true;
                case R.id.navigation_flace:

                    // Select list page fragment
                    PlaceFragment fragmentList = new PlaceFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragmentList, "Fragment");
                    fragmentTransaction2.commit();

                    return true;
                case R.id.navigation_area:

                    // Select area page fragment
                    AreaFragment fragmentArea = new AreaFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, fragmentArea, "Fragment");
                    fragmentTransaction3.commit();

                    return true;
                case R.id.navigation_user:

                    // Select user page fragment
                    mData.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                            if (mdUser.getUserMail().equalsIgnoreCase(mAuth.getCurrentUser().getEmail())) {
                                if (mdUser.getUserType().equalsIgnoreCase("business")) {
                                    UserFragment fragmentUser = new UserFragment();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction4.replace(R.id.content, fragmentUser, "Fragment");
                                    fragmentTransaction4.commit();
                                    Toast.makeText(HomeActivity.this, "business", Toast.LENGTH_SHORT).show();
                                } else if (mdUser.getUserType().equalsIgnoreCase("normal")) {
                                    BlankFragment fragmentUser = new BlankFragment();
                                    android.support.v4.app.FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction5.replace(R.id.content, fragmentUser, "Fragment");
                                    fragmentTransaction5.commit();
                                    Toast.makeText(HomeActivity.this, "normal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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
        fragmentTransaction1.replace(R.id.content, fragmentHome, "Fragment");
        fragmentTransaction1.commit();
    }

}
