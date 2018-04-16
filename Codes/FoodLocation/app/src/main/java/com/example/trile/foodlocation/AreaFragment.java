package com.example.trile.foodlocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends Fragment implements OnMapReadyCallback {


    public AreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(10.850789, 106.758846);
        //LatLng sydney = new LatLng(-33.852, 151.211);

        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Nhóm 3")
                .snippet("Trường Cao Đẳng Công Nghệ Thủ Đức"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }
}
