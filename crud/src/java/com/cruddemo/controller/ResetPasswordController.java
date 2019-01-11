/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.controller;

import com.cruddemo.dao.TokenDao;
import com.cruddemo.dao.UserDao;
import com.cruddemo.model.Token;
import com.cruddemo.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nitin
 */
public class ResetPasswordController extends HttpServlet {

    private TokenDao tokendao;

    public ResetPasswordController() {
        
        super();
        tokendao = new TokenDao();
        
        System.out.println("ForgotPasswordController constructor");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.

        System.out.println("here is post request...");
        String userName = req.getParameter("username");
        String token = req.getParameter("token");
        String newPassword = req.getParameter("password");
        String newPassword2 = req.getParameter("confirmpassword");
        Map<String, String> errors = new HashMap<String, String>();
        Map<String, String> success = new HashMap<String, String>();
        System.out.println("token is: "+token);
        if (token == null || "".equals(token)) {
            errors.put("token", "token is empty");
        }

        if (newPassword == null || "".equals(newPassword)) {
            errors.put("password", "new passwold can not be blank");
        }

        if (newPassword2 == null || "".equals(newPassword2)) {
            errors.put("confirmpassword", "new password can not be blank");
        }

        if (!newPassword.equals(newPassword2)) {
            errors.put("passwordError", "two passwords are different");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
//            req.getRequestDispatcher("/resetpassword?username=" + userName);
//                    
//                    
//            req.forward(req, resp);

//            String path = req.getContextPath();
//    String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
//    String  url  =  "http://"  +  req.getServerName()  +  ":"  +  req.getServerPort()  +  req.getContextPath()+req.getServletPath().substring(0,req.getServletPath().lastIndexOf("/")+1);
//    if(req.getQueryString()!=null)
//    {
//        url+="?"+req.getQueryString();
//    }
//            System.out.println("request path: "+url);
            System.out.println("here is username: " + userName);
            req.setAttribute("username", userName);
            req.setAttribute("token", token);
            // RequestDispatcher view =  req.getRequestDispatcher("/resetPassword.jsp?username=" + userName);

            RequestDispatcher view = req.getRequestDispatcher("/resetPassword.jsp");
            // resp.sendRedirect("/crud/resetPassword.jsp?username=" + userName);
            view.forward(req, resp);
            return;
        }

        User userdata = null;
        try {
            userdata = new UserDao().findUserByName(userName);
//              if(userdata == null)
//              {
//                   req.setAttribute("invalidMsg", userName + "is not exist!");
//                   
//		   req.getRequestDispatcher("login").forward(req, resp);
//                   return;
//              }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userdata.setPassword(newPassword);

        UserDao dao = new UserDao();
        
        System.out.println("hiiiiiii");

        try {
            if (dao.changePassword(userdata)) {
                Token tok = new Token();
                tok.setUserid(userdata.getUserid());
                tokendao.removeToken(tok);
                success.put("token", "Successfully reset password..");
                req.setAttribute("username", userName);
                req.setAttribute("success", success);
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("login");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("here is get request");

        Map<String, String> errors = new HashMap<String, String>();
        
        String token = req.getParameter("token");
        req.setAttribute("token", token);
        String urlpath = "";
        if (token == null || token == "") {
            errors.put("token", "token is null");
            urlpath = "/login.jsp";
        } else {

            Token tok = new Token();
            tok.setToken(token);
            boolean isvalidToken = tokendao.isValidToken(tok);
            if (isvalidToken) {
                String username = tokendao.getUsernameByToken(tok.getToken());
                if (token == null || "".equals(token)) {
                    errors.put("token", "token user not found");
                    urlpath = "/login.jsp";
                } else {
                    req.setAttribute("username", username);
                    req.setAttribute("token", token);
                    urlpath = "/resetPassword.jsp";
                }

            } else {
                errors.put("token", "token is expired or invalid");
                urlpath = "/login.jsp";
            }

        }
        req.setAttribute("errors", errors);
        RequestDispatcher view = req.getRequestDispatcher(urlpath);
        view.forward(req, resp);
//            String userName = req.getParameter("username");
//		
//              req.setAttribute("username", userName);
//		
//                req.getRequestDispatcher("/resetpassword.jsp").forward(req, resp);

        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

}
