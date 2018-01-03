package serverAPI;

import user.User;

public class LogoutRequest extends Request {

	User user;
	
	public LogoutRequest(User user)
	{
		super("LogoutRequest");
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
