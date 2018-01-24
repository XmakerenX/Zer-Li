package order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import prototype.FormController;
import serverAPI.Response;

//*************************************************************************************************
	/**
	*  Provides a GUI that shows all the user orders and allows to cancel them
	*/
//*************************************************************************************************
public class CancelOrderGUI extends FormController implements ClientInterface{
	
	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private Response replay;
	private Customer currentCustomer = null;
	
    @FXML
    private TableView<OrderRow> orderTable;

    @FXML
    private TableColumn<OrderRow, Number> IDCol;

    @FXML
    private TableColumn<OrderRow, String> statusCol;

    @FXML
    private TableColumn<OrderRow, Number> totalCol;

    @FXML
    private TableColumn<OrderRow, String> creationDateCol;

    @FXML
    private TableColumn<OrderRow, LocalDate> orderDateCol;

    @FXML
    private TableColumn<OrderRow, Button> deliveryInfoCol;

    @FXML
    private TableColumn<OrderRow, String> payMethodPol;

    @FXML
    private TableColumn<OrderRow, Number> storeIDCol;

    @FXML
    private TableColumn<OrderRow, Button> viewProductCol;

    @FXML
    private TableColumn<OrderRow, Button> cancelCol;

    @FXML
    private Button backBtn;

    //*************************************************************************************************
    /**
  	*  Called when the back button is pressed
  	*  Goes back to the parent GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onBack(ActionEvent event) {
    	Client.client.setUI((ClientInterface)parent);
    	FormController.primaryStage.setScene(parent.getScene());
    }

 	//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*  Initializes the table view
  	*/
 	//*************************************************************************************************
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    }
    
    //*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
    //*************************************************************************************************
    private void InitTableView()
    {
    	IDCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Number>("ID"));
    	statusCol.setCellValueFactory( new PropertyValueFactory<OrderRow,String>("Status"));
    	totalCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Number>("Price"));
    	creationDateCol.setCellValueFactory( new PropertyValueFactory<OrderRow,String>("CreationDateTime")); 
    	orderDateCol.setCellValueFactory( new PropertyValueFactory<OrderRow,LocalDate>("RequiredDateTime")); 
    	deliveryInfoCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Button>("viewInfoButton"));
    	payMethodPol.setCellValueFactory( new PropertyValueFactory<OrderRow,String>("orderPaymentMethod"));
    	storeIDCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Number>("orderOriginStore")); 
    	viewProductCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Button>("viewProductsButton"));
    	cancelCol.setCellValueFactory( new PropertyValueFactory<OrderRow,Button>("cancelButton"));
    }
    
    //*************************************************************************************************
    /**
  	*  cancels the Order that it's cancel button was pressed
  	*/
    //*************************************************************************************************
	EventHandler<ActionEvent> cancelAction  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {	    	
	    	Button b = (Button)e.getSource();
	    	OrderRow orderItem = (OrderRow)b.getUserData();
	    	
			ButtonType result = showConfirmationDialog("About to cancel order",
					"Are you sure you want to cancel the order?");
			if (result == ButtonType.YES)
			{
				OrderController.cancelOrder((Order)orderItem);

				// wait for response
				waitForResponse();

				if (replay == null)
					return;

				if (replay.getType() == Response.Type.SUCCESS)
				{
					showInformationMessage((String)replay.getMessage());
					orderTable.getItems().remove(orderItem);
				}
				else
				{
					showErrorMessage("Order was not canceled");
				}
			}
	    }
	};
    
	//*************************************************************************************************
    /**
  	*  Displays the products in the current order
  	*/
    //*************************************************************************************************
	EventHandler<ActionEvent> viewProducts  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {	    	
	    	Button b = (Button)e.getSource();
	    	OrderRow orderItem = (OrderRow)b.getUserData();
	    	
			OrderController.getOrderProducts(orderItem.getID());
			// wait for response
			waitForResponse();
			
			if (replay == null)
				return;

			if (replay.getType() == Response.Type.SUCCESS)
			{
				ArrayList<ProductInOrder> prodcutsInOrder = (ArrayList<ProductInOrder>)replay.getMessage();
				showOrderProducts(prodcutsInOrder);
			}
			else
			{
				replay = null;
				OrderController.getOrderCustomProducts(orderItem.getID());
				// wait for response
				waitForResponse();

				if (replay == null)
					return;

				if (replay.getType() == Response.Type.SUCCESS)
				{
					ArrayList<CustomItemInOrder> customItems = (ArrayList<CustomItemInOrder>)replay.getMessage();
					shwoOrderCustomProducts(customItems);
				}
			}
	    }
	};
		
	//*************************************************************************************************
    /**
  	*  Shows the order Products
  	*  @param prodcutsInOrder the order Products to show
  	*/
    //*************************************************************************************************
	private void showOrderProducts(ArrayList<ProductInOrder> prodcutsInOrder)
	{
		Stage newWindow = new Stage();
		ViewProductInOrder viewProductsInOrder = FormController.<ViewProductInOrder, AnchorPane>loadFXML(getClass().getResource("/order/ViewProductsInOrder.fxml"), null);

		newWindow.initOwner(FormController.getPrimaryStage());
		newWindow.initModality(Modality.WINDOW_MODAL);  
		newWindow.setScene(viewProductsInOrder.getScene());
		viewProductsInOrder.loadProducts(prodcutsInOrder);
		viewProductsInOrder.setWindowStage(newWindow);
		newWindow.requestFocus();     
		newWindow.showAndWait();
	}
	
	//*************************************************************************************************
    /**
  	*  Shows the order custom Products
  	*  @param customItems the order custom Products to show
  	*/
    //*************************************************************************************************
	private void shwoOrderCustomProducts(ArrayList<CustomItemInOrder> customItems)
	{
		Stage newWindow = new Stage();
		ViewCustomProductsInOrder viewProductsInOrder = FormController.<ViewCustomProductsInOrder, AnchorPane>loadFXML(getClass().getResource("/order/ViewCustomProductsInOrder.fxml"), null);

		newWindow.initOwner(FormController.getPrimaryStage());
		newWindow.initModality(Modality.WINDOW_MODAL);  
		newWindow.setScene(viewProductsInOrder.getScene());
		viewProductsInOrder.loadCustomProducts( customItems);
		viewProductsInOrder.setWindowStage(newWindow);
		newWindow.requestFocus();     
		newWindow.showAndWait();
	}
	
    //*************************************************************************************************
    /**
  	*  Request from the Server the customer orders
  	*  TODO: delete the event parameter
  	*/
    //*************************************************************************************************
    public void onRefresh(ActionEvent event) {
    	replay = null;
    	OrderController.requestCustomerOrders(currentCustomer.getID());
    	
    	// wait for response
    	waitForResponse();
		
    	if (replay == null)
    		return;
    	
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		ArrayList<Order> orders = (ArrayList<Order>)replay.getMessage();
    		final ObservableList<OrderRow> observableOrders = FXCollections.observableArrayList();

    		for (Order o : orders)
    		{
    			try {
    				OrderRow newOrderRow = new OrderRow(o);
    				observableOrders.add(newOrderRow);
    				newOrderRow.getCancelButton().setOnAction(cancelAction);
    				newOrderRow.getViewProductsButton().setOnAction(viewProducts);
    			} catch (OrderException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}

    		orderTable.setItems(observableOrders);
    		Collections.sort(orderTable.getItems());
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  Called from the client when the server sends a response
  	*  fills the TableView with the received products data
  	*  @param message The Server response , an ArrayList of products
  	*/
    //*************************************************************************************************
	@Override
	public void display(Object message)
	{
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	replay = (Response)message;

		synchronized(this)
		{
			this.notify();
		}
	}

    //*************************************************************************************************
    /**
    *  Sets the current Customer that is viewing the catalog
  	*  @param currentCustomer the customer to be set
  	*/
    //*************************************************************************************************
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}
				
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
}
