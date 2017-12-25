package user;

import java.sql.ResultSet;
import java.util.ArrayList;

import client.Client;
import user.User.Permissions;
import user.User.Status;
import user.User.*;

public class UserController {
	
	public static void requestLogin(String username, String password, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		message.add("Verify"); 						
		message.add("User");
		message.add(username);
		message.add(password);
		
		
		client.handleMessageFromClientUI(message);
	}
	
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
	
	public static void createNewUser(String userName, String userPassword, Permissions userPermission, String personID, Status userStatus, Client client)
	{
		
		ArrayList<String> message = new ArrayList<String>();
		
		message.add("Create");					
		message.add("User"); 
		message.add(""+userName);					
		message.add(""+userPassword);		
		message.add(""+userPermission);
		message.add(""+personID);
		message.add(""+userStatus);
		message.add("0");
		
		client.handleMessageFromClientUI(message);
	}
	
	/**
	 * Applies changes in user's info
	 * @param updatedUser - updated User object
	 * @param userName - old user name (in case it changed)
	 * @param client - current running client
	 */
	
	public static void updateUserDetails(User updatedUser, String formerUserName, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		
		message.add("SET");
		message.add("User"); 					
		message.add(""+updatedUser.getUserName());
		message.add(""+updatedUser.getUserPassword());		
		message.add(""+updatedUser.getUserPermission());
		message.add(""+updatedUser.getPersonID());
		message.add(""+updatedUser.getUserStatus());
		message.add(""+updatedUser.getUnsuccessfulTries());
		message.add(""+formerUserName);
		
		client.handleMessageFromClientUI(message);
	}
	
	/**
	 * Gets user from data base
	 * @param userName - user name (is the key) of the person who uses the program
	 * @param client - current running client
	 */
	public static void getUser(String userName, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		
		message.add("Get"); 						
		message.add("User"); 					
		message.add(""+userName);
		
		client.handleMessageFromClientUI(message);
	}
}
