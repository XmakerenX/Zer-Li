package serverAPI;

public class RemoveOrderRequest extends Request {

	private long orderID;
	
	public RemoveOrderRequest(long orderID)
	{
		super("RemoveOrderRequest");
		this.orderID = orderID;
	}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}	
}
