package com.cskaoyan.bean;

public class Product {
    private int pid;
    private String pname;
    private double estorePrice;
    private double markPrice;
    private int pnum;
    private int cid;
    private String imgUrl;
    private String description;
    private Category category;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getEstorePrice() {
        return estorePrice;
    }

    public void setEstorePrice(double estorePrice) {
        this.estorePrice = estorePrice;
    }

    public double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(double markPrice) {
        this.markPrice = markPrice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", estorePrice=" + estorePrice +
                ", markPrice=" + markPrice +
                ", pnum=" + pnum +
                ", cid=" + cid +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}
