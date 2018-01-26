package serverAPI;

import user.User;

//*************************************************************************************************
/**
*  a Request for the server to log out user
*  Stores the data for the LogoutRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class LogoutRequest extends Request {

	private static final long serialVersionUID = -7841631408114190159L;
	private User user;
	
	//*************************************************************************************************
	/**
	 * Creates an LogoutRequest with the following parameters
	 * @param user the user to log out
	 */
	//*************************************************************************************************
	public LogoutRequest(User user)
	{
		super("LogoutRequest");
		this.user = user;
	}

	//*************************************************************************************************
	/**
	 *  Returns the LogoutRequest user
	 *  @returns the LogoutRequest user 
	 */
	//*************************************************************************************************
	public User getUser() {
		return user;
	}

}
