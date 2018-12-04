package com.cskaoyan.test;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoTest {




    @Test
    public void testMulitConditionSearch(){


        ProductDao dao = new ProductDaoImpl();

        String pid="";
        String cid="2";
        String pname = "%跑鞋%";
        String minprice = "200";
        String maxprice = "250";
        try {
            List<Product> productsByMultiCondition = dao.findProductsByMultiCondition(pid, cid, pname, minprice, maxprice);
            System.out.println("list="+productsByMultiCondition);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
