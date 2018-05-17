package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class UpdateInforActivity extends AppCompatActivity {

    private TextView tvCloseUpdate;
    private ImageView img_choosen;
    //private TextView tvChoosen;
    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private LinearLayout linearOpenCamera;
    private TextView tvUpdateAdressBusiness;
    private TextView tvUpdateNameBusiness;
    private TextView tvUpdatePhoneBusiness;
    private TextView tvUpdateTimeBusiness;
    private Button btnUpdate;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    Dialog dialog;

    // ImageFireBase
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int REQUEST_CODE_IMAGE = 1;
    Uri uri;

    // Check chụp camera or lấy ảnh từ thư viện
    boolean takePhoToFromCamera = false;
    boolean takePhoToFromLibrary = false;

    // Biến check xem khi update có phải là hình  mới hay hình cũ
    boolean CheckNewImageCamera = false;
    boolean CheckNewImageLibrary = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_infor_layout);

        // Khởi tạo các thành phần
        init();

        // biến cần thiết firebase
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // biến cần thiết dùng firebase
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // sự kiện đóng activity update
        tvCloseUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Khởi tạo dialog chọn hình
        dialog = new Dialog(UpdateInforActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);


        // sự kiện chọn hình
        img_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                tvCloseChoosenImg = (TextView) dialog.findViewById(R.id.tvCloseChoosenImg);
                linearOpenLibrary = (LinearLayout) dialog.findViewById(R.id.linearOpenLibrary);
                linearOpenCamera = (LinearLayout) dialog.findViewById(R.id.linearOpenCamera);


                // Chọn ảnh từ thư viện
                linearOpenLibrary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePhoToFromLibrary = true;
                        Intent intentLibrary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intentLibrary, 100);
                        dialog.dismiss();
                    }
                });

                // Chọn ảnh từ camera
                linearOpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePhoToFromCamera = true;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    }
                });

                // sự kiện đóng dialog chọn hình
                tvCloseChoosenImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        // load data business need update
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                    Picasso.with(UpdateInforActivity.this).load(mdBusiness.getStrImage()).into(img_choosen);
                    tvUpdateAdressBusiness.setText(mdBusiness.getStrAddress());
                    tvUpdateNameBusiness.setText(mdBusiness.getStrName());
                    tvUpdatePhoneBusiness.setText(mdBusiness.getStrPhone());
                    tvUpdateTimeBusiness.setText(mdBusiness.getStrOpenTime());
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                img_choosen.setDrawingCacheEnabled(true);
                img_choosen.buildDrawingCache();
                Bitmap bitmap = img_choosen.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(UpdateInforActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        uri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UpdateInforActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA",uri + "");
                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                                    if (CheckNewImageLibrary == true) {
                                        CheckNewImageLibrary = false;
                                        mData.child("Business").child(mdBusiness.getStrID()).child("strImage").setValue(uri + "");
                                    }
                                    if (CheckNewImageCamera == true) {
                                        CheckNewImageCamera = false;
                                        mData.child("Business").child(mdBusiness.getStrID()).child("strImage").setValue(uri + "");
                                    }
                                    mData.child("Business").child(mdBusiness.getStrID()).child("strAddress").setValue(tvUpdateAdressBusiness.getText().toString());
                                    mData.child("Business").child(mdBusiness.getStrID()).child("strName").setValue(tvUpdateNameBusiness.getText().toString());
                                    mData.child("Business").child(mdBusiness.getStrID()).child("strPhone").setValue(tvUpdatePhoneBusiness.getText().toString());
                                    mData.child("Business").child(mdBusiness.getStrID()).child("strOpenTime").setValue(tvUpdateTimeBusiness.getText().toString());
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
                });

            }
        });

    tvCloseUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            // DISPLAY IMAGE FROM LIBRARY
            CheckNewImageLibrary = true;
            Uri imgUri = data.getData();
            img_choosen.setImageURI(imgUri);
            dialog.dismiss();
        }

        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckNewImageCamera = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_choosen.setImageBitmap(bitmap);
            dialog.dismiss();
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
