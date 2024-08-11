import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
 * Servlet implementation class ReadStudentServlet
 */
@WebServlet("/readStudent")
public class ReadStudentServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bienvenu";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123";

    // Get the logger instance
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<List<String>> students = new ArrayList<>();
        logger.log(Level.INFO, "Received request to read all students");

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "PostgreSQL JDBC Driver loaded successfully");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                logger.log(Level.INFO, "Database connection established");

                String sql = "SELECT * FROM student";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        List<String> student = new ArrayList<>();
                        student.add(String.valueOf(rs.getInt("id")));
                        student.add(rs.getString("name"));
                        student.add(String.valueOf(rs.getInt("age")));
                        student.add(rs.getString("address"));
                        students.add(student);
                    }
                    logger.log(Level.INFO, "Student records retrieved successfully");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while reading student records", e);
        }

        req.setAttribute("students", students);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
