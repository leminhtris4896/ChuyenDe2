package com.example.trile.foodlocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        mData = FirebaseDatabase.getInstance().getReference();

        recyclePlace = (RecyclerView) view.findViewById(R.id.recyclerViewPlace);
        spinnerPlace = (Spinner) view.findViewById(R.id.spinnerType);
        // Spinner
        spinnerPlace();
        // RecyclerView 1
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclePlace.setHasFixedSize(true);
        recyclePlace.setLayoutManager(layoutManager);

        // Add Place
        arrProductPlace = new ArrayList<mdBusiness>();
        loadData();
        /*arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_cafe.jpg?alt=media&token=3e0f0894-b78b-4109-a2a1-0882df2675b8","Napoli","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4.5","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_gongcha.jpg?alt=media&token=9a3209fa-c90b-4a0d-878d-43231b561628","Apola","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_lau.jpg?alt=media&token=529767db-3fcc-4ba3-9bd8-3cc79eedd3a7","Lẩu Giấy","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán ăn","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nuong.jpg?alt=media&token=2245dbdb-e671-49d0-b766-f6309c9a303c","Set Nướng","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán ăn","07h00 - 22h00","3.5","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_trasua.jpg?alt=media&token=b4dfc766-4840-4976-a65c-ab1f07e47d1a","Apache","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nhau1.JPG?alt=media&token=9cfb41fc-afa4-4343-a8cc-2c89b636ee94","O2 Quán","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nuong.jpg?alt=media&token=2245dbdb-e671-49d0-b766-f6309c9a303c","Khram 2e","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/banh-mi-tho-nhy-ki.jpg?alt=media&token=b52f8b98-fafb-4f6b-822c-adfd6af05538","Bánh Mỳ Thổ Nhỹ Kỳ Kebab Tiếp Cruise","097 744 66 86","309/93 Võ Văn Ngân, P. Linh Chiểu, Quận Thủ Đức","Quán ăn","09:00 - 21:00","4","",""));
        mData.child("Business").setValue(arrProductPlace);
        mData.child("Business").setValue(arrProductPlace);*/


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
                            arrProductPlace.add(new mdBusiness(business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getStrListProductList(),business.getStrListCommentList()));
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 4 && business.getStrBusinessType().equalsIgnoreCase("Quán ăn")){
                            arrProductPlace.add(new mdBusiness(business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getStrListProductList(),business.getStrListCommentList()));
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 3 && business.getStrBusinessType().equalsIgnoreCase("Quán nước")) {
                            arrProductPlace.add(new mdBusiness(business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getStrListProductList(),business.getStrListCommentList()));
                            adapterMenuPlace.notifyDataSetChanged();
                        }else if (posision == 5 && business.getStrBusinessType().equalsIgnoreCase("Quán nhậu")) {
                            arrProductPlace.add(new mdBusiness(business.getStrEmail(),business.getStrPass(),business.getStrImage(),business.getStrName(),business.getStrPhone(),business.getStrAddress(),business.getStrBusinessType(),business.getStrOpenTime(),business.getStrScoreRating(),business.getStrListProductList(),business.getStrListCommentList()));
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
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_add,"Gần bạn"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_drink,"Quán nước"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_food,"Quán ăn"));
        arrSpinnerType.add(new mdSpinnerPlace(R.drawable.spinner_bia,"Quán nhậu"));

        adapterSpinnerPlace = new AdapterSpinnerPlace(arrSpinnerType,getContext(),R.layout.customview_spinner_place);
        spinnerPlace.setAdapter(adapterSpinnerPlace);
    }



}
