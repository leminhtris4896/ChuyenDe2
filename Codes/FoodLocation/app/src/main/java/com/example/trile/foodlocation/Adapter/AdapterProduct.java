package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdProduct;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{
    private ArrayList<mdProduct> mdProducts;
    Context context;

    public AdapterProduct(ArrayList<mdProduct> mdProducts, Context context) {
        this.mdProducts = mdProducts;
        this.context = context;
    }

    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_product,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, int position) {
        mdProduct mdProduct = mdProducts.get(position);
        holder.tvName.setText(mdProduct.getStrProductName());
        holder.tvDescription.setText(mdProduct.getStrDescription());
        holder.tvPrice.setText(Integer.toString(mdProduct.getnPrice()));
        Picasso.with(context).load(mdProducts.get(position).getStrURLImage()).into(holder.imgProDuct);
    }

    @Override
    public int getItemCount() {
        return mdProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProDuct;
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_nameproduct);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_descip_product);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price_product);
            imgProDuct = (ImageView) itemView.findViewById(R.id.img_products);
        }
    }
}
