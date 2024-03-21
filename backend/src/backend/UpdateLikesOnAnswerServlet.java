package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateLikesOnAnswerServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		
		ArrayList<JSONObject> ar = new ArrayList();

		JSONObject json = new JSONObject();
		
		String ans_id = "",likes_prev = "";
		JSONObject jsontosend = new JSONObject();
		HttpServletRequest request = req;
		if("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			System.out.println(myjsonString);
			//ques_id = myjsonString[""]
			//System.out.println(ques_id);
			try {
				json = new JSONObject(myjsonString);
				ans_id = json.getString("Ans_id").toString();
				likes_prev = json.getString("likes_prev").toString();
				
				System.out.println(ans_id);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

			System.out.println("Connected to PostgreSQL database! - UpdateLikesPage");
			
			String likes_count_updated = "-1";
			Statement statement = connection.createStatement();
			try {
				int likes = Integer.valueOf(likes_prev);
				statement.executeUpdate(String.format("UPDATE answers set like_count = '%s' where answer_id = '%s' ",String.valueOf(likes+1),ans_id));
				ResultSet rs1 = statement.executeQuery(String.format("Select like_count from answers where answer_id LIKE '%s' ",ans_id));
				if(rs1.next()) {
					likes_count_updated = rs1.getString("like_count");
					json = new JSONObject();
					json.put("ans_id",ans_id);
					json.put("likes_new",likes_count_updated);
					ar.add(json);
					System.out.println(json.toString());
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}
			

			// System.out.println("Got req");
			res.setContentType("application/json");
			res.setHeader("Access-Control-Allow-Headers","*");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Credentials", "true");
			res.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
			//res.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
			
			PrintWriter out = res.getWriter();
			out.print(ar.toString());

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
		
	}

}
