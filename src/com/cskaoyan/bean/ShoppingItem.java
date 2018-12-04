package com.cskaoyan.bean;

import java.util.Objects;

public class ShoppingItem {
    private int itemId;
    private int sid;
    private int pid;
    private int snum;
    private Product product;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "itemId=" + itemId +
                ", sid=" + sid +
                ", pid=" + pid +
                ", snum=" + snum +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingItem item = (ShoppingItem) o;
        return sid == item.sid &&
                pid == item.pid;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sid, pid);
    }
}
