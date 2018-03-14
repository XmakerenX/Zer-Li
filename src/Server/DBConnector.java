package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

//*************************************************************************************************
/**
 * This Class handles all the actions the server does on the database
 * Including connecting and closing the connection
 */
//*************************************************************************************************
public class DBConnector implements DBInterface{
	
	final static String DBName = "prototype";
	private static boolean isDriverLoaded = false;
	private Connection conn = null;
	
	//*************************************************************************************************
	/**
	 * Creates a new DBConnector and load the jdbc driver
	 * @param username The DB username
	 * @param password The DB password
	 */
	//*************************************************************************************************
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

	//*************************************************************************************************
	/**
	 * This method create a connection to the local database
	 * if already connected , close the active connection first
	 * 
	 * @param username The DB username
	 * @param password The DB password
	 */
	//*************************************************************************************************
	  public void connectToDB(String username, String password)
	  { 
			// Initialize connection to database
			try {
				if (conn != null)
					conn.close();

				conn = DriverManager.getConnection("jdbc:mysql://localhost/"+DBName, username, password);

			} catch (SQLException ex) 
			{
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				ex.printStackTrace();
				System.exit(0);
			}
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to select data from table
	   * 
	   * @param fields The fields to select from the table
	   * @param table The table to select from
	   * @param condition the condition on the data to select
	   * @return The ResultSet returned from running the query
	   */
	  //*************************************************************************************************
	  public ResultSet selectTableData(String fields, String table, String condition)
	  {
		  Statement stmt;

		  try {
			  stmt = conn.createStatement();
			  ResultSet rs;
			  if (condition.length() == 0)
			  {
				  System.out.println("SELECT "+fields+" FROM "+DBName+"."+table+";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+DBName+"."+table+";");
			  }
			  else
			  {
				  System.out.println("SELECT "+fields+" FROM "+DBName+"."+table+" Where "+condition+";");
				  rs = stmt.executeQuery("SELECT "+fields+" FROM "+DBName+"."+table+" Where "+condition+";");
			  }
			  return rs;
		  }catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  return null;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to select data from table inner joined with another table
	   * 
	   * @param fields The fields to select from the table
	   * @param table The table to select from
	   * @param joinTable the table to join with when selecting
	   * @param condition the condition on the data to select
	   * @return The ResultSet returned from running the query
	   */
	  //*************************************************************************************************
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
			  ex.printStackTrace();
			  return null;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to select data from table and counts the number of rows returned
	   * 
	   * @param field The fields to select from the table
	   * @param table The table to select from
	   * @param condition the condition on the data to select
	   * @return The Count of rows returned form the query
	   */
	  //*************************************************************************************************
	  public int countSelectTableData(String field, String table, String condition)
	  {
		  Statement stmt;

		  try {
			  stmt = conn.createStatement();
			  ResultSet rs;
			  if (condition.length() == 0)
			  {
				  System.out.println("SELECT COUNT("+field+") FROM "+DBName+"."+table+";");
				  rs = stmt.executeQuery("SELECT COUNT("+field+") FROM "+DBName+"."+table+";");
			  }
			  else
			  {
				  System.out.println("SELECT COUNT("+field+") FROM "+DBName+"."+table+" Where "+condition+";");
				  rs = stmt.executeQuery("SELECT COUNT("+field+") FROM "+DBName+"."+table+" Where "+condition+";");
			  }
			  
			  if (rs.isBeforeFirst())
			  {
				  rs.next();
				  return rs.getInt(1);
			  }
			  else
				  return -1;
			  
		  }catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  return -1;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to return the last inserted ID
	   * 
	   * @return The ResultSet returned from running the query
	   */
	  //*************************************************************************************************
	  public ResultSet selectLastInsertID()
	  {
		  Statement stmt;
		  ResultSet rs;
		  try
		  {
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
			  return rs;
		  }
		  catch (SQLException ex) {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  return null;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to update data in the table
	   * 
	   * @param table The table to update
	   * @param fieldsToUpdate The fields to update from the table
	   * @param condition the condition on what rows to update in the table
	   * @throws SQLException thrown if the query fails 
	   */
	  //*************************************************************************************************
	  public void executeUpdate(String table, String fieldsToUpdate, String condition) throws SQLException
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
			  ex.printStackTrace();
			  throw ex;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to add data to the table
	   * 
	   * @param table The table to update
	   * @param fieldToInsert The fields to insert from the table
	   * @throws SQLException thrown if the query fails 
	   */
	  //*************************************************************************************************
	  public void insertData(String table, String fieldToInsert) throws SQLException
	  {
		  Statement stmt;
		  
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("INSERT INTO "+table+" VALUES("+fieldToInsert+")" + ";");
			  stmt.executeUpdate("INSERT INTO "+table+" VALUES("+fieldToInsert+")" + ";");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  throw ex;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates a query to remove data from table
	   * 
	   * @param table The table to remove from
	   * @param keys The primary keys for the data to remove
	   * @return true on entrry removed , false on query failure
	   */
	  //*************************************************************************************************
	  public Boolean removeEntry(String table,ArrayList<String> keys)
	  {
		  //ArrayList<String> primaryKeys = this.getTableKeyName(table);
		  String primaryCondition = null;
		  primaryCondition = generateConditionForPrimayKey(table, keys, primaryCondition); 
		  Statement stmnt;

		  try 
		  {
			  stmnt = conn.createStatement();
			  System.out.println("delete from "+DBName+"."+table+" where "+ primaryCondition + " limit 1");
			  stmnt.executeUpdate("delete from "+DBName+"."+table+" where "+ primaryCondition  +" limit 1");
			  return true;
		  } 

		  catch (SQLException e)
		  {
			  e.printStackTrace();
			  return false;
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method creates prepared Statement by the given query
	   * 
	   * @param query the query for the prepared Statement
	   * @return the newly created PreparedStatement
	   */
	  //*************************************************************************************************
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
			  ex.printStackTrace();
			  return null;
		  }
	  }
	  	  
	  //*************************************************************************************************
	  /**
	   * This method returns the names for the given table primary keys
	   * 
	   * @param table The table who's keys names we want
	   * @return an ArrayList of the primary keys names for the given table
	   */
	  //*************************************************************************************************
	  public ArrayList<String> getTableKeyName(String table)
	  {
		  Statement stmt;
		  ArrayList<String> primaryKeysNames = new ArrayList<String>();
		  
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("SHOW KEYS FROM "+DBName+"."+table+" WHERE Key_name ='PRIMARY';");
			  ResultSet rs = stmt.executeQuery("SHOW KEYS FROM "+DBName+"."+table+" WHERE Key_name ='PRIMARY';");
			  while (rs.next())
			  {
				  primaryKeysNames.add(rs.getString("Column_name"));
			  }
			  rs.close();
			  
			  return primaryKeysNames;
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  return null;
		  }  
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method returns the type for the table column
	   * 
	   * @param table The table name
	   * @param columnName the column that we want to its type
	   * @return a String of what type is the column
	   */
	  //*************************************************************************************************
	  public String getColumnType(String table , String columnName)
	  {
		  Statement stmt;
		  try
		  {
			  stmt = conn.createStatement();
			  System.out.println("SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"' AND COLUMN_NAME = '"+columnName+"';");
			  ResultSet rs = stmt.executeQuery("SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"' AND COLUMN_NAME = '"+columnName+"';");
			  rs.next();
			  return rs.getString("DATA_TYPE");
		  }
		  catch (SQLException ex) 
		  {
			  System.out.println("SQLException: " + ex.getMessage());
			  System.out.println("SQLState: " + ex.getSQLState());
			  System.out.println("VendorError: " + ex.getErrorCode());
			  ex.printStackTrace();
			  return null;
		  } 
	  }
	  	  
	  //*************************************************************************************************
	  /**
	   * This method closes the connection to the database
	   */
	  //*************************************************************************************************
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
			  ex.printStackTrace();
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method check if a entry with the given primary keys exist in the table
	   * @param table the table to search in
	   * @param keys the primary keys for the row we want to know if exist
	   * @return true if there such a row with this primary keys
	   * false if there isn't
	   */
	  //*************************************************************************************************
	public Boolean doesExists(String table, ArrayList<String> keys) 
	{
		   //ArrayList<String> primaryKeys = this.getTableKeyName(table);
		   //String primaryKey = this.getTableKeyName(table);
		   String primaryCondition = null;
		   primaryCondition = generateConditionForPrimayKey(table, keys, primaryCondition);
		   Statement stmnt;
		   ResultSet rs;
		try 
		{
			stmnt = conn.createStatement();
			System.out.println("Select * from "+table+" where "+primaryCondition);
			rs = stmnt.executeQuery("Select * from "+table+" where "+primaryCondition);
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
			e.printStackTrace();
			return false;
		}
	}
	
	//*************************************************************************************************
	/**
	 * This method given a table and a primary key value generate a condition for it 
	 * for the table product and key value 5 will generate in condition "ProductID=4"
	 * @param table the table that the primary key belongs to
	 * @param keys the value of the primary key
	 * @param condition the generated MySQL condition or error message
	 * @return true if the condition was generated successfully , false if error was incurred
	 * 
	 */
	//*************************************************************************************************
	  public String generateConditionForPrimayKey(String table, ArrayList<String> keys, String condition)
	  {		
		  System.out.println("keys");
		  System.out.println(keys);
		  condition = "";
		  ArrayList<String> primaryKeyName = getTableKeyName(table);

		  if (keys.size() == 0)
		  {
			  System.out.println("no key was given");
			  return ""; 
		  }
		  
		  if (keys.size() > primaryKeyName.size())
		  {
			  System.out.println("too many keys given");
			  return "";
		  }

		  
		  if (primaryKeyName.size() > 0)
		  {
			  for (int i = 0; i < keys.size(); i++)
			  {
				  String colType = getColumnType(table, primaryKeyName.get(i));
				  System.out.println(""+colType);
				  if (colType != null)
				  {
					  if (colType.equals("int"))
						  if (i != 0)
							  condition = condition + " AND " + primaryKeyName.get(i)+"="+keys.get(i);
						  else
							  condition = primaryKeyName.get(i)+"="+keys.get(i);
					  else
						  if (i != 0)
							  condition = condition + " AND " + primaryKeyName.get(i)+"="+"\""+keys.get(i)+"\"";
						  else
							  condition = primaryKeyName.get(i)+"="+"\""+keys.get(i)+"\"";
				  }
				  else
				  {
					  condition = "Failed to get primary key type";
					  return "";	//Temporary lolz right...
				  }
			  }
			  return condition;
		  }
		  else 
		  {
			  condition = "Failed to get primary key name";
			  return "";	//Temporary
		  }
	  }
}
