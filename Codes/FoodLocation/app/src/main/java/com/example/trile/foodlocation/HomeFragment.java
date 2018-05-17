package com.example.trile.foodlocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trile.foodlocation.Adapter.AdapterPlace;
import com.example.trile.foodlocation.Adapter.AdapterPost;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdProduct;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    // Array of AdapterPlace
    private ArrayList<mdBusiness> arrBusiness;
    private AdapterPlace adapterBusiness;
    private RecyclerView recyclerBusiness;
    // Array of HomeProduct
    private ArrayList<mdPost> arrPost;
    private AdapterPost adapterPost;
    private RecyclerView recyclerPost;
    // Firebase
    DatabaseReference mData;


    public HomeFragment() {
        // Required empty public constructor
    }

    private int position = 0;

    private void scrollByTime() {
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                position++;
                if (position >= arrBusiness.size()) {
                    position = 0;
                }
                recyclerBusiness.smoothScrollToPosition(position);
                handler.postDelayed(this, 3000);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mData = FirebaseDatabase.getInstance().getReference();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        recyclerBusiness = (RecyclerView) view.findViewById(R.id.recyclerView_Business);
        recyclerPost = (RecyclerView) view.findViewById(R.id.recyclerView_Post);


        // Create new data

        // món ăn mẫu trong arraylist

/*
        // Create new user Business Saigon - Vieux Coffee
        String key = mData.child("Business").push().getKey();
        final String newProductKey = mData.child("Business").child(key).child("arrayListProductList").push().getKey();
        mdProduct mdProduct = new mdProduct("Há Cảo", "Món ăn từ Tàu, ngon miệng", 20000, "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/basic.png?alt=media&token=3d9e613b-fa54-4183-9e05-bde397b82024",newProductKey);
        ArrayList<mdProduct> productArrayList = new ArrayList<>();
        productArrayList.add(mdProduct);
        mdBusiness mdBusiness = new mdBusiness(key,"saigonvieuxcoffee@gmail.com","saigonvieuxcoffee","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/saigon_vieuxcoffee.jpg?alt=media&token=14d42bad-6b7d-4a5e-8a0e-b5e5f9c7caa8","Saigon - Vieux Coffee","098 910 46 33","Tầng 2,  Tầng 2, Chung Cư 42 Nguyễn Huệ,  Quận 1, TP. HCM","Quán nước","08:00 - 23:00","0.0",productArrayList,"",0.0,0.0,0.0);
        mData.child("Business").child(key).setValue(mdBusiness);
        // Create new user Business Boo Coffee 1 - Lầu 9 Chung Cư Nguyễn Huệ
        String key1 = mData.child("Business").push().getKey();
        final String newProductKey1 = mData.child("Business").child(key1).child("arrayListProductList").push().getKey();
        mdProduct mdProduct1 = new mdProduct("Há Cảo", "Món ăn từ Tàu, ngon miệng", 20000, "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/basic.png?alt=media&token=3d9e613b-fa54-4183-9e05-bde397b82024",newProductKey1);
        ArrayList<mdProduct> productArrayList1 = new ArrayList<>();
        productArrayList.add(mdProduct1);
        mdBusiness mdBusiness1 = new mdBusiness(key1,"boocoffee1@gmail.com","boocoffee1","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/boocoffee.jpg?alt=media&token=ebe90ea0-e856-438d-b3cd-f14251635648","Boo Coffee 1 - Lầu 9 Chung Cư Nguyễn Huệ","090 198 42 98","Lầu 9, Chung Cư 42 Nguyễn Huệ,  Quận 1, TP. HCM","Quán nước","08:00 - 22:00","0.0",productArrayList1,"",0.0,0.0,0.0);
        mData.child("Business").child(key1).setValue(mdBusiness1);

        // create arrayCommentPost mẫu
        ArrayList<mdComment> mdComments = new ArrayList<>();
        mdComments.add(new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", "Chúng tôi luôn lắng nghe ý kiến từ khách hàng để có thể phục vụ một cách tốt nhất !"));

        //Create Saigon - Vieux Coffee post
        String keypost = mData.child("Post").push().getKey();
        mdPost mdPost = new mdPost(keypost,"Giới thiệu : Saigon - Vieux Coffee","Không gian thoáng và thoải mái. Có thể ngắm phố đi bộ từ trên cao. Phục vụ cafe và nhiều loại nước giải khát hấp dẫn.","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/saigon_vieuxcoffee_post.jpg?alt=media&token=5f08f478-a772-4984-8abb-7bb6bf7d33b1","0","0","0","Saigon - Vieux Coffee",mdComments);
        mData.child("Post").child(keypost).setValue(mdPost);

        //Create Boo Coffee 1 - Lầu 9 Chung Cư Nguyễn Huệ post
        String keypost1 = mData.child("Post").push().getKey();
        mdPost mdPost1 = new mdPost(keypost1,"Giới thiệu : Boo Coffee 1 - Lầu 9 Chung Cư Nguyễn Huệ","Phố đi bộ, địa điểm được khá nhiều người ưa chuộng. Tuy nhiên, để có thể thoải mái tụ tập bạn bè, vừa được hòa mình vào không khí rộn ràng của phố đi bộ, vừa được ngắm toàn cảnh Sài Gòn lý thú, bạn phải tìm đến Boo Coffee.","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/boocoffee_post.jpg?alt=media&token=0776d3c1-d77d-4336-9727-325aae323aac","0","0","0","Boo Coffee 1 - Lầu 9 Chung Cư Nguyễn Huệ",mdComments);
        mData.child("Post").child(keypost1).setValue(mdPost1);*/

        // RecyclerView 1
        CustomLinearLayout layoutManagertop = new CustomLinearLayout(getActivity(), 500);
        layoutManagertop.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerBusiness.setHasFixedSize(true);
        recyclerBusiness.setLayoutManager(layoutManagertop);

        // RecyclerView 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerPost.setHasFixedSize(true);
        recyclerPost.setLayoutManager(layoutManager);


        // Array of HomeProduct
        arrPost = new ArrayList<mdPost>();

        // Adapter of AdapterPlace
        arrBusiness = new ArrayList<mdBusiness>();
        adapterBusiness = new AdapterPlace(arrBusiness, getContext());
        recyclerBusiness.setAdapter(adapterBusiness);
        recyclerBusiness.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                arrBusiness.add(new mdBusiness(mdBusiness.getStrID(),mdBusiness.getStrEmail(), mdBusiness.getStrPass(), mdBusiness.getStrImage(), mdBusiness.getStrName(), mdBusiness.getStrPhone(), mdBusiness.getStrAddress(), mdBusiness.getStrBusinessType(), mdBusiness.getStrOpenTime(), mdBusiness.getStrScoreRating(), mdBusiness.getArrayListProductList(), mdBusiness.getStrListCommentList(),mdBusiness.getDbLatitude(),mdBusiness.getDbLongitude(),mdBusiness.getnNumberRate()));
                adapterBusiness.notifyDataSetChanged();
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

        // Adapter of HomeProduct
        arrPost = new ArrayList<mdPost>();
        adapterPost = new AdapterPost(arrPost, getContext());
        recyclerPost.setAdapter(adapterPost);
        recyclerPost.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        mData.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdPost mdPost = dataSnapshot.getValue(mdPost.class);
                arrPost.add(new mdPost(mdPost.getPostID(), mdPost.getNameProduct(), mdPost.getDescriptionProduct(), mdPost.getImgProduct(), mdPost.getnNumberLike(), mdPost.getnNumberUnlike(), mdPost.getnNumberComment(),mdPost.getLienKetDiaDiem(), mdPost.getArrayListCommentPost()));
                adapterPost.notifyDataSetChanged();
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

        recyclerBusiness.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerBusiness, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent myIntent = new Intent(getActivity().getBaseContext(), BusinessDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detailBusiness", arrBusiness.get(position).getStrID());
                myIntent.putExtra("bundle", bundle);
                getActivity().startActivity(myIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }
        ));

        scrollByTime();

        return view;
    }


}
