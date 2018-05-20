package com.example.trile.foodlocation;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Adapter.AdapterManagerPostShow;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagerPost extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<mdPost> postArrayList;
    private AdapterManagerPostShow adapterManagerPostShow;
    private RecyclerView recyclerViewPostShow;
    private Button btnAddPost;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView close_manager_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_post_layout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
       /* mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                LoadPost();
            }
        });*/


        Init();

        close_manager_post = (TextView) findViewById(R.id.close_manager_post);
        close_manager_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPostShow.setHasFixedSize(true);
        recyclerViewPostShow.setLayoutManager(layoutManager);

        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShowActivityAddPost = new Intent(ManagerPost.this,AddPost.class);
                startActivity(intentShowActivityAddPost);
            }
        });

        LoadPost();

    }

    public void Init() {
        recyclerViewPostShow = (RecyclerView) findViewById(R.id.recyclerPostShowManager);
        btnAddPost = (Button) findViewById(R.id.btn_add_post);
    }

    public void LoadPost() {
        postArrayList = new ArrayList<>();
        adapterManagerPostShow = new AdapterManagerPostShow(postArrayList, this);
        recyclerViewPostShow.setAdapter(adapterManagerPostShow);
        mData.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                postArrayList.add(mdPost);
                adapterManagerPostShow.notifyDataSetChanged();
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        LoadPost();
    }
}
