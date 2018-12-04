package com.cskaoyan.form;

public class SearchCondition {

    private String pid;
    private String pname;
    private String cid;
    private String maxPrice;
    private String minPrice;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", cid='" + cid + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", minPrice='" + minPrice + '\'' +
                '}';
    }
}
