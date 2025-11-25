<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.funfit.model.Batch" %>
<html>
<head>
    <title>Batches</title>
</head>
<body>

<h2>Batches</h2>

<a href="${pageContext.request.contextPath}/addBatch.html">Add Batch</a>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">

<table border="1" cellpadding="6" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Start</th>
        <th>End</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>

    <%
        List<Batch> batches = (List<Batch>) request.getAttribute("batches");
        if (batches != null) {
            for (Batch b : batches) {
    %>
    <tr>
        <td><%= b.getId() %></td>
        <td><%= b.getName() %></td>
        <td><%= b.getStartTime() %></td>
        <td><%= b.getEndTime() %></td>
        <td><%= b.getDescription() %></td>

        <td>

            <!-- EDIT button -->
            <a href="<%= request.getContextPath() %>/batches/<%= b.getId() %>">Edit</a>

            &nbsp; | &nbsp;

            <!-- DELETE button -->
            <form action="<%= request.getContextPath() %>/batches/<%= b.getId() %>"
                  method="post" style="display:inline;">
                <input type="hidden" name="_method" value="DELETE">
                <button type="submit" style="color:red;">Delete</button>
            </form>

            &nbsp; | &nbsp;

            <!-- View participants in this batch -->
            <a href="<%= request.getContextPath() %>/participants?batchId=<%= b.getId() %>">
                View Participants
            </a>

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

