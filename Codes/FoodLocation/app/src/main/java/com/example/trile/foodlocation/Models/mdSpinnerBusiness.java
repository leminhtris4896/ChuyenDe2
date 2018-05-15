package com.example.trile.foodlocation.Models;

import android.widget.ImageView;

public class mdSpinnerBusiness {


    private String UrlImg;
    private String strNameBusiness;

    public mdSpinnerBusiness() {
    }

    public mdSpinnerBusiness(String urlImg, String strNameBusiness) {
        UrlImg = urlImg;
        this.strNameBusiness = strNameBusiness;
    }

    public String getUrlImg() {
        return UrlImg;
    }

    public void setUrlImg(String urlImg) {
        UrlImg = urlImg;
    }
    public String getStrNameBusiness() {
        return strNameBusiness;
    }

    public void setStrNameBusiness(String strNameBusiness) {
        this.strNameBusiness = strNameBusiness;
    }
}
