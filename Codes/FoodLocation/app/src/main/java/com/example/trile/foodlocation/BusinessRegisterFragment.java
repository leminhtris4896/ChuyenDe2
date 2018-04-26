package com.example.trile.foodlocation;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessRegisterFragment extends Fragment {

    private EditText edtMail, edtPass, edtName, edtPhone, edtTime;
    private Button btnRegister;
    private CheckBox cbxfood, cbxdrink, cbxpub;
    private ProgressDialog mProgress;
    // FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference root;
    private DatabaseReference mData;

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
        edtTime = (EditText) view.findViewById(R.id.edt_time);
        cbxfood = (CheckBox) view.findViewById(R.id.cbxFood);
        cbxdrink = (CheckBox) view.findViewById(R.id.cbxDrink);
        cbxpub = (CheckBox) view.findViewById(R.id.cbxPub);
        btnRegister = (Button) view.findViewById(R.id.btn_register_business);

        //Get FireBasae
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        root = FirebaseDatabase.getInstance().getReference().child("Users");
        //Progressbar
        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Đang tạo tài khoản");
        mProgress.setMessage("Xin vui lòng chờ");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtMail.getText().toString().trim();
                final String pass = edtPass.getText().toString().trim();
                final String name = edtName.getText().toString().trim();
                final String phones = edtPhone.getText().toString().trim();
                //final int phone = Integer.valueOf(phones);
                final String time = edtPass.getText().toString().trim();
                final String cbx1 = cbxfood.getText().toString().trim();
                final String cbx2 = cbxdrink.getText().toString().trim();
                final String cbx3 = cbxpub.getText().toString().trim();

                if (cbxfood.isChecked()) {

                }

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
                                final String idNewUser = root.push().getKey();
                                final ArrayList<String> arrayListKeyPost = new ArrayList<>();
                                final ArrayList<String> arrayListKeyBusiness = new ArrayList<>();
                                final ArrayList<mdUserStatusPost> arrayListUserStatusPost = new ArrayList<>();
                                final ArrayList<mdUserStatusRate> arrayListUserStatusRate = new ArrayList<>();
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
                                        arrayListUserStatusRate.clear();
                                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);

                                                arrayListKeyBusiness.add(mdBusiness.getStrID());

                                                if (arrayListUserStatusRate.size() == 0) {
                                                    mdUserStatusRate userStatusRate = new mdUserStatusRate(mdBusiness.getStrID(), "0");
                                                    arrayListUserStatusRate.add(userStatusRate);
                                                } else {
                                                    for (int i = 0; i < arrayListUserStatusRate.size(); i++) {
                                                        if (ktTrung(arrayListUserStatusRate, mdBusiness.getStrID()) == false) {
                                                            mdUserStatusRate userStatusRate = new mdUserStatusRate(mdBusiness.getStrID(), "0");
                                                            arrayListUserStatusRate.add(userStatusRate);
                                                        }
                                                    }
                                                }
                                                mdUser mdUserNew = new mdUser(email + "", pass + "", idNewUser + "", "business", arrayListListSuHoatDong, arrayListUserStatusPost, arrayListUserStatusRate);
                                                root.child(idNewUser).setValue(mdUserNew);
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
                                /*current_user_id.child("mail").setValue(email);
                                current_user_id.child("password").setValue(pass);
                                current_user_id.child("name").setValue(name);
                                current_user_id.child("phone").setValue(phones);
                                current_user_id.child("time").setValue(time);
                                if (cbxfood.isChecked()) {
                                    current_user_id.child("type").setValue(cbx1);
                                } else if (cbxdrink.isChecked()) {
                                    current_user_id.child("type").setValue(cbx2);
                                } else if (cbxpub.isChecked()) {
                                    current_user_id.child("type").setValue(cbx3);
                                }*/
                            }
                        }
                    });
                }
                edtMail.setText("");
                edtPass.setText("");
            }
        });

        return view;
    }

    public boolean ktTrung(ArrayList<mdUserStatusRate> strings, String chuoi) {
        boolean kt = false;
        for (int i = 0; i < strings.size(); i++) {
            if (chuoi.equalsIgnoreCase(strings.get(i).getStrIDBusiness())) {
                kt = true;
            }
        }
        return kt;
    }

}
