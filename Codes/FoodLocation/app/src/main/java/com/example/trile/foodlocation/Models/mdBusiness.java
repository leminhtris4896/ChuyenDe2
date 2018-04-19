package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 09/03/2018.
 */

public class mdBusiness {
    private String strEmail;
    private String strPass;
    private String strImage;
    private String strName;
    private String strPhone;
    private String strAddress;
    private String strBusinessType;
    private String strOpenTime;
    private String strScoreRating;
    private String strListProductList;
    private String strListCommentList;

    public mdBusiness() {
    }

    public mdBusiness(String strEmail, String strPass, String strImage, String strName, String strPhone, String strAddress, String strBusinessType, String strOpenTime, String strScoreRating, String strListProductList, String strListCommentList) {
        this.strEmail = strEmail;
        this.strPass = strPass;
        this.strImage = strImage;
        this.strName = strName;
        this.strPhone = strPhone;
        this.strAddress = strAddress;
        this.strBusinessType = strBusinessType;
        this.strOpenTime = strOpenTime;
        this.strScoreRating = strScoreRating;
        this.strListProductList = strListProductList;
        this.strListCommentList = strListCommentList;
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

    public String getStrListProductList() {
        return strListProductList;
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

    public void setStrListProductList(String strListProductList) {
        this.strListProductList = strListProductList;
    }

    public void setStrListCommentList(String strListCommentList) {
        this.strListCommentList = strListCommentList;
    }
}
