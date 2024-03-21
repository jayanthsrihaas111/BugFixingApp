package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;   
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
 
public class RegisterServlet extends HttpServlet {
			 
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {


		String username = "", password = "", dob = "", email = "", user_id = "";
		
		JSONObject jsontosend = new JSONObject();

		HttpServletRequest request = req;
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(myjsonString);
			try {
				JSONObject json = new JSONObject(myjsonString);
				System.out.println(json);
				username = json.get("username").toString();
				password = json.get("password").toString();
				dob = json.get("dob").toString();
				email = json.get("email").toString();
				user_id = json.get("user_id").toString();
				
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

					System.out.println("Connected to PostgreSQL database!");

					Statement statement = connection.createStatement();

		            					
					ResultSet rs = statement.executeQuery(String.format("select * from users where (username = '%s' or email = '%s' or user_id = '%s')", username, email, user_id)); 

		            if (rs.next()) {
		            	jsontosend.put("userexists", "1");
		            }
		            else
		            {
						jsontosend.put("userexists", "0");
						String query = String.format("insert into users values ('%s', '%s', '%s', '%s',%s,'%s') ;", email, username, password, dob, "false", user_id);
						statement.execute(query);
		            }

				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


		
		res.setContentType("application/json");
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST,GET");
		PrintWriter out = res.getWriter();
		out.print(jsontosend.toString());
		 out.flush();
		 out.close();
	}
}
