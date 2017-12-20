package user;

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
			if (user.getUserStatus() != User.Status.BLOCKED)
			{
				if (user.getUserPassword().equals(password))
				{
					user.clearUnsuccessfulTries();
				}
				else
				{
					user.incUnsuccessfulTries();
					throw new LoginException("username or password is wrong");
				}
			}
			else
			{
				throw new LoginException("User is blocked");
			}
		}
		else 
		{
			user.incUnsuccessfulTries();
			throw new LoginException("username or password is wrong");
		}
		
	}
	
	public static boolean createNewUser(String userName, String userPassword, Permissions userPermission, String personID, Status userStatus) throws UserException
	{
		
		return false;
	}
	
	public static boolean isUserExist(String personID)
	{
		return false;
	}
	
	public static void updateUserDetails(User updatedUser)
	{
		
	}
	
	public static User getUser(String personID)
	{
		return null;
	}
}
