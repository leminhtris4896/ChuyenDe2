package com.example.trile.foodlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Adapter.AdapterProductList;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    ArrayList<mdProduct> productArrayList;
    AdapterProductList adapterProductList;
    private TextView tvCloselistProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewListProduct);

        productArrayList = new ArrayList<>();
        adapterProductList = new AdapterProductList(productArrayList,ListProductActivity.this);
        recyclerView.setAdapter(adapterProductList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        String businessName = getIntent().getStringExtra("name_business");
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if ( mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail()))
                {
                    for ( int i  = 0 ;  i < mdBusiness.getArrayListProductList().size(); i ++)
                    {
                        productArrayList.add(mdBusiness.getArrayListProductList().get(i));
                    }
                    adapterProductList.notifyDataSetChanged();
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

        tvCloselistProduct = findViewById(R.id.tvCloseListProduct);
        tvCloselistProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
