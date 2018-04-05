package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 04/04/2018.
 */

public class mdPlace {
    private int imgProduct;
    private String businessName;
    private String businessAddress;
    private String businessWorkingtime;
    private String businessVote;

    public mdPlace(int imgProduct, String businessName, String businessAddress, String businessWorkingtime, String businessVote) {
        this.imgProduct = imgProduct;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessWorkingtime = businessWorkingtime;
        this.businessVote = businessVote;
    }

    public mdPlace() {
        //
    }

    public int getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(int imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessWorkingtime() {
        return businessWorkingtime;
    }

    public void setBusinessWorkingtime(String businessWorkingtime) {
        this.businessWorkingtime = businessWorkingtime;
    }

    public String getBusinessVote() {
        return businessVote;
    }

    public void setBusinessVote(String businessVote) {
        this.businessVote = businessVote;
    }
}
