package com.cskaoyan.utils;

import java.util.UUID;

public class OrderIdUtil {
    public static int generateOrderId() {
        int oid = -1;
        while (oid < 0)
            oid = UUID.randomUUID().hashCode();
        return oid;
    }
}
