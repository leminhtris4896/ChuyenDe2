package com.example.trile.foodlocation.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Adapter.AdapterPost;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TRILE on 08/03/2018.
 */

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    ArrayList<mdPost> mdPosts;
    Context context;
    // List view Comment
    private ArrayList<mdComment> arrCommentHome;
    private RecyclerView recyclerViewComment;
    private AdapterComment adapterComment;

    // COMMENT
    private TextView tvExitComment;
    //


    public AdapterPost(ArrayList<mdPost> arrmdPosts, Context context) {
        this.mdPosts = arrmdPosts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_post,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // COMMENT
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_comment);

        Picasso.with(context).load(mdPosts.get(position).getImgProduct()).into(holder.imgHomeProduct);
        holder.tvHomeProductName.setText(mdPosts.get(position).getNameProduct());
        holder.tvHomeProductDesciption.setText(mdPosts.get(position).getDescriptionProduct());
        holder.tvNumberLike.setText(mdPosts.get(position).getnNumberLike());
        holder.tvNumberUnlike.setText(mdPosts.get(position).getnNumberUnlike());
        holder.tvNumberCommment.setText(mdPosts.get(position).getnNumberComment());

        holder.cbxHomeLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.cbxHomeLike.isChecked())
                {
                    mdPosts.get(position).setCheckLike(true);
                }
                else
                {
                    mdPosts.get(position).setCheckLike(false);
                }
            }
        });

        holder.cbxHomeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.cbxHomeUnlike.isChecked())
                {
                    mdPosts.get(position).setCheckUnLike(true);
                }
                else
                {
                    mdPosts.get(position).setCheckUnLike(false);
                }
            }
        });




        // Open dialog when click comment icon
        holder.imgOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });


        // Close dialog when click exit icon
        tvExitComment = (TextView) dialog.findViewById(R.id.tvExitComment);
        tvExitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Comment list
        recyclerViewComment = (RecyclerView) dialog.findViewById(R.id.recyclerComment);
        // RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setLayoutManager(layoutManager);
        // Set adapter
        arrCommentHome = new ArrayList<mdComment>();
        arrCommentHome.add(new mdComment(R.drawable.man,"Rất hay chổ này ăn rất ngon"));
        arrCommentHome.add(new mdComment(R.drawable.man,"Qúa tuyệt vời luôn"));
        arrCommentHome.add(new mdComment(R.drawable.man,"Tuyệt vời đó chứ"));
        arrCommentHome.add(new mdComment(R.drawable.man,"Khá ổn , cho 10 điểm luôn"));
        arrCommentHome.add(new mdComment(R.drawable.man,"Cảm ơn đã giới thiệu , sẽ đến đây ăn thường xuyên , thành thật cảm ơn rất nhiều"));
        arrCommentHome.add(new mdComment(R.drawable.man,"Rất tuyệt"));
        // Set
        adapterComment = new AdapterComment(context,arrCommentHome);
        recyclerViewComment.setAdapter(adapterComment);
        recyclerViewComment.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    @Override
    public int getItemCount() {
        return mdPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHomeProductName;
        TextView tvHomeProductDesciption;
        TextView tvNumberLike;
        TextView tvNumberUnlike;
        TextView tvNumberCommment;
        ImageView imgHomeProduct;
        CheckBox cbxHomeLike;
        CheckBox cbxHomeUnlike;
        // Image open dialog
        ImageView imgOpenDialog;

        public ViewHolder(View itemView) {
            super(itemView);
            imgHomeProduct = (ImageView) itemView.findViewById(R.id.img_product);
            tvHomeProductName = (TextView) itemView.findViewById(R.id.tv_name_product);
            tvHomeProductDesciption = (TextView) itemView.findViewById(R.id.tv_desciption_product);
            tvNumberLike = (TextView) itemView.findViewById(R.id.tv_number_like);
            tvNumberUnlike = (TextView) itemView.findViewById(R.id.tv_number_unlike);
            tvNumberCommment = (TextView) itemView.findViewById(R.id.tv_number_comment);
            cbxHomeLike = (CheckBox) itemView.findViewById(R.id.cbx_like);
            cbxHomeUnlike = (CheckBox) itemView.findViewById(R.id.cbx_unlike);
            // Image open dialog
            imgOpenDialog = (ImageView) itemView.findViewById(R.id.img_comment);
        }
    }
}
