

<%@ include file="header.jsp" %>
<p>
    <jsp:include page="search.jsp" >

        <jsp:param name="searchstring" value="${searchstring}" />


    </jsp:include>


    <a href="UserController?action=insert">Add User</a>

    <a href="UserController?action=export&sortby=${sortby}&searchstring=${searchstring}&sorting=${sorting}">Export CSV</a>
</p>
<table border=1>
    <thead>
        <tr>
            <th>User Id</th>
            <th><a href="UserController?action=listuser&page=${page}&sortby=username&searchstring=${searchstring}&sorting=${sorting}">User Name</a></th>
            <th><a href="UserController?action=listuser&page=${page}&sortby=firstname&searchstring=${searchstring}&sorting=${sorting}">First Name</a></th>
            <th><a href="UserController?action=listuser&page=${page}&sortby=lasttname&searchstring=${searchstring}&sorting=${sorting}">Last Name</a></th>
            <th>DOB</th>
            <th>Email</th>
            <th colspan=2>Action</th>
        </tr>
    </thead>
    <tbody>
       
        <%
        
        String currentUsername  = "";
        if(session.getAttribute("username") != null)
{
        currentUsername = (String)session.getAttribute("username");
} 
        %>
        <c:set var="currentUser" value="<%= currentUsername %>"/>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.userid}" /></td>
                <td><c:out value="${user.username}" /></td>
                <td><c:out value="${user.firstName}" /></td>
                <td><c:out value="${user.lastName}" /></td>
                <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${user.dob}" /></td>
                <td><c:out value="${user.email}" /></td>

       
        
                <c:choose>
                    <c:when test='${user.username.equals(currentUser)}'>
                        
                         <td>current login</td>
                        <td>current login</td>
                        
                    </c:when>    
                    <c:otherwise>
                       <td><a href="UserController?action=edit&userId=<c:out value="${user.userid}&page=${page}&searchstring=${searchstring}"/>">Update</a></td>
                        <td><a href="UserController?action=delete&userId=<c:out value="${user.userid}&page=${page}&searchstring=${searchstring}"/>">Delete</a></td>
                    </c:otherwise>
                </c:choose>




            </tr>
        </c:forEach>


    <div> 
        <c:if test="${numberOfPages > 1}">
            <div style="float: left;">
                <!--For displaying previous link except for the 1st page -->
                <c:if test="${page != 1}">
                    <td>

                        <a href="UserController?action=listuser&page=${page-1}&sortby=${sortby}&searchstring=${searchstring}&sorting=${sorting}">Previous</a>

                    </td>
                </c:if>
            </div>

            <div id="div3" style="float: left;"> 
                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
                <table border="1" cellpadding="3" cellspacing="0">
                    <tr>
                        <c:forEach begin="1" end="${numberOfPages}" var="i">
                            <c:choose>
                                <c:when test="${page eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <a href="UserController?action=listuser&page=${i}&sortby=${sortby}&searchstring=${searchstring}">${i}</a>                                
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </table>
            </div>

            <div style="float: left;">
                <%--For displaying Next link except for the last page --%>
                <c:if test="${page lt numberOfPages}">
                    <td>
                        <a href="UserController?action=listuser&page=${page+1}&sortby=${sortby}&searchstring=${searchstring}&sorting=${sorting}">Next</a>                                



                    </td>
                </c:if>
            </div>
        </c:if>
    </div>
</tbody>
</table>

<jsp:include page="footer.jsp" ></jsp:include>
