/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.controller;

import com.cruddemo.dao.UserDao;
import com.cruddemo.model.User;

import com.cruddemo.util.CsvUtil;
import com.cruddemo.util.ValidationUtil;
import static com.cruddemo.util.ValidationUtil.validateRules;
import com.cruddemo.util.constantUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nitin
 */
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";
    private String defaultSortType = "asc";
    private String defaultSortColumn = "firstname";
    private int recordPerPage = 3;
    private int page = 1;
    private int offset = 0;
    private UserDao dao;

    public UserController() {
        super();
        dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set standard HTTP/1.1 no-cache headers.
//response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
//
//// Set standard HTTP/1.0 no-cache header.
//response.setHeader("Pragma", "no-cache");

        // HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        String forward = "";
        String action = request.getParameter("action");

        System.out.println("action is: " + action);
        String sorting = defaultSortType;
        String sortby = defaultSortColumn;

        if (request.getParameter("page") != null) {
            try {
                System.out.println("inside page");
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ex) {
                //handle exception here
                ex.printStackTrace();
            }
        } else {
            page = 1;
        }

        System.out.println("page is GET :" + page);
        offset = (page - 1) * recordPerPage;
        if (request.getParameter("sorting") != null) {
            if (request.getParameter("sorting").equalsIgnoreCase("desc")) {
                sorting = "asc";
            }
            if (request.getParameter("sorting").equalsIgnoreCase("asc")) {
                sorting = "desc";
            }
        }

        if (request.getParameter("sortby") != null) {
            if (request.getParameter("sortby").equalsIgnoreCase("firstname")) {
                sortby = "firstname";
            }

            if (request.getParameter("sortby").equalsIgnoreCase("lastname")) {
                sortby = "lastname";
            }
        }

        String searchstring = "";

        if (request.getParameter("searchstring") != null) {
            searchstring = request.getParameter("searchstring");
            if (!searchstring.isEmpty() || searchstring != "") {
                //page = 1;
                // offset = 0;
            }
        }
        if(action == null)
        {
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers(searchstring, sortby, sorting, recordPerPage, offset));
        }
        else if (action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            List check = dao.getAllUsers(searchstring, sortby, sorting, recordPerPage, offset);
            if (check.isEmpty()) {
                page = 1;
                request.setAttribute("users", dao.getAllUsers(searchstring, sortby, sorting, recordPerPage, 0));

            } else {
                request.setAttribute("users", check);

            }
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = dao.getUserById(userId);
            request.setAttribute("user", user);

        } else if (action.equalsIgnoreCase("listUser")) {
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers(searchstring, sortby, sorting, recordPerPage, offset));
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("export")) {
            response.setContentType("text/csv");

            List<User> userobj = dao.getAllUsers(searchstring, sortby, sorting);

            CsvUtil<User> csv = new CsvUtil<>();
            csv.setFilename("csv.csv");
            byte[] csvdata = csv.exportCSV(userobj);
            //csv.generateCSVFile(csvdata);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + csv.getFilename() + "\"");
            try {
                OutputStream outputStream = response.getOutputStream();
                //String outputResult = "xxxx, yyyy, zzzz, aaaa, bbbb, ccccc, dddd, eeee, ffff, gggg\n";
                String data = new String(csvdata);
                outputStream.write(csvdata);
                //outputStream.write(csvdata.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            //forward = INSERT_OR_EDIT;
            forward = LIST_USER;
        }

        int userCount = dao.getUserCount();
        int numberOfPages = (int) Math.ceil(userCount * 1.0 / recordPerPage);

        System.out.println("Get page is: " + page);
        System.out.println("get search string is: " + searchstring);
        request.setAttribute("sortby", sortby);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("searchstring", searchstring);
        request.setAttribute("sorting", sorting);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();

        // Set response content type
        response.setContentType("text/html");

        String userid = request.getParameter("userid");
        System.out.println("userid: " + userid);
        String emailaddress = "";

       
        if(userid != null)
        {
        if(userid.equals("0"))
        {
            userid = "";
        }
        }
        
        System.out.println("userid: " + userid);
        
        if (userid == null || userid.isEmpty()) {
        }
        else
        {
            if(!userid.equals("0"))
            {
            int userId = Integer.parseInt(request.getParameter("userid"));
            emailaddress = dao.getUserById(userId).getEmail();
            }
        }
        System.out.println("already email: " + emailaddress);
        System.out.println("request email: " + request.getParameter("email"));
        // Actual logic goes here.
        PrintWriter out = response.getWriter();

        String searchstring = "";
        if (request.getParameter("search") != null) {
            searchstring = request.getParameter("search");
            if (!searchstring.isEmpty() || searchstring != "") {
                //page = 1;
                offset = 0;
            }
        } else if (request.getParameter("searchstring") != null) {
            searchstring = request.getParameter("searchstring");
        }
        out.println("hhhhh");
        out.println("<h1>" + request.getParameter("search") + "</h1>");
        System.out.println("hello: " + searchstring);
        System.out.println("page: " + page);
        String searchButton = "";
        if (request.getParameter("searchButton") != null) {
            searchButton = request.getParameter("searchButton");
        }
        if (searchButton.equals("search")) {
            System.out.println("search button   clicked");
            offset = 0;
            page = 1;
        } else {
            System.out.println("email: " + user);
            System.out.println("email request : " + request.getParameter("email"));
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            try {
                System.out.println("dob: " + request.getParameter("dob"));
                if (request.getParameter("dob") != null && request.getParameter("dob") != "") {
                    Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("dob"));

                    user.setDob(dob);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            user.setEmail(request.getParameter("email"));

            //validation
            System.out.println("username: " + request.getParameter("username"));
            System.out.println("password: " + request.getParameter("password"));

//            if(request.getParameter("username") != null)
//            {
            HashMap<String, String> validateUsername = new HashMap<>();
            validateUsername.put(constantUtil.fieldName.toString(), "username");
            validateUsername.put(constantUtil.fieldValue.toString(), request.getParameter("username"));
            validateUsername.put(constantUtil.notEmpty.toString(), "username");
            ValidationUtil.validateRules(validateUsername);
//            }

            HashMap<String, String> validatePassword = new HashMap<>();
            validatePassword.put(constantUtil.fieldName.toString(), "password");
            validatePassword.put(constantUtil.fieldValue.toString(), request.getParameter("password"));
            validatePassword.put(constantUtil.notEmpty.toString(), "password");
            ValidationUtil.validateRules(validatePassword);

            HashMap<String, String> validateFirstname = new HashMap<>();
            validateFirstname.put(constantUtil.fieldName.toString(), "firstname");
            validateFirstname.put(constantUtil.fieldValue.toString(), request.getParameter("firstName"));
            validateFirstname.put(constantUtil.notEmpty.toString(), "firstname");
            ValidationUtil.validateRules(validateFirstname);

            HashMap<String, String> validateLastname = new HashMap<>();
            validateLastname.put(constantUtil.fieldName.toString(), "lastname");
            validateLastname.put(constantUtil.fieldValue.toString(), request.getParameter("lastName"));
            validateLastname.put(constantUtil.notEmpty.toString(), "lastname");
            ValidationUtil.validateRules(validateLastname);

            HashMap<String, String> validateDob = new HashMap<>();
            validateDob.put(constantUtil.fieldName.toString(), "dob");
            validateDob.put(constantUtil.fieldValue.toString(), request.getParameter("dob"));
            validateDob.put(constantUtil.notEmpty.toString(), "dob");
            ValidationUtil.validateRules(validateDob);

            HashMap<String, String> validateEmail = new HashMap<>();
            validateEmail.put(constantUtil.fieldName.toString(), "email");
            validateEmail.put(constantUtil.fieldValue.toString(), request.getParameter("email"));
            validateEmail.put(constantUtil.notEmpty.toString(), "email");
            validateEmail.put(constantUtil.isValidEmail.toString(), "email");

            if (!emailaddress.equals(request.getParameter("email"))) {
                // if (userid == null || userid.isEmpty()) {
                validateEmail.put(constantUtil.isExistsEmail.toString(), "email");
            }
            // }
            ValidationUtil.validateRules(validateEmail);

            System.out.println(ValidationUtil.getInstance().errorMsg);
            boolean isemptyerror = ValidationUtil.getInstance().errorMsg.isEmpty();
            
            //end validation
            if (isemptyerror) {
//                ValidationUtil.getInstance().errorMsg.clear();
                //String userid = request.getParameter("userid");
                System.out.println("userid is: " + userid);
//                if(userid.equals("0"))
//                {
//                    System.out.println("zero");
//                }
//                if(userid == null)
//                {
//                    System.out.println("null user");
//                }
                if (userid == null || userid.isEmpty()) {
                    System.out.println("add");
                    dao.addUser(user);
                } else {
                    System.out.println("edit");
                    user.setUserid(Integer.parseInt(userid));
                    dao.updateUser(user);
                }
            } else {
                System.out.println("useridIs: "+userid);
                
                if(userid.isEmpty() || userid.equals("0"))
                {
                    
                }
                else
                {
                user.setUserid(Integer.parseInt(userid));
                }
                request.setAttribute("user", user);
                request.setAttribute("errorMsg", ValidationUtil.getInstance().errorMsg);
                RequestDispatcher rd = request.getRequestDispatcher(INSERT_OR_EDIT + "?action=insert");
                out.println("<font color=red>Please fill all the fields</font>");
                rd.forward(request, response);
            }
            System.out.println("search not button clicked");
        }

        System.out.println("search button clicked offset : " + offset);
        System.out.println("post search string is: " + searchstring);
        System.out.println("post search 2 is :" + request.getParameter("search"));
        out.println("<h1>" + request.getParameter("search") + "</h1>");
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);

        request.setAttribute("users", dao.getAllUsers(searchstring, defaultSortColumn, defaultSortType, recordPerPage, offset));

        int userCount = dao.getUserCount();
        int numberOfPages = (int) Math.ceil(userCount * 1.0 / recordPerPage);
        request.setAttribute("sortby", defaultSortColumn);
        request.setAttribute("page", page);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("searchstring", searchstring);
        request.setAttribute("sorting", defaultSortType);
        view.forward(request, response);
    }

}
