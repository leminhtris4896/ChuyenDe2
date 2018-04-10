package com.example.trile.foodlocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BusinessDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView BusinessImage;
    private TextView BusinessName;
    private TextView Rating;
    private TextView BusinessOpenTime;
    private TextView BusinessAddress;
    private TextView tvCall;
    Intent intent;
    Bundle bundle;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_detail_layout);
        Init();
        SupportMapFragment mapFragmentDetail = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMapDetail);
        mapFragmentDetail.getMapAsync(BusinessDetailActivity.this);
        tvCall = (TextView) findViewById(R.id.btn_phone);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse("tel:0978137250"));
                startActivity(intentCall);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadDetailItemBusiness();
    }

    public void Init() {
        BusinessImage = (ImageView) findViewById(R.id.businessImage);
        BusinessName = (TextView) findViewById(R.id.businessName);
        Rating = (TextView) findViewById(R.id.rating);
        BusinessOpenTime = (TextView) findViewById(R.id.businessOpenTime);
        BusinessAddress = (TextView) findViewById(R.id.businessDiaChi);
        tvCall = (TextView) findViewById(R.id.btn_phone);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(10.850789, 106.758846);
        //LatLng sydney = new LatLng(-33.852, 151.211);

        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Nhóm 3")
                .snippet("Trường Cao Đẳng Công Nghệ Thủ Đức"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }

    public void loadDetailItemBusiness() {
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        bundle.getString("detailBusiness");
        databaseReference.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                if (mdBusiness.getStrName().equalsIgnoreCase(bundle.getString("detailBusiness"))) {
                    BusinessName.setText(mdBusiness.getStrName());
                    BusinessAddress.setText(mdBusiness.getStrAddress());
                    Rating.setText(mdBusiness.getStrScoreRating());
                    BusinessOpenTime.setText(mdBusiness.getStrOpenTime());
                    Picasso.with(BusinessDetailActivity.this).load(mdBusiness.getStrImage()).into(BusinessImage);
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
}
