package user;

import java.sql.ResultSet;
import java.util.ArrayList;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.LoginRequest;
import user.User.*;

public class UserController {
	
	public static void requestLogin(String username, String password, Client client)
	{		
		client.handleMessageFromClientUI(new LoginRequest(username, password));
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
	
	public static void createNewUser(String userName, String userPassword, Permissions userPermission, int personID, Status userStatus, Client client)
	{
		try {
			User newUser = new User(userName, userPassword, userPermission, personID);
			client.handleMessageFromClientUI(new AddRequest("USER", newUser));
		} catch (UserException e) {
			// TODO deal with error
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks whether the user exists in data base
	 * @param personID - ID of the person who uses the program
	 * @param client -  current running client
	 */
	
	public static void isUserExist(String personID, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		
		message.add("Get"); 						
		message.add("User"); 					
		message.add(""+personID);
		
		//client.handleMessageFromClientUI(message);
	}
	
	public static void updateUserDetails(User updatedUser)
	{
		
	}
	
	public static User getUser(String personID)
	{
		return null;
	}
}
