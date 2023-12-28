package application.utils.backendUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	private static Connection connection;
	
	public static Connection connect()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:sqlite:possystem.db");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	
}