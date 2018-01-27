package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to get order custom items
*  Stores the data for the GetCustomItemRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetCustomItemRequest extends Request {

	private static final long serialVersionUID = 346339036627079632L;
	private long orderID;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetCustomItemRequest with the following parameters
	 *  @param orderID the orderID from which to get the custom items
	 */
	//*************************************************************************************************
	public GetCustomItemRequest(long orderID)
	{
		super("GetCustomItemRequest");
		this.orderID = orderID;
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetCustomItemRequest orderID
	 *  @return the GetCustomItemRequest orderID 
	 */
	//*************************************************************************************************
	public long getOrderID() {
		return orderID;
	}
	
}
