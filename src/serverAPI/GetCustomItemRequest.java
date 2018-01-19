package serverAPI;

public class GetCustomItemRequest extends Request {

	private long orderID;
	
	public GetCustomItemRequest(long orderID)
	{
		super("GetCustomItemRequest");
		this.orderID = orderID;
	}

	public long getOrderID() {
		return orderID;
	}
	
}
