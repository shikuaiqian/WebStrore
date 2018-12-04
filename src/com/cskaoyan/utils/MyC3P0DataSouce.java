package com.cskaoyan.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyC3P0DataSouce {
    private  static DataSource dataSource;
    static {
        dataSource = new ComboPooledDataSource("mysql");

    }
    public static Connection getConnection() throws SQLException {
        return  dataSource.getConnection();
    }

    public static  DataSource getDataSource() throws SQLException {
        return dataSource;

    }
    public static void releaseDBResource(ResultSet rs, Statement st, Connection conn) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

