/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import java.sql.Connection;
;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nitin
 */


public class DbUtil {

    private static Connection connection = null;

    public static Connection getConnection() {

        if (connection != null) {
            return connection;
        } else {
            InputStream is = null;
            try {
                Properties prop = new Properties();
                is = DbUtil.class.getResourceAsStream("/db.properties");
                prop.load(is);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("connction is:connection "+connection);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
            //Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);

                ex.printStackTrace();
            } catch (SQLException ex) {
                //Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            return connection;
        }

    }
    
//    public static void main(String[] args) {
//      Connection c =   getConnection();
//        if(c == null)
//        {
//            System.out.println("db not connected");
//        }
//        else
//        {
//            System.out.println("db connected");
//        }
//    }
}
