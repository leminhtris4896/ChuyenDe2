package com.example.trile.foodlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DialogComment extends AppCompatActivity {
    /*private EditText edtCommentContext;
    private Button btnSentComment;
    Intent intent;
    Bundle bundle;


    DatabaseReference databaseReference;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        init();
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        bundle.getString("detailComment");
        btnSentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final mdPost mdPost = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdPost.class);
                        if (mdPost.getNameProduct().equalsIgnoreCase(bundle.getString("detailComment"))) {
                            String cmn = edtCommentContext.getText().toString();
                            mdComment cm =  new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", ""+cmn);
                            //mdPostOject.getArrayListCommentPost().add(new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", ""+edtCommentContext.getText().toString()));
                            //mdPostOject.setArrayListCommentPost(mdComments);
                            mdPost.getArrayListCommentPost().add(cm);
                            databaseReference.child("Post").child(mdPost.getPostID()).child("arrayListCommentPost").setValue(mdPost.getArrayListCommentPost());
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
    }

    /*public void init() {
        edtCommentContext = (EditText) findViewById(R.id.edt_comment);
        btnSentComment = (Button) findViewById(R.id.btnSentComment);
    }*/


}
