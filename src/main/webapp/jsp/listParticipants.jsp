<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.funfit.model.Participant" %>
<html>
<head>
    <title>Participants</title>
</head>
<body>

<h2>Participants</h2>

<a href="${pageContext.request.contextPath}/addParticipant.html">Add Participant</a>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">

<table border="1" cellpadding="6" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Batch</th>
        <th>Actions</th>
    </tr>

    <%
        List<Participant> list = (List<Participant>) request.getAttribute("participants");
        if (list != null) {
            for (Participant p : list) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getName() %></td>
        <td><%= p.getEmail() %></td>
        <td><%= p.getPhone() %></td>
        <td><%= p.getBatchId() %></td>

        <td>

            <!-- EDIT button -->
            <a href="<%= request.getContextPath() %>/participants/<%= p.getId() %>">Edit</a>

            &nbsp; | &nbsp;

            <!-- DELETE button -->
            <form action="<%= request.getContextPath() %>/participants/<%= p.getId() %>"
                  method="post" style="display:inline;">
                <input type="hidden" name="_method" value="DELETE">
                <button type="submit" style="color:red;">Delete</button>
            </form>

        </td>
    </tr>
    <%      }
        }
    %>

</table>

<br>
<a href="<%= request.getContextPath() %>/index.html">Home</a>

</body>
</html>

