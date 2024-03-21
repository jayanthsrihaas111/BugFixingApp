import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class reset_db{
    public static void main(String[] args) {
	try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bfapp", "postgres", "1919")) {
 

            System.out.println("Java JDBC PostgreSQL Example");
            // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
            // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver"); 
 
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
		
            // System.out.println("Reading car records...");
            // System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
            
            
            statement.execute("drop table if exists answers");
            statement.execute("drop table if exists questions");
            statement.execute("drop table if exists users");
            
            
            String createUsersTable = "create table USERS"
            		+ "("
            		+ "email varchar(50) not null unique,"
            		+ "username varchar(50) not null, "
            		+ "password varchar(50),"
            		+ "dob varchar(20),"
            		+ "mentor_role numeric(1),"
            		+ "user_id varchar(50) not null unique, "
            		+ "primary key(user_id)"
            		+ ")";
            statement.execute(createUsersTable);
            statement.execute("insert into users values ('sinkou@icloud.com', 	'although_posset' , 'Perseusproxy8554' , 	'4/8/1976'	, 1	, '52U82'); ");
            statement.execute("insert into users values ('sagal@att.net', 	'uranium_supplier' , 'Sepiadownload8741' , 	'3/2/1977'	, 0	, 'RI0GP'); ");
            statement.execute("insert into users values ('msherr@optonline.net', 	'dinginess_silent' , 'Anguishedshown1111' , 	'24/08/1977'	, 0	, '91ZV7'); ");
            statement.execute("insert into users values ('balchen@outlook.com', 	'uncounted_bulldozer' , 'Murmuringbuggy2225' , 	'9/8/1979'	, 1	, 'IRT3I'); ");
            statement.execute("insert into users values ('zavadsky@yahoo.ca', 	'salonlinda' , 'Abbystirgoa1234' , 	'12/8/1981'	, 0	, 'O1FSO'); ");
            statement.execute("insert into users values ('crowemojo@icloud.com', 	'writerpacified' , 'Bertharare0123' , 	'20/10/1987'	, 1	, 'C1W1M'); ");
            statement.execute("insert into users values ('janusfury@msn.com', 	'stewarts_clubbing' , 'Spellingbling1789' , 	'18/10/1989'	, 0	, '5MXRG'); ");
            statement.execute("insert into users values ('nullchar@yahoo.com', 	'manleyrolled_civilianbritish' , 'Tapejukebox1148' , 	'3/5/1993'	, 1	, 'GN2B2'); ");
            statement.execute("insert into users values ('tsuruta@yahoo.com', 	'peeved_mossy' , 'Reliancearbitrary1147' , 	'24/01/2008'	, 1	, 'X9Z2K'); ");
            statement.execute("insert into users values ('cgarcia@live.com', 	'troutmender' , 'Upheldcrush420' , 	'17/04/2008'	, 1	, 'WBSNK'); ");
            
            
            String createQuestionsTable = "create table QUESTIONS (" + 
            		"	question_id varchar(50) not null, " + 
            		"	question varchar(255) not null, " + 
            		"	user_id varchar(50) not null, " + 
            		"	viewcount numeric(10,0), " + 
            		"	answer_count numeric(10,0), " + 
            		"	primary key(question_id), " + 
            		"	foreign key(user_id) references USERS(user_id)" + 
            		"	);" ;
            
            statement.execute(createQuestionsTable);
            
            statement.execute("insert into questions values ('Q100000001' , 'Why is processing a sorted array faster than processing an unsorted array?' ,	'52U82'	, 64	, 64);");
            statement.execute("insert into questions values ('Q100000002' , 'How do I undo the most recent local commits in Git?' ,	'C1W1M'	, 7	, 98);");
            statement.execute("insert into questions values ('Q100000003' , 'How do I delete a Git branch locally and remotely?' ,	'C1W1M'	, 84	, 84);");
            statement.execute("insert into questions values ('Q100000004' , 'What is the difference between git pull and git fetch?' ,	'5MXRG'	, 4	, 88);");
            statement.execute("insert into questions values ('Q100000005' , 'What does the yield keyword do?' ,	'5MXRG'	, 16	, 47);");
            statement.execute("insert into questions values ('Q100000006' , 'What is the correct JSON content type?' ,	'WBSNK'	, 16	, 63);");
            statement.execute("insert into questions values ('Q100000007' , 'How do I undo git add before commit?' ,	'WBSNK'	, 76	, 54);");
            statement.execute("insert into questions values ('Q100000008' , 'What is the --> operator in C++?' ,	'RI0GP'	, 61	, 68);");
            statement.execute("insert into questions values ('Q100000009' , 'How do I rename a local Git branch?' ,	'RI0GP'	, 38	, 65);");
            statement.execute("insert into questions values ('Q100000010' , 'How can I remove a specific item from an array?' ,	'IRT3I'	, 47	, 46);");
            
            

            
            String createAnswersTable = "create table ANSWERS ("
            		+ "answer_id varchar(50) not null,"
            		+ " full_answer text, "
            		+ " question_id varchar(50),"
            		+ " user_id varchar(50),"
            		+ " acceptance_status numeric(1,0),"
            		+ " primary key(answer_id),"
            		+ " foreign key(user_id) references USERS(user_id));";
            
            statement.execute(createAnswersTable);
            
            statement.execute("insert into answers values ('A100000001',	'In C++, it is faster to process a sorted array than an unsorted array because of branch prediction. In computer ar''chitecture, a branch prediction determines whether a conditional branch (jump) in the instruction flow of a program is likely to be taken or not. Branch prediction doesnt play a significant role here.' , 	'Q100000001',	'IRT3I' ,1);");
            statement.execute("insert into answers values ('A100000002',   ' array.remove(number); '  ,  'Q100000002' , 'RI0GP' ,  0); ");
            statement.execute("insert into answers values ('A100000003',   ' array.remove(number); '  ,  'Q100000003' , '91ZV7' ,  0.5); ");
            statement.execute("insert into answers values ('A100000004',   ' array.remove(number); '  ,  'Q100000004' , 'IRT3I' ,  1); ");
            statement.execute("insert into answers values ('A100000005',   ' array.remove(number); '  ,  'Q100000005' , 'O1FSO' ,  0); ");
            statement.execute("insert into answers values ('A100000006',   ' array.remove(number); '  ,  'Q100000006' , 'C1W1M' ,  1); ");
            statement.execute("insert into answers values ('A100000007',   ' array.remove(number); '  ,  'Q100000007' , '5MXRG' ,  1); ");
            statement.execute("insert into answers values ('A100000008',   ' array.remove(number); '  ,  'Q100000008' , 'GN2B2' ,  1); ");
            statement.execute("insert into answers values ('A100000009',   ' array.remove(number); '  ,  'Q100000009' , 'X9Z2K' ,  0.5); ");
            statement.execute("insert into answers values ('A100000010',   ' array.remove(number); '  ,  'Q100000010' , 'WBSNK' ,  1); ");
            
            
            ResultSet rs = statement.executeQuery("select * from users;");
            System.out.println("\n\nEmail               Username          Password      DOB      mentor_role");
            while(rs.next())
            {
            	String email = rs.getString("email");
            	String username = rs.getString("username");
            	String password = rs.getString("password");
            	String dob = rs.getString("dob");
            	String mentor_role = rs.getString("mentor_role");
            	String user_id = rs.getString("user_id");
            	
            	System.out.println(email +"  "+ username+"   "+password + " " + dob + " " + mentor_role + " " + user_id);
            }

            
            
//             System.out.println(resultSet);
 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();


        }
    }
}