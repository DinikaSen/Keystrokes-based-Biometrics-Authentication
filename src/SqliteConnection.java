import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqliteConnection {

	private static Connection connection;
    
    private SqliteConnection() 
    {
        initConnection();
    }

    public static Connection getDBConnection()
    {
        if(connection == null)
            new SqliteConnection();

        return connection;
        
    }
    public void initConnection()
    {
        try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
		    connection = DriverManager.getConnection("jdbc:sqlite:biometricData.sqlite");
		    DBHandler.createUserTable();
		    DBHandler.createProfileTable();
		    
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
        
    }
}
