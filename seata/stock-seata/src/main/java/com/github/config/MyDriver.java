package com.github.config;

import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.jdbc.NonRegisteringDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyDriver extends NonRegisteringDriver implements java.sql.Driver {
    public MyDriver() throws SQLException {
    }

    static {
        try {
            DriverManager.registerDriver(new MyDriver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
//        return MyConnectionV2.getInstance(conStr.getMainHost());
        Connection connection =  super.connect(url, info);
        return new MyConnection(connection);
        // return connection;
    }
}
