package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import prototype.Config;
import prototype.Product;

public class ProtoTypeServer extends AbstractServer {

	  final public static int DEFAULT_PORT = 5555;
	  private Connection conn = null;
	
	  //-----------------------------------------------------------------
	  // Constructors 
	  //-----------------------------------------------------------------
	  /**
	   * Constructs an instance of the Prototype Server
	   *
	   * @param port The port number to connect on.
	   * @param username The useranme to connect with
	   * @param password The password to connect with
	   */
	  public ProtoTypeServer(int port, String username, String password) 
	  {
	    super(port);
	    
	    connectToDB(username, password);
	  }

	  //-----------------------------------------------------------------
	  // Instance methods
	  //-----------------------------------------------------------------
	  
	  /**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   */
	  public void handleMessageFromClient
	  (Object msg, ConnectionToClient client)
	  {
		  //Casting the received object back to an array list of strings
		  ArrayList<String> userInput = (ArrayList<String>)msg;
		  
		  switch(userInput.get(0))
		  {
		  case "GET":
			//saves all the product's as arrays of strings in another array called "data"
		  {
			  switch(userInput.get(1))
			  {
			  case "Product":
			  {
				  Statement stmt;
				  ArrayList<Product> data = new ArrayList<Product>();

				  try 
				  {
					  stmt = conn.createStatement();
					  ResultSet rs = stmt.executeQuery("SELECT * FROM Product;");
					  while(rs.next())
					  {
						  // save the values in data						  
						  data.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3)));
					  } 
					  rs.close();
					  System.out.println("sending message");
					  client.sendToClient(data);

				  } catch (SQLException e) {e.printStackTrace();}
				  catch (IOException e) {System.out.println("Could not send message to Client.");}
			  }break;

			  default:
				  System.out.println("Error Invalid message received");
				  break;
			  }
		  }break;
		  
		  case "SET":
			  //updates specific item's details in the Product table
		  {
			  switch (userInput.get(1))
			  {
			  case "Product":
			  {
				  System.out.println(userInput);
				  Statement stmt;
				  try 
				  {
					  System.out.println("updating database");
					  stmt = conn.createStatement();
					  
					  //stmt.executeUpdate("UPDATE Product SET ProductName=\""+userInput.get(3)+"\" ,ProductType=\""+userInput.get(4)+"\" WHERE ProductID="+userInput.get(2)+";");
					  stmt.executeUpdate("UPDATE Product SET ProductID="+userInput.get(3)+" ,ProductName=\""+userInput.get(4)+"\" ,ProductType=\""+userInput.get(5)+"\" WHERE ProductID="+userInput.get(2)+";");
					  
				  } catch (SQLException e) {e.printStackTrace();}
				  
			  }break;
			  
			  default:
				  System.out.println("Error Invalid message received");
				  break;
			  
			  }
		  }break;
		  
		  default:
			  System.out.println("Error Invalid message received");
			  break;
		  }
		  
	  }
	  
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  protected void serverStarted()
	  {
		  System.out.println("Server listening for connections on port " + getPort());
	  }
	  
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	  protected void serverStopped()
	  {
	    System.out.println("Server has stopped listening for connections.");
	    // close connection to DB
	    if (conn != null)
	    {
	    	try
	    	{
	    		conn.close();
	    	}
	    	catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
	    }
	  }
	  
	  /**
	   * This method overrides create a connection to the local database
	   * 
	   * @param username The DB username
	   * @param password The DB passowrd
	   */
	  public void connectToDB(String username, String password)
	  {
			// init driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) 
			{
				System.out.println("Failed to get jdbc Driver");
				System.exit(0);
			}

			// init connection to database
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost/prototype", username, password);

			} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				System.exit(0);
			}
	  }
	  
	  /**
	   * This method is responsible for the creation of 
	   * the server instance (there is no UI in this phase).
	   *
	   */
	  public static void main(String[] args) 
	  {
		  String DBusername = "";
		  String DBpassword = "";	
		  int port = DEFAULT_PORT; //Port to listen on
		  Config serverConfig = new Config("server.properties");
		 
		  DBusername = serverConfig.getProperty("DB_USERNAME");
		  DBpassword = serverConfig.getProperty("DB_PASSWORD");
		  
		  if(!serverConfig.getProperty("SERVER_PORT").equals("default"))
		  {
			  try 
			  {
				  port = Integer.parseInt(serverConfig.getProperty("SERVER_PORT"));
			  }
			  catch(NumberFormatException e) 
			  {
				  System.out.println("port in server.properties is invalid");
			  }
		  }
		  

		  ProtoTypeServer sv = new ProtoTypeServer(port, DBusername, DBpassword);

		  try 
		  {
			  //Start listening for connections
			  sv.listen();
		  } 
		  catch (IOException ex) 
		  {
			  System.out.println("ERROR - Could not listen for clients!");
		  }
	  }
}
