package com.example.trile.foodlocation.Models;

public class mdUserStatusRate {
    private String strIDBusiness;
    private String strStartRate;

    public mdUserStatusRate(String strIDBusiness, String strStartRate) {
        this.strIDBusiness = strIDBusiness;
        this.strStartRate = strStartRate;
    }

    public mdUserStatusRate() {
    }


    public String getStrIDBusiness() {
        return strIDBusiness;
    }

    public void setStrIDBusiness(String strIDBusiness) {
        this.strIDBusiness = strIDBusiness;
    }

    public String getStrStartRate() {
        return strStartRate;
    }

    public void setStrStartRate(String strStartRate) {
        this.strStartRate = strStartRate;
    }
}
