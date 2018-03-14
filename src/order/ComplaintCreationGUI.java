package order;


import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.Customer.CustomerException;
import customer.CustomerController;
import customer.CustomerView;
import customer.CustomerView.CustomerViewButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceWorkerGUI;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import user.User;
import utils.FormController;
/**
 * this class holds all the GUI related info so we could start the complaint creation process
 * @author dk198
 *
 */
public class ComplaintCreationGUI extends FormController implements ClientInterface{

	//local variables:
	ClientInterface SelectOrderInterface = this;
	Response response = null;
	User user;
	ObservableList<CustomerView> customerList; //table's data
	SelectOrderForComplaintGUI selectOrderForComplaintGUI;
	ArrayList<Store> currentStores = new ArrayList<Store>();
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
    
    @FXML // fx:id="storeAddress"
    private TableColumn<?, ?> storeAddress;// Value injected by FXMLLoader
    /**
     * changes the default listener
     */
  public void initialize(){
	  
	  idTextField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) 
	    {
	    	if(newValue.isEmpty() || (newValue.matches("[0-9]+") && newValue.length()<=9))
	    		idTextField.setText(newValue);

	    	else
	    		idTextField.setText(oldValue);
	    }
	});
	  phoneNumberTextField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) 
		    {
		    	if(newValue.isEmpty() || (newValue.matches("[0-9]+")&& newValue.length()<=10))
		    		phoneNumberTextField.setText(newValue);

		    	else
		    		phoneNumberTextField.setText(oldValue);
		    }
		});
  }
    //===============================================================================================================
    /**
     * an initialization function that sets the tables
     */
    public void doInit()
    {
    	currentStores = getCurrentStores();
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	storeColomn.setCellValueFactory(new PropertyValueFactory<>("storeID"));
    	storeAddress.setCellValueFactory(new PropertyValueFactory<>("StoreAddress"));
    	phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
    	selectColumn.setCellValueFactory(new PropertyValueFactory<>("SelectButton"));
    	
    	selectOrderForComplaintGUI = FormController.<SelectOrderForComplaintGUI, AnchorPane>loadFXML(getClass().getResource("/order/SelectOrderForComplaintGUI.fxml"), this);
    }
  //===============================================================================================================
    /*
     * return a local view of the store table
     */
    private ArrayList<Store> getCurrentStores()
    {
    	Client.getInstance().handleMessageFromClientUI(new GetRequest("Store"));
    	waitForServerResponse();
    	if(response.getType().name().equals("SUCCESS"))
    	{
    		return ( ArrayList<Store>)response.getMessage();
    	}
    	else return null;
    }
    //===============================================================================================================
    /*
     * search the local table of stores to get the store address of a given storeID
     */
    private String getStoreName(long storeID)
    {
    	for(Store storeElement : currentStores)
    	{
    		if (storeElement.getStoreID() == storeID)
    		{
    			return storeElement.getStoreAddress();
    		}
    	}
    	return "";
    }
  //===============================================================================================================
    @FXML
    /**
     * 	returns us back to the gui we came from
     * @param event
     */
    void onBackButton(ActionEvent event) {
    	idTextField.clear();
    	phoneNumberTextField.clear();
    	userTable.getItems().clear();
    	
    	CustomerServiceWorkerGUI customerServiceWorkerGUI = (CustomerServiceWorkerGUI)parent;
    	Client.getInstance().setUI(customerServiceWorkerGUI);
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
	    		System.out.println("searching by id and a phone number");
	    		String conditionForSearch = idTextField.getText()+"--"+phoneNumberTextField.getText();
	    		System.out.println(conditionForSearch);
	    		initCustomerTableContent("personID",conditionForSearch);
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
    //changing the display method so that we could use the response object
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
	//select button event handler:
	EventHandler<ActionEvent> selectUser  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {
	    	
	    	CustomerViewButton src = (CustomerViewButton)e.getSource();
	    	CustomerView customerView = src.getOrigin();
			Customer customer = src.getOrigin();
			
			if (selectOrderForComplaintGUI != null)
			{
				Client.getInstance().setUI(selectOrderForComplaintGUI);
				selectOrderForComplaintGUI.setCustomer(customer);			
				selectOrderForComplaintGUI.setUser(user);
				selectOrderForComplaintGUI.setStoreAddress(customerView.getStoreAddress());
				selectOrderForComplaintGUI.doInit();
				
				FormController.primaryStage.setScene(selectOrderForComplaintGUI.getScene());
			}
	    }
	};	
	//===============================================================================================================
	//waiting for the server to respond
    protected void waitForServerResponse()
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
    	String newCondition = condition;
    	if(condition.contains("--"))
    	{
    		String customerANdPhone = condition;
			String[] parts = customerANdPhone.split("--");
			String customerID = parts[0];
			String phoneNumber = parts[1];
			newCondition = customerID;
    	}
	    	CustomerController.getCertainCustomers(column, newCondition, Client.getInstance());
	    	waitForServerResponse();
	    	if(response.getType() == Response.Type.SUCCESS)
	    	{
		    	ArrayList<CustomerView> customerViewList = new ArrayList<CustomerView>();
		    	ArrayList<Customer> customerList = (ArrayList<Customer>)response.getMessage();
		    	for(Customer customer : customerList)
		    	{
		    		if(condition.contains("--"))
		    		{
		    			System.out.println("By phone and number.");
		    			String customerANdPhone2 = condition;
						String[] parts = customerANdPhone2.split("--");
						String customerID = parts[0];
						String phoneNumber = parts[1];
						newCondition = customerID;
						System.out.println("customer phone: "+customer.getPhoneNumber()+" given phone num: "+phoneNumber);
		    			if(customer.getPhoneNumber().equals(phoneNumber))
		    			{
			    			CustomerView view = null;
			    			try 
							{
								view = new CustomerView(customer);
								view.setStoreAddress(getStoreName(view.getStoreID()));
							} catch (CustomerException e) 
							{
								e.printStackTrace();
							}
				    		//add function for the button:
				    		view.getSelectButton().setOnAction(selectUser);
				    		customerViewList.add(view);
		    			}
		    		}
		    		else
		    		{
			    		CustomerView view = null;
						try 
						{
							view = new CustomerView(customer);
							view.setStoreAddress(getStoreName(view.getStoreID()));
						} catch (CustomerException e) 
						{
							e.printStackTrace();
						}
			    		//add function for the button:
			    		view.getSelectButton().setOnAction(selectUser);
			    		customerViewList.add(view);
		    		}
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
//    private Client getClient()
//	{
//		return this.client;
//	}
    
  //===============================================================================================================
    public void clearForm()
    {
    	idTextField.clear();
    	phoneNumberTextField.clear();
    	userTable.getItems().clear();
    }
}
