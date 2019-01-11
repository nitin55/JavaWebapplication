/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.dao;

import com.cruddemo.model.User;
import com.cruddemo.util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nitin
 */
public class UserDao {

    private Connection connection;
    public int usercount = 0;

    public UserDao() {
        connection = DbUtil.getConnection();
    }

    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users(firstname,lastname,dob,email,username,password) values (?, ?, ?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from users where userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set firstname=?, lastname=?, dob=?, email=?, password=?"
                            + "where userid=?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getUserid());
            preparedStatement.executeUpdate();

            System.out.println("password is: " + user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(String searchstring, String sortby, String sorting) {

        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = null;
            System.out.println("search :" + searchstring);
            String sql = "";
            String sql_count = "";
            if (searchstring == null || searchstring.isEmpty()) {

                sql = "select * from users  ORDER BY " + sortby + " " + sorting;
            } else {
                sql = "select * from users where firstname like '%" + searchstring + "%' ORDER BY " + sortby + " " + sorting;

            }
            System.out.println("sql query is:" + sql);
            System.out.println("sql user count query is:" + sql_count);

            rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }

//            rs = statement.executeQuery("SELECT FOUND_ROWS()");
//	     if (rs.next()) {
//	    this.usercount = rs.getInt(1);
//               }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getAllUsers(String searchstring, String sortby, String sorting, int recordPerPage, int offset) {

        System.out.println("offset is: " + offset);

        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = null;
            System.out.println("search :" + searchstring);
            String sql = "";
            String sql_count = "";
            if (searchstring == null || searchstring.isEmpty()) {
                sql_count = "select count(*) as count from users";
                sql = "select * from users  ORDER BY " + sortby + " " + sorting + " limit " + offset + "," + recordPerPage;
            } else {
                sql = "select * from users where firstname like '%" + searchstring + "%' ORDER BY " + sortby + " " + sorting + " limit " + offset + "," + recordPerPage;
                sql_count = "select count(*) as count from users where firstname like '%" + searchstring + "%'";
            }
            System.out.println("sql query is:" + sql);
            System.out.println("sql user count query is:" + sql_count);
            usercount = getUserCountsql(sql_count);

            //System.out.println("user count is: "+this.usercount);
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }

//            rs = statement.executeQuery("SELECT FOUND_ROWS()");
//	     if (rs.next()) {
//	    this.usercount = rs.getInt(1);
//               }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userId) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where userid=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setUserid(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public int getUserCountsql(String sql) {
        ResultSet rs = null;
        Statement statement = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return count;

    }

    public boolean checkUsernameAndPassword(String username, String password) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {

            preparedStatement = connection.
                    prepareStatement("select count(*) as count from users where username=? and password=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {

                count = rs.getInt("count");
            }
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

    public User findUserByName(String userName) throws SQLException {
        User thisUser = null;
        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users  WHERE username=?");

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                thisUser = new User();
                thisUser.setUserid(rs.getInt("userid"));
                thisUser.setUsername(rs.getString("username"));
                thisUser.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisUser;
    }

    public boolean isExistsEmail(String email) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {

            preparedStatement = connection.
                    prepareStatement("select count(*) as count from users where email=?");
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {

                count = rs.getInt("count");
//                user.setUserid(rs.getInt("userid"));
//                user.setFirstName(rs.getString("firstname"));
//                user.setLastName(rs.getString("lastname"));
//                user.setDob(rs.getDate("dob"));
//                user.setEmail(rs.getString("email"));
            }
//             statement = connection.createStatement(String email);
//             rs  = statement.executeQuery("select count(*) as count from users where email = '"+email+"');
//            while (rs.next()) {
//                count = rs.getInt("count");
//            }
//            rs.close();
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

    public int getUserCount() {
        System.out.println("user count is: " + this.usercount);
        return this.usercount;
    }
    public List<String> getAutocompleteData(String searchstring)
    {
        //User thisUser = null;
        List<String> searchdata = new ArrayList<String>();

        try {

            
            
            PreparedStatement stmt = connection.prepareStatement("SELECT distinct(firstname) FROM users  WHERE firstname like ? ");

           stmt.setString(1, "%" + searchstring + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                searchdata.add(rs.getString("firstname"));
//                thisUser = new User();
//                thisUser.setUserid(rs.getInt("userid"));
//                thisUser.setUsername(rs.getString("username"));
//                thisUser.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchdata;
    }
    
//    public List<String> getData(String nm) {
//String country = null;
//nm = nm.toLowerCase();
//List<String> equal = new ArrayList<String>();
//for(int i=0; i<count; i++) {
//country = name.get(i).toLowerCase();
//if(country.startsWith(nm)) {
//equal.add(name.get(i));
//}
//}
//return equal;
//}

    //updates new password in database
    public boolean changePassword(User user) throws SQLException {
        
        System.out.println("in changepassword user:");
        System.out.println("username: "+user.getUsername());
        System.out.println("password: "+user.getPassword());
         String sql = "UPDATE users set password=?  WHERE username=?";
        try 
        {
            
            PreparedStatement stmt = connection.prepareStatement(sql);

             stmt.setString(1, user.getPassword());
             stmt.setString(2, user.getUsername());
             stmt.executeUpdate();
             
           
             return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
}
}
