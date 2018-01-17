package order;

import java.util.Observable;

import javafx.scene.control.Button;

public class OrderItemViewButton extends Observable {
	private Button button;
	private Object orderItem;
	
	public OrderItemViewButton(Object orderItem, String buttonText)
	{
		this.button = new Button(buttonText);
		this.button.setUserData(this);
		this.orderItem = orderItem;
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
