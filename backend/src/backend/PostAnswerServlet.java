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

public class PostAnswerServlet extends HttpServlet {

public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		
		String answer_id = "", full_answer = "", Ques_id = "", user_id = "";
		int acceptance_status = 0, like_count = 0;
			
		
		JSONObject jsontosend = new JSONObject();

		HttpServletRequest request = req;
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			try {
				JSONObject json = new JSONObject(myjsonString);
				

				
				
				
				answer_id = json.getString("answer_id");
				full_answer = json.getString("full_answer");
				Ques_id = json.get("Ques_id").toString();
				user_id = json.getString("user_id");
				acceptance_status = json.getInt("acceptance_status");
				like_count = json.getInt("like_count");
					
				
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919"))
				{

					System.out.println("Connected to PostgreSQL database!- askquestion");

					Statement statement = connection.createStatement();

					
					
			    String query = String.format("insert into answers values ('%s', '%s', '%s', '%s',%s, %s) ;", answer_id, full_answer, Ques_id, user_id, acceptance_status,like_count);
	                statement.execute(query);
	                

	                jsontosend.put("status", "200");
//		            

				} catch (SQLException e) {
					e.printStackTrace();
					
					jsontosend.put("status", "500");
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
		 System.out.println(jsontosend.toString());
		out.print(jsontosend.toString());
		// out.print(json.toString());
		 out.flush();
		 out.close();
	}

}

