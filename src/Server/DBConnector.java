package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class DBConnector {
	
	final static String DBName = "prototype";
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

				conn = DriverManager.getConnection("jdbc:mysql://localhost/"+DBName, username, password);

			} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				ex.printStackTrace();
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
	  
	  public void insertData(String table, String fieldToInsert) throws Exception
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
//---------------------------------------------------------------------
	  public Boolean removeEntry(String table,ArrayList<String> keys)
	  {
		   ArrayList<String> primaryKeys = this.getTableKeyName(table);
		   String primaryCondition = null;
		   primaryCondition = generateConditionForPrimayKey(table, keys, primaryCondition);
		   //String primaryKey = this.getTableKeyName(table);		   
		   Statement stmnt;
		try 
		{
			stmnt = conn.createStatement();
			//stmnt.executeUpdate("delete from "+table+" where "+primaryKeys.get(0)+"="+"\""+key+"\"" +"limit 1");
			System.out.println("delete from "+DBName+"."+table+" where "+ primaryCondition + " limit 1");
			stmnt.executeUpdate("delete from "+DBName+"."+table+" where "+ primaryCondition  +" limit 1");
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
			  ex.printStackTrace();
			  return null;
		  }
	  }
	  	  
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
//----------------------------------------------------------------------
	public Boolean doesExists(String table, ArrayList<String> keys) 
	{
		   ArrayList<String> primaryKeys = this.getTableKeyName(table);
		   //String primaryKey = this.getTableKeyName(table);
		   String primaryCondition = null;
		   primaryCondition = generateConditionForPrimayKey(table, keys, primaryCondition);
		   Statement stmnt;
		   ResultSet rs;
		try 
		{
			stmnt = conn.createStatement();
			//rs = stmnt.executeQuery("Select * from "+table+" where "+primaryKeys.get(0)+"="+"\""+key+"\"");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	   * This method given a table and a primary key value generate a condition for it 
	   * for the table product and key value 5 will generate in condition "ProductID=4"
	   * @param table the table that the primary key belongs to
	   * @param key the value of the primary key
	   * @param condition the generated MySQL condition or error message
	   * @return true if the condition was generated successfully , false if error was incurred
	   * 
	   */
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
