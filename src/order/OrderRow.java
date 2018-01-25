package order;

import java.util.Calendar;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.CatalogItem;
import product.EditProductGUI;
import product.Product;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the cancelOrder TableView  
	*/
//*************************************************************************************************
public class OrderRow extends Order  implements Comparable<OrderRow>{

	private static final long serialVersionUID = 920338174354531395L;
	private Button viewInfoButton;
	private orderRowButton viewProductsButton;
	private Button cancelButton;
	private OrderViewButton selectButton;
	
	public class orderRowButton extends Button
	{
		OrderRow origin;
		public orderRowButton(OrderRow org,String text)
		{
			super(text);
			this.origin = org;
		}
	}

	EventHandler<ActionEvent> onDeliveryAction  = new EventHandler<ActionEvent>() 
	{
		@Override public void handle(ActionEvent e) 
		{	    	
			Button b = (Button)e.getSource();

			OrderRow order = (OrderRow)b.getUserData();
			Stage newWindow = new Stage();
			ViewDelivery viewDelivery = FormController.<ViewDelivery, AnchorPane>loadFXML(getClass().getResource("/order/ViewDelivery.fxml"), null);

			newWindow.initOwner(FormController.getPrimaryStage());
			newWindow.initModality(Modality.WINDOW_MODAL);  
			newWindow.setScene(viewDelivery.getScene());
			viewDelivery.loadDeliveryInfo(order.getDeliveryInfo());
			viewDelivery.setWindowStage(newWindow);
			newWindow.requestFocus();     
			newWindow.showAndWait();
		}
	};
		
	public OrderRow(Order order) throws OrderException
	{
		super(order.getID(), order.getStatus(), order.getPrice(), order.getOrderCreationDateTime(),order.getOrderRequiredDateTime(),
				order.getDeliveryInfo(), order.getOrderPaymentMethod(), order.getOrderOriginStore(), order.getCustomerID());
		
		viewInfoButton = new Button("View Delivery");
		viewInfoButton.setOnAction(onDeliveryAction);
		if (this.getDeliveryInfo() == null)
			viewInfoButton.setDisable(true);
		else
			viewInfoButton.setUserData(this);
		
		viewProductsButton = new orderRowButton(this,"View Products");
		viewProductsButton.setUserData(this);
		
		cancelButton = new Button("Cancel");
		cancelButton.setUserData(this);
		
		Calendar currentTime = Calendar.getInstance();
		if (currentTime.after(order.getOrderRequiredDateTime()))
			cancelButton.setDisable(true);
		
		selectButton = new OrderViewButton(this, "Select");
	}

	public Button getViewInfoButton() {
		return viewInfoButton;
	}

	public void setViewInfoButton(Button viewInfoButton) {
		this.viewInfoButton = viewInfoButton;
	}

	public Button getViewProductsButton() {
		return viewProductsButton;
	}

		
	public Button getCancelButton() {
		return cancelButton;
	}
	
	public OrderViewButton getSelectButton() {
		return selectButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}
	
	//===========================================================================================================
		public class OrderViewButton extends Button
		{
			OrderRow origin;
			public OrderViewButton(OrderRow origin, String name)
			{
				super(name);
				this.origin = origin;
			}
			
			public OrderRow getOrigin()
			{
				return this.origin;
			}
		}
		//===========================================================================================================
	
	public int compareTo(OrderRow o)
	{
		if (this.orderRequiredDateTime.equals(o.getOrderRequiredDateTime()))
			return 0;
			
		if (this.orderRequiredDateTime.after(o.getOrderRequiredDateTime()))
			return -1;
			
		if (this.orderRequiredDateTime.before(o.getOrderRequiredDateTime()))
			return 1;
			
		return -1; 
	}
		
}
