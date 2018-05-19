package com.example.trile.foodlocation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.trile.foodlocation.Adapter.AdapterMenuPlace;
import com.example.trile.foodlocation.Adapter.AdapterSpinnerPlace;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdSpinnerPlace;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFragment extends Fragment {

    private ArrayList<mdSpinnerPlace> arrSpinnerType;
    private ArrayList<mdBusiness> arrProductPlace;
    private AdapterMenuPlace adapterMenuPlace;
    private RecyclerView recyclePlace;
    private Spinner spinnerPlace;
    private AdapterSpinnerPlace adapterSpinnerPlace;
    private DatabaseReference mData;
    AdapterMenuPlace adapterBussine = null;
    ArrayList<mdBusiness> searchFoodsArrayList = new ArrayList<mdBusiness>();
    //
    int posision;

    public PlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mData = FirebaseDatabase.getInstance().getReference();
        setHasOptionsMenu(true);
        recyclePlace = (RecyclerView) view.findViewById(R.id.recyclerViewPlace);
        spinnerPlace = (Spinner) view.findViewById(R.id.spinnerType);
        // Spinner
        spinnerPlace();
        // RecyclerView 1
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        layoutManager.removeAllViews();
        recyclePlace.setHasFixedSize(true);
        recyclePlace.setLayoutManager(layoutManager);



        // Add Place
        arrProductPlace = new ArrayList<mdBusiness>();
        loadData();
        /*String key = mData.child("Business").push().getKey();
        mdBusiness mdBusiness = new mdBusiness(key+"","","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_cafe.jpg?alt=media&token=3e0f0894-b78b-4109-a2a1-0882df2675b8","Napoli","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4.5","","",10.8373155,106.7449431,0.0);
        mData.child("Business").child(key).setValue(mdBusiness);

        String key1 = mData.child("Business").push().getKey();
        mdBusiness mdBusiness1 = new mdBusiness(key1+"","","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_gongcha.jpg?alt=media&token=9a3209fa-c90b-4a0d-878d-43231b561628","Apola","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","","",10.8917252,106.7249331,0.0);
        mData.child("Business").child(key1).setValue(mdBusiness1);

        String key2 = mData.child("Business").push().getKey();
        mdBusiness mdBusiness2 = new mdBusiness(key2+"","","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_lau.jpg?alt=media&token=529767db-3fcc-4ba3-9bd8-3cc79eedd3a7","Lẩu Giấy","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán ăn","07h00 - 22h00","4","","",10.89393766,106.72371001,0.0);
        mData.child("Business").child(key2).setValue(mdBusiness2);*/

       return view;
    }



    private void loadData() {
        adapterMenuPlace = new AdapterMenuPlace(arrProductPlace,getContext());
        recyclePlace.setAdapter(adapterMenuPlace);
        recyclePlace.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                arrProductPlace.clear();
                posision = i;
                mData.child("Business").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdBusiness business = dataSnapshot.getValue(mdBusiness.class);

                        if (posision == 0){
                            arrProductPlace.add(new mdBusiness(business.getStrID(),business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getArrayListProductList(),business.getStrListCommentList(),business.getDbLatitude(),business.getDbLongitude(),business.getnNumberRate()));
                            recyclePlace.getRecycledViewPool().clear();
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 3 && business.getStrBusinessType().equalsIgnoreCase("Quán ăn")){
                            arrProductPlace.add(new mdBusiness(business.getStrID(),business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getArrayListProductList(),business.getStrListCommentList(),business.getDbLatitude(),business.getDbLongitude(),business.getnNumberRate()));
                            recyclePlace.getRecycledViewPool().clear();
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 2 && business.getStrBusinessType().equalsIgnoreCase("Quán nước")) {
                            arrProductPlace.add(new mdBusiness(business.getStrID(),business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getArrayListProductList(),business.getStrListCommentList(),business.getDbLatitude(),business.getDbLongitude(),business.getnNumberRate()));
                            recyclePlace.getRecycledViewPool().clear();
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 4 && business.getStrBusinessType().equalsIgnoreCase("Quán nhậu")) {
                            arrProductPlace.add(new mdBusiness(business.getStrID(),business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getArrayListProductList(),business.getStrListCommentList(),business.getDbLatitude(),business.getDbLongitude(),business.getnNumberRate()));
                            recyclePlace.getRecycledViewPool().clear();
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 1 && Double.parseDouble(business.getStrScoreRating()) >= 4.0) {
                            arrProductPlace.add(new mdBusiness(business.getStrID(),business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getArrayListProductList(),business.getStrListCommentList(),business.getDbLatitude(),business.getDbLongitude(),business.getnNumberRate()));
                            recyclePlace.getRecycledViewPool().clear();
                            adapterMenuPlace.notifyDataSetChanged();
                        }
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

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerPlace() {
        arrSpinnerType = new ArrayList<mdSpinnerPlace>();
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_all,"Tất cả"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_vote,"Top vote"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_drink,"Quán nước"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_food,"Quán ăn"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_bia,"Quán nhậu"));

        adapterSpinnerPlace = new AdapterSpinnerPlace(arrSpinnerType,getContext(),R.layout.customview_spinner_place);
        spinnerPlace.setAdapter(adapterSpinnerPlace);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase(Locale.getDefault());
                searchFoodsArrayList.clear();
                if ( s.length() == 0)
                {
                    searchFoodsArrayList.addAll(arrProductPlace);
                }
                else
                {
                    for ( mdBusiness mdBusiness : arrProductPlace)
                    {
                        if ( mdBusiness.getStrName().toLowerCase(Locale.getDefault()).contains(s) )
                        {
                            searchFoodsArrayList.add(mdBusiness);
                        }
                    }
                }
                adapterBussine = new AdapterMenuPlace(searchFoodsArrayList, getContext());
                recyclePlace.setAdapter(adapterBussine);
                //lvPostAdapter.filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
