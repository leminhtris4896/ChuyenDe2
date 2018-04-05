package com.example.trile.foodlocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trile.foodlocation.Adapter.AdapterPlace;
import com.example.trile.foodlocation.Models.mdPlace;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFragment extends Fragment {

    private ArrayList<mdPlace> arrProductPlace;
    private AdapterPlace adapterPlace;
    private RecyclerView recyclePlace;

    public PlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        recyclePlace = (RecyclerView) view.findViewById(R.id.recyclerViewPlace);

        // RecyclerView 1
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclePlace.setHasFixedSize(true);
        recyclePlace.setLayoutManager(layoutManager);

        // Add Place
        arrProductPlace = new ArrayList<mdPlace>();
        arrProductPlace.add(new mdPlace(R.mipmap.img_cafe,"Cà phê TOGO","74/2/6 ,Linh Đông,Thủ Đức","07h00 - 22h00","5"));
        arrProductPlace.add(new mdPlace(R.mipmap.img_trasua,"Trà sữa ChaGo","74/2/6 ,Linh Đông,Thủ Đức","07h00 - 22h00","5"));
        arrProductPlace.add(new mdPlace(R.mipmap.img_lau,"Lẩu Giây","74/2/6 ,Linh Đông,Thủ Đức","07h00 - 22h00","4.5"));
        arrProductPlace.add(new mdPlace(R.mipmap.img_gongcha,"Gong Cha Garden","74/2/6 ,Linh Đông,Thủ Đức","07h00 - 22h00","4.5"));
        arrProductPlace.add(new mdPlace(R.mipmap.img_nuong,"Đồ nướng 199K","74/2/6 ,Linh Đông,Thủ Đức","07h00 - 22h00","4.5"));

        // Adapter of HomeProduct
        adapterPlace = new AdapterPlace(arrProductPlace,getContext());
        recyclePlace.setAdapter(adapterPlace);
        recyclePlace.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        return view;
    }

}
