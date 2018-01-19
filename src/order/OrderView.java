package order;

import java.time.LocalDate;

import customer.CustomerView;
import customer.CustomerView.CustomerViewButton;
import javafx.scene.control.Button;

public class OrderView extends Order{
	//===========================================================================================================
	public class OrderViewButton extends Button
	{
		OrderView origin;
		public OrderViewButton(OrderView origin, String name)
		{
			super(name);
			this.origin = origin;
		}
		
		public OrderView getOrigin()
		{
			return this.origin;
		}
	}
	//===========================================================================================================
	private OrderViewButton selectButton;
	
	public OrderView(Order order)
			throws OrderException 
	{
		//super(order.getID(), order.getStatus(), order.getPrice(), order.getOrderCreationDateTime(), order.getOrderRequiredDateTime(), 
			//	order.getDelivaryInfo().getDelivaryAddress(), order.getDelivaryInfo().getReceiverName(), order.getDelivaryInfo().getReceiverPhoneNumber(), order.getOrderPaymentMethod(), 
				//order.getOrderOriginStore(), order.getCustomerID());
		super(order.getID(), order.getStatus(), order.getPrice(), order.getOrderCreationDateTime(),order.getOrderRequiredDateTime(),
				order.getDeliveryInfo(), order.getOrderPaymentMethod(), order.getOrderOriginStore(), order.getCustomerID());
		this.selectButton = new OrderViewButton(this, "Select");
	}
	//===========================================================================================================
	public Button getSelectButton()
	{
		return this.selectButton;
	}
	public void setSelectButton(OrderViewButton button) 
	{
		this.selectButton = button;
	}
	//===========================================================================================================
	public String toString()
	{
		return "Order: id: "+this.getID()+" ,for customer: "+this.getCustomerID();
	}
}