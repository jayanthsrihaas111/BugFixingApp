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

public class ViewAnswersServlet extends HttpServlet {
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		ArrayList<JSONObject> ar = new ArrayList();

		JSONObject json = new JSONObject();
		
		
		String ques_id = "";
		JSONObject jsontosend = new JSONObject();
		
		HttpServletRequest request = req;
		if("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			System.out.println(myjsonString);
			//ques_id = myjsonString[""]
			//System.out.println(ques_id);
			try {
				json = new JSONObject(myjsonString);
				ques_id = json.getString("Ques_id").toString();
				System.out.println(ques_id);
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
			try {
			ResultSet rs = statement.executeQuery(String.format("select * from answers where question_id = '%s'", ques_id));
			
			while(rs.next()) {
				
				json = new JSONObject();
				String ans_id = rs.getString("answer_id");
				String full_ans = rs.getString("full_answer");
				String question_id = rs.getString("question_id");
				String user_id = rs.getString("user_id");
				String accp = rs.getString("acceptance_status");
				String like_count = rs.getString("like_count");
				
				json.put("answer_id",ans_id);
				json.put("full_answer", full_ans);
				json.put("question_id",question_id);
				json.put("user_id", user_id);
				json.put("accp", accp);
				json.put("question", question);
				json.put("creator_id", creator);
				json.put("like_count", like_count);
				System.out.println(json.toString());
				ar.add(json);
				
			}
			
			jsontosend.put("answers", ar);
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
			out.print(jsontosend.toString());

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	

}
	
}
