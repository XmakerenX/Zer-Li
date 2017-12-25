package user;

import java.util.ArrayList;

import client.Client;
import serverAPI.LoginRequest;
import user.User.Permissions;
import user.User.Status;
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
