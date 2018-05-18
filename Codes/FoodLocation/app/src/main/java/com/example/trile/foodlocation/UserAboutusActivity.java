package com.example.trile.foodlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserAboutusActivity extends AppCompatActivity {

    private TextView about_us_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_aboutus_layout);

        about_us_close = (TextView) findViewById(R.id.about_us_close);
        about_us_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
