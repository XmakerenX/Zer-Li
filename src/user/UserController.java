package user;

import java.sql.ResultSet;
import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import serverAPI.AddRequest;
import serverAPI.CheckExistsRequest;
import serverAPI.GetEmployeeStoreRequest;
import serverAPI.GetRequestByKey;
import serverAPI.GetRequestWhere;
import serverAPI.LoginRequest;
import serverAPI.LogoutRequest;
import serverAPI.RemoveRequest;
import serverAPI.Response;
import serverAPI.UpdateRequest;
import user.User.*;
/**
 * this class holds the needed functionality for the use
 * @author dk198
 *
 */
public class UserController implements ClientInterface
{
	
	/**
	 * Sends a login request to server
	 * @param userName the username to login 
	 * @param userPassword the user password for the login 
	 * @param client - current running client
	 */
	
	
	Response res;
	static UserController thisUserController = new UserController();
	
	public static void requestLogin(String username, String password, Client client)
	{		
		client.handleMessageFromClientUI(new LoginRequest(username, password));
	}
	
	/**
	 * Sends a logout request to server
	 * @param user - user to log out
	 * @param client - current running client
	 */
	public static void requestLogout(User user, Client client)
	{
		client.handleMessageFromClientUI(new LogoutRequest(user));
	}
	
	/**
	 * Sends a login request to server
	 * @param user the user to verify against 
	 * @param username the username given to login with 
	 * @param password the user password to login with
	 * @throws LoginException if bad login information was given
	 */
	public static void verifyLogin(User user, String username , String password) throws LoginException
	{
		
		if (user.getUserName().equals(username))
		{
			if (user.getUserPassword().equals(password))
			{
				if (user.getUserStatus() == User.Status.BLOCKED)
					throw new LoginException("User is blocked");
				
				user.clearUnsuccessfulTries();

				if (user.getUserStatus() == User.Status.LOGGED_IN)
					throw new LoginException("User is already logged in!");
			}
			else
			{
				user.incUnsuccessfulTries();
				throw new LoginException("username or password is wrong");
			}
		}
		else 
		{
			user.incUnsuccessfulTries();
			throw new LoginException("username or password is wrong");
		}
		
	}
	
	/*
	 * this apply's only 
	 */
	public static void getStoreOfEmployee(String userName, Client client)
	{
		
		client.handleMessageFromClientUI(new GetEmployeeStoreRequest(userName));
	}
	
	
	
	
	
	/*
	 * The userController may need to get an answer from the server
	 * this method helps to ensure that the communication is synchronized
	 */
	private void waitForResponseFromServer() 
	{
		synchronized(thisUserController)
		{
			try {
				thisUserController.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Creates new user and adds to database
	 * @param userName  user name in program program
	 * @param userPassword  password to access the program
	 * @param userPermission  permission to distinguish between users and their rights
	 * @param personID  ID of the person who uses the program
	 * @param client  current running client
	 */
	public static void createNewUser(String userName, String userPassword, Permissions userPermission, int personID, Client client)
	{
		try {
			User newUser = new User(userName, userPassword, userPermission, personID, User.Status.valueOf("REGULAR"), 0);
			client.handleMessageFromClientUI(new AddRequest("User", newUser));
		} catch (UserException e) {
			// TODO deal with error
			// shouldn't get here!
			e.printStackTrace();
		}	
	}
		
	/**
	 * Applies changes in user's info
	 * @param updatedUser  updated User entity
	 * @param formerUserName  old user name (in case it has been changed)
	 * @param client  current running client
	 */
	public static void updateUserDetails(User updatedUser, String formerUserName, Client client)
	{		
		client.handleMessageFromClientUI(new UpdateRequest("User", formerUserName, updatedUser));
	}
		
	/**
	 * Gets user from data base
	 * @param userName - user name (is the key) of the person who uses the program
	 * @param client - current running client
	 */
	public static void getUser(String userName, Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(userName);
		client.handleMessageFromClientUI(new GetRequestByKey("User", keys));
	}
	
	/**
	 * Removes user's row from data base
	 * @param userName - user name (is the key) to be deleted
	 * @param client - currently running client
	 */
	public static void RemoveUser(String userName, Client client)
	{
		ArrayList<String> userPrimaryKey = new ArrayList<String>();
		userPrimaryKey.add(userName);
    	client.handleMessageFromClientUI(new RemoveRequest("User", userPrimaryKey));
	}
	
	/**
	 * Gets user by specific key
	 * @param condition  is a specific key
	 * @param column  the column name
	 * @param client  currently running client
	 */
	public static void GetUserByCertainCondition(String condition, String column, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("User", column,condition));
	}

	@Override
	public void display(Object message) 
	{
		this.res = (Response) message;
		synchronized(this)
		{
			this.notify();
		}
		// TODO Auto-generated method stub
		
	}	
}
