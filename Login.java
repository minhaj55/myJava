import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pwd");
		HttpSession sess=request.getSession();
		String validUser=null;
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/student", "root", "minhaj");
			PreparedStatement ps = con
					.prepareStatement("select email , pass from Detail where email=? and  pass=? ");
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet b = ps.executeQuery();

			if (b.next()) {
				
				validUser="valid";
				sess.setAttribute("user", validUser);
				sess.setMaxInactiveInterval(60);
				RequestDispatcher rd = request
						.getRequestDispatcher("Home.html");
				pw.write("<h3 style='color:#1096DE  ;font-family:Century Gothic;'> Welcome\t" + b.getString(1) + "<br></h3>");
				rd.include(request, response);
			} else {
				validUser="inValid";
				sess.setAttribute("user", validUser);
				//sess.invalidate();
				pw.write("<h3 style='color:#E22446    ;font-family:Century Gothic;'> Invalid User Or Password</h3>");
				RequestDispatcher rd = request
						.getRequestDispatcher("Login.html");
				rd.include(request, response);
				
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}

}
