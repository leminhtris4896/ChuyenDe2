package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TRILE on 08/03/2018.
 */

public class AdapterMenuPlace extends RecyclerView.Adapter<AdapterMenuPlace.ViewHolder> {
    ArrayList<mdBusiness> arrBusiness;
    Context context;

    public AdapterMenuPlace(ArrayList<mdBusiness> arrTopLocation, Context context) {
        this.arrBusiness = arrTopLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_place,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(context).load(arrBusiness.get(position).getStrImage()).into(holder.img);
        holder.tvName.setText(arrBusiness.get(position).getStrName());
        holder.tvAdd.setText(arrBusiness.get(position).getStrAddress());
        holder.tvTime.setText(arrBusiness.get(position).getStrOpenTime());
        holder.tvVote.setText(arrBusiness.get(position).getStrScoreRating());
    }

    @Override
    public int getItemCount() {
        return arrBusiness.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvName;
        TextView tvAdd;
        TextView tvTime;
        TextView tvVote;
        TextView tvNear;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_place);
            tvName = (TextView) itemView.findViewById(R.id.tv_business_name);
            tvAdd = (TextView) itemView.findViewById(R.id.tv_business_address);
            tvTime = (TextView) itemView.findViewById(R.id.tv_workingtime);
            tvVote = (TextView) itemView.findViewById(R.id.tv_vote);
            tvNear = (TextView) itemView.findViewById(R.id.tv_business_near);
        }
    }
}
