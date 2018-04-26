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

public class AdapterPlace extends RecyclerView.Adapter<AdapterPlace.ViewHolder> {
    ArrayList<mdBusiness> arrBusiness;
    Context context;

    public AdapterPlace(ArrayList<mdBusiness> arrTopLocation, Context context) {
        this.arrBusiness = arrTopLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_business,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*holder.imgTopLocation.setImageResource(arrBusiness.get(position).getImgLocation());
        holder.tvNameTopLocation.setText(arrBusiness.get(position).getNameBusiness());
        holder.tvAddTopLocation.setText(arrBusiness.get(position).getAddressBusiness());*/
        mdBusiness mdBusiness = arrBusiness.get(position);
        Picasso.with(context).load(arrBusiness.get(position).getStrImage()).into(holder.imgTopLocation);
        holder.tvAddTopLocation.setText(mdBusiness.getStrAddress());
        holder.tvNameTopLocation.setText(mdBusiness.getStrName());


    }

    @Override
    public int getItemCount() {
        return arrBusiness.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTopLocation;
        TextView tvNameTopLocation;
        TextView tvAddTopLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTopLocation = (ImageView) itemView.findViewById(R.id.imgTopLocation);
            tvNameTopLocation = (TextView) itemView.findViewById(R.id.tvNameTopLocation);
            tvAddTopLocation = (TextView) itemView.findViewById(R.id.tvAddTopLocation);
        }
    }
}
