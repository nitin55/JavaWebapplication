/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.filter;

import com.cruddemo.util.ValidationUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nitin
 */
public class SessionFilter implements Filter {

    private ArrayList<String> urlList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList<String>();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

       
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0); // Proxies.
        
        String url = req.getServletPath();
        System.out.println("here is servlet path from filter: " + url);
        boolean allowedRequest = false;
        if (urlList.contains(url)) {
            allowedRequest = true;
        }
         HttpSession session = req.getSession(false);
        System.out.println("allowedRequest: "+allowedRequest);
        
        
        if (!allowedRequest) {
           
            System.out.println("session: "+session.toString());
            
            if (null == session || session.getAttribute("username") == null) {
                
                ValidationUtil.getInstance().errorMsg.clear();
                System.out.println("if loop");
//                res.sendRedirect("/");
                 RequestDispatcher rd = req.getRequestDispatcher("login");
                 rd.forward(req, res);
            }
            else
            {
                
//                System.out.println(session.getAttribute("username"));
//                if(url.equalsIgnoreCase("/login") && session.getAttribute("username") != null)
//                {
//                    RequestDispatcher rd = req.getRequestDispatcher("UserController?action=listuser");
//                    rd.forward(req, res);
//                   // res.sendRedirect("UserController?action=listuser");
//                }
            }
        }
        else
        {
            ValidationUtil.getInstance().errorMsg.clear();
           
//            if(session.getAttribute("username") != null)
//            {
//                if(url.equalsIgnoreCase("/login") && session.getAttribute("username") != null)
//                {
//                    RequestDispatcher rd = req.getRequestDispatcher("UserController?action=listuser");
//                    rd.forward(req, res);
//                   // res.sendRedirect("UserController?action=listuser");
//                }
//            }
        }

        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
