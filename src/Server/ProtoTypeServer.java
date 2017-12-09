package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import prototype.Product;

public class ProtoTypeServer extends AbstractServer {

	  final public static int DEFAULT_PORT = 5555;
	  private Connection conn;
	
	  //Constructors ****************************************************
	  
	  /**
	   * Constructs an instance of the echo server.
	   *
	   * @param port The port number to connect on.
	   */
	  public ProtoTypeServer(int port, String username, String password) 
	  {
	    super(port);
	    
	    connectToDB(username, password);
	  }
	  
	  //Instance methods ************************************************
	  
	  /**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   */
	  public void handleMessageFromClient
	  (Object msg, ConnectionToClient client)
	  {
		  ArrayList<String> userInput = (ArrayList<String>)msg;
		  
		  switch(userInput.get(0))
		  {
		  case "GET":
		  {
			  switch(userInput.get(1))
			  {
			  case "Product":
			  {
				  Statement stmt;
				  //ArrayList<Product> data = new ArrayList<Product>();
				  ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
				  //ObservableList<Product> data = FXCollections.observableArrayList();

				  try 
				  {
					  stmt = conn.createStatement();
					  ResultSet rs = stmt.executeQuery("SELECT * FROM Product;");
					  while(rs.next())
					  {
						  // save the values in data
						  ArrayList<String> row = new ArrayList<String>();
						  row.add(""+rs.getInt(1));
						  row.add(rs.getString(2));
						  row.add(rs.getString(3));
						  data.add(row);
						  //data.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
		  
		  //System.out.println("["+userInput.get(0)+", "+userInput.get(1)+", "+userInput.get(2)+"]");
		  //parsingTheData(conn, userInput);

		  //System.out.println("Message received: " + msg + " from " + client);
		  //this.sendToAllClients(msg);
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
	    System.out.println
	      ("Server has stopped listening for connections.");
	  }
	  
	  public void connectToDB(String username, String password)
	  {
			// init driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception ex) {
				/* handle the error */}

			// init connection to database
			try {
				conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0/prototype", username, password);

			} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
	  }
	  
	  /**
	   * This method is responsible for the creation of 
	   * the server instance (there is no UI in this phase).
	   *
	   * @param args[0] The port number to listen on.  Defaults to 5555 
	   *          if no argument is entered.
	   */
	  public static void main(String[] args) 
	  {
		  String DBusername = "";
		  String DBpassword = "";
		  int port = 0; //Port to listen on

		  try
		  {
			  DBusername = args[0];
			  DBpassword = args[1];
		  }
		  catch (ArrayIndexOutOfBoundsException e)
		  {
			  System.out.println("no DB credential was given quitting...");
			  System.exit(0);
		  }
		  
		  try
		  {
			  port = Integer.parseInt(args[3]); //Get port from command line
		  }
		  catch(Throwable t)
		  {
			  port = DEFAULT_PORT; //Set port to 5555
		  }

		  ProtoTypeServer sv = new ProtoTypeServer(port, DBusername, DBpassword);

		  try 
		  {
			  sv.listen(); //Start listening for connections
		  } 
		  catch (Exception ex) 
		  {
			  System.out.println("ERROR - Could not listen for clients!");
		  }
	  }
}
