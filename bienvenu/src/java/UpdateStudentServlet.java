import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Import LoggerUtil
import util.LoggerUtil;
import java.util.logging.Logger;

/**
 * Servlet implementation class UpdateStudentServlet
 */
@WebServlet("/updateStudent")
public class UpdateStudentServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bienvenu";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123";

    // Get the logger instance
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String address = req.getParameter("address");

        logger.log(Level.INFO, "Received request to update student: ID = {0}, Name = {1}, Age = {2}, Address = {3}", 
                   new Object[]{id, name, age, address});

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "PostgreSQL JDBC Driver loaded successfully");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                logger.log(Level.INFO, "Database connection established");

                String sql = "UPDATE student SET name=?, age=?, address=? WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, age);
                    stmt.setString(3, address);
                    stmt.setInt(4, id);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        logger.log(Level.INFO, "Student with ID {0} updated successfully", id);
                    } else {
                        logger.log(Level.WARNING, "No student found with ID {0}", id);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating student with ID: " + id, e);
        }

        resp.sendRedirect("readStudent");
    }
}
