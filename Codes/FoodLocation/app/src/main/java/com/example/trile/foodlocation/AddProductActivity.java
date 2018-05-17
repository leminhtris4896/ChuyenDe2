package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdProduct;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {

    private TextView tvCloseAddProduct;
    private ImageView img_choosen_product_add;
    private TextView tv_name_product_add;
    private TextView tv_price_product_add;
    private TextView tv_description_product_add;
    private Button btn_add_product;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference mData;
    FirebaseAuth mAuth;

    ArrayList<mdProduct> productArrayList ;

    int REQUEST_CODE_IMAGE = 1;

    Uri uri;

    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private LinearLayout linearOpenCamera;

    Dialog dialog;

    // Check chụp camera or lấy ảnh từ thư viện
    boolean takePhoToFromCamera = false;
    boolean takePhoToFromLibrary = false;

    // Biến check xem khi update có phải là hình  mới hay hình cũ
    boolean CheckNewImageCamera = false;
    boolean CheckNewImageLibrary = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_product_layout);

        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        mData = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        Init();

        // Khởi tạo dialog chọn hình
        dialog = new Dialog(AddProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);

        img_choosen_product_add.setOnClickListener(new View.OnClickListener() {
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

        tvCloseAddProduct = (TextView) findViewById(R.id.tvCloseAddProduct);
        tvCloseAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent closeAdd = new Intent(AddProductActivity.this, ProductManagerActivity.class);
                startActivity(closeAdd);
            }
        });

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                img_choosen_product_add.setDrawingCacheEnabled(true);
                img_choosen_product_add.buildDrawingCache();
                Bitmap bitmap = img_choosen_product_add.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddProductActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        uri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(AddProductActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
                        //Log.d("AAA",uri + "");
                        productArrayList = new ArrayList<>();
                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail()))
                                {
                                    productArrayList = mdBusiness.getArrayListProductList();
                                    final String newProductKey = mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").push().getKey();
                                    mdProduct newProduct = new mdProduct(tv_name_product_add.getText().toString(),tv_description_product_add.getText().toString(),Integer.parseInt(tv_price_product_add.getText().toString()),uri+"",newProductKey);
                                    productArrayList.add(newProduct);
                                    mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").setValue(productArrayList);

                                    tv_name_product_add.setText("");
                                    tv_description_product_add.setText("");
                                    tv_price_product_add.setText("");
                                    img_choosen_product_add.setImageResource(R.mipmap.image);

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
            img_choosen_product_add.setImageURI(imgUri);
            dialog.dismiss();
        }

        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckNewImageCamera = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_choosen_product_add.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }

    public void Init() {
        img_choosen_product_add = (ImageView) findViewById(R.id.img_choosen_product_add);
        tv_name_product_add = (TextView) findViewById(R.id.tv_name_product_add);
        tv_description_product_add = (TextView) findViewById(R.id.tv_description_product_add);
        tv_price_product_add = (TextView) findViewById(R.id.tv_price_product_add);
        btn_add_product = (Button) findViewById(R.id.btn_add_product);
    }
}
