package user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import networkGUI.CustomerServiceExpertGUI;
import networkGUI.CustomerServiceGUI;
import networkGUI.CustomerServiceWorkerGUI;
import networkGUI.NetworkWorkerGUI;
import networkGUI.StoreManagerGUI;
import networkGUI.StoreWorkerGUI;
import networkGUI.SystemManagerGUI;
import product.ProdcutController;
import product.Product;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.HyperlinkEvent.EventType;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import customer.CustomerGUI;
import prototype.FormController;
import prototype.ProductInfoFormController;
import serverAPI.GetRequest;
import serverAPI.Response;
import utils.Config;


public class LoginGUI extends FormController implements ClientInterface  {

	// holds the last replay we got from server
	private Response replay = null;
	
	/**
	 * All the GUI's are distinguished by users permissions and will be shown as main GUI after the log in.
	 */
	CustomerGUI customerGUI;
	SystemManagerGUI sysManagerGUI;
	NetworkWorkerGUI networkWorkerGui;
	CustomerServiceGUI customerServiceGUI;
	StoreWorkerGUI storeWorkerGUI;
	CustomerServiceExpertGUI customerServiceExpertGUI;
	StoreManagerGUI storeManagerGUI;
	CustomerServiceWorkerGUI customerServiceWorkerGUI;
	
	/* For fast login only, edit user.properties:
	 * 
	 */
	Config userConf;
	Boolean rememberSelect;

	@FXML
    private RadioButton rememberMeBtn;

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

    	userConf = new Config("user.properties");
    	rememberSelect = userConf.getProperty("REMEMBER").equals("TRUE");
    	
    	customerGUI = FormController.<CustomerGUI, AnchorPane>loadFXML(getClass().getResource("/customer/CustomerGUI.fxml"), this);
    	sysManagerGUI = FormController.<SystemManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/SystemManagerGUI.fxml"), this);
    	networkWorkerGui = FormController.<NetworkWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NetworkWorkerGUI.fxml"), this);
    	customerServiceGUI = FormController.<CustomerServiceGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceGUI.fxml"), this);
    	storeWorkerGUI = FormController.<StoreWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/StoreWorkerGUI.fxml"), this);
    	customerServiceExpertGUI = FormController.<CustomerServiceExpertGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceExpertGUI.fxml"), this);
    	storeManagerGUI = FormController.<StoreManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/StoreManagerGUI.fxml"), this);
    	customerServiceWorkerGUI =  FormController.<CustomerServiceWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceWorkerGUI.fxml"), this);
    	
