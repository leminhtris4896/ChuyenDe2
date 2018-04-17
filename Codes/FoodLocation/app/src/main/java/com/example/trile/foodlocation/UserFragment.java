package com.example.trile.foodlocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    LinearLayout personal,manager,aboutus,logout;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        personal = (LinearLayout) view.findViewById(R.id.linear_personal);
        manager = (LinearLayout) view.findViewById(R.id.linear_product_manager);
        aboutus = (LinearLayout) view.findViewById(R.id.linear_aboutus);
        logout = (LinearLayout) view.findViewById(R.id.linear_logout);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonal = new Intent(getContext(),DetailPersonalActivity.class);
                startActivity(intentPersonal);
            }
        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentManager = new Intent(getContext(),ProductManagerActivity.class);
                startActivity(intentManager);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbout = new Intent(getContext(),UserAboutusActivity.class);
                startActivity(intentAbout);
            }
        });
        return view;
    }

}
