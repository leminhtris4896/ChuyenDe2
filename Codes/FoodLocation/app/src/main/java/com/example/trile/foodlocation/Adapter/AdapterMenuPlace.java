package com.example.trile.foodlocation.Adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TRILE on 08/03/2018.
 */

public class AdapterMenuPlace extends RecyclerView.Adapter<AdapterMenuPlace.ViewHolder> implements LocationListener {
    ArrayList<mdBusiness> arrBusiness;
    Context context;
    public double langtitude;
    public double longitude;
    //
    private LocationManager locationManager;

    private final static int FADE_DURATION = 1000;

    public AdapterMenuPlace(ArrayList<mdBusiness> arrTopLocation, Context context) {
        this.arrBusiness = arrTopLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customview_place,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        double earthRadius = 3958.75;
        double dLat = Math.toRadians(arrBusiness.get(position).getDbLatitude() - langtitude);
        double dLng = Math.toRadians(arrBusiness.get(position).getDbLongitude() - longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(langtitude))
                * Math.cos(Math.toRadians(arrBusiness.get(position).getDbLatitude())) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        int meterConversion = 1609;
        a = Math.round((new Float(dist * meterConversion).floatValue()) / 2) * 2;
        //Toast.makeText(this, ""+ new Float(dist * meterConversion).floatValue(), Toast.LENGTH_SHORT).show();
        if (a > 1000) {
            double kq = a / 1000;
            //tvLocation.setText(kq + "" + "KM");
            holder.tvNear.setText(kq + "" + "KM");
        }else {
            //tvLocation.setText(a + "" + "M");
            holder.tvNear.setText(a + "" + "M");
        }
        Picasso.with(context).load(arrBusiness.get(position).getStrImage()).into(holder.img);
        holder.tvName.setText(arrBusiness.get(position).getStrName());
        holder.tvAdd.setText(arrBusiness.get(position).getStrAddress());
        holder.tvTime.setText(arrBusiness.get(position).getStrOpenTime());
        holder.tvVote.setText(arrBusiness.get(position).getStrScoreRating());

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_listview_place);
        setFadeAnimation(holder.itemView);
        setScaleAnimation(holder.itemView);
        //setTranslateAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return arrBusiness.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.ZORDER_BOTTOM, 0.5f, Animation.ZORDER_BOTTOM, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setTranslateAnimation(View view)
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ZORDER_BOTTOM,1.0f,Animation.ZORDER_BOTTOM,0.0f);
        translateAnimation.setDuration(FADE_DURATION);
        view.startAnimation(translateAnimation);
    }

    @Override
    public void onLocationChanged(Location location) {
        //remove location callback:
        locationManager.removeUpdates(this);
            langtitude = location.getLatitude();
            longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvName;
        TextView tvAdd;
        TextView tvTime;
        TextView tvVote;
        TextView tvNear;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_place);
            tvName = (TextView) itemView.findViewById(R.id.tv_business_name);
            tvAdd = (TextView) itemView.findViewById(R.id.tv_business_address);
            tvTime = (TextView) itemView.findViewById(R.id.tv_workingtime);
            tvVote = (TextView) itemView.findViewById(R.id.tv_vote);
            tvNear = (TextView) itemView.findViewById(R.id.tv_business_near);
        }
    }
}
