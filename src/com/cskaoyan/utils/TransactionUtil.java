package com.cskaoyan.utils;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    public static Connection get() {
        return threadLocal.get();
    }

    public static void beginTransaction() throws SQLException {
        Connection connection = MyC3P0DataSouce.getConnection();
        connection.setAutoCommit(false);
        threadLocal.set(connection);
    }

    public static void commitTransaction() {
        DbUtils.commitAndCloseQuietly(get());
    }

    public static void rollBackAndRelease() {
        DbUtils.rollbackAndCloseQuietly(threadLocal.get());
    }
}
