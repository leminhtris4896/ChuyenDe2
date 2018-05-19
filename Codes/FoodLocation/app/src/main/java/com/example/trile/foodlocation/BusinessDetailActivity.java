package com.example.trile.foodlocation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.Models.mdUserStatusRate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BusinessDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView BusinessImage;
    private TextView BusinessName;
    private TextView Rating;
    private TextView BusinessOpenTime;
    private TextView BusinessAddress;
    private TextView tvCall;
    public RatingBar ratingBar;
    private TextView tvNumberRating;
    private TextView tvVote;
    Intent intent;
    Bundle bundle;
    private TextView btnCloseDetailBusiness;

    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;

    Double NumberVote = 0.0;

    Double Vote = 0.0;

    Double TempVote = 0.0;

    ArrayList<mdUser> arrayListUser = new ArrayList<>();

    private TextView tvListProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_detail_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Init();

        btnCloseDetailBusiness = (TextView) findViewById(R.id.btnCloseDetailBusiness);

        btnCloseDetailBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadDetailItemBusiness();
        databaseReference.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                if (mdBusiness.getStrID().equalsIgnoreCase(bundle.getString("detailBusiness"))) {
                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                for (int i = 0; i < mdUser.getArrayListUserStatusRate().size(); i++) {
                                    if (mdBusiness.getStrID().equalsIgnoreCase(mdUser.getArrayListUserStatusRate().get(i).getStrIDBusiness())) {
                                        final mdUserStatusRate userStatusRate = mdUser.getArrayListUserStatusRate().get(i);
                                        final String idStatusRate = Integer.toString(i);
                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                tvNumberRating.setText(String.valueOf(ratingBar.getRating()));
                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusRate").child(idStatusRate).child("strStartRate").setValue(String.valueOf(ratingBar.getRating()));
                                                if (userStatusRate.getStatusRate() == false && Double.parseDouble(userStatusRate.getStrStartRate()) == 0.0) {
                                                    databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusRate").child(idStatusRate).child("statusRate").setValue(true);
                                                    databaseReference.child("Business").child(mdBusiness.getStrID()).child("nNumberRate").setValue(mdBusiness.getnNumberRate() + 1);
                                                }
                                            }
                                        });
                                    }
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


                    BusinessName.setText(mdBusiness.getStrName());
                    BusinessAddress.setText(mdBusiness.getStrAddress());
                    BusinessOpenTime.setText(mdBusiness.getStrOpenTime());
                    Picasso.with(BusinessDetailActivity.this).load(mdBusiness.getStrImage()).into(BusinessImage);
                    tvVote.setText(mdBusiness.getStrScoreRating());
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
        databaseReference.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                if (mdBusiness.getStrID().equalsIgnoreCase(bundle.getString("detailBusiness"))) {
                    NumberVote = mdBusiness.getnNumberRate();
                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                            if (arrayListUser.size() == 0) {
                                arrayListUser.add(mdUser);
                            } else {
                                if (ktTrung(arrayListUser, mdUser.getUserID()) == false) {
                                    arrayListUser.add(mdUser);
                                }
                            }
                            if (arrayListUser.size() != 0) {
                                Vote = 0.0;
                                TempVote = 0.0;
                                for (int j = 0; j < arrayListUser.size(); j++) {
                                    for (int i = 0; i < arrayListUser.get(j).getArrayListUserStatusRate().size(); i++) {
                                        mdUserStatusRate userStatusRate = arrayListUser.get(j).getArrayListUserStatusRate().get(i);
                                        if ((userStatusRate.getStrIDBusiness().equalsIgnoreCase(bundle.getString("detailBusiness"))) && ktTrung(arrayListUser, mdUser.getUserID()) == true) {
                                            TempVote = TempVote + Double.parseDouble(userStatusRate.getStrStartRate());
                                        }
                                    }
                                }
                                Vote = Double.valueOf(Math.round((TempVote / NumberVote) * 10) / 10);
                                databaseReference.child("Business").child(bundle.getString("detailBusiness")).child("strScoreRating").setValue(Vote.toString());
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

        SupportMapFragment mapFragmentDetail = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMapDetail);
        mapFragmentDetail.getMapAsync(BusinessDetailActivity.this);
        tvCall = (TextView) findViewById(R.id.btn_phone);
        final Dialog dialog = new Dialog(BusinessDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_callphone);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                TextView tvphone = (TextView) dialog.findViewById(R.id.ok_callphone);
                tvphone.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:0985134519"));
                        startActivity(callIntent);
                    }
                });
            }
        });

        tvListProduct = findViewById(R.id.tvListProduct);
        tvListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentList = new Intent(BusinessDetailActivity.this,ListProductActivity.class);
                intentList.putExtra("name_business",BusinessName.getText().toString());
                startActivity(intentList);
            }
        });

    }

    public void Init() {
        BusinessImage = (ImageView) findViewById(R.id.imgBusiness);
        BusinessName = (TextView) findViewById(R.id.tv_business_name);
        //Rating = (TextView) findViewById(R.id.rate_me);
        BusinessOpenTime = (TextView) findViewById(R.id.tv_workingtime);
        BusinessAddress = (TextView) findViewById(R.id.tvBusinessAddress);
        tvCall = (TextView) findViewById(R.id.btn_phone);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvNumberRating = (TextView) findViewById(R.id.numberRating);
        tvVote = (TextView) findViewById(R.id.tv_vote);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(10.850789, 106.758846);
        //LatLng sydney = new LatLng(-33.852, 151.211);

        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Nhóm 3")
                .snippet("Trường Cao Đẳng Công Nghệ Thủ Đức"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }

    public void loadDetailItemBusiness() {
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        bundle.getString("detailBusiness");
        databaseReference.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                if (mdBusiness.getStrID().equalsIgnoreCase(bundle.getString("detailBusiness"))) {
                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                for (int i = 0; i < mdUser.getArrayListUserStatusRate().size(); i++) {
                                    if (mdBusiness.getStrID().equalsIgnoreCase(mdUser.getArrayListUserStatusRate().get(i).getStrIDBusiness())) {
                                        ratingBar.setRating(Float.parseFloat(mdUser.getArrayListUserStatusRate().get(i).getStrStartRate()));
                                        ratingBar.setStepSize(0.1f);
                                        tvNumberRating.setText(mdUser.getArrayListUserStatusRate().get(i).getStrStartRate());
                                        tvVote.setText(mdBusiness.getStrScoreRating());
                                    }
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
                    BusinessName.setText(mdBusiness.getStrName());
                    BusinessAddress.setText(mdBusiness.getStrAddress());
                    BusinessOpenTime.setText(mdBusiness.getStrOpenTime());
                    Picasso.with(BusinessDetailActivity.this).load(mdBusiness.getStrImage()).into(BusinessImage);
                    tvVote.setText(mdBusiness.getStrScoreRating());
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

    public boolean ktTrung(ArrayList<mdUser> strings, String chuoi) {
        boolean kt = false;
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).getUserID().equalsIgnoreCase(chuoi)) {
                kt = true;
            }
        }
        return kt;
    }

}
