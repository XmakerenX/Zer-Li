package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import customer.Customer;
import customer.CustomerController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import order.CustomItemInOrder;
import order.Order;
import product.Product;
import serverAPI.AddRequest;
import serverAPI.CheckExistsRequest;
import serverAPI.GetCustomItemRequest;
import serverAPI.GetEmployeeStoreRequest;
import serverAPI.GetJoinedTablesRequest;
import serverAPI.GetJoinedTablesWhereRequest;
import serverAPI.GetRequest;
import serverAPI.GetRequestByKey;
import serverAPI.GetRequestWhere;
import serverAPI.ImageRequest;
import serverAPI.LoginRequest;
import serverAPI.RemoveOrderRequest;
import serverAPI.RemoveRequest;
import serverAPI.Request;
import serverAPI.Response;
import serverAPI.UpdateRequest;
import serverAPI.UploadImageRequest;
import timed.QuarterlyReportCreation;
import timed.SubscriptionPayment;
import user.LoginException;
import user.User;
import user.UserController;
import utils.Config;
import utils.EntityAdder;
import utils.EntityFactory;
import utils.EntityUpdater;
import utils.ImageData;

public class ProtoTypeServer extends AbstractServer {

	  final public static int DEFAULT_PORT = 5555;
	  private DBConnector db;
	
	  //*************************************************************************************************
	  // Constructors 
	  //*************************************************************************************************
	  /**
	   * Constructs an instance of the Prototype Server
	   *
	   * @param port The port number to connect on.
	   * @param username The useranme to connect with
	   * @param password The password to connect with
	   */
	  //*************************************************************************************************
	  public ProtoTypeServer(int port, String username, String password) 
	  {
	    super(port);
	    db = new DBConnector(username, password);
	  }

	  //*************************************************************************************************
	  // Instance methods
	  //*************************************************************************************************
	  
