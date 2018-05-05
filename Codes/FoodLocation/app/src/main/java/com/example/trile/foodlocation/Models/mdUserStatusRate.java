package com.example.trile.foodlocation.Models;

public class mdUserStatusRate {
    private String strIDBusiness;
    private String strStartRate;

    public Boolean getStatusRate() {
        return statusRate;
    }

    public void setStatusRate(Boolean statusRate) {
        this.statusRate = statusRate;
    }

    private Boolean statusRate;


    public mdUserStatusRate() {
    }

    public mdUserStatusRate(String strIDBusiness, String strStartRate, Boolean statusRate) {
        this.strIDBusiness = strIDBusiness;
        this.strStartRate = strStartRate;
        this.statusRate = statusRate;
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
