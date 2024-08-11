<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String name = "";
    String age = "";
    String address = "";
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/bienvenu", "root", "");
        String sql = "SELECT * FROM student WHERE id=?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        if (rs.next()) {
            name = rs.getString("name");
            age = rs.getString("age");
            address = rs.getString("address");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Student</title>
</head>
<body>
    <h2>Update Student</h2>
    <form action="updateStudent" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <label>Name:</label><input type="text" name="name" value="<%= name %>" required>
        <label>Age:</label><input type="number" name="age" value="<%= age %>" required>
        <label>Grade:</label><input type="text" name="address" value="<%= address %>" required>
        <button type="submit">Update Student</button>
    </form>
    <a href="readStudent">Back to Student List</a>
</body>
</html>
