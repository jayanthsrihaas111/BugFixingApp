
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class RefreshDBfromXML  {
	 public static void main(String[] args) throws ParserConfigurationException, IOException, ClassNotFoundException, SAXException {
	        // TODO Auto-generated method stub
		 	
		 	Class.forName("org.postgresql.Driver");
	        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp","postgres", "1919")) {

	            System.out.println("Java JDBC PostgreSQL Example");

	            System.out.println("Connected to PostgreSQL database!");

	            Statement statement = connection.createStatement();

	            statement.execute("drop table if exists answers");
	            statement.execute("drop table if exists questions");
	            statement.execute("drop table if exists users");
	            statement.execute("drop table if exists feedback");


	            String createUsersTable = "create table USERS"
	                    + "("
	                    + "email varchar(50) not null unique,"
	                    + "username varchar(50) not null, "
	                    + "password varchar(50),"
	                    + "dob varchar(20),"
	                    + "mentor_role boolean,"
	                    + "user_id varchar(50) not null unique, "
	                    + "primary key(user_id)"
	                    + ")";
	            statement.execute(createUsersTable);
	            
	            String createFeedbackTable = "create table feedback"
	                    + "("
	                    + "user_id varchar(50) not null unique, "
	                    + "rating numeric,"
	                    + "comment varchar(500),"
	                    + "primary key(user_id)"
	                    + ")";
	            statement.execute(createFeedbackTable );


	            String createQuestionsTable = "create table QUESTIONS (" +
	                    "	question_id varchar(50) not null, " +
	                    "	question_title varchar(255) not null, " +
	                    "	question_desc varchar(255555) not null, " +
	                    "	user_id varchar(50) not null, " +
	                    "	viewcount numeric(10,0), " +
	                    "	answer_count numeric(10,0), " +
	                    "	votes numeric(10,0), " +
	                    "   tags varchar(200)," + 
	                    "	primary key(question_id), " +
	                    "	foreign key(user_id) references USERS(user_id)" +
	                    "	);";

	            statement.execute(createQuestionsTable);


	            String createAnswersTable = "create table ANSWERS ("
	                    + "answer_id varchar(50) not null,"
	                    + " full_answer text, "
	                    + " question_id varchar(50),"
	                    + " user_id varchar(50),"
	                    + " acceptance_status numeric(1,0),"
	                    +" like_count numeric(7,0),"
	                    + " primary key(answer_id),"
	                    + " foreign key(user_id) references USERS(user_id));";

	            statement.execute(createAnswersTable);


	            File inputFile = new File("D:\\Projects\\BugFixingApp\\backend\\src\\sampledb.xml");
//	            File inputFile = new File("/home/sachmo/Documents/NCP_SEM7/PROJECT/BugFixingApp/backend/src/sampledb.xml");

	            
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(inputFile);

	            NodeList nList = doc.getElementsByTagName("user");

	            for (int temp = 0; temp < nList.getLength(); temp++) {
	                Node nNode = nList.item(temp);

	                Element eElement = (Element) nNode;

	                String Email = eElement.getElementsByTagName("Email").item(0).getTextContent();
	                String Username = eElement.getElementsByTagName("Username").item(0).getTextContent();
	                String Password = eElement.getElementsByTagName("Password").item(0).getTextContent();
	                String Date_of_birth = eElement.getElementsByTagName("Date_of_birth").item(0).getTextContent();
	                String Mentor_Role = eElement.getElementsByTagName("Mentor_Role").item(0).getTextContent();
	                String User_Id = eElement.getElementsByTagName("User_Id").item(0).getTextContent();


	                String query = String.format("insert into users values ('%s', '%s', '%s', '%s',%s,'%s') ;", Email, Username, Password, Date_of_birth, Mentor_Role, User_Id);
	                statement.execute(query);
//					System.out.println(query);

	            }

	             statement.execute("insert into feedback values ('52U82' , 4, 'good!!') ");
	             statement.execute("insert into feedback values ('RI0GP' , 5, 'very good!!') ");
	             statement.execute("insert into feedback values ('91ZV7' , 2, 'okayish!!') ");
	             statement.execute("insert into feedback values ('IRT3I' , 1, 'okayish!!') ");
	             statement.execute("insert into feedback values ('O1FSO' , 2, 'okayish!!') ");
	             statement.execute("insert into feedback values ('C1W1M' , 3, 'okayish!!') ");
	             statement.execute("insert into feedback values ('5MXRG' , 3, 'okayish!!') ");
	             statement.execute("insert into feedback values ('GN2B2' , 3, 'okayish!!') ");
	             
	            // String query = String.format("insert into users values ('%s', '%s', '%s', %s,%s,%s,%s) ;", college,state,city,tution,hostel,mess,total);


	            nList = doc.getElementsByTagName("question");

	            for (int temp = 0; temp < nList.getLength(); temp++) {
	                Node nNode = nList.item(temp);

	                Element eElement = (Element) nNode;

	                String Ques_id = eElement.getElementsByTagName("Ques_id").item(0).getTextContent();
	                String Question_Title  = eElement.getElementsByTagName("Question_Title").item(0).getTextContent();
	                String Question_Desc  = eElement.getElementsByTagName("Question_Desc").item(0).getTextContent();

	                
//	                XMLSerializer s = new XMLSerializer();
//	                var d = eElement;
//	                String str = s.serializeToString(d);
	                
	                TransformerFactory tf = TransformerFactory.newInstance();
	                Transformer transformer;
	                try {
	                    transformer = tf.newTransformer();
	                    // below code to remove XML declaration
	                    // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	                    StringWriter writer = new StringWriter();
	                    
	                    Node node = eElement.getElementsByTagName("Question_Desc").item(0);
	                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	                    factory.setNamespaceAware(true);
	                    DocumentBuilder builder = factory.newDocumentBuilder();
	                    Document newDocument = builder.newDocument();
	                    Node importedNode = newDocument.importNode(node, true);
	                    newDocument.appendChild(importedNode);

	                    
	                    
	                    transformer.transform(new DOMSource(newDocument), new StreamResult(writer));
	                    String output = writer.getBuffer().toString();
//	                    System.out.println(output);
	                    
	                    Question_Desc = output.substring(
	                    				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Question_Desc>".length(), 
	                    				output.length() -  "</Question_Desc>".length()
	                    				).toString() ;
	                } catch (TransformerException e) {
	                    e.printStackTrace();
	                }


//
	                
//	                System.out.println(Question_Desc);
	                String Creator_id = eElement.getElementsByTagName("Creator_id").item(0).getTextContent();
	                String Viewcount = eElement.getElementsByTagName("Viewcount").item(0).getTextContent();
	                String Answer_count = eElement.getElementsByTagName("Answer_count").item(0).getTextContent();
	                String Votes = eElement.getElementsByTagName("Votes").item(0).getTextContent();
	                String Tags = eElement.getElementsByTagName("Tags").item(0).getTextContent();


	                String query = String.format("insert into QUESTIONS values ('%s', '%s', '%s' , '%s', %s,%s ,%s, '%s') ;", Ques_id, Question_Title, Question_Desc , Creator_id, Viewcount, Answer_count,Votes, Tags);
//					System.out.println(query);
	                statement.execute(query);
	            }

	            nList = doc.getElementsByTagName("answer");

	            for (int temp = 0; temp < nList.getLength(); temp++) {
	                Node nNode = nList.item(temp);

	                Element eElement = (Element) nNode;

	                String answer_id = eElement.getElementsByTagName("answer_id").item(0).getTextContent();
	                String full_answer = eElement.getElementsByTagName("full_answer").item(0).getTextContent();
	                String ques_id = eElement.getElementsByTagName("ques_id").item(0).getTextContent();
	                String answered_person_id = eElement.getElementsByTagName("answered_person_id").item(0).getTextContent();
	                String acceptance_status = eElement.getElementsByTagName("acceptance_status").item(0).getTextContent();
	                String likes_count = eElement.getElementsByTagName("like_count").item(0).getTextContent();
	                full_answer = full_answer.replace("'", "\"");
	                String query = String.format("insert into answers values ('%s', '%s', '%s', '%s',%s, %s) ;", answer_id, full_answer, ques_id, answered_person_id, acceptance_status,likes_count);
//					System.out.println(query);

	                statement.execute(query);
	            }


	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

}
