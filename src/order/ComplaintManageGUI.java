package order;

import java.time.LocalDate;
import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import customer.CustomerView;
import customer.Customer.CustomerException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import networkGUI.CustomerServiceWorkerGUI;
import order.OrderComplaintView.ComplaintViewButton;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class ComplaintManageGUI extends FormController implements ClientInterface{

	Response response = null;
	HandleComplaintGUI handleComplaintGUI = null;
	User user;
	ClientInterface ManageCatInterface = this;
	Client myClient;
	ObservableList<OrderComplaintView> complaintList; //table's data
	
    @FXML
    private TableColumn<OrderComplaintView, ?> customerIDColumn;

    @FXML
    private TableColumn<OrderComplaintView, ?> storeIDComlumn;

    @FXML
    private TableColumn<OrderComplaintView, ?> complaintTimeColumn;

    @FXML
    private TableColumn<OrderComplaintView, LocalDate> complaintDateColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableView<OrderComplaintView> complaintTable;

    @FXML
    private TableColumn<OrderComplaintView, ?> selectColumn;

    @FXML
    void onBackButton(ActionEvent event) {
    	complaintTable.getItems().clear();
    	CustomerServiceWorkerGUI customerServiceWorkerGUI = (CustomerServiceWorkerGUI)parent;
    	client.setUI(customerServiceWorkerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
  //===============================================================================================================
 	public void doInit()
 	{
 		customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
 		storeIDComlumn.setCellValueFactory(new PropertyValueFactory<>("StoreID"));
 		complaintTimeColumn.setCellValueFactory(new PropertyValueFactory<>("ComplaintTime"));
 		complaintDateColumn.setCellValueFactory(new PropertyValueFactory<OrderComplaintView,LocalDate>("ComplaintDate"));
    	selectColumn.setCellValueFactory(new PropertyValueFactory<>("SelectButton"));
    	
    	handleComplaintGUI = FormController.<HandleComplaintGUI, AnchorPane>loadFXML(getClass().getResource("/order/HandleComplaintGUI.fxml"), this);
    	initComplaintTableContent("status", "NEW");
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
		//select button event handler:
		EventHandler<ActionEvent> selectComplaint = new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		    	
		    	ComplaintViewButton src = (ComplaintViewButton)e.getSource();
				OrderComplaint complaint = src.getOrigin();
				
				if (handleComplaintGUI != null)
				{
					handleComplaintGUI.setComplaint(complaint);
					getClient().setUI(handleComplaintGUI);
					handleComplaintGUI.setClinet(client);
					handleComplaintGUI.doInit();
					
//					handleComplaintGUI.setComplaint(complaint);
//					handleComplaintGUI.setUser(user);
//					//handleComplaintGUI.setClinet(client);
//					getClient().setUI(handleComplaintGUI);
//					handleComplaintGUI.setClinet(client);
//					handleComplaintGUI.doInit();
					FormController.primaryStage.setScene(handleComplaintGUI.getScene());
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
	    /**
	     * initializes the table view
	     * @param column		the column we are checking
	     * @param condition		the condition we are looking for
	     */
	    public void initComplaintTableContent(String column, String condition)
	    {
				complaintList = getComplaintList(column, condition);
		    	complaintTable.getItems().clear();
				if(complaintList==null) return;
				complaintTable.getItems().addAll(complaintList);
	    }
	    //===============================================================================================================
	    /**
	     * receives the customers we need to the table view
	     * @param column		the column we are checking
	     * @param condition		the condition we are looking for
	     * @return				returns a list of CustomerView items
	     */
	    private ObservableList<OrderComplaintView> getComplaintList(String column, String condition)
	    {
	    	OrderComplaintController.getActiveComplaints();
	    	waitForServerResponse();
	    	if(response.getType() == Response.Type.SUCCESS)
	    	{
		    	ArrayList<OrderComplaintView> customerViewList = new ArrayList<OrderComplaintView>();
		    	ArrayList<OrderComplaint> complaintsList = (ArrayList<OrderComplaint>)response.getMessage();
		    	for(OrderComplaint complaint : complaintsList)
		    	{
		    		OrderComplaintView view = null;
					try {
						view = new OrderComplaintView(complaint);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		//add function for the button:
		    		view.getSelectButton().setOnAction(selectComplaint);
		    		customerViewList.add(view);
		    	}
		    	System.out.println(customerViewList);
		    	//clearing response
	    		response = null;
		    	return FXCollections.observableArrayList(customerViewList);
	    	}
	    	else if(response.getType() == Response.Type.ERROR)
	    	{
	    		Alert alert = new Alert(AlertType.ERROR, "No active complaints found!", ButtonType.OK);
	    		alert.showAndWait();
	    		//clearing response
	    		response = null;
	    		return null;
	    	}
	    	return null;
	    }
}
