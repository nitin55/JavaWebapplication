<%-- 
    Document   : login
    Created on : Jan 3, 2019, 3:31:21 PM
    Author     : nitin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
          <% if(request.getAttribute("invalidMsg") != null)
            {%>
                   <p style="color:Tomato;" >${invalidMsg}</p>
                
                <%
            }
                    
            
            %>
        <form action="forgotpassword" method="POST">
          

            <br>
            Username:<input type="text" name="username" value="${user.username}"><br>
             <c:forEach items="${errorMsg}" var="usernameerror">
                <c:if test="${usernameerror.key == 'username'}">
                    <p style="color:Tomato;" >${usernameerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
           
       
            <input type="submit" name="forgotpassword" value="Reset Password"/>
            
        </form>
             <a href="login"> << back</a>
    </body>
</html>
