package order;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import customer.Customer.CustomerException;
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
import networkGUI.CustomerServiceWorkerGUI;
import order.OrderView.OrderViewButton;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class SelectOrderForComplaintGUI extends FormController implements ClientInterface{

	User user;
	Response response=null;
	Customer customer;
	ClientInterface SelectOrderInterface = this;
	ObservableList<OrderView> orderList; //table's data
	NewComplaintCreationGUI newComplaintCreationGUI;
	
	//==============================================================================================================
 	@FXML
    private TableColumn<?, ?> selectCulomn;
    @FXML
    private TableView<OrderView> orderTable;
    @FXML
    private TableColumn<?, ?> deliveryDateColumn;
    @FXML
    private Button testCustomer;
    @FXML
    private TableColumn<?, ?> priceColumn;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<?, String> storeColumn;
    @FXML
    private TableColumn<?, ?> deliveryTimeColumn;
    @FXML
    private TableColumn<?, String> shipmentAddressColumn;
    //==============================================================================================================
    public void doInit()
    {
    	storeColumn.setCellValueFactory(new PropertyValueFactory("orderOriginStore"));
    	deliveryDateColumn.setCellValueFactory(new PropertyValueFactory("orderDate"));
    	deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory("orderTime"));
    	shipmentAddressColumn.setCellValueFactory(new PropertyValueFactory("DeliveryAddress"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory("Price"));
    	selectCulomn.setCellValueFactory(new PropertyValueFactory("SelectButton"));
    	
//    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
//    	idColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
//    	storeColomn.setCellValueFactory(new PropertyValueFactory<>("storeID"));
//    	phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
//    	selectColumn.setCellValueFactory(new PropertyValueFactory<>("SelectButton"));
    	
    	newComplaintCreationGUI = FormController.<NewComplaintCreationGUI, AnchorPane>loadFXML(getClass().getResource("/order/NewComplaintCreationGUI.fxml"), this);
    	initOrderTableContent(Long.toString(customer.getID()));
    	System.out.println("IN DO INIT "+orderList);
    }
  //==============================================================================================================
    @FXML
    void onTestCustomer(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Customer received: "+customer, ButtonType.OK);
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
    private ObservableList<OrderView> getOrderList(String condition)
    {
    	OrderController.getOrdersOfaUser(condition);
    	waitForServerResponse();
    	if(response.getType() == Response.Type.SUCCESS)
    	{
	    	ArrayList<OrderView> orderViewList = new ArrayList<OrderView>();
	    	ArrayList<Order> orderList = (ArrayList<Order>)response.getMessage();
	    	for(Order order : orderList)
	    	{
	    		OrderView view = null;
				try {
					view = new OrderView(order);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		//add function for the button:
	    		view.getSelectButton().setOnAction(selectOrder);
	    		orderViewList.add(view);
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
//  				Stage newWindow = new Stage();
//  				getClient().setUI(selectOrderForComplaintGUI);
//  				selectOrderForComplaintGUI.setClinet(client);
//  				selectOrderForComplaintGUI.setCustomer(customer);
//  				newWindow.initOwner(FormController.primaryStage);
//  		    	newWindow.initModality(Modality.WINDOW_MODAL);  
//  				newWindow.setScene(selectOrderForComplaintGUI.getScene());
//  				newWindow.showAndWait();
//  				getClient().setUI(SelectOrderInterface);
  				newComplaintCreationGUI.setCustomer(customer);
  				newComplaintCreationGUI.setOrder(order);
  				newComplaintCreationGUI.setUser(user);
  				newComplaintCreationGUI.setClinet(client);
  				getClient().setUI(newComplaintCreationGUI);
  				//getClient().setUI(SelectOrderInterface);
  				newComplaintCreationGUI.setClinet(client);
  				FormController.primaryStage.setScene(newComplaintCreationGUI.getScene());
  			}
  	    }
  	};	
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
}
