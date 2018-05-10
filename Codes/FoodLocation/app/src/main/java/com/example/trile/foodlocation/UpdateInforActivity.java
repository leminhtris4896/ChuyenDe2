package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class UpdateInforActivity extends AppCompatActivity {

    private TextView tvCloseUpdate;
    private ImageView img_choosen;
    //private TextView tvChoosen;
    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private TextView tvUpdateAdressBusiness;
    private TextView tvUpdateNameBusiness;
    private TextView tvUpdatePhoneBusiness;
    private TextView tvUpdateTimeBusiness;
    private Button btnUpdate;
    DatabaseReference mData;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_infor_layout);
        init();
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tvCloseUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent closeUpdate = new Intent(UpdateInforActivity.this, DetailPersonalActivity.class);
                startActivity(closeUpdate);
            }
        });
        final Dialog dialog = new Dialog(UpdateInforActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);



        img_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                tvCloseChoosenImg = (TextView) dialog.findViewById(R.id.tvCloseChoosenImg);
                linearOpenLibrary = (LinearLayout) dialog.findViewById(R.id.linearOpenLibrary);

                linearOpenLibrary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentLibrary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intentLibrary, 100);
                        dialog.dismiss();
                    }
                });


                tvCloseChoosenImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if ( mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail()))
                {
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Dialog dialogUpdate = new Dialog(UpdateInforActivity.this);
                            dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                            dialogUpdate.setContentView(R.layout.dialog_successfull);
                            mData.child("Business").child(mdBusiness.getStrID()).child("strName").setValue(tvUpdateNameBusiness.getText().toString());
                            mData.child("Business").child(mdBusiness.getStrID()).child("strAddress").setValue(tvUpdateAdressBusiness.getText().toString());
                            mData.child("Business").child(mdBusiness.getStrID()).child("strPhone").setValue(tvUpdatePhoneBusiness.getText().toString());
                            mData.child("Business").child(mdBusiness.getStrID()).child("strOpenTime").setValue(tvUpdateTimeBusiness.getText().toString());
                            dialogUpdate.show();
                            TextView ok_sc = (TextView) dialogUpdate.findViewById(R.id.ok_sc);
                            ok_sc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogUpdate.dismiss();
                                }
                            });
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            // DISPLAY IMAGE FROM LIBRARY
            Uri imgUri = data.getData();
            img_choosen.setImageURI(imgUri);
        }
    }

    private void init() {
        img_choosen = (ImageView) findViewById(R.id.img_choosen);
        tvCloseUpdate = (TextView) findViewById(R.id.tvCloseUpdate);
        tvUpdateNameBusiness = (TextView) findViewById(R.id.edt_business_name);
        tvUpdateAdressBusiness = (TextView) findViewById(R.id.edt_business_addess);
        tvUpdatePhoneBusiness = (TextView) findViewById(R.id.edt_business_phone);
        tvUpdateTimeBusiness = (TextView) findViewById(R.id.edt_time);
        btnUpdate = (Button) findViewById(R.id.btn_update);
    }
}
