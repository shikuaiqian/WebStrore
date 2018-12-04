package com.cskaoyan.bean;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private int sid;
    private int uid;
    private List<ShoppingItem> shoppingItems = new ArrayList<>();

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", shoppingItems=" + shoppingItems +
                '}';
    }
}
