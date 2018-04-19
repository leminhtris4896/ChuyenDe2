package com.example.trile.foodlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductManagerActivity extends AppCompatActivity {

    private TextView tvAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_product_manager_layout);
        tvAddProduct = (TextView) findViewById(R.id.tvAddProduct);
        tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(ProductManagerActivity.this,AddProductActivity.class);
                startActivity(intentAdd);
            }
        });
    }
}
