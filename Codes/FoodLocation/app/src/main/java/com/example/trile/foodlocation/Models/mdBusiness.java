package com.example.trile.foodlocation.Models;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by TRILE on 09/03/2018.
 */

public class mdBusiness {

    private String strID;
    private String strEmail;
    private String strPass;
    private String strImage;
    private String strName;
    private String strPhone;
    private String strAddress;
    private String strBusinessType;
    private String strOpenTime;
    private String strScoreRating;
    private ArrayList<mdProduct> ArrayListProductList;
    private String strListCommentList;
    private Double dbLatitude;
    private Double dbLongitude;
    private Double nNumberRate;

    public mdBusiness(String strID, String strEmail, String strPass, String strImage, String strName, String strPhone, String strAddress, String strBusinessType, String strOpenTime, String strScoreRating, ArrayList<mdProduct> arrayListProductList, String strListCommentList, Double dbLatitude, Double dbLongitude, Double nNumberRate) {
        this.strID = strID;
        this.strEmail = strEmail;
        this.strPass = strPass;
        this.strImage = strImage;
        this.strName = strName;
        this.strPhone = strPhone;
        this.strAddress = strAddress;
        this.strBusinessType = strBusinessType;
        this.strOpenTime = strOpenTime;
        this.strScoreRating = strScoreRating;
        ArrayListProductList = arrayListProductList;
        this.strListCommentList = strListCommentList;
        this.dbLatitude = dbLatitude;
        this.dbLongitude = dbLongitude;
        this.nNumberRate = nNumberRate;
    }

    public ArrayList<mdProduct> getArrayListProductList() {
        return ArrayListProductList;
    }

    public void setArrayListProductList(ArrayList<mdProduct> arrayListProductList) {
        ArrayListProductList = arrayListProductList;
    }

    public Double getnNumberRate() {
        return nNumberRate;
    }

    public void setnNumberRate(Double nNumberRate) {
        this.nNumberRate = nNumberRate;
    }

    public Double getDbLatitude() {
        return dbLatitude;
    }

    public void setDbLatitude(Double dbLatitude) {
        this.dbLatitude = dbLatitude;
    }

    public Double getDbLongitude() {
        return dbLongitude;
    }

    public void setDbLongitude(Double dbLongitude) {
        this.dbLongitude = dbLongitude;
    }

    public mdBusiness() {

    }
    public String getStrID() {
        return strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public String getStrPass() {
        return strPass;
    }

    public String getStrImage() {
        return strImage;
    }

    public String getStrName() {
        return strName;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public String getStrBusinessType() {
        return strBusinessType;
    }

    public String getStrOpenTime() {
        return strOpenTime;
    }

    public String getStrScoreRating() {
        return strScoreRating;
    }


    public String getStrListCommentList() {
        return strListCommentList;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public void setStrPass(String strPass) {
        this.strPass = strPass;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public void setStrBusinessType(String strBusinessType) {
        this.strBusinessType = strBusinessType;
    }

    public void setStrOpenTime(String strOpenTime) {
        this.strOpenTime = strOpenTime;
    }

    public void setStrScoreRating(String strScoreRating) {
        this.strScoreRating = strScoreRating;
    }



    public void setStrListCommentList(String strListCommentList) {
        this.strListCommentList = strListCommentList;
    }


}
