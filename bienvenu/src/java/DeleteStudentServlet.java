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
 * Servlet implementation class DeleteStudentServlet
 */
@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bienvenu";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123";

    // Get the logger instance
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        
        logger.log(Level.INFO, "Received request to delete student with ID: {0}", id);

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "PostgreSQL JDBC Driver loaded successfully");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                logger.log(Level.INFO, "Database connection established");

                String sql = "DELETE FROM student WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        logger.log(Level.INFO, "Student with ID {0} deleted successfully", id);
                    } else {
                        logger.log(Level.WARNING, "No student found with ID {0}", id);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while deleting student with ID: " + id, e);
        }

        resp.sendRedirect("readStudent");
    }
}
