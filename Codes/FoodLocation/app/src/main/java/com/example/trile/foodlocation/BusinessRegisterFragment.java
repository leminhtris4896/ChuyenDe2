package com.example.trile.foodlocation;


import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdProduct;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.Models.mdUserStatusPost;
import com.example.trile.foodlocation.Models.mdUserStatusRate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessRegisterFragment extends Fragment {

    private EditText edtMail, edtPass, edtName, edtPhone, edtTime, edtAddress;
    private Button btnRegister;
    private CheckBox cbxdrink,cbxpub,cbxfood;
    private ProgressDialog mProgress;
    // FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference root;
    private DatabaseReference mData;

    private String type = "";
    // MAP

    private List<Address> arr;

    public BusinessRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_register, container, false);
        // Init
        edtMail = (EditText) view.findViewById(R.id.edt_mail_business);
        edtPass = (EditText) view.findViewById(R.id.edt_pass_business);
        edtName = (EditText) view.findViewById(R.id.edt_business_name);
        edtPhone = (EditText) view.findViewById(R.id.edt_business_phone);
        edtAddress = (EditText) view.findViewById(R.id.edt_address);
        edtTime = (EditText) view.findViewById(R.id.edt_time);
        cbxfood = (CheckBox) view.findViewById(R.id.cbxFood);
        cbxdrink = (CheckBox) view.findViewById(R.id.cbxDrink);
        cbxpub = (CheckBox) view.findViewById(R.id.cbxPubbb);
        btnRegister = (Button) view.findViewById(R.id.btn_register_business);

        //Get FireBasae
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        root = FirebaseDatabase.getInstance().getReference().child("Users");
        //Progressbar
        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Đang tạo tài khoản");
        mProgress.setMessage("Xin vui lòng chờ");

        cbxdrink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    type = cbxdrink.getText().toString().trim();
                }
            }
        });

        cbxpub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    type = cbxpub.getText().toString().trim();
                }
            }
        });

        cbxfood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    type = cbxfood.getText().toString().trim();;
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = edtMail.getText().toString().trim();
                final String pass = edtPass.getText().toString().trim();
                final String name = edtName.getText().toString().trim();
                final String phones = edtPhone.getText().toString().trim();
                //final int phone = Integer.valueOf(phones);
                final String time = edtTime.getText().toString().trim();
                final String cbx1 = cbxfood.getText().toString().trim();
                final String cbx2 = cbxdrink.getText().toString().trim();
                final String cbx3 = cbxpub.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtMail.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Không bỏ trống");
                    return;
                } else if (pass.length() < 10 || pass.length() > 30) {
                    edtPass.setError("Số kí tự không hợp lệ");
                    return;
                } else if (TextUtils.isEmpty(name)) {
                    edtName.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(time)) {
                    edtTime.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(time)) {
                    edtAddress.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(cbx1) || TextUtils.isEmpty(cbx2) || TextUtils.isEmpty(cbx3)) {
                    Toast.makeText(getContext(), "Chọn loại hình kinh doanh", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mProgress.show();
                    //Create user
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                mProgress.dismiss();
                                Toast.makeText(getContext(), "Tài khoản đã tồn tại",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                mProgress.dismiss();
                                Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                final String userID = mAuth.getCurrentUser().getUid();
                                final Date currentTime = Calendar.getInstance().getTime();
                                final ArrayList<String> arrayListListSuHoatDong = new ArrayList<>();
                                arrayListListSuHoatDong.add("Doanh nghiệp " + name + " vừa mới đăng kí tài khoản - " + currentTime.toString());
                                //final String idNewUser = root.push().getKey();
                                final ArrayList<String> arrayListKeyPost = new ArrayList<>();
                                final ArrayList<String> arrayListKeyBusiness = new ArrayList<>();
                                final ArrayList<mdUserStatusPost> arrayListUserStatusPost = new ArrayList<>();
                                final ArrayList<mdUserStatusRate> arrayListUserStatusRate = new ArrayList<>();

                                // GET and SET coordinates from editext adress to firebase
                                String searchString = edtAddress.getText().toString();
                                Geocoder geocoder = new Geocoder(getContext());
                                List<Address> list = new ArrayList<>();
                                try {
                                    list = geocoder.getFromLocationName(searchString, 1);
                                } catch (Exception e) {
                                    Log.e(TAG, "Not found location" + e.getMessage());
                                }
                                // List >0 character
                                Address address = list.get(0);
                                Log.e(TAG, "Found a location : " + address.toString());
                                Log.e(TAG, "Lat : " + address.getLatitude());
                                Log.e(TAG, "Lng : " + address.getLongitude());
                                //Toast.makeText(this, "Lat : " + address.getLatitude() + "\n Lng : " + address.getLongitude() , Toast.LENGTH_SHORT).show();


                                final String newBusinessKey = mData.child("Business").push().getKey();
                                final String newProductKey = mData.child("Business").child(newBusinessKey).child("arrayListProductList").push().getKey();
                                mdProduct mdProduct = new mdProduct("Há Cảo", "Món ăn từ Tàu, ngon miệng", 20000, "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/basic.png?alt=media&token=3d9e613b-fa54-4183-9e05-bde397b82024",newProductKey);
                                ArrayList<mdProduct> productArrayList = new ArrayList<>();
                                productArrayList.add(mdProduct);
                                final mdBusiness mdBusiness = new mdBusiness(newBusinessKey, email, pass, "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/basic.png?alt=media&token=3d9e613b-fa54-4183-9e05-bde397b82024", name, phones, edtAddress.getText().toString() + "", type, time, "", productArrayList, "", address.getLatitude(), address.getLongitude(), 0.0);
                                mData.child("Business").child(newBusinessKey).setValue(mdBusiness);


                                mData.child("Post").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                                        arrayListKeyPost.add(mdPost.getPostID());
                                        arrayListUserStatusPost.clear();
                                        for (int i = 0; i < arrayListKeyPost.size(); i++) {
                                            mdUserStatusPost mdUserStatusPost = new mdUserStatusPost(arrayListKeyPost.get(i), false, false);
                                            arrayListUserStatusPost.add(mdUserStatusPost);
                                        }
                                        //arrayListUserStatusRate.clear();
                                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);


                                                if (arrayListUserStatusRate.size() == 0) {
                                                    mdUserStatusRate userStatusRate = new mdUserStatusRate(mdBusiness.getStrID(), "0", false);
                                                    arrayListUserStatusRate.add(userStatusRate);
                                                } else {
                                                    if (ktTrung(arrayListUserStatusRate, mdBusiness.getStrID()) == false) {
                                                        mdUserStatusRate userStatusRate = new mdUserStatusRate(mdBusiness.getStrID(), "0", false);
                                                        arrayListUserStatusRate.add(userStatusRate);
                                                    }
                                                }
                                                mdUser mdUserNew = new mdUser(email + "", pass + "", newBusinessKey + "", "business", arrayListListSuHoatDong, arrayListUserStatusPost, arrayListUserStatusRate);
                                                root.child(newBusinessKey).setValue(mdUserNew);
                                            }

                                            @Override
                                            public void onChildChanged(DataSnapshot
                                                                               dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                                            }

                                            @Override
                                            public void onChildMoved(DataSnapshot dataSnapshot, String
                                                    s) {

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
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
                    });
                }
                edtMail.setText("");
                edtPass.setText("");
                edtName.setText("");
                edtPhone.setText("");
                edtTime.setText("");
                cbxdrink.setChecked(false);
                cbxfood.setChecked(false);
                cbxpub.setChecked(false);

            }
        });

        return view;
    }

    public boolean ktTrung(ArrayList<mdUserStatusRate> strings, String chuoi) {
        boolean kt = false;
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).getStrIDBusiness().equalsIgnoreCase(chuoi)) {
                kt = true;
            }
        }
        return kt;
    }

}
