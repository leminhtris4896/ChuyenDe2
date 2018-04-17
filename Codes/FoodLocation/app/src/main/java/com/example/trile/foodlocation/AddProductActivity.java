package com.example.trile.foodlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddProductActivity extends AppCompatActivity {

    private TextView tvCloseAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_product_layout);

        tvCloseAddProduct = (TextView) findViewById(R.id.tvCloseAddProduct);
        tvCloseAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent closeAdd = new Intent(AddProductActivity.this,ProductManagerActivity.class);
                startActivity(closeAdd);
            }
        });
    }
}
