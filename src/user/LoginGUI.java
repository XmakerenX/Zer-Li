package user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import networkGUI.CustomerServiceGUI;
import networkGUI.NetworkWorkerGUI;
import networkGUI.SystemManagerGUI;
import product.ProdcutController;
import product.Product;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.HyperlinkEvent.EventType;

import client.Client;
import client.ClientInterface;
import customer.CustomerGUI;
import prototype.FormController;
import prototype.ProductInfoFormController;
import serverAPI.GetRequest;
import serverAPI.Response;


public class LoginGUI extends FormController implements ClientInterface  {

	// holds the last replay we got from server
	private Response replay = null;
	
	CustomerGUI customerGUI;
	SystemManagerGUI sysManagerGUI;
	NetworkWorkerGUI networkWorkerGui;
	CustomerServiceGUI customerServiceGUI;
	
    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button registerInfo;    
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){

    	customerGUI = FormController.<CustomerGUI, AnchorPane>loadFXML(getClass().getResource("/customer/CustomerGUI.fxml"), this);
    	sysManagerGUI = FormController.<SystemManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/SystemManagerGUI.fxml"), this);
    	networkWorkerGui = FormController.<NetworkWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NetworkWorkerGUI.fxml"), this);
    	customerServiceGUI = FormController.<CustomerServiceGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceGUI.fxml"), this);
    }
    
    @FXML
    void registerInfo(ActionEvent event) 
    {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Register Information");
    	alert.setHeaderText("Registration is done manually in a network store.\nAsk the store manager to sign you up.");
    	

    	alert.showAndWait();
    	//messegeD
    	//client.quit();
    	//System.exit(0);
    }

    @FXML
    void onLogin(ActionEvent event) 
    {
    	UserController.requestLogin(usernameTxt.getText(), passwordTxt.getText(), client);
    	
    	try
    	{
    		synchronized(this)
    		{
    			// wait for server response
    			this.wait(ClientInterface.TIMEOUT);
    		}
    	
    		if (replay == null)
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    		  	alert.setTitle("Server Respone timed out");
    	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
    	    	
    	    	alert.showAndWait();
    			return;
    		}
    		
    	// show success 
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		User user = (User)replay.getMessage();
    		String permission = ""+user.getUserPermission();
    		// clear replay
    		replay = null;
    		
    		switch (permission)
    		{
	    		case "CUSTOMER":
	    		{
	        		if (customerGUI != null)
	        		{
	        			customerGUI.setClinet(client);
	        			FormController.primaryStage.setScene(customerGUI.getScene());
	        		}
	    		}break;
	    			
	    		case "SYSTEM_MANAGER":
	    		{
	        		if (sysManagerGUI != null)
	        		{
	        			sysManagerGUI.setUser(user);
	        			sysManagerGUI.setClinet(client);
	        			FormController.primaryStage.setScene(sysManagerGUI.getScene());
	        		}
	    		}break;
	    		case "NETWORK_WORKER":
	    		{
	    			if (networkWorkerGui != null)
	        		{
	    				networkWorkerGui.setClinet(client);
	        			FormController.primaryStage.setScene(networkWorkerGui.getScene());
	        		}
	    			break;
	    		}
	    		case "CUSTOMER_SERVICE":
	    		{
	    			
	    			if (customerServiceGUI != null)
	        		{
	    				customerServiceGUI.setClinet(client);
	        			FormController.primaryStage.setScene(customerServiceGUI.getScene());
	        		}
	    			break;
	    		}
	  		  default:
				  System.out.println("Error Invalid message received");
				  break;
    		}


    		usernameTxt.setText("");;
    	    passwordTxt.setText("");;
//    		Alert alert = new Alert(AlertType.INFORMATION, "Logged in successfully :)", ButtonType.OK);
//    		alert.showAndWait();
    	}
    	else
    	{
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, (String)replay.getMessage(), ButtonType.OK);
    		alert.showAndWait();
    		// clear replay
    		replay = null;
    	}
    	
    	}catch(InterruptedException e) {}
    }
	    
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
		
	
	
	
	public void display(Object message)
	{
		
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
		
		Response replay = (Response)message;
		this.replay = replay;
		
		synchronized(this)
		{
			this.notify();
		}
	}

}