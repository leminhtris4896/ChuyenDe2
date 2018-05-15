package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdSpinnerPlace;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TRILE on 10/04/2018.
 */

public class AdapterSpinnerPlace extends BaseAdapter {

    Context context;
    int myLayout;
    List<mdSpinnerPlace> arraySpinner;


    public AdapterSpinnerPlace(List<mdSpinnerPlace> arraySpinner, Context context, int myLayout) {
        this.arraySpinner = arraySpinner;
        this.context = context;
        this.myLayout = myLayout;
    }

    @Override
    public int getCount() {
        return arraySpinner.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout,null);

        ImageView img = (ImageView) view.findViewById(R.id.imgSpinner);
        TextView tv = (TextView) view.findViewById(R.id.tvSpinner);

        tv.setText(arraySpinner.get(i).getStrType());
        img.setImageResource(arraySpinner.get(i).getImgSpinner());
        return view;
    }
}
