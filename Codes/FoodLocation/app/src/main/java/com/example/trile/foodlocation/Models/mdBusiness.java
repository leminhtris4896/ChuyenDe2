package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 07/04/2018.
 */

public class mdBusiness {
    private String BusinessMail;
    private String BusinessPass;
    private String BusinessName;
    private int BusinessPhone;
    private String BusinessTime;
    private String Type;

    public mdBusiness(String businessMail, String businessPass, String businessName, int businessPhone, String businessTime, String type) {
        BusinessMail = businessMail;
        BusinessPass = businessPass;
        BusinessName = businessName;
        BusinessPhone = businessPhone;
        BusinessTime = businessTime;
        Type = type;
    }

    public mdBusiness() {
        //
    }

    public String getBusinessMail() {
        return BusinessMail;
    }

    public void setBusinessMail(String businessMail) {
        BusinessMail = businessMail;
    }

    public String getBusinessPass() {
        return BusinessPass;
    }

    public void setBusinessPass(String businessPass) {
        BusinessPass = businessPass;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public int getBusinessPhone() {
        return BusinessPhone;
    }

    public void setBusinessPhone(int businessPhone) {
        BusinessPhone = businessPhone;
    }

    public String getBusinessTime() {
        return BusinessTime;
    }

    public void setBusinessTime(String businessTime) {
        BusinessTime = businessTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
