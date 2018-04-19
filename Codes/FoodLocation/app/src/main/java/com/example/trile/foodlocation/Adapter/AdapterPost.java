package com.example.trile.foodlocation.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.example.trile.foodlocation.Models.mdUser;
import com.example.trile.foodlocation.PostDetailActivity;
import com.example.trile.foodlocation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private Button btnSentComment;
    private EditText edtCommentContext;
    private Dialog dialog;
    // COMMENT
    private TextView tvExitComment;
    private FirebaseAuth firebaseAuth;
    //
    DatabaseReference databaseReference;


    public AdapterPost(ArrayList<mdPost> arrmdPosts, Context context) {
        this.mdPosts = arrmdPosts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        firebaseAuth = FirebaseAuth.getInstance();

        // COMMENT
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_comment);

        btnSentComment = (Button) dialog.findViewById(R.id.btnSentComment);
        edtCommentContext = (EditText) dialog.findViewById(R.id.edtCM);

        Picasso.with(context).load(mdPosts.get(position).getImgProduct()).into(holder.imgPost);
        holder.tvPostName.setText(mdPosts.get(position).getNameProduct());
        holder.tvPostDescription.setText(mdPosts.get(position).getDescriptionProduct());
        holder.tvNumberLike.setText(mdPosts.get(position).getnNumberLike());
        holder.tvNumberUnlike.setText(mdPosts.get(position).getnNumberUnlike());
        holder.tvNumberCommment.setText(mdPosts.get(position).getnNumberComment());

        // Comment list
        recyclerViewComment = (RecyclerView) dialog.findViewById(R.id.recyclerComment);
        // RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setLayoutManager(layoutManager);


        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detailPost", mdPosts.get(position).getNameProduct());
                myIntent.putExtra("bundle", bundle);
                context.startActivity(myIntent);
            }
        });
        /*databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                if (mdPost.isCheckUnLike() == true) {
                    holder.cbxPostUnLike.setSelected(true);
                } else if (mdPost.isCheckUnLike() == false) {
                    holder.cbxPostUnLike.setSelected(false);
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
        });*/


        final Date currentTime = Calendar.getInstance().getTime();

        /*holder.cbxPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdPost mdPostOject = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                        final int LikeCount = Integer.parseInt(mdPostOject.getnNumberLike());

                        if (mdPostOject.getNameProduct().equalsIgnoreCase(mdPosts.get(position).getNameProduct())) {

                            if (mdPostOject.isCheckLike() == false) {
                                databaseReference.child("Post").child(mdPostOject.getPostID()).child("checkLike").setValue(true);
                                databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberLike").setValue(Integer.toString(LikeCount + 1));
                                holder.tvNumberLike.setText(Integer.toString(LikeCount + 1));
                                holder.cbxPostLike.setButtonDrawable(R.drawable.ic_like);
                                databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                        if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                            String newHistoryActivity = mdUser.getUserMail() + " vừa mới thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                            ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                            newArrayListLichSuHoatDong.add(newHistoryActivity);
                                            databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);

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
                            } else if (mdPostOject.isCheckLike() == true) {
                                databaseReference.child("Post").child(mdPostOject.getPostID()).child("checkLike").setValue(false);
                                databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberLike").setValue(Integer.toString(LikeCount - 1));
                                holder.tvNumberLike.setText(Integer.toString(LikeCount - 1));
                                holder.cbxPostLike.setButtonDrawable(R.drawable.ic_nulllike);
                                databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                        if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                            String newHistoryActivity = mdUser.getUserMail() + " vừa mới bỏ thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                            ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                            newArrayListLichSuHoatDong.add(newHistoryActivity);
                                            databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);
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

        });*/
        holder.cbxPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdPost mdPostOject = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);

                        if (mdPostOject.getNameProduct().equalsIgnoreCase(mdPosts.get(position).getNameProduct())) {
                            final int LikeCount = Integer.parseInt(mdPostOject.getnNumberLike());
                            databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                    if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                        for (int i = 0; i < mdUser.getArrayListUserStatusPost().size(); i++) {
                                            if (mdUser.getArrayListUserStatusPost().get(i).getStrIDPost().equalsIgnoreCase(mdPosts.get(position).getPostID())) {
                                                if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusLike() == false) {
                                                    if (mdPostOject.getPostID().equalsIgnoreCase(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost())) {
                                                        databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").child(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost()).child("strStatusLike").setValue(true);
                                                    }
                                                    databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberLike").setValue(Integer.toString(LikeCount + 1));
                                                    holder.tvNumberLike.setText(Integer.toString(LikeCount + 1));
                                                    holder.cbxPostLike.setButtonDrawable(R.drawable.ic_like);
                                                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                                                String newHistoryActivity = mdUser.getUserMail() + " vừa mới thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                                                ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                                                newArrayListLichSuHoatDong.add(newHistoryActivity);
                                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);

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
                                                } else if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusLike() == true) {
                                                    if (mdPostOject.getPostID().equalsIgnoreCase(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost())) {
                                                        databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").child(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost()).child("strStatusLike").setValue(false);
                                                    }
                                                    databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberLike").setValue(Integer.toString(LikeCount - 1));
                                                    holder.tvNumberLike.setText(Integer.toString(LikeCount - 1));
                                                    holder.cbxPostLike.setButtonDrawable(R.drawable.ic_nulllike);
                                                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                                                String newHistoryActivity = mdUser.getUserMail() + " vừa mới bỏ thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                                                ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                                                newArrayListLichSuHoatDong.add(newHistoryActivity);
                                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);
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
                                            }
                                        }

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


        holder.cbxPostUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdPost mdPostOject = dataSnapshot.getValue(mdPost.class);
                        if (mdPostOject.getNameProduct().equalsIgnoreCase(mdPosts.get(position).getNameProduct())) {
                            final int UnLikeCount = Integer.parseInt(mdPostOject.getnNumberUnlike());
                            databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                    if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                        for (int i = 0; i < mdUser.getArrayListUserStatusPost().size(); i++) {
                                            if (mdUser.getArrayListUserStatusPost().get(i).getStrIDPost().equalsIgnoreCase(mdPosts.get(position).getPostID())) {
                                                if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusUnlikeLike() == false) {
                                                    if (mdPostOject.getPostID().equalsIgnoreCase(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost())) {
                                                        databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").child(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost()).child("strStatusUnlikeLike").setValue(true);
                                                    }
                                                    databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberUnlike").setValue(Integer.toString(UnLikeCount + 1));
                                                    holder.tvNumberUnlike.setText(Integer.toString(UnLikeCount + 1));
                                                    holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_unlike);
                                                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                                                String newHistoryActivity = mdUser.getUserMail() + " vừa nhấn không thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                                                ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                                                newArrayListLichSuHoatDong.add(newHistoryActivity);
                                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);

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
                                                } else if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusUnlikeLike() == true) {
                                                    if (mdPostOject.getPostID().equalsIgnoreCase(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost())) {
                                                        databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListUserStatusPost").child(mdUser.getArrayListUserStatusPost().get(i).getStrIDPost()).child("strStatusUnlikeLike").setValue(false);
                                                    }
                                                    databaseReference.child("Post").child(mdPostOject.getPostID()).child("nNumberUnlike").setValue(Integer.toString(UnLikeCount - 1));
                                                    holder.tvNumberUnlike.setText(Integer.toString(UnLikeCount - 1));
                                                    holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_nullunlike);
                                                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                                                String newHistoryActivity = mdUser.getUserMail() + " vừa bỏ không thích bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                                                ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                                                newArrayListLichSuHoatDong.add(newHistoryActivity);
                                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);
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
                                            }
                                        }

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


        holder.tvPostDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detailPost", mdPosts.get(position).getNameProduct());
                myIntent.putExtra("bundle", bundle);
                context.startActivity(myIntent);
            }
        });


        // Open dialog when click comment icon
        holder.imgOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdPost mdPostOject = dataSnapshot.getValue(mdPost.class);
                        if (mdPostOject.getNameProduct().equalsIgnoreCase(mdPosts.get(position).getNameProduct())) {

                            adapterComment = new AdapterComment(context, mdPostOject.getArrayListCommentPost());
                            recyclerViewComment.setAdapter(adapterComment);
                            recyclerViewComment.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
                            dialog.show();
                            btnSentComment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String cmn = edtCommentContext.getText().toString();
                                    mdComment cm = new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", "" + cmn);
                                    ArrayList<mdComment> arrayListComment = mdPostOject.getArrayListCommentPost();
                                    arrayListComment.add(cm);
                                    databaseReference.child("Post").child(mdPostOject.getPostID()).child("arrayListCommentPost").setValue(arrayListComment);
                                    edtCommentContext.setText("");

                                    adapterComment.notifyDataSetChanged();
                                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            final mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                                            if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                                                String newHistoryActivity = mdUser.getUserMail() + " vừa mới comment bài viết " + mdPostOject.getNameProduct() + " " + currentTime.toString();
                                                ArrayList<String> newArrayListLichSuHoatDong = mdUser.getArrayListLichSuHoatDong();
                                                newArrayListLichSuHoatDong.add(newHistoryActivity);
                                                databaseReference.child("Users").child(mdUser.getUserID()).child("arrayListLichSuHoatDong").setValue(newArrayListLichSuHoatDong);
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


        // Close dialog when click exit icon
        tvExitComment = (TextView) dialog.findViewById(R.id.tvExitComment);
        tvExitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        /*if (mdPosts.get(position).isCheckUnLike() == false) {
            holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_nullunlike);
        } else {
            holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_unlike);
        }*/


        databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mdUser mdUser = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdUser.class);
                if (mdUser.getUserMail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
                    for (int i = 0; i < mdUser.getArrayListUserStatusPost().size(); i++) {
                        if (mdUser.getArrayListUserStatusPost().get(i).getStrIDPost().equalsIgnoreCase(mdPosts.get(position).getPostID())) {
                            if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusLike() == false) {
                                holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_nullunlike);
                            } else {
                                holder.cbxPostUnLike.setButtonDrawable(R.drawable.ic_unlike);
                            }

                            if (mdUser.getArrayListUserStatusPost().get(i).isStrStatusUnlikeLike() == false) {
                                holder.cbxPostLike.setButtonDrawable(R.drawable.ic_nulllike);
                            } else {
                                holder.cbxPostLike.setButtonDrawable(R.drawable.ic_like);
                            }
                            if (i > 2) {
                                break;
                            }
                        }
                    }
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


    @Override
    public int getItemCount() {
        return mdPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPostName;
        TextView tvPostDescription;
        TextView tvNumberLike;
        TextView tvNumberUnlike;
        TextView tvNumberCommment;
        ImageView imgPost;
        CheckBox cbxPostLike;
        CheckBox cbxPostUnLike;
        // Image open dialog
        ImageView imgOpenDialog;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPost = (ImageView) itemView.findViewById(R.id.img_product);
            tvPostName = (TextView) itemView.findViewById(R.id.tv_name_product);
            tvPostDescription = (TextView) itemView.findViewById(R.id.tv_desciption_product);
            tvNumberLike = (TextView) itemView.findViewById(R.id.tv_number_like);
            tvNumberUnlike = (TextView) itemView.findViewById(R.id.tv_number_unlike);
            tvNumberCommment = (TextView) itemView.findViewById(R.id.tv_number_comment);
            cbxPostLike = (CheckBox) itemView.findViewById(R.id.cbx_like);
            cbxPostUnLike = (CheckBox) itemView.findViewById(R.id.cbx_unlike);

            // Image open dialog
            imgOpenDialog = (ImageView) itemView.findViewById(R.id.img_comment);
        }
    }
}