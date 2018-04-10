package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 10/04/2018.
 */

public class mdSpinnerPlace {
    private int imgSpinner;
    private String strType;

    public mdSpinnerPlace(int imgSpinner, String strType) {
        this.imgSpinner = imgSpinner;
        this.strType = strType;
    }

    public mdSpinnerPlace() {
    }

    public int getImgSpinner() {
        return imgSpinner;
    }

    public void setImgSpinner(int imgSpinner) {
        this.imgSpinner = imgSpinner;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
