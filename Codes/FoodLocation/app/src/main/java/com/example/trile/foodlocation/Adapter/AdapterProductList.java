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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {
    private ArrayList<mdProduct> mdProducts;
    Context context;

    public AdapterProductList(ArrayList<mdProduct> mdProducts, Context context) {
        this.mdProducts = mdProducts;
        this.context = context;
    }

    @Override
    public AdapterProductList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_product_list, parent, false);
        return new AdapterProductList.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterProductList.ViewHolder holder, int position) {
        mdProduct mdProduct = mdProducts.get(position);
        Picasso.with(context).load(mdProduct.getStrURLImage()).into(holder.imgProduct);
        holder.tvName.setText(mdProduct.getStrProductName());
        holder.tvDescription.setText(mdProduct.getStrDescription());
        holder.tvPrice.setText(Integer.toString(mdProduct.getnPrice()));
    }

    @Override
    public int getItemCount() {
        return mdProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_products_list);
            tvName = itemView.findViewById(R.id.tv_nameproduct_list);
            tvDescription = itemView.findViewById(R.id.tv_descip_product_list);
            tvPrice = itemView.findViewById(R.id.tv_price_product_list);
        }
    }
}
