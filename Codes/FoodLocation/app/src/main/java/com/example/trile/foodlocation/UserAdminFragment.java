package com.example.trile.foodlocation;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserAdminFragment extends Fragment {

    LinearLayout aboutus, logout, change_password;

    private GoogleApiClient mGoogleApiClient;

    private Dialog dialogChangePass;
    private TextView CloseDialogChangePass;

    private LinearLayout linearLayoutManagerPost;
    private Dialog dialog;
    private FirebaseAuth mAuth;

    public UserAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_admin, container, false);

        mAuth = FirebaseAuth.getInstance();

        linearLayoutManagerPost = (LinearLayout) view.findViewById(R.id.linear_post_manager);

        // DIALOG REQUEST LOGIN
                dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_login_request);
        dialog.setCanceledOnTouchOutside(false); // NO LICK OUTSIDE DIALOG

        aboutus = (LinearLayout) view.findViewById(R.id.linear_aboutus);
        logout = (LinearLayout) view.findViewById(R.id.linear_logout);
        change_password = (LinearLayout) view.findViewById(R.id.linear_change_passwork);

        // Click logout account
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
                    CloseDialogChangePass = (TextView) dialogChangePass.findViewById(R.id.tvCloseChangePass);
                    CloseDialogChangePass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogChangePass.dismiss();
                        }
                    });
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

        linearLayoutManagerPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    mAuth.signOut();
                    dialog.show();
                } else {
                    Intent intentAdmin = new Intent(getContext(), ManagerPost.class);
                    startActivity(intentAdmin);
                }
            }
        });

        // Click about us ( about group )
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
