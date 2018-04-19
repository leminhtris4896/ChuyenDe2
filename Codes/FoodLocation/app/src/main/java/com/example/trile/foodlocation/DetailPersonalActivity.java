package com.example.trile.foodlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailPersonalActivity extends AppCompatActivity {

    private TextView tvUpdateInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_personal_layout);

        tvUpdateInfor = (TextView) findViewById(R.id.btnUpdateInfor);
        tvUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdate = new Intent(DetailPersonalActivity.this,UpdateInforActivity.class);
                startActivity(intentUpdate);
            }
        });
    }
}
