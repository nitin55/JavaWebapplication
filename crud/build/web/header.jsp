<%-- 
    Document   : header
    Created on : Jan 4, 2019, 9:51:25 AM
    Author     : nitin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if (session.getAttribute("username") != null) {
                %>
                <h2>Username is:<%= (String)session.getAttribute("username") %></h2>
                <%
            }
          %>
        		
          <a href="logout">Logout</a>
   
