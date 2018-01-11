package serverAPI;

public class GetEmployeeStoreRequest extends Request
{
	String username;
	public GetEmployeeStoreRequest(String userName) 
	{
		super("GetEmployeeStoreRequest");
		this.username = userName;
	}
	
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}

}
