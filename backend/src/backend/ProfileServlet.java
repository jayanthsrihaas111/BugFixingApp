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

public class ProfileServlet extends HttpServlet {
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		ArrayList<JSONObject> ar = new ArrayList();

		JSONObject json = new JSONObject();
		String user_id = "",username="",email="",dob="";
		int no_of_questions = 0,no_of_answers=0;
		JSONObject jsontosend = new JSONObject();
		HttpServletRequest request = req;
		if("POST".equalsIgnoreCase(request.getMethod())) {
			String myjsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			try {
				json = new JSONObject(myjsonString);
				user_id = json.get("user_id").toString();
				System.out.println(user_id);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

			System.out.println("Connected to PostgreSQL database! - DashboardPage");

			Statement statement = connection.createStatement();
			try {
				ResultSet rs1 = statement.executeQuery(String.format("select count(*) as CNT from questions where user_id = '%s'",user_id));
				if(rs1.next()) {
					no_of_questions = rs1.getInt("CNT");
				}
				rs1 = statement.executeQuery(String.format("select count(*) as CNT from answers where user_id = '%s'",user_id));
				if(rs1.next()) {
					no_of_answers = rs1.getInt("CNT");
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}

			try {

				ResultSet rs = statement.executeQuery(String.format("select * from users where user_id = '%s'",user_id));

				if(rs.next()) {

					json = new JSONObject();
					

					username=rs.getString("username");
					email = rs.getString("email");
					dob = rs.getString("dob");
					System.out.println(username + " " + email + " " + dob + " " + no_of_questions + " " + no_of_answers);

					json.put("username" ,username);
					json.put("email" , email);
					json.put("dob" , dob);
					json.put("user_id",user_id);
					json.put("q_count", no_of_questions);
					json.put("ans_count",no_of_answers);	

					ar.add(json);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("Got req");
			res.setContentType("application/json");
			res.setHeader("Access-Control-Allow-Headers", "*");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Methods", "POST,GET");

			PrintWriter out = res.getWriter();
			out.print(ar.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
