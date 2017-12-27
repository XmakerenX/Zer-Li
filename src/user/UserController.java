package user;

import java.sql.ResultSet;
import java.util.ArrayList;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.GetRequestByKey;
import serverAPI.LoginRequest;
import serverAPI.UpdateRequest;
import user.User.*;

public class UserController {
	
	/**
	 * Sends a login reqeust to server
	 * @param userName the username to login 
	 * @param userPassword the user password for the login 
	 * @param client - current running client
	 */
	public static void requestLogin(String username, String password, Client client)
	{		
		client.handleMessageFromClientUI(new LoginRequest(username, password));
	}
	
	/**
	 * Sends a login reqeust to server
	 * @param user the user to verify against 
	 * @param userName the username given to login with 
	 * @param userPassword the user password to login with
	 * @param client - current running client
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
	
	/**
	 * Creates new user and adds him to database
	 * @param userName - user name in program program
	 * @param userPassword - password to access the program
	 * @param userPermission - permission to distinguish between users and their rights
	 * @param personID - ID of the person who uses the program
	 * @param userStatus - user's status, is he: blocked, logged in, or not connected yet (regular)
	 * @param client - current running client
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
	 * @param updatedUser - updated User object
	 * @param userName - old user name (in case it changed)
	 * @param client - current running client
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
		client.handleMessageFromClientUI(new GetRequestByKey("User", userName));
	}
}
