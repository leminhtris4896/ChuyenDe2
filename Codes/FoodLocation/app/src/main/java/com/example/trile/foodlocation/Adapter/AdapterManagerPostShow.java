package com.example.trile.foodlocation.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.Models.mdUserStatusPost;
import com.example.trile.foodlocation.R;
import com.example.trile.foodlocation.UpdatePost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterManagerPostShow extends RecyclerView.Adapter<AdapterManagerPostShow.ViewHolder> {
    private ArrayList<mdPost> postArrayList;
    Context context;
    private Dialog dialogAcceptDelete;
    private Button btn_Yes_Delete_Post;
    private Button btn_No_Delete_Post;
    private DatabaseReference mData;
    ArrayList<mdUserStatusPost> userStatusPosts ;

    public AdapterManagerPostShow(ArrayList<mdPost> postArrayList, Context context) {
        this.postArrayList = postArrayList;
        this.context = context;
    }


    @Override
    public AdapterManagerPostShow.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_post_show_manager, parent, false);
        return new AdapterManagerPostShow.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterManagerPostShow.ViewHolder holder, final int position) {
        final mdPost arrayPost = postArrayList.get(position);

        mData = FirebaseDatabase.getInstance().getReference();

        dialogAcceptDelete = new Dialog(context);
        dialogAcceptDelete.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialogAcceptDelete.setContentView(R.layout.custom_dialog_accept_delete_post);
        dialogAcceptDelete.setCanceledOnTouchOutside(false);

        btn_Yes_Delete_Post = (Button) dialogAcceptDelete.findViewById(R.id.btn_Yes_accept_delete);
        btn_No_Delete_Post = (Button) dialogAcceptDelete.findViewById(R.id.btn_No_accept_delete);

        Picasso.with(context).load(arrayPost.getImgProduct()).into(holder.img_post);
        holder.tv_id_post_maganer.setText(arrayPost.getPostID());
        holder.tv_name_post_manager.setText(arrayPost.getNameProduct());
        holder.tv_description_post_manager.setText(arrayPost.getDescriptionProduct());
        holder.tv_address_post_manager.setText("Bài đăng thuộc doanh nghiệp " + arrayPost.getLienKetDiaDiem());


        holder.imageViewDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogAcceptDelete.show();
                btn_Yes_Delete_Post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mData.child("Post").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                final mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                                if (mdPost.getPostID().equalsIgnoreCase(postArrayList.get(position).getPostID())) {
                                    mData.child("Users").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                            userStatusPosts = new ArrayList<>();
                                            userStatusPosts = mdUser.getArrayListUserStatusPost();
                                            dialogAcceptDelete.dismiss();
                                            for (int i = 0; i < userStatusPosts.size(); i++)
                                            {
                                                if (userStatusPosts.get(i).getStrIDPost().equalsIgnoreCase(mdPost.getPostID()))
                                                {
                                                    userStatusPosts.remove(i);
                                                }
                                            }
                                            mData.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").setValue(userStatusPosts);
                                            mData.child("Post").child(mdPost.getPostID()).removeValue();
                                            /*for ( int i = 0; i < mdUser.getArrayListUserStatusPost().size(); i++)
                                            {
                                                if ( mdUser.getArrayListUserStatusPost().get(i).getStrIDPost().equalsIgnoreCase(mdPost.getPostID()))
                                                {
                                                    mData.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").child(i+"").removeValue();

                                                }
                                            }
                                            mData.child("Post").child(mdPost.getPostID()).removeValue();*/
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

                btn_No_Delete_Post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAcceptDelete.dismiss();
                    }
                });
            }
        });

        holder.imageViewUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatePost.class);
                intent.putExtra("id_post_update",postArrayList.get(position).getPostID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_post;
        TextView tv_id_post_maganer;
        TextView tv_name_post_manager;
        TextView tv_description_post_manager;
        TextView tv_address_post_manager;
        ImageView imageViewDeletePost;
        ImageView imageViewUpdatePost;

        public ViewHolder(View itemView) {
            super(itemView);
            img_post = (ImageView) itemView.findViewById(R.id.img_post_show_manager);
            tv_id_post_maganer = (TextView) itemView.findViewById(R.id.id_post_show_manager);
            tv_name_post_manager = (TextView) itemView.findViewById(R.id.tv_name_post_show_manager);
            tv_description_post_manager = (TextView) itemView.findViewById(R.id.tv_description_post_show_manager);
            tv_address_post_manager = (TextView) itemView.findViewById(R.id.tv_address_post_show_manager);
            imageViewDeletePost = (ImageView) itemView.findViewById(R.id.img_delete_post_show_manager);
            imageViewUpdatePost = (ImageView) itemView.findViewById(R.id.img_update_post_show_manager);
        }
    }
}
