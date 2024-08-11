<%@page import="java.util.List"%>
<%@page import="util.LoggerUtil"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Management</title>
</head>
<body>
    <%
        // Get the logger instance
        Logger logger = LoggerUtil.getLogger();
        logger.log(Level.INFO, "Accessing the Student Management JSP page");
    %>

    <h2>Student Management</h2>
    <form action="createStudent" method="post">
        <label>Name:</label><input type="text" name="name" required>
        <label>Age:</label><input type="number" name="age" required>
        <label>Address:</label><input type="text" name="address" required>
        <button type="submit">Add Student</button>
    </form>
    <hr>
    <h3>Student List</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
        <% 
            List<List<String>> students = (List<List<String>>) request.getAttribute("students");
            if (students != null) {
                logger.log(Level.INFO, "Rendering student list with {0} entries", students.size());
                for (List<String> student : students) {
        %>
        <tr>
            <td><%= student.get(0) %></td>
            <td><%= student.get(1) %></td>
            <td><%= student.get(2) %></td>
            <td><%= student.get(3) %></td>
            <td>
                <form action="deleteStudent" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= student.get(0) %>">
                    <button type="submit">Delete</button>
                </form>
                <form action="updateForm.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= student.get(0) %>">
                    <button type="submit">Edit</button>
                </form>
            </td>
        </tr>
        <% 
                }
            } else {
                logger.log(Level.WARNING, "No students found in the database");
        %>
        <tr>
            <td colspan="5">No students found.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>
