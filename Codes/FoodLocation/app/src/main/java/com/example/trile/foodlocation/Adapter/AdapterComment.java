package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TRILE on 19/03/2018.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    Context context;
    ArrayList<mdComment> arrayListComment;

    public AdapterComment(Context context, ArrayList<mdComment> arrayListComment) {
        this.context = context;
        this.arrayListComment = arrayListComment;
    }

    @Override
    public AdapterComment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_comment,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterComment.ViewHolder holder, int position) {
        holder.tvComment.setText(arrayListComment.get(position).getTvComment());
        Picasso.with(context).load(arrayListComment.get(position).getImgUserComment()).into(holder.imgUserComment);
    }

    @Override
    public int getItemCount() {
        return arrayListComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUserComment;
        TextView tvComment;

        public ViewHolder(View itemView) {
            super(itemView);

            imgUserComment = (ImageView) itemView.findViewById(R.id.imgUserComment);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);

        }
    }
}
