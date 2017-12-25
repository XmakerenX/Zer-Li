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
import product.Product;
import prototype.Config;
import serverAPI.GetRequest;
import serverAPI.LoginRequest;
import serverAPI.Replay;
import serverAPI.Request;
import serverAPI.UpdateRequest;
import user.LoginException;
import user.User;
import user.User.*;
import user.UserController;
import utils.EntityFactory;
import utils.EntityUpdater;

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
	   * This method sends replay to a client
	   *
	   * @param client The connection to whom to replay
	   * @param r The Replay message
	   */
	  private void sendToClient(ConnectionToClient client, Replay r)
	  {
		  try
		  {
			  System.out.println("sending message");
			  client.sendToClient(r);
		  }	
		  catch (IOException e) {System.out.println("Could not send message to Client.");}
	  }
	  
	  private void loginUser(ConnectionToClient client, LoginRequest loginRequest,User user)
	  {
		  try
		  {
			  UserController.verifyLogin(user, loginRequest.getUsername(), loginRequest.getPassword());
			  // if client had a user logged in , log him out first
			  logoutUser(client);
			  client.setInfo("username", user.getUserName());
			  // update DB user has logged in
			  db.executeUpdate("User", "userStatus=\""+User.Status.LOGGED_IN+"\","+"unsuccessfulTries="+user.getUnsuccessfulTries() , "username=\""+user.getUserName()+"\"");
			  sendToClient(client, new Replay(Replay.Type.SUCCESS, user));
		  }
		  catch (LoginException le)
		  {
			  if (le.getMessage().contains("blocked"))
				  // logout user to prevent us from unblocking him on logout
				  this.logoutUser(client);
			  
			  //update DB user failed to log in
			  db.executeUpdate("User", "userStatus=\""+user.getUserStatus()+"\","+"unsuccessfulTries="+user.getUnsuccessfulTries(), "username=\""+user.getUserName()+"\"");
			  sendToClient(client, new Replay(Replay.Type.ERROR, le.getMessage()));							  
		  }
	  }
	  
	  /**
	   * logout the user connected using the given client
	   *
	   * @param client The connection which user to logout
	   */
	  private void logoutUser(ConnectionToClient client)
	  {
		  // check if the client has a user logged in the server 
		  String username = (String)client.getInfo("username");
		  if (username != null)
		  {
			  // make sure there is no way we are unblocking a blocked user!
			  // log the user out
			  client.setInfo("username", null);
			  db.executeUpdate("User", "userStatus=\""+User.Status.REGULAR+"\"", "username=\""+username+"\"");
		  }
	  }
	  	  
	  /**
	   * logs out the user logged in exception client
	   * @see AbastServer.clientException
	   */
	  synchronized protected void clientException(
			    ConnectionToClient client, Throwable exception) {
		  System.out.println("Clinet exception!");
		  System.out.println("exception "+exception.getMessage());
		  System.out.println(exception.getClass());
		  
		  logoutUser(client);
	  }
	  
	  /**
	   * logs out the user logged in the disconnected client
	   * @see AbastServer.clientDisconnected
	   */
	  @Override
	  synchronized protected void clientDisconnected(
			    ConnectionToClient client) {
		  System.out.println("Clinet disconnected");
		  logoutUser(client);
	  }
	  
	  public <T> void executeGetQueary(String tableName)
	  {
		  ArrayList<T> data = new ArrayList<T>();
		  try 
		  {
			  ArrayList<Object> columnsData = new ArrayList<Object>();
			  ResultSet rs = db.selectTableData("*", tableName, "");
			  if (rs != null)
			  {
				  while(rs.next())
				  {
					  int columnCount = rs.getMetaData().getColumnCount();
					  for (int i = 0; i < columnCount; i++)
						  columnsData.add(rs.getObject(i));
					  
					  
					  // save the values in data						  
					  //data.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3)));
				  } 
				  rs.close();
			  }

		  } catch (SQLException e) {e.printStackTrace();}
	  }
	  	  
	  /**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   */
	  public void handleMessageFromClient(Object msg, ConnectionToClient client)
	  {
		  Request request = (Request)msg;
		  
		  switch(request.getType())
		  {
		  case "GetRequest":
		  {
			  GetRequest getRequest = (GetRequest)request;
			  ResultSet rs = db.selectTableData("*", getRequest.getTable(), "");
			  Object res =EntityFactory.loadEntity(getRequest.getTable(), rs);
			  if (res != null)
				  sendToClient(client, new Replay(Replay.Type.SUCCESS, res));
		  }break;
		  
		  case "UpdateRequest":
		  {
			  UpdateRequest updateRequest =  (UpdateRequest)request;
			  EntityUpdater.setEntity(updateRequest.getTable(), updateRequest.getEntityKey(), updateRequest.getEntity(), db);
		  }break;
		  
		  case "LoginRequest":
		  {
			  LoginRequest loginRequest = (LoginRequest)request;
			  ResultSet rs  =  db.selectTableData("*", "prototype.User", "userName=\""+loginRequest.getUsername() +"\"");
			  ArrayList<User> users = (ArrayList<User>)EntityFactory.loadEntity("User", rs);
			  
			  if (users.size() > 0)
			  {
				  loginUser(client, loginRequest,users.get(0));
			  }
			  else
				  sendToClient(client, new Replay(Replay.Type.ERROR, "username or password is wrong"));
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
