package com.example.trile.foodlocation.Models;

import java.util.ArrayList;

/**
 * Created by TRILE on 07/04/2018.
 */

public class mdUser {
    private String UserMail;
    private String UserPass;
    private String UserID;
    private ArrayList<String> arrayListLichSuHoatDong;

    private ArrayList<mdUserStatusPost> arrayListUserStatusPost;

    public mdUser() {
        //
    }

    public mdUser(String userMail, String userPass, String userID, ArrayList<String> arrayListLichSuHoatDong, ArrayList<mdUserStatusPost> arrayListUserStatusPost) {
        UserMail = userMail;
        UserPass = userPass;
        UserID = userID;
        this.arrayListLichSuHoatDong = arrayListLichSuHoatDong;
        this.arrayListUserStatusPost = arrayListUserStatusPost;
    }


    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public ArrayList<String> getArrayListLichSuHoatDong() {
        return arrayListLichSuHoatDong;
    }

    public void setArrayListLichSuHoatDong(ArrayList<String> arrayListLichSuHoatDong) {
        this.arrayListLichSuHoatDong = arrayListLichSuHoatDong;
    }

    public ArrayList<mdUserStatusPost> getArrayListUserStatusPost() {
        return arrayListUserStatusPost;
    }

    public void setArrayListUserStatusPost(ArrayList<mdUserStatusPost> arrayListUserStatusPost) {
        this.arrayListUserStatusPost = arrayListUserStatusPost;
    }
}
