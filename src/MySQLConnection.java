import java.sql.DriverManager;


public class MySQLConnection {

	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://174.79.32.150:22/jap751";

	   //  Database credentials
	   static final String USER = "jap751";
	   static final String PASS = "jap751123";
	   
	   
	   public static com.mysql.jdbc.Connection getConnection() throws Exception {
		    // load the MYSQL JDBC Driver
		    Class.forName(JDBC_DRIVER);
		    // define database connection parameters
		    return (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		  }
	   
}
