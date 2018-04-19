package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateInforActivity extends AppCompatActivity {

    private TextView tvCloseUpdate;
    private ImageView img_choosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_infor_layout);
        init();
        tvCloseUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent closeUpdate = new Intent(UpdateInforActivity.this,DetailPersonalActivity.class);
                startActivity(closeUpdate);
            }
        });
        final Dialog dialog = new Dialog(UpdateInforActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_choosen_image);

        img_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
    private void init() {
        img_choosen = (ImageView) findViewById(R.id.img_choosen);

        tvCloseUpdate = (TextView) findViewById(R.id.tvCloseUpdate);
    }
}
