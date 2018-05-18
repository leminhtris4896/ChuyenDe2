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
            R.drawable.restaurant,
            R.drawable.exchange,
            R.drawable.store
    };

    public String[] slide_title = {
            "FOOD",
            "SEARCH",
            "MAP",
            "VIEW",
            "UPDATE",
            "BUSINESS"
    };

    public String[] slide_content = {
            "Mọi của hàng , quán ăn , quán nước đều có , chỉ cần xem là có hết",
            "Tìm kiếm các quán chuyện nhỏ , đầy đủ các loại hình",
            "Bắt được vị trí của bạn , hiển thị các quán ở gần bạn nhất",
            "Bạn sẽ xem được những địa điểm tốt nhất , tìm kiếm các quán ăn , khu vực gần bạn dễ dàng nhất",
            "Bạn sẽ cập nhật được những tin tức , sản phẩm , cũng như địa điểm mới , hoặc khuyến mãi tại các quán",
            "Doanh nghiệp có thể tạo tài khoản , và quảng cáo sản phẩm cửa hàng của mình cũng như là quảng cáo buôn bán"
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
