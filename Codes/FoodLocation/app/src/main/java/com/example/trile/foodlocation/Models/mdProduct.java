package com.example.trile.foodlocation.Models;

public class mdProduct  {

    private String strProductName;
    private  String strDescription;
    private Integer nPrice;
    private String strURLImage;

    public mdProduct() {
    }

    public mdProduct(String strProductName, String strDescription, Integer nPrice, String strURLImage) {
        this.strProductName = strProductName;
        this.strDescription = strDescription;
        this.nPrice = nPrice;
        this.strURLImage = strURLImage;
    }

    public String getStrProductName() {
        return strProductName;
    }

    public void setStrProductName(String strProductName) {
        this.strProductName = strProductName;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public Integer getnPrice() {
        return nPrice;
    }

    public void setnPrice(Integer nPrice) {
        this.nPrice = nPrice;
    }

    public String getStrURLImage() {
        return strURLImage;
    }

    public void setStrURLImage(String strURLImage) {
        this.strURLImage = strURLImage;
    }
}
