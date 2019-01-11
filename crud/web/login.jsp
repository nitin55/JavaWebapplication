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

        <p style="color:Tomato;" >${errors.token}</p>
        <p style="color:green;" >${success.token}</p>
        <form action="login" method="POST">
            <% if (request.getAttribute("loginError") != null) {%>
            <p style="color:Tomato;" >${loginError}</p>

            <%
                }


            %>
            <!--          <c:if test="${not empty loginError}">
                          <p style="color:Tomato;" ><c:out value="${loginError}"/></p>
            </c:if>-->
            <br>
            Username:<input type="text" name="username" value="${user.username}"><br>
            <c:forEach items="${errorMsg}" var="usernameerror">
                <c:if test="${usernameerror.key == 'username'}">
                    <p style="color:Tomato;" >${usernameerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            password<input type="password" name="password" value="${user.password}"><br>
            <c:forEach items="${errorMsg}" var="passwordeerror">
                <c:if test="${passwordeerror.key == 'password'}">
                    <p style="color:Tomato;" >${passwordeerror.value}</p>
                </c:if>
            </c:forEach> 


            Catcha Code:<input type="text" name="code"><br>
            <% 
            
//            String captcha = (String) session.getAttribute("captcha");
//                String code = (String) request.getParameter("code");
//
//                if (captcha != null && code != null) {
//
//                    if (captcha.equals(code)) {
//                        out.print("<p class='alert'>Correct</p>");
//                    } else {
//                        out.print("<p style='color:Tomato;'>Incorrect or Empty Captcha code</p>");
//                    }
//                }
//            %>
              <% if (request.getAttribute("captchaError") != null) {%>
            <p style="color:Tomato;" >${captchaError}</p>

            <%
                }
              %>
            <br>
            <img src="CaptchaServlet"> 
            <br>
            <input type="submit" name="login" value="login"/>

        </form>
        <a href="forgotpassword">ForgotPassword</a>
    </body>
</html>
