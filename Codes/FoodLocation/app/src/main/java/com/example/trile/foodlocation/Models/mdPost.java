package com.example.trile.foodlocation.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TRILE on 10/03/2018.
 */

public class mdPost {

    private String postID;
    private String nameProduct;
    private String descriptionProduct;
    private String imgProduct;
    private String nNumberLike;
    private String nNumberUnlike;
    private String nNumberComment;
    private boolean isCheckLike;
    private boolean isCheckUnLike;
    private String lienKetDiaDiem;
    private ArrayList<mdComment> arrayListCommentPost;

    public mdPost() {
    }

    public mdPost(String postID, String nameProduct, String descriptionProduct, String imgProduct, String nNumberLike, String nNumberUnlike, String nNumberComment, boolean isCheckLike, boolean isCheckUnLike, String lienKetDiaDiem, ArrayList<mdComment> arrayListCommentPost) {
        this.postID = postID;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.imgProduct = imgProduct;
        this.nNumberLike = nNumberLike;
        this.nNumberUnlike = nNumberUnlike;
        this.nNumberComment = nNumberComment;
        this.isCheckLike = isCheckLike;
        this.isCheckUnLike = isCheckUnLike;
        this.lienKetDiaDiem = lienKetDiaDiem;
        this.arrayListCommentPost = arrayListCommentPost;
    }


    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getnNumberLike() {
        return nNumberLike;
    }

    public void setnNumberLike(String nNumberLike) {
        this.nNumberLike = nNumberLike;
    }

    public String getnNumberUnlike() {
        return nNumberUnlike;
    }

    public void setnNumberUnlike(String nNumberUnlike) {
        this.nNumberUnlike = nNumberUnlike;
    }

    public String getnNumberComment() {
        return nNumberComment;
    }

    public void setnNumberComment(String nNumberComment) {
        this.nNumberComment = nNumberComment;
    }

    public boolean isCheckLike() {
        return isCheckLike;
    }

    public void setCheckLike(boolean checkLike) {
        isCheckLike = checkLike;
    }

    public boolean isCheckUnLike() {
        return isCheckUnLike;
    }

    public void setCheckUnLike(boolean checkUnLike) {
        isCheckUnLike = checkUnLike;
    }

    public String getLienKetDiaDiem() {
        return lienKetDiaDiem;
    }

    public void setLienKetDiaDiem(String lienKetDiaDiem) {
        this.lienKetDiaDiem = lienKetDiaDiem;
    }

    public ArrayList<mdComment> getArrayListCommentPost() {
        return arrayListCommentPost;
    }

    public void setArrayListCommentPost(ArrayList<mdComment> arrayListCommentPost) {
        this.arrayListCommentPost = arrayListCommentPost;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

}
