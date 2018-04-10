package com.example.trile.foodlocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PostDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvCall;
    private ImageView postImage;
    private TextView postName;
    private TextView postContent;
    private TextView postAddress;

    Intent intent;
    Bundle bundle;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_layout);

        SupportMapFragment mapFragmentDetail = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMapDetail);
        mapFragmentDetail.getMapAsync(PostDetailActivity.this);


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
        iniṭ();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadDetailItemPost();
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

    public void iniṭ()
    {
        postImage = (ImageView) findViewById(R.id.imgPost);
        postName = (TextView) findViewById(R.id.tvPostName);
        postContent = (TextView) findViewById(R.id.tvPostContent);
        postAddress = (TextView) findViewById(R.id.tvPostAddress);
    }
    public void loadDetailItemPost() {
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        bundle.getString("detailPost");
        databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdPost mdPost = dataSnapshot.getValue(mdPost.class);
                if (mdPost.getNameProduct().equalsIgnoreCase(bundle.getString("detailPost"))) {
                    postName.setText(mdPost.getNameProduct());
                    postContent.setText(mdPost.getDescriptionProduct());
                    postAddress.setText(mdPost.getnNumberLike());
                    Picasso.with(PostDetailActivity.this).load(mdPost.getImgProduct()).into(postImage);
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
