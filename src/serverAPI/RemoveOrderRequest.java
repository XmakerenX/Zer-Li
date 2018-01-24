package serverAPI;

import order.Order;

public class RemoveOrderRequest extends Request 
{
	private Order reqOrder;
	
	public RemoveOrderRequest(Order order)
	{
		super("RemoveOrderRequest");
		this.reqOrder = order;
	}

	public Order getReqOrder() 
	{
		return reqOrder;
	}

	public void setReqOrder(Order reqOrder) 
	{
		this.reqOrder = reqOrder;
	}
	
}
