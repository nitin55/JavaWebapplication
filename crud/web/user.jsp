<%-- 
    Document   : user
    Created on : Dec 29, 2018, 11:50:33 AM
    Author     : nitin
--%>

<%@ include file="header.jsp" %>

        <form method="POST" action='UserController' name="frmAddUser">
            <input type ="hidden" name="searchstring" value="${searchstring}" />
            <% String action = request.getParameter("action"); %>
            
             <input type="hidden"  name="userid"
                             value="<c:out value="${user.userid}" />" /> <br /> 
            
           
            User Name : <input
                type="text" name="username"
                value="<c:out value="${user.username}" />" /> <br /> 
            
            <c:forEach items="${errorMsg}" var="usernameerror">
                <c:if test="${usernameerror.key == 'username'}">
                    <p style="color:Tomato;" >${usernameerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>

            password : <input
                type="password" name="password"
                value="<c:out value="${user.password}" />" /> <br /> 

            <c:forEach items="${errorMsg}" var="passworderror">
                <c:if test="${passworderror.key == 'password'}">
                    <p style="color:Tomato;" >${passworderror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            First Name : <input
                type="text" name="firstName"
                value="<c:out value="${user.firstName}" />" /> <br /> 

            <c:forEach items="${errorMsg}" var="firstnameerror">
                <c:if test="${firstnameerror.key == 'firstname'}">
                    <p style="color:Tomato;" >${firstnameerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            Last Name : <input
                type="text" name="lastName"
                value="<c:out value="${user.lastName}" />" /> <br /> 
            <c:forEach items="${errorMsg}" var="lastnameerror">
                <c:if test="${lastnameerror.key == 'lastname'}">
                    <p style="color:Tomato;" >${lastnameerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            DOB : <input
                type="text" name="dob"
                value="<fmt:formatDate pattern="MM/dd/yyyy" value="${user.dob}" />" /> <br /> 
            <c:forEach items="${errorMsg}" var="doberror">
                <c:if test="${doberror.key == 'dob'}">
                    <p style="color:Tomato;" >${doberror.value}</p>
                </c:if>
            </c:forEach> 
            <br>
            Email : <input type="text" name="email"
                           value="<c:out value="${user.email}" />" /> <br />
            <c:forEach items="${errorMsg}" var="emailerror">
                <c:if test="${emailerror.key == 'email'}">
                    <p style="color:Tomato;">${emailerror.value}</p>
                </c:if>
            </c:forEach> 
            <br>

            <input
                type="submit" value="Submit"  />
        </form>
            <a href="UserController">Home</a>
  <%@ include file="footer.jsp" %>