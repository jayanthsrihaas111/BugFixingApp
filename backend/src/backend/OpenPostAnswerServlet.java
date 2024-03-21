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

public class OpenPostAnswerServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {



		JSONObject json = new JSONObject();
		
		
		String ques_id = "";
		JSONObject jsontosend = new JSONObject();
		
		HttpServletRequest request = req;
		if("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			System.out.println(myjsonString);

			try {
				json = new JSONObject(myjsonString);
				ques_id = json.getString("Ques_id").toString();
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

			System.out.println("Connected to PostgreSQL database! - ViewAnswersPage");
			
			String question="anonymous",creator="anonymous", question_desc = "";
			
			Statement statement = connection.createStatement();
			try {
				ResultSet rs1 = statement.executeQuery(String.format("select question_desc, question_id,question_title,user_id from questions where question_id LIKE '%s'",ques_id)); 
				
				if(rs1.next()) {
					question = rs1.getString("question_title");
					creator = rs1.getString("user_id");
					question_desc = rs1.getString("question_desc");
					
					jsontosend.put("question", question);
					jsontosend.put("creator_id", creator);
					jsontosend.put("question_desc", question_desc);
					
				}
				
			}catch(Exception e) {
				
			}


			// System.out.println("Got req");
			res.setContentType("application/json");
			res.setHeader("Access-Control-Allow-Headers","*");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Credentials", "true");
			res.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
			
			PrintWriter out = res.getWriter();
			out.print(jsontosend.toString());

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	

}

}
