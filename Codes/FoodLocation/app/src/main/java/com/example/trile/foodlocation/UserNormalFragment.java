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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
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
public class UserNormalFragment extends Fragment {

    private LinearLayout editpersonal,aboutus, logout, change_password;
    private FirebaseAuth mAuth;
    private Dialog dialog;
    private Dialog dialogChangePass;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mData;

    public UserNormalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_normal_layout, container, false);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        editpersonal = (LinearLayout) view.findViewById(R.id.linear_edit_personal);
        aboutus = (LinearLayout) view.findViewById(R.id.linear_aboutus);
        logout = (LinearLayout) view.findViewById(R.id.linear_logout);
        change_password = (LinearLayout) view.findViewById(R.id.linear_change_passwork);

        // EDIT INFORAMTION USER NORMAL
        editpersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbout = new Intent(getContext(), UserAboutusActivity.class);
                startActivity(intentAbout);
            }
        });
        //
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
        //
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DIALOG CHANGE PASSWOD
                dialogChangePass = new Dialog(getContext());
                dialogChangePass.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialogChangePass.setContentView(R.layout.dialog_change_passwork);
                dialogChangePass.setCanceledOnTouchOutside(false); // NO LICK OUTSIDE DIALOG
                //
                if (mAuth.getCurrentUser() == null) {
                    mAuth.signOut();
                    dialog.show();
                } else {
                    // display dialog change password

                    dialogChangePass.show();
                    final EditText edtPass = (EditText) dialogChangePass.findViewById(R.id.edt_pass_change);
                    final EditText edtPassAgaint = (EditText) dialogChangePass.findViewById(R.id.edt_pass_change_againt);
                    Button btnChange = (Button) dialogChangePass.findViewById(R.id.btn_change_pass);
                    TextView tvCloseComment = dialogChangePass.findViewById(R.id.tvExitComment);
                    // Click button accept change password
                    btnChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (edtPass.getText().toString().trim().equalsIgnoreCase(edtPassAgaint.getText().toString().trim())) {
                                edtPassAgaint.setError("Nhập lại không khớp");
                            }else if (edtPass.getText().toString() == "" || edtPassAgaint.getText().toString() == "") {
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

        return view;
    }

}
