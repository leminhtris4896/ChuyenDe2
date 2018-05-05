package com.example.trile.foodlocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton rdbNormal;
    private RadioButton rdbBusiness;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.rdg_type);
        rdbNormal = (RadioButton) view.findViewById(R.id.rdb_normal);
        rdbBusiness = (RadioButton) view.findViewById(R.id.rdb_business);

        rdbNormal.setChecked(true); //Here the radio button is checked

        // Display tab resgister normal user
        NormalRegisterFragment fragmentUserNormal = new NormalRegisterFragment();
        android.support.v4.app.FragmentTransaction fragmentTransactionUser = getFragmentManager().beginTransaction();
        fragmentTransactionUser.replace(R.id.frame_type_login,fragmentUserNormal,"Fragment");
        fragmentTransactionUser.commit();
        //

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.rdb_normal:
                        NormalRegisterFragment fragmentUserNormal = new NormalRegisterFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionUser = getFragmentManager().beginTransaction();
                        fragmentTransactionUser.replace(R.id.frame_type_login,fragmentUserNormal,"Fragment");
                        fragmentTransactionUser.commit();
                        break;
                    case R.id.rdb_business:
                        BusinessRegisterFragment fragmentBusiness = new BusinessRegisterFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionBusiess = getFragmentManager().beginTransaction();
                        fragmentTransactionBusiess.replace(R.id.frame_type_login,fragmentBusiness,"Fragment");
                        fragmentTransactionBusiess.commit();
                        break;
                }
            }
        });

        return view;
    }
}
