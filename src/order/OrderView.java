package order;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.EditProductGUI;
import product.Product;
import prototype.FormController;

public class OrderView extends Order {

	private Button viewInfoButton;
	private OrderItemViewButton viewProductsButton;
	private OrderItemViewButton cancelButton;
	
	EventHandler<ActionEvent> cancelAction  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {	    	
	    	Button b = (Button)e.getSource();
	    	OrderItemViewButton obsButton = (OrderItemViewButton)b.getUserData();
	    	obsButton.change();
	    	obsButton.notifyObservers(obsButton.getOrderItem());
	    }
	};

	EventHandler<ActionEvent> onDeliveryAction  = new EventHandler<ActionEvent>() 
	{
		@Override public void handle(ActionEvent e) 
		{	    	
			Button b = (Button)e.getSource();

			OrderView order = (OrderView)b.getUserData();
			Stage newWindow = new Stage();
			ViewDelivery viewDelivery = FormController.<ViewDelivery, AnchorPane>loadFXML(getClass().getResource("/order/ViewDelivery.fxml"), null);

			newWindow.initOwner(FormController.getPrimaryStage());
			newWindow.initModality(Modality.WINDOW_MODAL);  
			newWindow.setScene(viewDelivery.getScene());
			viewDelivery.loadDeliveryInfo(order.getDelivaryInfo());
			viewDelivery.setWindowStage(newWindow);
			newWindow.requestFocus();     
			newWindow.showAndWait();
		}
	};
	
	public OrderView(Order order) throws OrderException
	{
		super(order.getID(), order.getStatus(), order.getPrice(), order.getOrderCreationDateTime(),order.getOrderRequiredDateTime(),
				order.getDelivaryInfo(), order.getOrderPaymentMethod(), order.getOrderOriginStore(), order.getCustomerID());
		
		viewInfoButton = new Button("View Delivery");
		viewInfoButton.setOnAction(onDeliveryAction);
		if (this.getDelivaryInfo() == null)
			viewInfoButton.setDisable(true);
		else
			viewInfoButton.setUserData(this);
		
		viewProductsButton = new OrderItemViewButton(this, "View Products");
		viewProductsButton.getButton().setOnAction(cancelAction);
		
		cancelButton = new OrderItemViewButton(this, "Cancel");
		cancelButton.getButton().setOnAction(cancelAction);
	}

	public Button getViewInfoButton() {
		return viewInfoButton;
	}

	public void setViewInfoButton(Button viewInfoButton) {
		this.viewInfoButton = viewInfoButton;
	}

	public Button getViewProductsButton() {
		return viewProductsButton.getButton();
	}

	public void setViewProductsButton(Button viewProductsButton) {
		this.viewProductsButton.setButton(viewProductsButton);
	}

	public OrderItemViewButton getObservableViewProductsButton()
	{
		return this.viewProductsButton;
	}
	
	public Button getCancelButton() {
		return cancelButton.getButton();
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton.setButton(cancelButton);
	}
	
	public OrderItemViewButton getObservableCancelButton()
	{
		return this.cancelButton;
	}
	
	
}
