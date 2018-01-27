package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to cancel order
*  Stores the data for the RemoveOrderRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class RemoveOrderRequest extends Request {

	private static final long serialVersionUID = -5432502271765102195L;
	private long orderID;
	
	//*************************************************************************************************
	/**
	 * Creates an RemoveOrderRequest with the following parameters
	 * @param orderID the orderID of the order to cancel
	 */
	//*************************************************************************************************
	public RemoveOrderRequest(long orderID)
	{
		super("RemoveOrderRequest");
		this.orderID = orderID;
	}

	//*************************************************************************************************
	/**
	 *  Returns the RemoveOrderRequest orderID
	 *  @return the RemoveOrderRequest orderID 
	 */
	//*************************************************************************************************
	public long getOrderID() {
		return orderID;
	}

	//*************************************************************************************************
	/**
	 *  sets the RemoveOrderRequest orderID
	 *  @param orderID the orderID to be set 
	 */
	//*************************************************************************************************
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}	
}
