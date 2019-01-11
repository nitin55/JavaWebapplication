/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.controller;

import com.cruddemo.dao.UserDao;
import com.cruddemo.model.User;
import com.cruddemo.util.ValidationUtil;
import com.cruddemo.util.constantUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nitin
 */
public class LoginController  extends HttpServlet {

    private static String LOGIN = "/login.jsp";
    private static String UserList = "/listUser.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
        System.out.println("here is login data");

        User user = new User();
        String username=req.getParameter("username");
        String password=req.getParameter("password");
       
        user.setUsername(username);
        user.setPassword(password);
        
        HashMap<String, String> validateUsername = new HashMap<>();
        validateUsername.put(constantUtil.fieldName.toString(), "username");
        validateUsername.put(constantUtil.fieldValue.toString(), req.getParameter("username"));
        validateUsername.put(constantUtil.notEmpty.toString(), "username");
        ValidationUtil.validateRules(validateUsername);
                
        HashMap<String, String> validatePassword = new HashMap<>();
        validatePassword.put(constantUtil.fieldName.toString(), "password");
        validatePassword.put(constantUtil.fieldValue.toString(), req.getParameter("password"));
        validatePassword.put(constantUtil.notEmpty.toString(), "password");
        ValidationUtil.validateRules(validatePassword);
        
        
        
        
        boolean isemptyerror = ValidationUtil.getInstance().errorMsg.isEmpty();
       
        String captcha = (String) req.getSession(false).getAttribute("captcha");
        String code = (String) req.getParameter("code");
        boolean isvalidCaptcha = false;
        if (captcha != null && code != null) {

                if (captcha.equals(code)) {
                    isvalidCaptcha = true;
                }
                else
                {
                    isvalidCaptcha = false;
                }
        }
        else
        {
            isvalidCaptcha = false;
        }
        
        if(isemptyerror && isvalidCaptcha)
        {
//            ValidationUtil.getInstance().errorMsg.clear();
            HttpSession userSession = req.getSession(true);
            System.out.println(" no error msg");
            boolean isvalidUser = new UserDao().checkUsernameAndPassword(username, password);
            
            System.out.println("isvalidUser "+isvalidUser);
            if(isvalidUser)
            {
                userSession.setAttribute("login", true);
                userSession.setAttribute("username", username);
                
                resp.sendRedirect("UserController?action=listuser");
               // req.getRequestDispatcher("/UserController?action=listUser").forward(req, resp);
            }
            else
            {
                req.setAttribute("loginError","username or password is incorect!");
                RequestDispatcher rd = req.getRequestDispatcher(LOGIN);
                rd.forward(req, resp);
            }
        }
        else
        {      
                if(!isvalidCaptcha)
                {
                    req.setAttribute("captchaError", "Invalid or Empty captcha code");
                }
                System.out.println("error: "+ValidationUtil.getInstance().errorMsg);
                req.setAttribute("user", user);
                req.setAttribute("errorMsg", ValidationUtil.getInstance().errorMsg);
                RequestDispatcher rd = req.getRequestDispatcher(LOGIN);
              //  out.println("<font color=red>Please fill all the fields</font>");
                rd.forward(req, resp);
        }
                
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        
        RequestDispatcher view = req.getRequestDispatcher(LOGIN);
        view.forward(req, resp);
    }
    
}
