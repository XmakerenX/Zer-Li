package serverAPI;

public class LoginRequest extends Request {

	String username;
	String password;
	
	public LoginRequest(String username, String password)
	{
		super("LoginRequest");
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
