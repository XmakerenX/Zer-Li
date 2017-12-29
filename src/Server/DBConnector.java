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
	  
	  public ResultSet selectJoinTablesData(String fields, String table, String joinTable, String condition)
	  {
		  Statement stmt;

		  try {
			  stmt = conn.createStatement();
			  ResultSet rs;
			  if (condition.length() == 0)
			  {
				  System.out.println("SELECT "+fields+" FROM "+table+ " INNER JOIN " + joinTable+";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+table+ " INNER JOIN " + joinTable+";");
			  }
			  else
			  {
				  System.out.println("SELECT "+fields+" FROM "+table+ " INNER JOIN "+ joinTable+ " ON " + condition + ";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+table+ " INNER JOIN "+ joinTable+ " ON " + condition + ";");
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
			  System.out.println("UPDATE "+table+" SET "+fieldsToUpdate+" WHERE "+condition+ ";");
			  stmt.executeUpdate("UPDATE "+table+" SET "+fieldsToUpdate+" WHERE "+condition+ ";");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
		  }
	  }
	  
	  public void insertData(String table, String fieldToInsert)
	  {
		  Statement stmt;
		  
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("INSERT INTO "+table+" VALUES("+fieldToInsert+")");
			  stmt.executeUpdate("INSERT INTO "+table+" VALUES("+fieldToInsert+")");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
		  }
	  }
//---------------------------------------------------------------------
	  public Boolean removeEntry(String table,String key)
	  {
		   String primaryKey = this.getTableKeyName(table);		   
		   Statement stmnt;
		try 
		{
			stmnt = conn.createStatement();
			stmnt.executeUpdate("delete from "+table+" where "+primaryKey+"="+"\""+key+"\"" +"limit 1");
			return true;
		} 
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	  }
//---------------------------------------------------------------------
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
	  	  
	  public String getTableKeyName(String table)
	  {
		  Statement stmt;
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("SHOW KEYS FROM "+table+" WHERE Key_name ='PRIMARY';");
			  ResultSet rs = stmt.executeQuery("SHOW KEYS FROM "+table+" WHERE Key_name ='PRIMARY';");
			  rs.next();
			  //return rs.getString(5);
			  return rs.getString("Column_name");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  return null;
		  }  
	  }
	  
	  public String getColumnType(String table , String columnName)
	  {
		  Statement stmt;
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"' AND COLUMN_NAME = '"+columnName+"';");
			  ResultSet rs = stmt.executeQuery("SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"' AND COLUMN_NAME = '"+columnName+"';");
			  return rs.getString(0);
		  }
		  catch (SQLException ex) 
		  {
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
//----------------------------------------------------------------------
	public Boolean doesExists(String table, String key) 
	{
		   String primaryKey = this.getTableKeyName(table);		   
		   Statement stmnt;
		   ResultSet rs;
		try 
		{
			stmnt = conn.createStatement();
			rs = stmnt.executeQuery("Select * from "+table+" where "+primaryKey+"="+"\""+key+"\"");
			rs.next();
			try
			{
			   if(rs.getString(1) == null) return false;
			   else
				   return true;
			}
			catch(Exception e)
			{
				return false;
			}
		} 
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
