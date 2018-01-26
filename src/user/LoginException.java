package user;
/**
 * an exception that is thrown if there's something wrong with the login process
 * @author dk198
 *
 */
public class LoginException extends Exception 
{
	public LoginException(String message)
	{
		super(message);
	}
}
