package com.example.trile.foodlocation;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private Button btn_login;
    private EditText edtMail;
    private EditText edtPass;
    private TextView next_no_login;
    // FIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "MAIN__ACTIVITY";
    private static final int RC_SIGN_IN = 1;
    private ProgressDialog mProgress;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        edtMail = (EditText) view.findViewById(R.id.edt_mail_login);
        edtPass = (EditText) view.findViewById(R.id.edt_pass_login);

        //Get Firebase
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress = new ProgressDialog(getContext());
                mProgress.setTitle("Đang kết nối");
                mProgress.setMessage("Vui lòng chờ");

                final String email = edtMail.getText().toString();
                final String password = edtPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edtMail.setError("Không bỏ trống");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    edtPass.setError("Không bỏ trống");
                    return;
                } else {
                    mProgress.show();
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            if (password.length() < 10 || password.length() > 30) {
                                edtPass.setError("Tối thiểu 10 ký tự");
                                mProgress.dismiss();
                            } else {
                                mProgress.dismiss();
                                Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),HomeActivity.class));
                        }
                    }
                });

            }
        });

        edtPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtPass.getRight() - edtPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }
}
