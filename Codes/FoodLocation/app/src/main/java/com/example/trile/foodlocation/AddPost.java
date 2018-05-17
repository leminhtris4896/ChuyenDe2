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

import com.example.trile.foodlocation.Adapter.AdapterSpinerBusiness;
import com.example.trile.foodlocation.Adapter.AdapterSpinnerPlace;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdProduct;
import com.example.trile.foodlocation.Models.mdSpinnerBusiness;
import com.example.trile.foodlocation.Models.mdSpinnerPlace;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddPost extends AppCompatActivity {
    private ImageView imgPost;
    private EditText edtPostName;
    private EditText edtPostDescription;
    private TextView tvDongAddPost;
    private Spinner spinnerBusiness;
    private Button btn_Add_Post;
    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;
    private LinearLayout linearOpenCamera;
    private Dialog dialog;

    // arraylist status post new
    ArrayList<mdUserStatusPost> arrayListStatusPostNew;

    // SpinnerBusiness
    DatabaseReference mData;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

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
        setContentView(R.layout.add_post_layout);
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        Init();

        // mở dialog cho chọn ảnh từ thư viện
        dialog = new Dialog(AddPost.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);

        // nhấn vào ảnh để hiện dialog cho chọn ảnh từ thư viện hay chụp
        imgPost.setOnClickListener(new View.OnClickListener() {
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

        // lấy ảnh từ thư viện

        spinnerPlace();

        btn_Add_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                imgPost.setDrawingCacheEnabled(true);
                imgPost.buildDrawingCache();
                Bitmap bitmap = imgPost.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddPost.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        uri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(AddPost.this, "Thành công", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA",uri + "");

                        // ArrayList Comment new Post
                        ArrayList<mdComment> mdComments = new ArrayList<>();
                        mdComments.add(new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", "Chúng tôi luôn lắng nghe ý kiến từ khách hàng để có thể phục vụ một cách tốt nhất !"));

                        // ADD NEW POST
                        final String keyNewPost = mData.child("Post").push().getKey();
                        final mdPost mdPost = new mdPost(keyNewPost, edtPostName.getText().toString(), edtPostDescription.getText().toString(), uri + "", "0", "0", "0", spinnerBusiness.getSelectedItem().toString(), mdComments);
                        mData.child("Post").child(keyNewPost).setValue(mdPost);

                        // Clear layout resource

                        edtPostDescription.setText("");
                        edtPostName.setText("");
                        spinnerBusiness.setSelection(0);
                        imgPost.setImageResource(R.mipmap.image);


                        // ADD NEW POST TO ARRAYLIST STATUS POST IN EACH USER
                        arrayListStatusPostNew = new ArrayList<>();
                        mData.child("Users").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                arrayListStatusPostNew = mdUser.getArrayListUserStatusPost();
                                arrayListStatusPostNew.add(new mdUserStatusPost(keyNewPost, false, false));
                                mData.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").setValue(arrayListStatusPostNew);
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

        tvDongAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void spinnerPlace() {
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(AddPost.this, android.R.layout.simple_spinner_item, arrayList);
        spinnerBusiness.setAdapter(arrayAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            // DISPLAY IMAGE FROM LIBRARY
            CheckNewImageLibrary = true;
            Uri imgUri = data.getData();
            imgPost.setImageURI(imgUri);
            dialog.dismiss();
        }

        if (requestCode == REQUEST_CODE_IMAGE /*&& requestCode == RESULT_OK */ && data != null) {
            CheckNewImageCamera = true;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPost.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }

    public void Init() {
        imgPost = (ImageView) findViewById(R.id.img_add_post);
        edtPostDescription = (EditText) findViewById(R.id.edt_description_post);
        edtPostName = (EditText) findViewById(R.id.edt_name_post);
        spinnerBusiness = (Spinner) findViewById(R.id.spinner_Business);
        btn_Add_Post = (Button) findViewById(R.id.btn_Add_Post);
        tvDongAddPost = (TextView) findViewById(R.id.tvCloseAddPost);
    }
}
