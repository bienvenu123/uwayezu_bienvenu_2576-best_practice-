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
 * Servlet implementation class CreateStudentServlet
 */
@WebServlet("/createStudent")
public class CreateStudentServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bienvenu";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123";

    // Get the logger instance
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String address = req.getParameter("address");

        logger.log(Level.INFO, "Received request to create a new student: Name = {0}, Age = {1}, Address = {2}", new Object[]{name, age, address});

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "PostgreSQL JDBC Driver loaded successfully");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                logger.log(Level.INFO, "Database connection established");

                String sql = "INSERT INTO student (name, age, address) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, age);
                    stmt.setString(3, address);
                    stmt.executeUpdate();
                    logger.log(Level.INFO, "Student record inserted successfully");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while creating a student", e);
        }

        resp.sendRedirect("readStudent");
    }
}
