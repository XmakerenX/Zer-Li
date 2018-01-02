package serverAPI;

import user.User;

public class LogoutRequest extends Request {

	User user;
	
	public LogoutRequest(User user)
	{
		super("LoginRequest");
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
