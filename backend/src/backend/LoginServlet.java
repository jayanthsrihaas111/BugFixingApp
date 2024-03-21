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
 
public class LoginServlet extends HttpServlet {

	// public static boolean equals(Object a, Object b) {
	// return (a == b) || (a != null && a.equals(b));
	// }

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		
		String username = "", password = "", user_id = "";
//		username = req.getParameter("username");
//		password = req.getParameter("password");
//		System.out.println(username + " " + password);
		
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
					
				System.out.println(username + " " + password);
				
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919"))
				{

					System.out.println("Connected to PostgreSQL database!");

					Statement statement = connection.createStatement();

		            					
					ResultSet rs = statement.executeQuery(String.format("select * from users where username = '%s' and password = '%s'", username,password )); 

		            if (rs.next()) {
//		                System.out.print(rs.getString(2) + " ");
//		                System.out.println(rs.getString(3));
		            	jsontosend.put("userstatus", "1");
		            	
		            	jsontosend.put("user_id", rs.getString("user_id"));
		            	jsontosend.put("mentor_role", rs.getString("mentor_role"));
		            }
		            else
		            {
		            	jsontosend.put("userstatus", "0");
		            }
		            
//		            try {
//		    			String a = username;
//		    			String b = "psp";
//
//		    			if ((a == b) || (a != null && a.equals(b))) {
//		    				jsontosend.put("userstatus", "1");
//		    			} else {
//		    				jsontosend.put("userstatus", "0");
//		    			}
//		    		} catch (JSONException e) {
//		    			e.printStackTrace();
//		    		}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// out.print(employeeJsonString);

		

		 res.setContentType("application/json");
		// res.setCharacterEncoding("UTF-8");
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST,GET");
		PrintWriter out = res.getWriter();
//		 out.println(String.format("<h1>Details are %s %s</h1>", username ,
		// password));
		// out.println(String.format(" %s %s ", username , password));
		out.print(jsontosend.toString());
		// out.print(json.toString());
		// out.flush();
		// out.close();
	}
}
