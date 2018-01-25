package order;

import java.util.Observable;

import javafx.scene.control.Button;

//*************************************************************************************************
	/**
	*  This class creates an observable button
	*/
//*************************************************************************************************
public class OrderItemViewButton extends Observable 
{
	private Button button;
	private Object orderItem;
	private OrderView origin;

	//*************************************************************************************************
	/**
	*  Creates a new OrderItemViewButton with the following parameters
	*  @param orderItem the item this button is connected to
	*  @param buttonText the button text
	*/
	//*************************************************************************************************
	public OrderItemViewButton(Object orderItem, String buttonText)
	{
		this.button = new Button(buttonText);
		this.button.setUserData(this);
		this.orderItem = orderItem;
	}
	public OrderView getOrigin()
	{
		return this.origin;
	}
	
	public void change()
	{
		this.setChanged();
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public Object getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Object orderItem) {
		this.orderItem = orderItem;
	}
	
	public String getButtonText()
	{
		return this.button.getText();
	}
	
	
}
