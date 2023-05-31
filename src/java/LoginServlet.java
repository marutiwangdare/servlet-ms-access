import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

   // String databasePath = getClass().getResource("/database/Q4.mdb").getPath();
    String databaseURL = "jdbc:ucanaccess://C:\\db/Q4.mdb";
Path currentRelativePath = Paths.get("");
String s = currentRelativePath.toAbsolutePath().toString();



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        request.setAttribute("path", s);
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            String query = "SELECT * FROM user WHERE user_name = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Successful login
                response.sendRedirect("welcome.jsp"); // Redirect to a welcome page
            } else {
                // Invalid login
                response.sendRedirect("login.jsp?error=1"); // Redirect back to the login page with an error parameter
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
        request.setAttribute("exception", e);
        request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }
    }
}
