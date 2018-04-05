package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdPlace;
import com.example.trile.foodlocation.R;

import java.util.ArrayList;

/**
 * Created by TRILE on 04/04/2018.
 */

public class AdapterPlace extends RecyclerView.Adapter<AdapterPlace.ViewHolder> {

    ArrayList<mdPlace> arrProductPlace;
    Context context;

    public AdapterPlace(ArrayList<mdPlace> arrProductPlace, Context context) {
        this.arrProductPlace = arrProductPlace;
        this.context = context;
    }

    @Override
    public AdapterPlace.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_place,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterPlace.ViewHolder holder, int position) {
        holder.imgPlace.setImageResource(arrProductPlace.get(position).getImgProduct());
        holder.tvPlaceName.setText(arrProductPlace.get(position).getBusinessName());
        holder.tvPlaceAddress.setText(arrProductPlace.get(position).getBusinessAddress());
        holder.tvWorkingTime.setText(arrProductPlace.get(position).getBusinessWorkingtime());
        holder.tvNumberVote.setText(arrProductPlace.get(position).getBusinessVote());
    }

    @Override
    public int getItemCount() {
        return arrProductPlace.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlace;
        TextView tvPlaceName;
        TextView tvPlaceAddress;
        TextView tvWorkingTime;
        TextView tvNumberVote;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPlace = (ImageView) itemView.findViewById(R.id.img_place);
            tvPlaceName = (TextView) itemView.findViewById(R.id.tv_business_name);
            tvPlaceAddress = (TextView) itemView.findViewById(R.id.tv_business_address);
            tvWorkingTime = (TextView) itemView.findViewById(R.id.tv_workingtime);
            tvNumberVote = (TextView) itemView.findViewById(R.id.tv_vote);
        }
    }
}
