package com.example.trile.foodlocation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trile.foodlocation.Adapter.AdapterSlideShow;

public class SlideShowStar extends AppCompatActivity {

    private ViewPager slide_viewPager;
    private LinearLayout mDotLayout;
    // Layout
    private TextView btnNext;
    private TextView btnBack;
    private int mCurrent;

    private TextView[] mDots;
    private AdapterSlideShow adapterSlideShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show_star_layout);
        // Initview
        slide_viewPager = (ViewPager) findViewById(R.id.viewPageSlideShow);
        mDotLayout = (LinearLayout) findViewById(R.id.dostSlideShow);
        btnBack = (TextView) findViewById(R.id.btnBack);
        btnNext = (TextView) findViewById(R.id.btnNext);


        btnBack.setVisibility(View.INVISIBLE);
        //
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_viewPager.setCurrentItem(mCurrent + 1);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_viewPager.setCurrentItem(mCurrent - 1);
            }
        });

        adapterSlideShow = new AdapterSlideShow(this);
        slide_viewPager.setAdapter(adapterSlideShow);
        addDotsIndicator(0);
        slide_viewPager.addOnPageChangeListener(viewListener);

    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[6];
        mDotLayout.removeAllViews();

        for (int i = 0;i < mDots.length;i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(android.R.color.white));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrent = i;

            if (i == 0) {
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
                //
                btnNext.setText("Tiếp tục");
                btnBack.setText("");
            }else if (i == mDots.length - 1){
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                //
                btnNext.setText("Kết thúc");
                if (btnNext.getText().equals("Kết thúc")) {
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SlideShowStar.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                btnBack.setText("Trở lại");
            }else {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                //
                btnNext.setText("Tiếp tục");
                btnBack.setText("Trở lại");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
