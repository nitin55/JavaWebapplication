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
import com.cruddemo.util.EmailUtils;
import com.cruddemo.util.GenerateLinkUtils;
import com.cruddemo.util.ValidationUtil;
import com.cruddemo.util.constantUtil;
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
public class ForgotPasswordController extends HttpServlet{

    private static String FORGOTPASSWORD = "/forgotpassword.jsp";
    
     private TokenDao tokendao;

    public ForgotPasswordController() {
        
        super();
        tokendao = new TokenDao();
        
        System.out.println("ForgotPasswordController constructor");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
        System.out.println("here is forgotpassword");
        String username = req.getParameter("username");
        User user = new User();
        user.setUsername(username);
        Map<String, String> success = new HashMap<String, String>();
        HashMap<String, String> validateUsername = new HashMap<>();
        validateUsername.put(constantUtil.fieldName.toString(), "username");
        validateUsername.put(constantUtil.fieldValue.toString(), req.getParameter("username"));
        validateUsername.put(constantUtil.notEmpty.toString(), "username");
        ValidationUtil.validateRules(validateUsername);
        
        boolean isemptyerror = ValidationUtil.getInstance().errorMsg.isEmpty();
       
        if(isemptyerror)
        {
            System.out.println("username is :"+username);
            User userdata = null;
            try
            {
              userdata = new UserDao().findUserByName(username);
                System.out.println("userdata is: "+userdata);
              if(userdata == null)
              {
                   req.setAttribute("invalidMsg", username + "is not exist!");
                   
		   req.getRequestDispatcher(FORGOTPASSWORD).forward(req, resp);
                   return;
              }
              else
              {
                  System.out.println("email is sending now: ");
                 
                  String token = GenerateLinkUtils.generateCheckcode(userdata);
                  
                  
                  boolean isSendMail = EmailUtils.sendResetPasswordEmail(userdata,token);
                  if(isSendMail)
                  {
                      Token tok = new Token();
                      tok.setUserid(userdata.getUserid());
                      tok.setToken(token);
                      tok.setUsername(userdata.getUsername());
//                      
                     tokendao.insertOrUpdateToken(tok);
                     success.put("token","Successfully send Password reset link in your email id Please check.");
                     req.setAttribute("success", success);
                  }
                  else
                  {
                       req.setAttribute("invalidMsg", "unable to reset password.Please contact admin");
                   
                       req.getRequestDispatcher(FORGOTPASSWORD).forward(req, resp);
                       return;
                  }
                  
                  req.getRequestDispatcher("/login.jsp").forward(req, resp);
                   return;
              }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            
            
        }
         else
        {
                System.out.println("error: "+ValidationUtil.getInstance().errorMsg);
                req.setAttribute("user", user);
                req.setAttribute("errorMsg", ValidationUtil.getInstance().errorMsg);
                RequestDispatcher rd = req.getRequestDispatcher(FORGOTPASSWORD);
              //  out.println("<font color=red>Please fill all the fields</font>");
                rd.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
         RequestDispatcher view = req.getRequestDispatcher(FORGOTPASSWORD);
        view.forward(req, resp);
    }
    
}
