package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		ArrayList<JSONObject> ar = new ArrayList();

		JSONObject json = new JSONObject();

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

			System.out.println("Connected to PostgreSQL database! - DashboardPage");

			Statement statement = connection.createStatement();

			try {

				ResultSet rs = statement.executeQuery(String.format("select * from questions"));

				while (rs.next()) {

					json = new JSONObject();
					

					String Ques_id = rs.getString("question_id");
					String Question_Title = rs.getString("Question_Title");
					String Creator_id = rs.getString("user_id");
					String Viewcount = rs.getString("viewcount");
					String Answer_count = rs.getString("answer_count");
					String Votes = rs.getString("votes");
					String Tags = rs.getString("tags");

					json.put("Ques_id" , Ques_id);
					json.put("Question_Title" , Question_Title);
					json.put("Creator_id" , Creator_id);
					json.put("Viewcount" , Viewcount);
					json.put("Answer_count" , Answer_count);
					json.put("Votes" , Votes);
					json.put("Tags" , Tags);

					ar.add(json);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("Got req");

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
