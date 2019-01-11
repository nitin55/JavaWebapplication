<%-- 
    Document   : login
    Created on : Jan 3, 2019, 3:31:21 PM
    Author     : nitin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>

<%
//    String path = request.getContextPath();
//    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//    String  url  =  "http://"  +  request.getServerName()  +  ":"  +  request.getServerPort()  +  request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
//    if(request.getQueryString()!=null)
//    {
//        url+="?"+request.getQueryString();
//    }
//    String result = url.substring(url.indexOf("=")+1,url.indexOf("&"));
//    System.out.println(result);
    
    String username = "";
     if(request.getAttribute("username") != null)
     {
              username =  (String)request.getAttribute("username");
      }
     
     String token = "";
     if(request.getAttribute("token") != null)
     {
              token =  (String)request.getAttribute("token");
      }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
          <p style="color:Tomato;" >${errors.passwordError}</p>
        <form action="resetpassword" method="POST">
          
            <input type="hidden" name="token" value="<%=token%>"/>
            <br>
           Username:  <input type="text" name="username" value="<%=username%>" class="form-control input-lg" readonly="readonly"/>
         
            <br>
           
            Password:<input type="text" name="password" ><br>
             <c:forEach items="${errors}" var="passworderror">
                <c:if test="${passworderror.key == 'password'}">
                    <p style="color:Tomato;" >${passworderror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
       
             Confirm Password:<input type="text" name="confirmpassword" value="${user.confirmpassword}"><br>
             <c:forEach items="${errors}" var="passworderror">
                <c:if test="${passworderror.key == 'confirmpassword'}">
                    <p style="color:Tomato;" >${passworderror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            
            <input type="submit" name="resetpassword" value="Reset Password"/>
            
        </form>
             <a href="login"> << back</a>
    </body>
</html>
