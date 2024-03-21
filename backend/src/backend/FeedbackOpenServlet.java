package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
 
public class FeedbackOpenServlet extends HttpServlet {


	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		
		String rating = "", comment = "", user_id = "";
		
		JSONObject jsontosend = new JSONObject();
		ArrayList<JSONObject> ar = new ArrayList();

		HttpServletRequest request = req;
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(myjsonString);
			try {
				JSONObject json = new JSONObject(myjsonString);
				System.out.println(json);
				
				user_id = json.get("user_id").toString();
					
				System.out.println(rating + " " + comment);
				
				
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919"))
				{

					System.out.println("Connected to PostgreSQL database!");
					
					
					Statement statement = connection.createStatement();
					
					ResultSet rs = statement.executeQuery(String.format("select * from feedback"));
					

					while (rs.next()) 
					{

						json = new JSONObject();
						

						rating = rs.getString("rating");
						comment = rs.getString("comment");
						
						json.put("rating" , rating);
						json.put("comment" , comment);
						

						ar.add(json);
					}
						
					rs = statement.executeQuery(String.format("select * from feedback where user_id = '%s' ", user_id));	
					if(rs.next()){
						jsontosend.put("rating" , rs.getString("rating"));
						jsontosend.put("comment" , rs.getString("comment"));
						System.out.println(rs.getString("rating") + " phanii" + rs.getString("comment"));
					}
					else
					{
						System.out.println("malli em ayyindhi ra");
					}
					
					
		            {
		            	jsontosend.put("feedback", ar.toString());
		            	jsontosend.put("status", "200");
		            }
		            

				} catch (SQLException e) {
					jsontosend.put("status", "500");
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

	}
}
