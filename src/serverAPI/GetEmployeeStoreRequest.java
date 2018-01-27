package serverAPI;


//*************************************************************************************************
/**
*  a Request for the server to get Employees store ID
*  Stores the data for the GetEmployeeStoreRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetEmployeeStoreRequest extends Request
{

	private static final long serialVersionUID = 5616528800319922352L;
	private String username;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetCustomItemRequest with the following parameters
	 *  @param userName the user name for whom to get Employees store ID 
	 */
	//*************************************************************************************************
	public GetEmployeeStoreRequest(String userName) 
	{
		super("GetEmployeeStoreRequest");
		this.username = userName;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the GetEmployeeStoreRequest username
	 *  @return the GetEmployeeStoreRequest username 
	 */
	//*************************************************************************************************
	public String getUsername() 
	{
		return username;
	}
	
	//*************************************************************************************************
	/**
	 *  sets the GetEmployeeStoreRequest username
	 *  @param username the GetEmployeeStoreRequest username to set
	 */
	//*************************************************************************************************
	public void setUsername(String username) 
	{
		this.username = username;
	}

}
