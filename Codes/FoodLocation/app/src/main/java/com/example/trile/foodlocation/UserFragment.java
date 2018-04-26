package com.example.trile.foodlocation;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    LinearLayout personal, manager, aboutus, logout, change_password;
    // Authencation
    private FirebaseAuth mAuth;
    private Dialog dialog;
    private Dialog dialogChangePass;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mData;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        personal = (LinearLayout) view.findViewById(R.id.linear_personal);
        manager = (LinearLayout) view.findViewById(R.id.linear_product_manager);
        aboutus = (LinearLayout) view.findViewById(R.id.linear_aboutus);
        logout = (LinearLayout) view.findViewById(R.id.linear_logout);
        change_password = (LinearLayout) view.findViewById(R.id.linear_change_passwork);

        // DIALOG REQUEST LOGIN
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_login_request);

        // DIALOG CHANGE PASSWOD
        dialogChangePass = new Dialog(getContext());
        dialogChangePass.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialogChangePass.setContentView(R.layout.dialog_change_passwork);
        dialogChangePass.setCanceledOnTouchOutside(false);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(getContext(), "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    if (mGoogleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                    } else {
                        mAuth.signOut();
                        Toast.makeText(getActivity(), "Đăng xuất tài khoản thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }

            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    mAuth.signOut();
                    dialog.show();
                } else {
                    Intent intentPersonal = new Intent(getContext(), DetailPersonalActivity.class);
                    startActivity(intentPersonal);
                }
            }
        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    mAuth.signOut();
                    dialog.show();
                } else {
                    Intent intentManager = new Intent(getContext(), ProductManagerActivity.class);
                    startActivity(intentManager);
                }
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    mAuth.signOut();
                    dialog.show();
                } else {
                    dialogChangePass.show();
                    final EditText edtPass = (EditText) dialogChangePass.findViewById(R.id.edt_pass_change);
                    final EditText edtPassAgaint = (EditText) dialogChangePass.findViewById(R.id.edt_pass_change_againt);
                    Button btnChange = (Button) dialogChangePass.findViewById(R.id.btn_change_pass);
                    btnChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (edtPass.getText().toString() == "" || edtPassAgaint.getText().toString() == "") {
                                Toast.makeText(getContext(), "Điền đầy đủ", Toast.LENGTH_SHORT).show();
                            }else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dialogChangePass.dismiss();
                                            Toast.makeText(getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        }
                    });
                }
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbout = new Intent(getContext(), UserAboutusActivity.class);
                startActivity(intentAbout);
            }
        });
        return view;
    }

    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}
