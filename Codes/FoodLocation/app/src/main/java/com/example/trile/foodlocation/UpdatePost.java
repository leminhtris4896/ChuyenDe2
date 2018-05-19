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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.Models.mdUserStatusPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.ArrayList;
import java.util.Calendar;

public class UpdatePost extends AppCompatActivity {
    private ImageView imgUpdatePost;
    private EditText edtPostNameUpdate;
    private EditText edtPostDescriptionUpdate;
    private TextView tvDongPostUpdate;
    private Spinner spinnerBusinessUpdate;
    private Button btn_Add_Post_Update;
    private DatabaseReference mData;

    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private LinearLayout linearOpenCamera;

    // ImageFireBase
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int REQUEST_CODE_IMAGE = 1;
    Uri uri;

    // SpinnerBusiness
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    // nhận lấy id của bài post chuyển sáng từ trang quản lí bài post
    String id_post_update;

    // Check chụp camera or lấy ảnh từ thư viện
    boolean takePhoToFromCamera = false;
    boolean takePhoToFromLibrary = false;

    // Biến check xem khi update có phải là hình  mới hay hình cũ
    boolean CheckNewImageCamera = false;
    boolean CheckNewImageLibrary = false;

    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_post_layout);

        // biến cần thiết dùng firebase
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // khởi tạo các phần tử
        Init();

        // load tên các doanh nghiệp lên spinner.
        spinnerPlace();

        // mở dialog cho chọn ảnh từ thư viện
        dialog = new Dialog(UpdatePost.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);


        imgUpdatePost.setOnClickListener(new View.OnClickListener() {
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

        // load data của bài post cần update từ trang quản lí bài post lên ac
        loadDataBusineNeedUpdate();


        UpdatePostAction();

        tvDongPostUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void UpdatePostAction() {
        btn_Add_Post_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                imgUpdatePost.setDrawingCacheEnabled(true);
                imgUpdatePost.buildDrawingCache();
                Bitmap bitmap = imgUpdatePost.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(UpdatePost.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        uri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UpdatePost.this, "Thành công", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA",uri + "");

                        // Update Post
                        mData.child("Post").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                                if (mdPost.getPostID().equalsIgnoreCase(id_post_update)) {
                                    if (CheckNewImageLibrary == true) {
                                        CheckNewImageLibrary = false;
                                        mData.child("Post").child(mdPost.getPostID()).child("imgProduct").setValue(uri + "");
                                    }
                                    if (CheckNewImageCamera == true) {
                                        CheckNewImageCamera = false;
                                        mData.child("Post").child(mdPost.getPostID()).child("imgProduct").setValue(uri + "");
                                    }
                                    mData.child("Post").child(mdPost.getPostID()).child("descriptionProduct").setValue(edtPostDescriptionUpdate.getText().toString());
                                    mData.child("Post").child(mdPost.getPostID()).child("nameProduct").setValue(edtPostNameUpdate.getText().toString());
                                    mData.child("Post").child(mdPost.getPostID()).child("lienKetDiaDiem").setValue(spinnerBusinessUpdate.getSelectedItem().toString());
                                }
                                Toast.makeText(UpdatePost.this, "Cập nhật thành công ", Toast.LENGTH_SHORT).show();
                                finish();
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
            imgUpdatePost.setImageURI(imgUri);
            dialog.dismiss();
        }

        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckNewImageCamera = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUpdatePost.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }

    public void spinnerPlace() {
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(UpdatePost.this, android.R.layout.simple_spinner_item, arrayList);
        spinnerBusinessUpdate.setAdapter(arrayAdapter);
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                arrayList.add(mdBusiness.getStrName());
                arrayAdapter.notifyDataSetChanged();
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
        imgUpdatePost = (ImageView) findViewById(R.id.img_update_post);
        edtPostDescriptionUpdate = (EditText) findViewById(R.id.edt_update_description_post);
        edtPostNameUpdate = (EditText) findViewById(R.id.edt_update_name__post);
        spinnerBusinessUpdate = (Spinner) findViewById(R.id.spinner_update_Post);
        btn_Add_Post_Update = (Button) findViewById(R.id.btn_Update_Post);
        tvDongPostUpdate = (TextView) findViewById(R.id.tvCloseUpdatePost);
    }

    public void loadDataBusineNeedUpdate() {
        id_post_update = getIntent().getStringExtra("id_post_update");

        mData.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                if (mdPost.getPostID().equalsIgnoreCase(id_post_update)) {
                    Picasso.with(UpdatePost.this).load(mdPost.getImgProduct()).into(imgUpdatePost);
                    edtPostDescriptionUpdate.setText(mdPost.getDescriptionProduct());
                    edtPostNameUpdate.setText(mdPost.getNameProduct());
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).equalsIgnoreCase(mdPost.getLienKetDiaDiem()))
                            spinnerBusinessUpdate.setSelection(i);
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
}
