package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class DBConnector {
	
	private static boolean isDriverLoaded = false;
	private Connection conn = null;
	
	public DBConnector(String username, String password)
	{
		// init driver
		try {
			if (!isDriverLoaded)
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				isDriverLoaded = true;
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) 
		{
			System.out.println("Failed to get jdbc Driver");
			System.exit(0);
		}
		
		connectToDB(username,password);
	}
	
	  /**
	   * This method overrides create a connection to the local database
	   * 
	   * @param username The DB username
	   * @param password The DB passowrd
	   */
	  public void connectToDB(String username, String password)
	  { 
			// init connection to database
			try {
				if (conn != null)
					conn.close();

				conn = DriverManager.getConnection("jdbc:mysql://localhost/prototype", username, password);

			} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				System.exit(0);
			}
	  }
	  
	  public ResultSet selectTableData(String fields, String table, String condition)
	  {
		  Statement stmt;

		  try {
			  stmt = conn.createStatement();
			  ResultSet rs;
			  if (condition.length() == 0)
			  {
				  System.out.println("SELECT "+fields+" FROM "+table+";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+table+";");
			  }
			  else
			  {
				  System.out.println("SELECT "+fields+" FROM "+table+" Where "+condition+";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+table+" Where "+condition+";");
			  }
			  return rs;
		  }catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  return null;
		  }
	  }
	  
	  public void executeUpdate(String table, String fieldsToUpdate, String condition)
	  {
		  Statement stmt;
		  
		  try
		  {
			  stmt = conn.createStatement();
			  stmt.executeUpdate("UPDATE "+table+" SET "+fieldsToUpdate+" WHERE "+condition+ ";");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
		  }
	  }
	  
	  public PreparedStatement createPreparedStatement(String query)
	  {
		  try
		  {
			  PreparedStatement preparedStatement = conn.prepareStatement(query);
			  return preparedStatement;
		  }catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  return null;
		  }
	  }
	  
	  public void closeConnection()
	  {
		  try
		  {
			  if (conn != null)
				  conn.close();
		  }catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
		  }
	  }
}
