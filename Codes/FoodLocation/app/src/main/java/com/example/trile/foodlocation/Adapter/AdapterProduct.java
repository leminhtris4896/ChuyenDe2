package com.example.trile.foodlocation.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdProduct;
import com.example.trile.foodlocation.R;
import com.example.trile.foodlocation.UpdateProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private ArrayList<mdProduct> mdProducts;
    Context context;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    private Dialog dialogAcceptDelete;
    private Button btn_Yes_Delete_Product;
    private Button btn_No_Delete_Product;
    ArrayList<mdProduct> arrayNewProduct;

    public AdapterProduct(ArrayList<mdProduct> mdProducts, Context context) {
        this.mdProducts = mdProducts;
        this.context = context;
    }

    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, final int position) {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        dialogAcceptDelete = new Dialog(context);
        dialogAcceptDelete.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialogAcceptDelete.setContentView(R.layout.custom_dialog_accept_delete_post);
        dialogAcceptDelete.setCanceledOnTouchOutside(false);


        btn_Yes_Delete_Product = (Button) dialogAcceptDelete.findViewById(R.id.btn_Yes_accept_delete);
        btn_No_Delete_Product = (Button) dialogAcceptDelete.findViewById(R.id.btn_No_accept_delete);

        final mdProduct mdProduct = mdProducts.get(position);
        holder.tvName.setText(mdProduct.getStrProductName());
        holder.tvDescription.setText(mdProduct.getStrDescription());
        holder.tvPrice.setText(Integer.toString(mdProduct.getnPrice()));
        Picasso.with(context).load(mdProducts.get(position).getStrURLImage()).into(holder.imgProDuct);

        holder.updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("id_product",mdProducts.get(position).getStrID());
                context.startActivity(intent);
            }
        });
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAcceptDelete.show();
                btn_Yes_Delete_Product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mData.child("Business").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                                if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(mdBusiness.getStrEmail())) {
                                    arrayNewProduct = mdBusiness.getArrayListProductList();
                                    for (int i = 0; i < arrayNewProduct.size(); i++) {
                                        if (arrayNewProduct.get(i).getStrID().equalsIgnoreCase(mdProduct.getStrID())) {
                                            arrayNewProduct.remove(i);
                                        }
                                    }
                                    mData.child("Business").child(mdBusiness.getStrID()).child("arrayListProductList").setValue(arrayNewProduct);
                                    Toast.makeText(context, "Xóa sản phẩm thành công !", Toast.LENGTH_SHORT).show();
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
                btn_No_Delete_Product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAcceptDelete.dismiss();
                        Toast.makeText(context, "Hủy  !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
        ImageView deleteProduct;
        ImageView updateProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_nameproduct);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_descip_product);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price_product);
            imgProDuct = (ImageView) itemView.findViewById(R.id.img_products);
            deleteProduct = (ImageView) itemView.findViewById(R.id.delete_product);
            updateProduct = (ImageView) itemView.findViewById(R.id.update_product);
        }
    }
}
