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
	  private DBConnector db;
	
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
	    db = new DBConnector(username, password);
	    //connectToDB(username, password);
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
				  ArrayList<Product> data = new ArrayList<Product>();
				  try 
				  {
					  ResultSet rs = db.selectTableData("*", "Product");
					  if (rs != null)
					  {
						  while(rs.next())
						  {
							  // save the values in data						  
							  data.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3)));
						  } 
						  rs.close();
						  System.out.println("sending message");
						  client.sendToClient(data);
					  }

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
				  System.out.println("updating database");

				  String productID = "ProductID="+userInput.get(3);
				  String productName = "ProductName=\""+userInput.get(4)+"\"";
				  String productType = "ProductType=\""+userInput.get(5)+"\"";
				  String condition = "ProductID="+userInput.get(2); 
				  db.executeUpdate("Product", productID + "," + productName + "," + productType, condition);
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
	    db.closeConnection();
	  }
	  
	  public static ArrayList<Object> parseConfigFile(String configFileNmae)
	  {
		  String DBusername = "";
		  String DBpassword = "";	
		  int port = DEFAULT_PORT; //Port to listen on
		  Config serverConfig = new Config("server.properties");
		  ArrayList<Object> output = new ArrayList<Object>();
		 
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
		  
		  output.add(port);
		  output.add(DBusername);
		  output.add(DBpassword);
		  return output;
	  }
	  
	  /**
	   * This method is responsible for the creation of 
	   * the server instance (there is no UI in this phase).
	   *
	   */
	  public static void main(String[] args) 
	  {	  
		  ArrayList<Object> serverArgs = parseConfigFile("server.properties");
		  ProtoTypeServer sv = new ProtoTypeServer((int)serverArgs.get(0), (String)serverArgs.get(1), (String)serverArgs.get(2));

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
