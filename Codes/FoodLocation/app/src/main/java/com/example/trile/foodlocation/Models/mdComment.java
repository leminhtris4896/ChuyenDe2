package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 19/03/2018.
 */

public class mdComment {
    private String imgUserComment;
    private String tvComment;



    public mdComment() {
        // Return
    }

    public mdComment(String imgUserComment, String tvComment) {
        this.imgUserComment = imgUserComment;
        this.tvComment = tvComment;
    }


    public String getImgUserComment() {
        return imgUserComment;
    }

    public void setImgUserComment(String imgUserComment) {
        this.imgUserComment = imgUserComment;
    }

    public String getTvComment() {
        return tvComment;
    }

    public void setTvComment(String tvComment) {
        this.tvComment = tvComment;
    }
}
