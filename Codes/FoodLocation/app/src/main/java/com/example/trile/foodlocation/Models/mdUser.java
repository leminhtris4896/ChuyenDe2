package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 07/04/2018.
 */

public class mdUser {
    private String UserMail;
    private String UserPass;

    public mdUser(String userMail, String userPass) {
        UserMail = userMail;
        UserPass = userPass;
    }

    public mdUser() {
        //
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
}
