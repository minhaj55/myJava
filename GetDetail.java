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
 * Servlet implementation class GetDetail
 */
@WebServlet("/GetDetail")
public class GetDetail extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession sn = request.getSession();
		String user = "Invalid";

		long mobile = Long.parseLong(request.getParameter("mobi"));
		sn.setMaxInactiveInterval(60);

		try {
			user = (String) sn.getAttribute("user");
			if (  user !=null && user.equals("valid")){

				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/student",
								"root", "minhaj");
				PreparedStatement ps = con
						.prepareStatement("select fname , lname,email,city,dob from Detail where mobi=? ");
				ps.setLong(1, mobile);

				ResultSet StuDetail = ps.executeQuery();

				if (StuDetail.next()) {
					pw.write("<h3 style='color:#1096DE  ;font-family:Century Gothic;'>"
							+ StuDetail.getString(1)
							+ "<br>"
							+ StuDetail.getString(2)
							+ "<br>"
							+ StuDetail.getString(4)
							+ "<br>"
							+ StuDetail.getString(5) + "<br>" + "</h3>");

				} else {

					pw.write("<h3 style='color:#1096DE;font-family:Century Gothic;'> No Records Found</h3> ");
				}
			} else {
				RequestDispatcher rd = request
						.getRequestDispatcher("Login.html");
				pw.write("<h3 style='color:#1096DE  ;font-family:Century Gothic;'>You are not Logged In</h3>");
				rd.include(request, response);
			}

		} catch (ClassNotFoundException |NullPointerException | SQLException e) {

			pw.println(e);
			
		}

	}

}
