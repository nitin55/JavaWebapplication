/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cruddemo.dao.UserDao;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

public class AutoCompleteController extends HttpServlet {

    public AutoCompleteController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        resp.setHeader("Cache-control", "no-cache, no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "-1");

        //JSONArray
        
        JSONArray arrayObj=new JSONArray();
        String query = req.getParameter("term");
        System.out.println(query);

        List<String> name = new UserDao().getAutocompleteData(query);

        Iterator<String> itr = name.iterator();
        while (itr.hasNext()) {
            String first = (String) itr.next();
           // System.out.println("firstname is: "+first);
            arrayObj.put(first);
          //  out.println(country);
        }
        
        out.println(arrayObj.toString());
         out.close();
        

//        query = query.toLowerCase();
//        for(int i=0; i<COUNTRIES.length; i++) {
//            String country = COUNTRIES[i].toLowerCase();
//            if(country.startsWith(query)) {
//                arrayObj.add(COUNTRIES[i]);
//            }
//        }
//        out.println(arrayObj.toString());
//        out.close();
    }

}
