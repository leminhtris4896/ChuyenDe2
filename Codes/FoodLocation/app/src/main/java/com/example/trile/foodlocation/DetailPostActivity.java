package com.example.trile.foodlocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailPostActivity extends AppCompatActivity {

    private TextView tvCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_post_layout);

        tvCall = (TextView) findViewById(R.id.btn_phone);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse("tel:0985134519"));
                startActivity(intentCall);
            }
        });

    }
}
