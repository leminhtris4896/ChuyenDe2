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
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.Models.mdUserStatusPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NormalRegisterFragment extends Fragment {

    private EditText edtMail, edtPass;
    private Button btnRegister;
    private ProgressDialog mProgress;
    // FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private DatabaseReference mDataUser;

    public NormalRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_register, container, false);
        // Init
        edtMail = (EditText) view.findViewById(R.id.edt_mail_login);
        edtPass = (EditText) view.findViewById(R.id.edt_pass_login);
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        //Get FireBasae
        mAuth = FirebaseAuth.getInstance();

        mDataUser = FirebaseDatabase.getInstance().getReference().child("Users");

        mData = FirebaseDatabase.getInstance().getReference();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Progressbar
                mProgress = new ProgressDialog(getContext());
                mProgress.setTitle("Đang tạo tài khoản");
                mProgress.setMessage("Xin vui lòng chờ");

                final String email = edtMail.getText().toString().trim();
                final String pass = edtPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtMail.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Không bỏ trống");
                    return;
                } else if (pass.length() < 10 || pass.length() > 30) {
                    edtPass.setError("Số kí tự không hợp lệ");
                    return;
                } else {
                    mProgress.show();
                    //Create user
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                mProgress.dismiss();
                                Toast.makeText(getContext(), "Tạo tài khoản thất bại",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();
                                final String userID = mAuth.getCurrentUser().getUid();
                                /*DatabaseReference current_user_id = mData.child(userID);
                                current_user_id.child("name").setValue(email);
                                current_user_id.child("password").setValue(pass);*/
                                final Date currentTime = Calendar.getInstance().getTime();
                                final ArrayList<String> arrayListListSuHoatDong = new ArrayList<>();
                                arrayListListSuHoatDong.add(email + " vừa mới đăng kí tài khoản - " + currentTime.toString());
                                final String idNewUser = mDataUser.push().getKey();
                                final ArrayList<String> arrayListKey = new ArrayList<>();
                                final ArrayList<mdUserStatusPost> arrayListUserStatusPost = new ArrayList<>();
                                mData.child("Post").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                                        arrayListKey.add(mdPost.getPostID());
                                        arrayListUserStatusPost.clear();
                                        for (int i = 0; i < arrayListKey.size();i++) {
                                            mdUserStatusPost mdUserStatusPost = new mdUserStatusPost(arrayListKey.get(i),false,false);
                                            arrayListUserStatusPost.add(mdUserStatusPost);
                                        }
                                        mdUser mdUserNew = new mdUser(email + "", pass + "", idNewUser+"", arrayListListSuHoatDong,arrayListUserStatusPost);
                                        mDataUser.child(idNewUser).setValue(mdUserNew);
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
            }
        });
        return view;
    }
}
