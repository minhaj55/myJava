import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		Long mobi = Long.parseLong(request.getParameter("mobi"));
		String email = request.getParameter("email");
		String pass = request.getParameter("cnfpass");
		String city = request.getParameter("city");
		String dob = request.getParameter("dob");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			java.util.Date dt = sdf.parse(dob);

			java.sql.Date sqldate = new java.sql.Date(dt.getTime());

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/student", "root", "minhaj");
			PreparedStatement ps = con
					.prepareStatement("insert into detail values(?,?,?,?,?,?,?)");
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setLong(3, mobi);
			ps.setString(4, email);
			ps.setString(5, pass);
			ps.setString(6, city);
			ps.setDate(7, sqldate);
			int state = ps.executeUpdate();
			if (state != 0) {
				
				RequestDispatcher ds=request.getRequestDispatcher("Login.html");
				pw.write("<h3 style='font-size:20px;color:#16A085  ;font-family:Century Gothic;margin-top:5px;'>Registered Successfully<br>Login with<br>"+email+"<br> and Your password </h3>");
				ds.include(request, response);
				
			} else {
				pw.write("Not Inserted");
			}

		} catch (Exception a) {
			a.printStackTrace();
		}

	}
}
