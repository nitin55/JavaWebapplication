/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.dao;

import com.cruddemo.model.Token;
import com.cruddemo.util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nitin
 */
public class TokenDao {

    private static Connection connection;

    public TokenDao() {
        connection = DbUtil.getConnection();
    }

    public  void insertOrUpdateToken(Token tokn) {
        try {
             
            if (tokn != null) {
                System.out.println("boss here tok1 : "+tokn.getToken());
                int tokenid = getTokenIdByUserId(tokn.getUserid());
                System.out.println("token id: "+tokenid);
                String sql = "";
                PreparedStatement preparedStatement = null;
                if (tokenid != 0) {
                    sql = "update tokens set token=? "
                            + "where id=?";
                    preparedStatement = connection
                            .prepareStatement(sql);
                    preparedStatement.setString(1, tokn.getToken());
                    preparedStatement.setInt(2, tokenid);
                } else {

                    preparedStatement = connection
                            .prepareStatement("insert into tokens(userid,username,token) values (?, ?, ? )");
                    // Parameters start with 1
                    preparedStatement.setInt(1, tokn.getUserid());
                    preparedStatement.setString(2, tokn.getUsername());
                    preparedStatement.setString(3, tokn.getToken());
                }
//            preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
//            preparedStatement.setString(4, user.getEmail());
//            preparedStatement.setString(5, user.getUsername());
//            preparedStatement.setString(6, user.getPassword());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void removeToken(Token tokn) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from tokens where userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, tokn.getUserid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  boolean isValidToken(Token tokn) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {

            preparedStatement = connection.
                    prepareStatement("select count(*) as count from tokens where token=?");
            preparedStatement.setString(1, tokn.getToken());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {

                count = rs.getInt("count");

            }
            ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("count is :" + count);
        if (count > 0) {
            System.out.println("count > is :" + count);

            return true;
        }
        return false;
    }

//    public boolean removeTokenUsername(String username) {
//
//    }
//
//    public boolean removeTokenId() {
//
//    }
//
    public  String getUsernameByToken(String token) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String username = "";
        try {

            preparedStatement = connection.
                    prepareStatement("select username from tokens where token=?");
            preparedStatement.setString(1, token);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {

                username = rs.getString("username");

            }
            ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return username;
    }
//

    
//        public int getUserIdByToken(String token) {
//
//        System.out.println("boss tok2: "+token);
//        ResultSet rs = null;
//        //PreparedStatement preparedStatement = null;
//        int tokenid = 0;
//        try {
//
//             PreparedStatement preparedStatement = connection.prepareStatement("select * from tokens where token=?");
//            preparedStatement.setString(1, token);
//            rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                
//                tokenid = rs.getInt("id");
//
//            }
//            System.out.println("boss here is tokenid: "+tokenid);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } 
//        return tokenid;
//    }
        
    public int getTokenIdByUserId(int userid) {

        System.out.println("boss username: "+userid);
        ResultSet rs = null;
        //PreparedStatement preparedStatement = null;
        int tokenid = 0;
        try {

             PreparedStatement preparedStatement = connection.prepareStatement("select * from tokens where userid=?");
            preparedStatement.setInt(1, userid);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                
                tokenid = rs.getInt("id");

            }
            System.out.println("boss here is tokenid: "+tokenid);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return tokenid;
    }
}