	  //*************************************************************************************************
	  /**
	   * This method sends replay to a client
	   *
	   * @param client The connection to whom to replay
	   * @param r The Replay message
	   */
	  //*************************************************************************************************
	  private void sendToClient(ConnectionToClient client, Response r)
	  {
		  try
		  {
			  System.out.println("sending message");
			  client.sendToClient(r);
		  }	
		  catch (IOException e) {System.out.println("Could not send message to Client.");}
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Perform the login check for the user if login data is valid login the users and sends 
	   * a response to the client
	   *
	   * @param client The connection to whom to replay, and associate with the user logging in
	   * @param loginRequest the login request data to check
	   * @param user the user matching the given username
	   */
	  //*************************************************************************************************
	  private void loginUser(ConnectionToClient client, LoginRequest loginRequest,User user)
	  {
		  try
		  {
			  UserController.verifyLogin(user, loginRequest.getUsername(), loginRequest.getPassword());

			  // if client had a user logged in , log him out first
			  logoutUser(client);
			  client.setInfo("username", user.getUserName());
			  // update DB user has logged in
			  try {
				  db.executeUpdate("User", "userStatus=\""+User.Status.LOGGED_IN+"\","+"unsuccessfulTries="+user.getUnsuccessfulTries() , "username=\""+user.getUserName()+"\"");
			  } catch (SQLException e) {
				  System.out.println("Failed to change user status when logging in");
				  e.printStackTrace();
			  }
			  sendToClient(client, new Response(Response.Type.SUCCESS, user));
		  }
		  catch (LoginException le)
		  {
			  if (le.getMessage().contains("blocked"))
				  // logout user to prevent us from unblocking him on logout
				  this.logoutUser(client);

			  //update DB user failed to log in
			  try {
				  db.executeUpdate("User", "userStatus=\""+user.getUserStatus()+"\","+"unsuccessfulTries="+(user.getUnsuccessfulTries()), "username=\""+user.getUserName()+"\"");
			  } catch (SQLException e) {
				  System.out.println("Failed to update user failed log in");
				  e.printStackTrace();
			  }
			  sendToClient(client, new Response(Response.Type.ERROR, le.getMessage()));							  
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * logout the user connected using the given client
	   *
	   * @param client The connection which user to logout
	   */
	  //*************************************************************************************************
	  private void logoutUser(ConnectionToClient client)
	  {
		  // check if the client has a user logged in the server 
		  String username = (String)client.getInfo("username");
		  if (username != null)
		  {
			  // make sure there is no way we are unblocking a blocked user!
			  // log the user out
			  client.setInfo("username", null);
			  try 
			  {
				db.executeUpdate("User", "userStatus=\""+User.Status.REGULAR+"\"", "username=\""+username+"\"");
			  } catch (SQLException e) 
			  {
				System.out.println("Failed to update user status when logging out");
				e.printStackTrace();
			}
		  }
	  }
	  
	  
	  	  
	  //*************************************************************************************************
	  /**
	   * logs out the user logged in exception client
	   * @see AbastServer.clientException
	   */
	  //*************************************************************************************************
	  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
		  System.out.println("Client exception!");
		  System.out.println("Client exception "+exception.getMessage());
		  System.out.println(exception.getClass() +" Client");
		  
		  logoutUser(client);
	  }
	  
	  //*************************************************************************************************
	  /**
	   * logs out the user logged in the disconnected client
	   * @see AbastServer.clientDisconnected
	   */
	  //*************************************************************************************************
	  @Override
	  synchronized protected void clientDisconnected(
			    ConnectionToClient client) {
		  System.out.println("Clinet disconnected");
		  logoutUser(client);
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different login/logout Requests from the client
	   * @param request the login/logout request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  @SuppressWarnings("unchecked")
	  private void handleUserLogin_OutRequests(Request request, ConnectionToClient client)
	  {
		  switch (request.getType())
		  {
		  
		  case "LoginRequest":
		  {
			  LoginRequest loginRequest = (LoginRequest)request;
			  ResultSet rs  =  db.selectTableData("*", "User", "userName=\""+loginRequest.getUsername() +"\"");
			  ArrayList<User> users = (ArrayList<User>)EntityFactory.loadEntity("User", rs);

			  if (users.size() > 0)
				  loginUser(client, loginRequest,users.get(0));
			  else
				  sendToClient(client, new Response(Response.Type.ERROR, "username or password is wrong"));
		  }break;
		  
		  case "LogoutRequest":
		  {
			  this.logoutUser(client);
		  }break;
		  
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different simple(non join) get Request from the client
	   * @param getRequest the get request sent by the client
	   * @returns An ArrayList of the desired entity data loaded form the database
	   */
	  //*************************************************************************************************
	  private ArrayList<?> handleGetRequest(Request getRequest)
	  {
		  String table = "";
		  String condition = "";
		  String fields = "*";
		  
		  switch (getRequest.getType())
		  {
		  case "GetRequest":
		  {
			  GetRequest GetRequest = (GetRequest)getRequest;
			  table = GetRequest.getTable();
		  }break;
		  
		  case "GetRequestWhere":
		  {
			  GetRequestWhere getRequestWhere = (GetRequestWhere)getRequest;
			  table = getRequestWhere.getTable();

			  if (!getRequestWhere.getCheckColomn().equals("OrderCustomerID"))
				  condition = "" + getRequestWhere.getCheckColomn() + " = " + "'" + getRequestWhere.getCondition() + "'";
			  else
				  condition = "" + getRequestWhere.getCheckColomn() + " = " + getRequestWhere.getCondition();

		  }break;

		  case "GetRequestByKey":
		  {
			  GetRequestByKey getRequestByKey  = (GetRequestByKey)getRequest;
			  table = getRequestByKey.getTable();
			  condition = db.generateConditionForPrimayKey(getRequestByKey.getTable(), getRequestByKey.getKey(), condition);
		  }break;
		  
		  }

		  ResultSet rs = db.selectTableData(fields, table, condition);
		  return  EntityFactory.loadEntity(table, rs);
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different get join Request from the client
	   * @param getJoinRequest the get join request sent by the client
	   * @returns An ArrayList of the desired entity data loaded form the database
	   */
	  //*************************************************************************************************
	  private ArrayList<?>  handleGetJoinedRequest(Request getJoinRequest)
	  {
		  String table = "";
		  String joinTable = "";
		  String condition = "";
		  String fields = "*";
		  
		  switch (getJoinRequest.getType())
		  {
		  
		  case "GetJoinedTablesRequest":
		  {
			  GetJoinedTablesRequest joinedTablesRequest = (GetJoinedTablesRequest)getJoinRequest;
			  ArrayList<String> tableKeyName = db.getTableKeyName(joinedTablesRequest.getTable());
			  ArrayList<String> joinedTableKeyName = db.getTableKeyName(joinedTablesRequest.getJoinedTable());
			  // make the join on the primary key between the tables who should be the same for this to work
			  // condition  = <table>.<tableKey> = <joinedTable>.<joinedTableKey>;
			  condition = joinedTablesRequest.getTable()+"."+tableKeyName.get(0)+"="
					  			+joinedTablesRequest.getJoinedTable()+"."+joinedTableKeyName.get(joinedTablesRequest.getKeyIndex());
			  table = joinedTablesRequest.getTable();
			  joinTable = joinedTablesRequest.getJoinedTable();
			  
		  }break;
		  
		  case "GetJoinedTablesWhereRequest":
		  {
			  GetJoinedTablesWhereRequest getJoinedTablesWhereRequest = (GetJoinedTablesWhereRequest)getJoinRequest;
			  
			  if (!getJoinedTablesWhereRequest.getCheckColumn().equals("OrderID"))
				  condition = "" + getJoinedTablesWhereRequest.getCheckColumn() + " = " + "'" + getJoinedTablesWhereRequest.getCondition() + "'";
			  else
				  condition = "" + getJoinedTablesWhereRequest.getCheckColumn() + " = " + getJoinedTablesWhereRequest.getCondition();
			  
			  GetJoinedTablesRequest joinedTablesRequest = (GetJoinedTablesRequest)getJoinRequest;
			  ArrayList<String> tableKeyName = db.getTableKeyName(joinedTablesRequest.getTable());
			  ArrayList<String> joinedTableKeyName = db.getTableKeyName(joinedTablesRequest.getJoinedTable());
			  // make the join on the primary key between the tables who should be the same for this to work
			  // condition  = <table>.<tableKey> = <joinedTable>.<joinedTableKey>;
			  condition = joinedTablesRequest.getTable()+"."+tableKeyName.get(0)+"="+ 
					  	  joinedTablesRequest.getJoinedTable()+"."+ joinedTableKeyName.get(joinedTablesRequest.getKeyIndex())
					  	  + " AND " + condition;
			  table = joinedTablesRequest.getTable();
			  joinTable = joinedTablesRequest.getJoinedTable();
		  }break;
		  
		  }
		  
		  ResultSet rs = db.selectJoinTablesData(fields, table, joinTable, condition);
		  System.out.println(joinTable);
		  return EntityFactory.loadEntity(joinTable, rs);
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles responding to client after a get request
	   * Check if entityArray is null or empty send error otherwise send entityArray to the client
	   * @param entityArray the entity data loaded from the getRequest
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void sentGetReqeustResultToClient(ArrayList<?> entityArray, ConnectionToClient client)
	  {
		  if (entityArray != null)
		  {
			  if (entityArray.size() > 0)
				  sendToClient(client, new Response(Response.Type.SUCCESS, entityArray));
			  else
				  sendToClient(client, new Response(Response.Type.ERROR, "No results under this condition"));
		  }
		  else
			  sendToClient(client, new Response(Response.Type.ERROR, "unknown table given"));
	  }

	  @SuppressWarnings("unchecked")
	  //*************************************************************************************************
	  /**
	   * Handles GetEmployeeStoreRequest and GetCustomItemRequest
	   * @param request the request that was sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void handleGetMiscRequests(Request request, ConnectionToClient client)
	  {
		  switch (request.getType())
		  {
		  case "GetEmployeeStoreRequest":
		  {
			  GetEmployeeStoreRequest getEmpStore = (GetEmployeeStoreRequest)request;

			  String condition = "" + "username" + " = " + "'" + getEmpStore.getUsername() + "'";

			  //Get said users from user table:
			  ResultSet rs = db.selectTableData("*", "storeEmployees",condition);
			  try 
			  {
				  // check if rs returned empty
				  if (rs.isBeforeFirst())
				  {
					  rs.next();			 
					  sendToClient(client, new Response(Response.Type.SUCCESS, Integer.parseInt(rs.getString("storeID"))));
				  }
				  else
				  {
					  System.out.println("Could not fatch data from database about this user. Are you sure the user is registerd as a store employee?");
					  sendToClient(client, new Response(Response.Type.ERROR, "Could not fatch data from database about this user. Are you sure the user is registerd as a store employee?"));
				  }
			  } 
			  catch (SQLException e) 
			  {
				  e.printStackTrace();
			  }
		  }
		  break;

		  case "GetCustomItemRequest":
		  {
			  GetCustomItemRequest getCustomItemRequest = (GetCustomItemRequest)request;
			  ArrayList<CustomItemInOrder> customItems = (ArrayList<CustomItemInOrder>)handleGetRequest(new GetRequestWhere("CustomItem",
					  "CustomItemOrderID", ""+getCustomItemRequest.getOrderID()));

			  for (CustomItemInOrder customItem : customItems)
			  {
				  ArrayList<Product> components = (ArrayList<Product>)handleGetJoinedRequest(
						  new GetJoinedTablesWhereRequest("Product","CustomItemProduct", 1, "CustomItemID",
								  ""+customItem.getCustomItemID()));				  
				  customItem.setComponents(components);
			  }
			  sentGetReqeustResultToClient(customItems, client);
		  }break;
		  }
	  }
	  	  	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different add Requests from the client
	   * @param request the add request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void handleAddRequests(Request request, ConnectionToClient client)
	  {
		  AddRequest addRequest = (AddRequest)request;
		  Boolean result = EntityAdder.addEntity(addRequest.getTable(), addRequest.getEntity(), db);
		  if(result == true)
			  sendToClient(client, new Response(Response.Type.SUCCESS,"entry was added to table:"+addRequest.getTable()));
		  else
			  sendToClient(client, new Response(Response.Type.ERROR,"Couldnt not add entry to table:"+addRequest.getTable()));
	  }

	  //*************************************************************************************************
	  /**
	   * Handles all the different remove Requests from the client
	   * @param request the remove request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  @SuppressWarnings("unchecked")
	  private void handleRemoveRequests(Request request, ConnectionToClient client)
	  {
		  switch(request.getType())
		  {
		  case "RemoveRequest":
		  {
			  RemoveRequest removeRequest =  (RemoveRequest)request;
			  boolean result = db.removeEntry(removeRequest.getTable(), removeRequest.getKey());
			  if(result)
			  {
				  sendToClient(client, new Response(Response.Type.SUCCESS, "entry with key:"+removeRequest.getKey() +
						  " was removed from table:"+removeRequest.getTable()));
			  }
			  else
			  {
				  sendToClient(client, new Response(Response.Type.ERROR, "Cannot remove entry with key:"+
			  removeRequest.getKey() + "from table:"+removeRequest.getTable()+"Are you sure it exists?"));
			  }
			  break;
		  }

		  case "RemoveOrderRequest":
		  {
			  RemoveOrderRequest removeOrderRequest = (RemoveOrderRequest)request;
			  float refundAmount = 0;

			  ArrayList<String> key = new ArrayList<String>();
			  key.add(""+removeOrderRequest.getOrderID());

			  ArrayList<Order> order = (ArrayList<Order>)handleGetRequest(new GetRequestByKey("Order", key));
			  if (order.size() > 0)
			  {
				  Calendar requiredDate = order.get(0).getOrderRequiredDateTime();
				  if (order.get(0).getOrderPaymentMethod() != Order.PayMethod.SUBSCRIPTION)
				  {
					  float refundRate = CustomerController.calcCustomerRefund(requiredDate);

					  if (refundRate != 0)
					  {
						  ArrayList<String> customerKeys = new ArrayList<String>();
						  customerKeys.add(""+order.get(0).getCustomerID());
						  customerKeys.add(""+order.get(0).getOrderOriginStore());
						  try {
							  refundAmount = refundCustomer(customerKeys, refundRate, order.get(0).getPrice());
							  order.get(0).setRefund(refundAmount);
							  order.get(0).setStatus(Order.Status.CANCELED);
						  } catch (SQLException e) {
							  sendToClient(client, new Response(Response.Type.ERROR, "Aborted couldn't refund customer"));
							  e.printStackTrace();
							  break;
						  }
					  }
				  }
				  // Cancel order(update its status and refund ammount)
				  if (EntityUpdater.setEntity("Order", Integer.toString(order.get(0).getID()), order.get(0), db))
					  sendToClient(client, new Response(Response.Type.SUCCESS, "Order Canceled Successfully,  you were refunded "+refundAmount));
				  else
					  sendToClient(client, new Response(Response.Type.ERROR, "Failed to cancel the order"));
			  }
			  else
				  sendToClient(client, new Response(Response.Type.ERROR, "No such Order exist"));

		  }break;
		  
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different Update Requests from the client
	   * @param request the Update request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void handleUpdateRequests(Request request, ConnectionToClient client)
	  {
		  UpdateRequest updateRequest =  (UpdateRequest)request;
		  System.out.println("table: "+updateRequest.getTable()+" entity: "+updateRequest.getEntity());
		  Boolean result = EntityUpdater.setEntity(updateRequest.getTable(), updateRequest.getEntityKey(), 
				  updateRequest.getEntity(), db);
		  if(result)
		  {
			  System.out.println("inside inside update request in PrototypeServer = success");
			  sendToClient(client, new Response(Response.Type.SUCCESS, "entry with key:"+updateRequest.getEntityKey()+
					  		" was updated in table:"+updateRequest.getTable()));
		  }
		  else
		  {
			  sendToClient(client, new Response(Response.Type.ERROR, "entry with key:"+updateRequest.getEntityKey() +
				  		" in table:"+updateRequest.getTable()+" could not be updated"));
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different Update Requests from the client
	   * @param request the Check request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void handleCheckRequests(Request request, ConnectionToClient client)
	  {
		  //checks whether the entry exists in a specific table
		  //Success - exists
		  //Error - isn't found in the table
		  CheckExistsRequest existsRequest =  (CheckExistsRequest)request;
		  boolean result = db.doesExists(existsRequest.getTable(), existsRequest.getPrimaryKey());
		  if(result)
		  {
			  sendToClient(client, new Response(Response.Type.SUCCESS, "entry with key:"+existsRequest.getPrimaryKey() +
					  		" exists in table:"+existsRequest.getTable()));
		  }
		  else
		  {
			  sendToClient(client, new Response(Response.Type.ERROR, "entry with key:"+existsRequest.getPrimaryKey() +
				  		" doesnt exists in table:"+existsRequest.getTable()));
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * Handles all the different Images Requests from the client
	   * @param request the image request sent by the client
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  private void handleImageRequests(Request request, ConnectionToClient client)
	  {
		  switch (request.getType())
		  {
		  case "ImageRequest":
		  {
			  ImageRequest imageRequest = (ImageRequest)request;
			  ArrayList<ImageData> images = new ArrayList<ImageData>();
			  for (String imageName : imageRequest.getImageNames())
			  {
				  try {
					  // sanity check
					  if (!imageName.equals(""))
						  images.add(new ImageData(ImageData.ServerImagesDirectory+imageName));
				  } catch (IOException e) {
					  // we didn't find the image the client wanted... well tot bad...
					  System.out.println("ImageRequest: Failed to load "+ImageData.ServerImagesDirectory+imageName);
					  e.printStackTrace();
				  }
			  }
			  sendToClient(client, new Response(Response.Type.SUCCESS, images));
		  }break;

		  case "UploadImageRequest":
		  {
			  UploadImageRequest uploadImageRequest = (UploadImageRequest)request;				  
			  try
			  {
				  uploadImageRequest.getImage().saveToDisk(ImageData.ServerImagesDirectory);
				  sendToClient(client, new Response(Response.Type.SUCCESS, "Image was uploaded to server"));

			  }
			  catch(Exception e)
			  {
				  sendToClient(client, new Response(Response.Type.ERROR, "Image was not uploaded to server"));
			  }
			  break;
		  }
		  }
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method handles any requests received from the client.
	   *
	   * @param msg : The request received from the client.
	   * @param client:  The connection from which the message originated.
	   */
	  //*************************************************************************************************
	  public void handleMessageFromClient(Object msg, ConnectionToClient client)
	  {
		  Request request = (Request)msg;

		  switch(request.getType())
		  { //start of switch:

		  case "LoginRequest":
		  case "LogoutRequest":
			  handleUserLogin_OutRequests(request, client);
			  break;

		  case "GetRequest":
		  case "GetRequestWhere":
		  case "GetRequestByKey":
			  sentGetReqeustResultToClient(handleGetRequest(request), client);
			  break;

		  case "GetJoinedTablesRequest":
		  case "GetJoinedTablesWhereRequest":
			  sentGetReqeustResultToClient(handleGetJoinedRequest(request), client);
			  break;

		  case "GetEmployeeStoreRequest":
		  case "GetCustomItemRequest":
			  handleGetMiscRequests(request, client);
			  break;

		  case "AddRequest":
			  handleAddRequests(request, client);
			  break;

		  case "RemoveRequest":
		  case "RemoveOrderRequest":
			  handleRemoveRequests(request, client);
			  break;

		  case "UpdateRequest":
			  handleUpdateRequests(request, client);
			  break;

		  case "ImageRequest":
		  case "UploadImageRequest":
			  handleImageRequests(request, client);
			  break;

		  case "CheckExistsRequest":
			  handleCheckRequests(request, client);
			  break;

		  default:
			  System.out.println("Error Invalid message received");
			  break;

		  }	//end of switch  
	  }
	  
	  //*************************************************************************************************
	  /** refunds money to the customer accountBalance
	   * @param customerKeys the customer ID and stoerID
	   * @param refundRate how much to refund form 0.0 of  refundAmount to 1.0 of refundAmount
	   * @param refundAmount the original amount paid by the customer
	   * @throws SQLException
	   */
	  //*************************************************************************************************
	  private float refundCustomer(ArrayList<String> customerKeys, float refundRate, float refundAmount) throws SQLException
	  {
		  @SuppressWarnings("unchecked")
		ArrayList<Customer> customer = (ArrayList<Customer>)handleGetRequest(new GetRequestByKey("Customers", customerKeys));
		  
		  customer.get(0).setAccountBalance(customer.get(0).getAccountBalance() + refundAmount * refundRate);
		db.executeUpdate("Customers", "accountBalance="+customer.get(0).getAccountBalance(), 
					  "PersonID="+customer.get(0).getID()+" AND StoreID="+customer.get(0).getStoreID() );
		
		return refundAmount * refundRate;
	
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  //*************************************************************************************************
	  protected void serverStarted()
	  {
		  System.out.println("Server listening for connections on port " + getPort());
	  }
	  
	  //*************************************************************************************************
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	  //*************************************************************************************************
	  protected void serverStopped()
	  {
	    System.out.println("Server has stopped listening for connections.");
	    // close connection to DB
	    db.closeConnection();
	  }
	  
	  //*************************************************************************************************
	  /** update config file. params are transferred from the serverGUI.
	   * @param configPath path server config file
	   * @param port the port to save in the config file
	   * @param username the database username to save in the config file
	   * @param pass the database password to save in the config file
	   */
	  //*************************************************************************************************
	  public static void updateConfigFile(String configPath,String port,String username,String pass)
	  {
		  Config serverConfig = new Config(configPath);
		  FileOutputStream out;
		try 
		{
			
			out = new FileOutputStream(configPath);
			
			  serverConfig.configFile.setProperty("DB_USERNAME", username);
			  serverConfig.configFile.setProperty("DB_PASSWORD", pass);
			  serverConfig.configFile.setProperty("SERVER_PORT", port);
			  serverConfig.configFile.store(out, null);
			  out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	  
	  }

	  //*************************************************************************************************
	  /** parse the server config file and loads what :
	   * port to listen to and database username and password
	   * @param configFileNmae path server config file
	   * @return the an Arraylist of the loaded port and database username and password
	   */
	  //*************************************************************************************************
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
	  
	  
	  //*************************************************************************************************
	  /**
	   * This method is responsible for the creation of 
	   * the server instance (there is no UI in this phase).
	   *
	   */
	  //*************************************************************************************************
	  public static void main(String[] args) 
	  {	  
		  ArrayList<Object> serverArgs = parseConfigFile("server.properties");
		  ProtoTypeServer sv = new ProtoTypeServer((int)serverArgs.get(0), (String)serverArgs.get(1), (String)serverArgs.get(2));

		  try 
		  {
			  //Start listening for connections
			  sv.listen();
			  runTimedTasks(sv.db);
		  } 
		  catch (IOException ex) 
		  {
			  System.out.println("ERROR - Could not listen for clients!");
		  }
	  }
	  
	  public static void runTimedTasks(DBConnector db){

	        Calendar calendar = Calendar.getInstance();
	        calendar.set(
	           Calendar.DAY_OF_WEEK,
	           Calendar.MONDAY
	        );
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);

	        Timer time = new Timer(); // Instantiate Timer Object
	        
	        Calendar now = Calendar.getInstance();
	        //getting the quarter: 1/1, 1/4, 1/7, 1/
	        int quarterForReports;
	        int month = now.get(Calendar.MONTH);
	        if (month == 0 || month ==1 || month== 2)
	        {
	        	quarterForReports = 4;
	        }
	        else if (month == 3 || month == 4 || month== 5)
	        {
	        	quarterForReports = 1;
	        }
	        else if (month == 6 || month == 7 || month== 8)
	        {
	        	quarterForReports = 2;
	        }
	        else
	        	quarterForReports = 3;
	        
	        //getting current year:
	        int year = now.get(Calendar.YEAR);
	        String yearInString = String.valueOf(year);
	        
	        // Start running the task on Monday at 15:40:00, period is set to 8 hours
	        // if you want to run the task immediately, set the 2nd parameter to 0
	        time.schedule(new QuarterlyReportCreation(quarterForReports, yearInString, db), calendar.getTime(), TimeUnit.DAYS.toMillis(1));
	        time.schedule(new SubscriptionPayment(yearInString, db), calendar.getTime(), TimeUnit.DAYS.toMillis(1));
	}
}
