package com.example.trile.foodlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trile.foodlocation.Adapter.AdapterProduct;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdProduct;
import com.example.trile.foodlocation.Models.mdUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProDuct;
    private TextView tvAddProduct;
    private ArrayList<mdProduct> mdProducts;
    AdapterProduct adapterProduct;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_product_manager_layout);

        mData = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerViewProDuct = (RecyclerView) findViewById(R.id.recyclerProductManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProDuct.setHasFixedSize(true);
        recyclerViewProDuct.setLayoutManager(layoutManager);


        mdProducts = new ArrayList<mdProduct>();
        adapterProduct = new AdapterProduct(mdProducts, this);
        recyclerViewProDuct.setAdapter(adapterProduct);
        mData.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                if (firebaseAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                    //mdProducts = mdBusiness.getArrayListProductList();
                    for (int i = 0; i < mdBusiness.getArrayListProductList().size(); i++) {
                        mdProduct mdProduct = new mdProduct(mdBusiness.getArrayListProductList().get(i).getStrProductName(), mdBusiness.getArrayListProductList().get(i).getStrDescription(), mdBusiness.getArrayListProductList().get(i).getnPrice(), mdBusiness.getArrayListProductList().get(i).getStrURLImage(),mdBusiness.getArrayListProductList().get(i).getStrID());
                        mdProducts.add(mdProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
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
        tvAddProduct = (TextView) findViewById(R.id.tvAddProduct);
        tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(ProductManagerActivity.this, AddProductActivity.class);
                startActivity(intentAdd);
            }
        });

    }
}
