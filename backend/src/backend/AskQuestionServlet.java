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

public class AskQuestionServlet extends HttpServlet {
	//Connection connection;

public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String Question_Title = "",
				Question_Desc = "",
				Creator_id = "70204", Tags = "", Ques_id = "";
			
		int Viewcount = 0,
				Answer_count = 0,
				Votes = 0;

		
		JSONObject jsontosend = new JSONObject();
		
		HttpServletRequest request = req;
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
//			System.out.println(myjsonString);
			try {
				JSONObject json = new JSONObject(myjsonString);
				
				Ques_id = json.get("Ques_id").toString();
				
				
				Question_Title = json.get("Question_Title").toString();
				Question_Desc = json.getString("Question_Desc"); 
				Creator_id = json.get("Creator_id").toString();
				Viewcount = json.getInt("Viewcount");
				Answer_count = json.getInt("Answer_count");
				Votes = json.getInt("Votes");
				Tags = json.get("Tags").toString();
					
				
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919"))
				{

					System.out.println("Connected to PostgreSQL database!- askquestion");

					Statement statement = connection.createStatement();

		            					
	                String query = String.format("insert into QUESTIONS values ('%s', '%s', '%s' , '%s', %s,%s ,%s, '%s') ;", Ques_id, Question_Title, Question_Desc , Creator_id, Viewcount, Answer_count,Votes, Tags);
//					System.out.println(query);
	                statement.execute(query);		  

	                jsontosend.put("status", "200");
//		            

				} catch (SQLException e) {
					e.printStackTrace();
					
					jsontosend.put("status", "500");
				}
				finally {
					
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
