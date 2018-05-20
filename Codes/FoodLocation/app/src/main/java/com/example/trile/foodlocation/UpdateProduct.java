package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class UpdateProduct extends AppCompatActivity {
    private ImageView imgUpdateProduct;
    private EditText edtUpdateNameProduct;
    private EditText edtUpdateDescriptionProduct;
    private EditText edtUpdatePriceProduct;
    private Button btnUpdateProduct;
    private TextView tvClodeUpdateProduct;

    private DatabaseReference mData;

    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private LinearLayout linearOpenCamera;

    // ImageFireBase
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int REQUEST_CODE_IMAGE = 1;
    Uri uri;

    // Check chụp camera
    boolean CheckChup = false;

    // nhận lấy id của bài post chuyển sáng từ trang quản lí bài post
    String id_post_update ;

    // Check chụp camera or lấy ảnh từ thư viện
    boolean takePhoToFromCamera = false;
    boolean takePhoToFromLibrary = false;

    // Biến check xem khi update có phải là hình  mới hay hình cũ
    boolean CheckNewImageCamera = false;
    boolean CheckNewImageLibrary = false;

    private Dialog dialog;

    FirebaseAuth mAuth;

    String id_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product_layout);

        // biến cần thiết dùng firebase
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // khởi tạo các phần tử
        Init();

        // mở dialog cho chọn ảnh từ thư viện
        dialog = new Dialog(UpdateProduct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);

        imgUpdateProduct.setOnClickListener(new View.OnClickListener() {
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

        id_product = getIntent().getStringExtra("id_product");

        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if ( mdBusiness.getStrEmail().equalsIgnoreCase(mAuth.getCurrentUser().getEmail()))
                {
                    for ( int i = 0; i < mdBusiness.getArrayListProductList().size(); i++)
                    {
                        if ( mdBusiness.getArrayListProductList().get(i).getStrID().equalsIgnoreCase(id_product))
                        {
                            Picasso.with(UpdateProduct.this).load(mdBusiness.getArrayListProductList().get(i).getStrURLImage()).into(imgUpdateProduct);
                            edtUpdateNameProduct.setText(mdBusiness.getArrayListProductList().get(i).getStrProductName());
                            edtUpdateDescriptionProduct.setText(mdBusiness.getArrayListProductList().get(i).getStrDescription());
                            edtUpdatePriceProduct.setText(Integer.toString(mdBusiness.getArrayListProductList().get(i).getnPrice()));
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

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                imgUpdateProduct.setDrawingCacheEnabled(true);
                imgUpdateProduct.buildDrawingCache();
                Bitmap bitmap = imgUpdateProduct.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(UpdateProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        uri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UpdateProduct.this, "Thành công", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA",uri + "");

                        // Update Post
                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                                    for (int i = 0; i < mdBusiness.getArrayListProductList().size(); i++) {
                                        if (mdBusiness.getArrayListProductList().get(i).getStrID().equalsIgnoreCase(id_product) ) {
                                            if (CheckNewImageLibrary == true) {
                                                CheckNewImageLibrary = false;
                                                mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").child(i+"").child("strURLImage").setValue(uri+"");
                                            }
                                            if (CheckNewImageCamera == true) {
                                                CheckNewImageCamera = false;
                                                mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").child(i+"").child("strURLImage").setValue(uri+"");
                                            }
                                            mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").child(i+"").child("strProductName").setValue(edtUpdateNameProduct.getText().toString());
                                            mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").child(i+"").child("strDescription").setValue(edtUpdateDescriptionProduct.getText().toString());
                                            mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").child(i+"").child("nPrice").setValue(Integer.parseInt(edtUpdatePriceProduct.getText().toString()));

                                            edtUpdateDescriptionProduct.setText("");
                                            edtUpdateNameProduct.setText("");
                                            edtUpdatePriceProduct.setText("");
                                            imgUpdateProduct.setImageResource(R.mipmap.image);
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
                    }
                });
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
            imgUpdateProduct.setImageURI(imgUri);
            dialog.dismiss();
        }

        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckNewImageCamera = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUpdateProduct.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }

    public void Init() {
        imgUpdateProduct = (ImageView) findViewById(R.id.img_update_product);
        edtUpdateNameProduct = (EditText) findViewById(R.id.edt_product_update_name);
        edtUpdateDescriptionProduct = (EditText) findViewById(R.id.edt_product_description_update);
        edtUpdatePriceProduct = (EditText) findViewById(R.id.edt_price_product_update);
        btnUpdateProduct = (Button) findViewById(R.id.btn_update_product);
        tvClodeUpdateProduct = (TextView) findViewById(R.id.tvCloseUpdateProduct);
    }
}
