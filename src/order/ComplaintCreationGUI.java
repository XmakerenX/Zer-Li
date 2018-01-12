package order;


import java.util.ArrayList;

//import com.sun.jdi.NativeMethodException;

import catalog.AddToCatalogGUI;
import catalog.EditableCatalogItem;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.Customer.CustomerException;
import customer.CustomerController;
import customer.CustomerView;
import customer.CustomerView.CustomerViewButton;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceWorkerGUI;
import networkGUI.StoreWorkerGUI;
import product.CatalogItem;
import product.Product;
import product.EditableProductVIew.EditableProductVIewButton;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class ComplaintCreationGUI extends FormController implements ClientInterface{

	ClientInterface SelectOrderInterface = this;
	Response response = null;
	User user;
	ObservableList<CustomerView> customerList; //table's data
	SelectOrderForComplaintGUI selectOrderForComplaintGUI;
	
	 //===============================================================================================================
    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader

    @FXML // fx:id="userTable"
    private TableView<CustomerView> userTable; // Value injected by FXMLLoader
    
    @FXML // fx:id="phoneNumberTextField"
    private TextField phoneNumberTextField; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="nameColumn"
    private TableColumn<CustomerView, String> nameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="selectColumn"
    private TableColumn<?, ?> selectColumn; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberColumn"
    private TableColumn<CustomerView, String> phoneNumberColumn; // Value injected by FXMLLoader

    @FXML // fx:id="idColumn"
    private TableColumn<CustomerView, String> idColumn; // Value injected by FXMLLoader

    @FXML // fx:id="storeColomn"
    private TableColumn<CustomerView, String> storeColomn; // Value injected by FXMLLoader
    //===============================================================================================================
    public void doInit()
    {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	storeColomn.setCellValueFactory(new PropertyValueFactory<>("storeID"));
    	phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
    	selectColumn.setCellValueFactory(new PropertyValueFactory<>("SelectButton"));
    	
    	selectOrderForComplaintGUI = FormController.<SelectOrderForComplaintGUI, AnchorPane>loadFXML(getClass().getResource("/order/SelectOrderForComplaintGUI.fxml"), this);
    }
  //===============================================================================================================
    @FXML
    void onBackButton(ActionEvent event) {
    	idTextField.clear();
    	phoneNumberTextField.clear();
    	userTable.getItems().clear();
    	
    	CustomerServiceWorkerGUI customerServiceWorkerGUI = (CustomerServiceWorkerGUI)parent;
    	client.setUI(customerServiceWorkerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    //===============================================================================================================
    /**
     * searching for specific users
     */
    @FXML
    void onSearchButton(ActionEvent event) {
    	if(!phoneNumberTextField.getText().isEmpty() || !idTextField.getText().isEmpty())
    	{
    		System.out.println("one of the fields is not empty");
    		//we only have phone number
    		if(idTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty())
    		{
    			System.out.println("searching by phone number");
	    		initCustomerTableContent("phoneNumber",phoneNumberTextField.getText());
    		}
    		//we only have id
    		else if(phoneNumberTextField.getText().isEmpty() && !idTextField.getText().isEmpty())
	    	{
	    		System.out.println("searching by id");
	    		initCustomerTableContent("personID",idTextField.getText());
	    	}
    		//we have BOTH fields filled
    		else
    		{
	    		System.out.println("searching by id, SHOULD SEARCH BY BOTH");
	    		initCustomerTableContent("personID",idTextField.getText());
    		}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("One search field must be filled");
			alert.setContentText("Please add searching criteria.");
			alert.showAndWait();
    	}
    		

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
  	//===============================================================================================================

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	//===============================================================================================================
	//select button event handler:
	EventHandler<ActionEvent> selectUser  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {
	    	
	    	CustomerViewButton src = (CustomerViewButton)e.getSource();
			Customer customer = src.getOrigin();
			
			if (selectOrderForComplaintGUI != null)
			{
//				Stage newWindow = new Stage();
//				getClient().setUI(selectOrderForComplaintGUI);
//				selectOrderForComplaintGUI.setClinet(client);
//				selectOrderForComplaintGUI.setCustomer(customer);
//				newWindow.initOwner(FormController.primaryStage);
//		    	newWindow.initModality(Modality.WINDOW_MODAL);  
//				newWindow.setScene(selectOrderForComplaintGUI.getScene());
//				newWindow.showAndWait();
//				getClient().setUI(SelectOrderInterface);
				selectOrderForComplaintGUI.setCustomer(customer);
				selectOrderForComplaintGUI.setUser(user);
				selectOrderForComplaintGUI.setClinet(client);
				getClient().setUI(selectOrderForComplaintGUI);
				//getClient().setUI(SelectOrderInterface);
				selectOrderForComplaintGUI.setClinet(client);
				FormController.primaryStage.setScene(selectOrderForComplaintGUI.getScene());
			}
	    }
	};	
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
    public void initCustomerTableContent(String column, String condition)
    {
			customerList = getCustomerList(column, condition);
	    	userTable.getItems().clear();
			if(customerList==null) return;
	    	userTable.getItems().addAll(customerList);
    }
    //===============================================================================================================
  	public void setUser(User user)
  	{
  		this.user = user;
  	}
    //===============================================================================================================
    /**
     * receives the customers we need to the table view
     * @param column		the column we are checking
     * @param condition		the condition we are looking for
     * @return				returns a list of CustomerView items
     */
    private ObservableList<CustomerView> getCustomerList(String column, String condition)
    {
    	CustomerController.getSertainCustomers(column, condition, client);
    	waitForServerResponse();
    	if(response.getType() == Response.Type.SUCCESS)
    	{
	    	ArrayList<CustomerView> customerViewList = new ArrayList<CustomerView>();
	    	ArrayList<Customer> customerList = (ArrayList<Customer>)response.getMessage();
	    	for(Customer customer : customerList)
	    	{
	    		CustomerView view = null;
				try {
					view = new CustomerView(customer);
				} catch (CustomerException e) {
					e.printStackTrace();
				}
	    		//add function for the button:
	    		view.getSelectButton().setOnAction(selectUser);
	    		customerViewList.add(view);
	    	}
	    	System.out.println(customerViewList);
	    	//clearing response
    		response = null;
	    	return FXCollections.observableArrayList(customerViewList);
    	}
    	else if(response.getType() == Response.Type.ERROR)
    	{
    		Alert alert = new Alert(AlertType.ERROR, "No customers found!", ButtonType.OK);
    		alert.showAndWait();
    		//clearing response
    		response = null;
    		return null;
    	}
    	return null;
    }
  //===============================================================================================================
    //allows us to hand over the client in event handlers
    private Client getClient()
	{
		return this.client;
	}
}