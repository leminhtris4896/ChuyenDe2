package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UpdateInforActivity extends AppCompatActivity {

    private TextView tvCloseUpdate;
    private ImageView img_choosen;
    private TextView tvChoosen;
    private TextView tvCloseChoosenImg;
    private LinearLayout linearOpenLibrary;

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

                tvCloseChoosenImg = (TextView) dialog.findViewById(R.id.tvCloseChoosenImg);
                linearOpenLibrary = (LinearLayout) dialog.findViewById(R.id.linearOpenLibrary);

                linearOpenLibrary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentLibrary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intentLibrary,100);
                        dialog.dismiss();
                    }
                });


                tvCloseChoosenImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100)
        {
            // DISPLAY IMAGE FROM LIBRARY
            Uri imgUri = data.getData();
            img_choosen.setImageURI(imgUri);
        }
    }

    private void init() {
        img_choosen = (ImageView) findViewById(R.id.img_choosen);
        tvCloseUpdate = (TextView) findViewById(R.id.tvCloseUpdate);
    }
}
