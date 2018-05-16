package com.example.trile.foodlocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    // ImageFireBase
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int REQUEST_CODE_IMAGE = 1;
    Uri uri;

    // SpinnerBusiness
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    // Check chụp camera
    boolean CheckChup = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_post_layout);

        mData = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Init();

        spinnerPlace();

        imgUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        final String id_post_update = getIntent().getStringExtra("id_post_update");

        mData.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                if (mdPost.getPostID().equalsIgnoreCase(id_post_update)) {
                    Picasso.with(UpdatePost.this).load(mdPost.getImgProduct()).into(imgUpdatePost);
                    edtPostDescriptionUpdate.setText(mdPost.getDescriptionProduct());
                    edtPostNameUpdate.setText(mdPost.getNameProduct());
                   for ( int i = 0; i < arrayList.size();i++) {
                       if ( arrayList.get(i).equalsIgnoreCase(mdPost.getLienKetDiaDiem()))
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
                                    if (CheckChup == true)
                                    {
                                        CheckChup = false;
                                        mData.child("Post").child(mdPost.getPostID()).child("imgProduct").setValue(uri +"");
                                    }
                                    mData.child("Post").child(mdPost.getPostID()).child("descriptionProduct").setValue(edtPostDescriptionUpdate.getText().toString());
                                    mData.child("Post").child(mdPost.getPostID()).child("nameProduct").setValue(edtPostNameUpdate.getText().toString());
                                    mData.child("Post").child(mdPost.getPostID()).child("lienKetDiaDiem").setValue(spinnerBusinessUpdate.getSelectedItem().toString());
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
        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckChup = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUpdatePost.setImageBitmap(bitmap);
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
        tvDongPostUpdate = (TextView) findViewById(R.id.tvCloseUpdate);
    }
}