    	if(rememberSelect)
    	{
        	rememberMeBtn.setSelected(true);
        	usernameTxt.setText(userConf.getProperty("USERNAME"));
        	passwordTxt.setText(userConf.getProperty("PASSWORD"));
        	
    	}
    	
    }
    
    @FXML
    void onRememberMe(ActionEvent event) 
    {
    	
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
    	UserController.requestLogin(usernameTxt.getText(), passwordTxt.getText(), Client.client);
    	
    	//UserController.getStoreOfEmployee(this.usernameTxt.getText(), client);
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
    		if(rememberMeBtn.isSelected())
    		{
    			rememberSelect = true;
    			updateUserConfigFile("user.properties", usernameTxt.getText(), passwordTxt.getText(),"TRUE");
    		}
    		else
    		{
    			rememberSelect = false;
    			updateUserConfigFile("user.properties", "", "","FALSE");
    		}
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
        				Client.client.setUI(customerGUI);
        				customerGUI.setClinet(Client.client);
        				customerGUI.setCurrentUser(user);
        				customerGUI.loadStores();
	        			FormController.primaryStage.setScene(customerGUI.getScene());
	        			
	        			//CustomerController.getCustomer(""+user.getPersonID(), client);
	        			
//	        			synchronized(this)
//	            		{
//	            			// wait for server response
//	            			this.wait(ClientInterface.TIMEOUT);
//	            		}
//	        			
//	        			if (replay != null)
//	        			{
//	        				if (replay.getType() == Response.Type.SUCCESS)
//	        				{
//	        					ArrayList<Customer> customers = (ArrayList<Customer>)replay.getMessage();
//	        					customerGUI.setCurrentCustomer(customers.get(0));
//	        				}
//	        				else
//	        				{
//	        					customerGUI.setCurrentCustomer(null);
//	        				}
//	        				
//	        			}
//	        			else
//	        			{
//	            			Alert alert = new Alert(AlertType.ERROR);
//	            		  	alert.setTitle("Server Respone timed out");
//	            	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
//	            	    	
//	            	    	alert.showAndWait();
//	            			return;
//	        			}
	        		}
	    		}break;
	    			
	    		
	    		case "CUSTOMER_SERVICE_WORKER":
	    		{
	        		if (customerServiceWorkerGUI != null)
	        		{
	        			customerServiceWorkerGUI.setUser(user);
	        			customerServiceWorkerGUI.setClinet(Client.client);
	        			client.setUI(customerServiceWorkerGUI);
	        			FormController.primaryStage.setScene(customerServiceWorkerGUI.getScene());
	        		}
	    		}break;
	    		
	    		
	    		case "SYSTEM_MANAGER":
	    		{
	        		if (sysManagerGUI != null)
	        		{
	        			sysManagerGUI.setUser(user);
	        			sysManagerGUI.setClinet(Client.client);
	        			client.setUI(sysManagerGUI);
	        			FormController.primaryStage.setScene(sysManagerGUI.getScene());
	        		}
	    		}break;
	    		
	    		case "NETWORK_WORKER":
	    		{
	    			if (networkWorkerGui != null)
	        		{
	    				networkWorkerGui.setClinet(Client.client);
	        			FormController.primaryStage.setScene(networkWorkerGui.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "CUSTOMER_SERVICE":
	    		{
	    			
	    			if (customerServiceGUI != null)
	        		{
	    				customerServiceGUI.setUser(user);
	    				customerServiceGUI.setClinet(Client.client);
	        			FormController.primaryStage.setScene(customerServiceGUI.getScene());
	        		}
	    			break;
	    		}
	    		case "STORE_WORKER":
	    		{
	    			
	    			if (storeWorkerGUI != null)
	        		{
	    				storeWorkerGUI.setUser(user);
	    				storeWorkerGUI.setClinet(Client.client);
	        			FormController.primaryStage.setScene(storeWorkerGUI.getScene());
	        		}
	    			break;
	    		}
	    			
	    		case "CUSTOMER_SERVICE_EXPERT":
	    		{
	    			
	    			if (customerServiceExpertGUI != null)
	        		{
	    				customerServiceExpertGUI.setUser(user);
	    				customerServiceExpertGUI.setClinet(Client.client);
	        			FormController.primaryStage.setScene(customerServiceExpertGUI.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "STORE_MANAGER":
	    		{
	    			if (storeManagerGUI != null)
	        		{
	    				storeManagerGUI.setUser(user);
	    				storeManagerGUI.setClinet(Client.client);
	        			FormController.primaryStage.setScene(storeManagerGUI.getScene());
	        		}
	    			break;
	    		}
	  		  default:
				  System.out.println("Error Invalid message received");
				  break;
    		}


    		usernameTxt.setText("");;
    	    passwordTxt.setText("");;
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
	
		
	
	private static void updateUserConfigFile(String configPath,String user,String pass,String isSelected)
	  {
		  File configFile = new File(configPath);
		  if (!configFile.exists())
			try {
				configFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("Failed to create "+ configPath);
				e1.printStackTrace();
			}
		  
		  Config serverConfig = new Config(configPath);
		  FileOutputStream out;
		try 
		{
			
			out = new FileOutputStream(configPath);
			
			  serverConfig.configFile.setProperty("USERNAME", user);
			  serverConfig.configFile.setProperty("PASSWORD", pass);
			  serverConfig.configFile.setProperty("REMEMBER", isSelected);
			  serverConfig.configFile.store(out, null);
			  out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	  
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
