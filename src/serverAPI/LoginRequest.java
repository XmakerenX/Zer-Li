package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to login user
*  Stores the data for the LoginRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class LoginRequest extends Request {

	private static final long serialVersionUID = 2033790265881488747L;
	private String username;
	private String password;
	
	//*************************************************************************************************
	/**
	 * Creates an LoginRequest with the following parameters
	 * @param username the username to login with
	 * @param password the password to login with
	 */
	//*************************************************************************************************
	public LoginRequest(String username, String password)
	{
		super("LoginRequest");
		this.username = username;
		this.password = password;
	}

	//*************************************************************************************************
	/**
	 *  Returns the LoginRequest username
	 *  @returns the LoginRequest username 
	 */
	//*************************************************************************************************
	public String getUsername() {
		return username;
	}

	//*************************************************************************************************
	/**
	 *  Returns the LoginRequest password
	 *  @returns the ImageRequest password 
	 */
	//*************************************************************************************************
	public String getPassword() {
		return password;
	}

}
