package order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import networkGUI.CustomerServiceWorkerGUI;
import order.OrderRow.OrderViewButton;
import prototype.FormController;
import serverAPI.Response;
import user.User;
/**
 * In this GUI we select an order of a specific user, to complain about
 * @author dk198
 *
 */
public class SelectOrderForComplaintGUI extends FormController implements ClientInterface, Observer{

	User user;
	Response response=null;
	Customer customer;
	ClientInterface SelectOrderInterface = this;
	ObservableList<OrderRow> orderList; //table's data
	NewComplaintCreationGUI newComplaintCreationGUI;
	String StoreAddress;
	//==============================================================================================================

   @FXML
    private Label storeLabel;
	@FXML
    private TableColumn<?, ?> selectCulomn;
    @FXML
    private TableView<OrderRow> orderTable;
    @FXML
    private TableColumn<OrderRow, LocalDate> deliveryDateColumn;
    @FXML
    private Button testCustomer;
    @FXML
    private TableColumn<?, ?> priceColumn;
    @FXML
    private Button backButton;
   // @FXML
   // private TableColumn<?, String> storeColumn;
    @FXML
    private TableColumn<OrderRow, OrderItemViewButton> orderColumn;
    //==============================================================================================================
    public void doInit()
    {
    	storeLabel.setText("Store Address: "+getStoreAddress()+"\nstoreID: "+customer.getStoreID());
    	//storeColumn.setCellValueFactory(new PropertyValueFactory("orderOriginStore"));
    	deliveryDateColumn.setCellValueFactory(new PropertyValueFactory<OrderRow,LocalDate>("CreationDateTime"));
    	orderColumn.setCellValueFactory( new PropertyValueFactory<OrderRow,OrderItemViewButton>("viewProductsButton"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory("Price"));
    	selectCulomn.setCellValueFactory(new PropertyValueFactory("SelectButton"));
    	
    	newComplaintCreationGUI = FormController.<NewComplaintCreationGUI, AnchorPane>loadFXML(getClass().getResource("/order/NewComplaintCreationGUI.fxml"), this);
    	initOrderTableContent(Long.toString(customer.getID()));
    }
  public String getStoreAddress() {
		return StoreAddress;
	}
	public void setStoreAddress(String storeAddress) {
		StoreAddress = storeAddress;
	}
	//==============================================================================================================
    @FXML
    void onTestCustomer(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Customer received: "+customer+" Store id: "+customer.getStoreID(), ButtonType.OK);
		alert.showAndWait();

    }
  //==============================================================================================================
    @FXML
    void onBackButton(ActionEvent event) {
    	orderTable.getItems().clear();
    	
    	ComplaintCreationGUI complaintCreationGUI = (ComplaintCreationGUI)parent;
    	client.setUI(complaintCreationGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
  //===============================================================================================================
  	public void display(Object message) {
  		
      	System.out.println(message.toString());
      	System.out.println(message.getClass().toString());
  		
  		Response response = (Response)message;
  		this.response = response;
  		
  		synchronized(this)
  		{
  			this.notify();
  		}
  	}
  	//==============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	//==============================================================================================================
  	public void setUser(User user)
  	{
  		this.user = user;
  	}
  //==============================================================================================================
  	public void setCustomer(Customer customer)
  	{
  		this.customer = customer;
  	}
  //===============================================================================================================
    /**
     * receives the order we need to the table view
     * @param condition		the id of the customer who's orders we need
     * @return				returns a list of OrderView items
     */
    private ObservableList<OrderRow> getOrderList(String condition)
    {
    	OrderController.getOrdersOfaUser(condition);
    	waitForServerResponse();
    	if(response.getType() == Response.Type.SUCCESS)
    	{
	    	ArrayList<OrderRow> orderViewList = new ArrayList<OrderRow>();
	    	ArrayList<Order> orderList = (ArrayList<Order>)response.getMessage();
	    	for(Order order : orderList)
	    	{
	    		if(order.getOrderOriginStore()==customer.getStoreID())
	    		{
		    		System.out.println(order);
		    		OrderRow view = null;
					try {
						view = new OrderRow(order);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		//add function for the button:
		    		view.getSelectButton().setOnAction(selectOrder);
		    		view.getViewProductsButton().setOnAction(showOrder);
		    		orderViewList.add(view);
	    		}
	    	}
	    	System.out.println(orderViewList);
	    	//clearing response
    		response = null;
	    	return FXCollections.observableArrayList(orderViewList);
    	}
    	else if(response.getType() == Response.Type.ERROR)
    	{
    		//Alert alert = new Alert(AlertType.ERROR, "No orders found for this customer!", ButtonType.OK);
    		//alert.showAndWait();
    		//clearing response
    		response = null;
    		return null;
    	}
    	return null;
    }
  //===============================================================================================================
  	//select button event handler:
  	EventHandler<ActionEvent> selectOrder  = new EventHandler<ActionEvent>() 
  	{
  	    @Override public void handle(ActionEvent e) 
  	    {
  	    	
  	    	OrderViewButton src = (OrderViewButton)e.getSource();
  			Order order = src.getOrigin();
  			if (newComplaintCreationGUI != null)
  			{
//  				newComplaintCreationGUI.setCustomer(customer);
//  				newComplaintCreationGUI.setOrder(order);
//  				newComplaintCreationGUI.setUser(user);
//  				newComplaintCreationGUI.setClinet(client);
//  				getClient().setUI(newComplaintCreationGUI);
//  				//getClient().setUI(SelectOrderInterface);
//  				newComplaintCreationGUI.setClinet(client);
//  				newComplaintCreationGUI.doInit();
//  				FormController.primaryStage.setScene(newComplaintCreationGUI.getScene());
  				
  				newComplaintCreationGUI.setCustomer(customer);
  				newComplaintCreationGUI.setOrder(order);
  				
  				
  				newComplaintCreationGUI.setUser(user);
  				getClient().setUI(newComplaintCreationGUI);
  				newComplaintCreationGUI.setClinet(Client.client);
  				
    			FormController.primaryStage.setScene(newComplaintCreationGUI.getScene());
  				
  				
  				
  			}
  	    }
  	};	
  	 //===============================================================================================================
  	//show order button event handler:
  	EventHandler<ActionEvent> showOrder  = new EventHandler<ActionEvent>() 
  	{
  	    @Override public void handle(ActionEvent e) 
  	    {
  	    	
  	    	OrderItemViewButton src = (OrderItemViewButton)e.getSource();
  			Order order = src.getOrigin();
  			onViewProducts((OrderRow)order);
  	    }
  	};	
  	//===============================================================================================================
  	private void onViewProducts(OrderRow orderItem)
	{
		OrderController.getOrderProducts(orderItem.getID());
		
		// wait for response
		synchronized(this)
		{
			// wait for server response
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (response != null)
		{
			if (response.getType() == Response.Type.SUCCESS)
			{
				ArrayList<ProductInOrder> prodcutsInOrder = (ArrayList<ProductInOrder>)response.getMessage();
				
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
			else
			{
				response = null;
				OrderController.getOrderCustomProducts(orderItem.getID());
				
				// wait for response
				synchronized(this)
				{
					// wait for server response
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (response != null)
				{
					if (response.getType() == Response.Type.SUCCESS)
					{
						ArrayList<CustomItemInOrder> customItems = (ArrayList<CustomItemInOrder>)response.getMessage();
						
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
				}				
			}
		}
	}
  	//===============================================================================================================
    //allows us to hand over the client in event handlers
    private Client getClient()
	{
		return this.client;
	}
	//===============================================================================================================
	//waiting for the server to respond
    public void waitForServerResponse()
    {
    	synchronized(this) 
    	{
    		try 
    		{
    			this.wait();
    		}
    		
    		catch (InterruptedException e) 
    		{
    			e.printStackTrace();
    		}
    	} 
    }
    //===============================================================================================================
    /**
     * initializes the table view
     * @param column		the column we are checking
     * @param condition		the condition we are looking for
     */
    public void initOrderTableContent(String condition)
    {
			orderList = getOrderList(condition);
	    	orderTable.getItems().clear();
			if(orderList==null) return;
			orderTable.getItems().addAll(orderList);
    }
	@Override
	public void update(Observable o, Object arg)
	{
		OrderItemViewButton b = (OrderItemViewButton)o;
		if (b.getButtonText().equals("Cancel"))
		{
			Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("About to cancel order");
			alert.setContentText("Are you sure you want to cancel the order?");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			ButtonType result = alert.showAndWait().get();
			if (result == ButtonType.YES)
			{
				OrderRow orderItem = (OrderRow)arg;
				OrderController.cancelOrder(orderItem.getID());

				// wait for response
				synchronized(this)
				{
					// wait for server response
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (response != null)
				{
					if (response.getType() == Response.Type.SUCCESS)
					{
						alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Sucesss");
						alert.setContentText("Order canceled successfully");
						alert.showAndWait();
						this.orderTable.getItems().remove(orderItem);
					}
					else
					{
						alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Failure");
						alert.setContentText("Order was not canceled");
						alert.showAndWait();
					}

				}

			}
		}
		
		if (b.getButtonText().equals("View Products"))
		{
			OrderRow orderItem = (OrderRow)arg;
			OrderController.getOrderProducts(orderItem.getID());
			
			// wait for response
			synchronized(this)
			{
				// wait for server response
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (response != null)
			{
				if (response.getType() == Response.Type.SUCCESS)
				{
					ArrayList<ProductInOrder> prodcutsInOrder = (ArrayList<ProductInOrder>)response.getMessage();
					
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
			}

		}
	}
}
