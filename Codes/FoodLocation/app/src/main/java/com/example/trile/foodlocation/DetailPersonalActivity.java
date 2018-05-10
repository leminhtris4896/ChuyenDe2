package com.example.trile.foodlocation;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailPersonalActivity extends AppCompatActivity {

    private TextView tvUpdateInfor;
    private ImageView img_business_pagePersonal;
    private TextView tv_name_business_pagePersonal;
    private TextView tv_Vote_business_pagePersonal;
    private TextView tv_adress_business_pagePersonal;
    private TextView tv_phone_business_pagePersonal;
    private TextView tv_time_business_pagePersonal;
    DatabaseReference mData;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_personal_layout);

        tvUpdateInfor = (TextView) findViewById(R.id.btnUpdateInfor);
        tvUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdate = new Intent(DetailPersonalActivity.this, UpdateInforActivity.class);
                startActivity(intentUpdate);
            }
        });

        Init();

        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                    Picasso.with(DetailPersonalActivity.this).load(mdBusiness.getStrImage()).into(img_business_pagePersonal);
                    tv_name_business_pagePersonal.setText(mdBusiness.getStrName());
                    tv_Vote_business_pagePersonal.setText(mdBusiness.getStrScoreRating());
                    tv_adress_business_pagePersonal.setText(mdBusiness.getStrAddress());
                    tv_time_business_pagePersonal.setText(mdBusiness.getStrOpenTime());
                    tv_phone_business_pagePersonal.setText(mdBusiness.getStrPhone());
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

    public void Init() {
        img_business_pagePersonal = (ImageView) findViewById(R.id.img_business_pagePersonal);
        tv_name_business_pagePersonal = (TextView) findViewById(R.id.tv_name_business_pagePersonal);
        tv_Vote_business_pagePersonal = (TextView) findViewById(R.id.tv_vote_business_pagePersonal);
        tv_time_business_pagePersonal = (TextView) findViewById(R.id.tv_time_business_pagePersonal);
        tv_phone_business_pagePersonal = (TextView) findViewById(R.id.tv_phone_business_pagePersonal);
        tv_adress_business_pagePersonal = (TextView) findViewById(R.id.tv_address_business_pagePersonal);
    }
}
