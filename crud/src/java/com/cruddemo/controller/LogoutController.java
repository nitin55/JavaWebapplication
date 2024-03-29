/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.controller;

import java.io.IOException;
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
public class LogoutController extends HttpServlet{

  
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        HttpSession session  = req.getSession(false);
        if(session != null)
        {
            session.invalidate();
            req.setAttribute("errMessage", "You have logged out successfully");
            RequestDispatcher rd = req.getRequestDispatcher("login");
            rd.forward(req, resp);
        }
    }
    
}
