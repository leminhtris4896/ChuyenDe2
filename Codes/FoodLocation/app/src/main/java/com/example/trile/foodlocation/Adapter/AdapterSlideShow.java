package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.trile.foodlocation.R;

import org.w3c.dom.Text;

/**
 * Created by TRILE on 16/05/2018.
 */

public class AdapterSlideShow extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public AdapterSlideShow(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.img_show_food,
            R.drawable.img_show_earth,
            R.drawable.img_show_gps,
    };

    public String[] slide_title = {
            "FOOD",
            "SEARCH",
            "MAP"
    };

    public String[] slide_content = {
            "Các địa điểm ăn , uống , nhậu nhẹt tất cả đều có , chỉ cần bạn có tiền đi đâu cũng có thể",
            "Tìm kiếm các địa điểm ăn uống trên Google Map . Chỉ cần tìm kiếm khu vực thì mọi quán ăn sẽ hiển thị",
            "Lấy được vị trí hiện tại của bạn ( GPS ) hiển thị ra các địa điểm gần bạn, gần bạn nhất"
    };

    @Override
    public int getCount() {
        return slide_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_show_iteam,container,false);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.imgSlide);
        TextView tvSlide = (TextView) view.findViewById(R.id.titleSilde);
        TextView contentSlide = (TextView) view.findViewById(R.id.contentSlide);


        imgSlide.setImageResource(slide_images[position]);
        tvSlide.setText(slide_title[position]);
        contentSlide.setText(slide_content[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
