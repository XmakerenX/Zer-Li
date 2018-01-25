package order;

import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the cancelOrder TableView  
	*  It is the order class with buttons added for showing in a TableView
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

	//*************************************************************************************************
	/**
	*  Handles the button press of the view Delivery inside the orderItemView
	*  shows the current Delivery Information  
	*/
	//*************************************************************************************************
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
		
	//*************************************************************************************************
	/**
	*  Creates a new OrderRow with the following parameters
	*  @param OrderRow The order form which to init the orderRow
	*/
	//*************************************************************************************************
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

	//*************************************************************************************************
	/**
	*  Return the View Info Button
	*  @return the View Info Button 
	*/
	//*************************************************************************************************
	public Button getViewInfoButton() {
		return viewInfoButton;
	}

	//*************************************************************************************************
	/**
	 *  sets the View Info Button
	 *  @param the new View Info Button to set  
	 */
	//*************************************************************************************************
	public void setViewInfoButton(Button viewInfoButton) {
		this.viewInfoButton = viewInfoButton;
	}

	//*************************************************************************************************
	/**
	*  Return the View Products Button
	*  @return the View Products Button 
	*/
	//*************************************************************************************************
	public Button getViewProductsButton() {
		return viewProductsButton;
	}

	//*************************************************************************************************
	/**
	*  Return the Cancel Button
	*  @return the Cancel Button 
	*/
	//*************************************************************************************************
	public Button getCancelButton() {
		return cancelButton;
	}
	
	//*************************************************************************************************
	/**
	*  Return the Select Button
	*  @return the Select Button 
	*/
	//*************************************************************************************************
	public OrderViewButton getSelectButton() {
		return selectButton;
	}

	//*************************************************************************************************
	/**
	 *  sets the Cancel Button
	 *  @param cancelButton the new Cancel Button to set  
	 */
	//*************************************************************************************************
	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}
	
	//*************************************************************************************************
	/**
	 *  Compares OrderRows by their order Required DateTime
	 *  @param o the order to Compare with
	 *  @see java.lang.Comparable
	 */
	//*************************************************************************************************
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
