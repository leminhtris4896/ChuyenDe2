package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 19/03/2018.
 */

public class mdComment {
    private int imgUserComment;
    private String tvComment;

    public mdComment(int imgUserComment, String tvComment) {
        this.imgUserComment = imgUserComment;
        this.tvComment = tvComment;
    }

    public mdComment() {
        // Return
    }

    public int getImgUserComment() {
        return imgUserComment;
    }

    public void setImgUserComment(int imgUserComment) {
        this.imgUserComment = imgUserComment;
    }

    public String getTvComment() {
        return tvComment;
    }

    public void setTvComment(String tvComment) {
        this.tvComment = tvComment;
    }
}
