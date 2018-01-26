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
import networkGUI.NetworkManagerGUI;
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
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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

/**
 * holds all the functionality needed for the login GUI
 * @author dk198
 *
 */
public class LoginGUI extends FormController implements ClientInterface  {

	// holds the last replay we got from server
	private Response replay = null;
	
	/**
	 * All the GUI's are distinguished by users permissions and will be shown as main GUI after the log in.
	 */
	CustomerGUI customerGUI;
	SystemManagerGUI sysManagerGUI;
	NetworkWorkerGUI networkWorkerGui;
	NetworkManagerGUI networkManagerGui;
	StoreWorkerGUI storeWorkerGUI;
	CustomerServiceExpertGUI customerServiceExpertGUI;
	StoreManagerGUI storeManagerGUI;
	CustomerServiceWorkerGUI customerServiceWorkerGUI;
	CustomerServiceGUI customerServiceGUI;
	
	int releventStoreOfUserEmployee = 0; // stores the relevent store to the user in case he is an employee.
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
    public void initialize()
    {
    	
    	userConf = new Config("user.properties");
    	rememberSelect = userConf.getProperty("REMEMBER").equals("TRUE");
    	
    	customerGUI = FormController.<CustomerGUI, AnchorPane>loadFXML(getClass().getResource("/customer/CustomerGUI.fxml"), this);
    	sysManagerGUI = FormController.<SystemManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/SystemManagerGUI.fxml"), this);
    	networkWorkerGui = FormController.<NetworkWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NetworkWorkerGUI.fxml"), this);
    	networkManagerGui = FormController.<NetworkManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NetworkManagerGUI.fxml"), this);
    	storeWorkerGUI = FormController.<StoreWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/StoreWorkerGUI.fxml"), this);
    	customerServiceExpertGUI = FormController.<CustomerServiceExpertGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceExpertGUI.fxml"), this);
    	storeManagerGUI = FormController.<StoreManagerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/StoreManagerGUI.fxml"), this);
    	customerServiceWorkerGUI =  FormController.<CustomerServiceWorkerGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceWorkerGUI.fxml"), this);
    	customerServiceGUI =  FormController.<CustomerServiceGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/CustomerServiceGUI.fxml"), this);
    	if(rememberSelect)
    	{
        	rememberMeBtn.setSelected(true);
        	usernameTxt.setText(userConf.getProperty("USERNAME"));
        	passwordTxt.setText(userConf.getProperty("PASSWORD"));
        	
    	}
    	
    }
    
    @FXML
    private Button changeServerIP;

    @FXML
    void changeServerIP(ActionEvent event) 
    {
    	String serverIP;
		String serverPort;
		
		Config clientConf = new Config("client.properties");


		serverIP =clientConf.getProperty("SERVER_IP").trim();
		
		
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Server ip changing");
    	dialog.setContentText("This numbers are critical as they guide the computer\nas to where to connect.\nDont change it unless told to by zerli");
    	dialog.setHeaderText("Please enter the new serverIP:\n");
    	
    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent())
    	{
    		FileOutputStream out =null;
    		try {
				out = new FileOutputStream("client.properties");
				 clientConf.configFile.setProperty("SERVER_IP",result.get().toString());
		    	    clientConf.configFile.store(out, null);
		    	    out.close();
			} catch (Exception e) 
    		{
				e.printStackTrace();

				// TODO Auto-generated catch block
				Alert mAlert = new Alert(Alert.AlertType.ERROR);
				mAlert.setContentText("Failed to update serverip.\nPlease call our customer service for help.");
				mAlert.showAndWait();
			}
    	   
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
    	
    	try
    	{
		prototype.Main.initClient(this);
    	}
    	catch(Exception e)
    	{
    		Alert couldntConnectAlert = new Alert(Alert.AlertType.ERROR);
    		couldntConnectAlert.setContentText("Failed to connect to the zerli server.\nCall customer service to make sure \nthat you are using the correct server ip.");
    		couldntConnectAlert.showAndWait();
    		return;
    	}
    	
    	
    	UserController.requestLogin(usernameTxt.getText(), passwordTxt.getText(), Client.client);
    	
    	
		
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
    	
    	//UserController.getStoreOfEmployee(this.usernameTxt.getText(), client);
    	
    	
    	// show success 
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		User user = (User)replay.getMessage();
    		String permission = ""+user.getUserPermission();
    		// clear replay
    		replay = null;
    		//store the relevent store of the user( in case he is an employee)
    		
    		UserController.getStoreOfEmployee(usernameTxt.getText(), Client.client);
        	try
        	{
        		synchronized(this) {this.wait();}
        	}
        	catch(Exception e) {}
        	
        	if (replay.getType() == Response.Type.SUCCESS)
        		releventStoreOfUserEmployee = (int)(replay.getMessage());
        	else
        		releventStoreOfUserEmployee = 0;
        	
        	
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
    	
    		//depending on the user's permissions we will show them a different gui
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
						networkWorkerGui.setUser(user);
						networkWorkerGui.setStoreID(releventStoreOfUserEmployee);
						client.setUI(networkWorkerGui);
	        			FormController.primaryStage.setScene(networkWorkerGui.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "NETWORK_MANAGER":
	    		{
	    			if (networkManagerGui != null)
	        		{
	    				networkManagerGui.setClinet(Client.client);
	    				networkManagerGui.setUser(user);
						client.setUI(networkManagerGui);
	        			FormController.primaryStage.setScene(networkManagerGui.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "STORE_WORKER":
	    		{
	    			
	    			if (storeWorkerGUI != null)
	        		{
	    				storeWorkerGUI.setUser(user);
	    				storeWorkerGUI.setStoreID(releventStoreOfUserEmployee);
	    				storeWorkerGUI.setClinet(Client.client);
	    				storeWorkerGUI.setFormParent(this);
	    				client.setUI(storeWorkerGUI);
	    				
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
	    				client.setUI(customerServiceExpertGUI);
	        			FormController.primaryStage.setScene(customerServiceExpertGUI.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "CUSTOMER_SERVICE":
	    		{
	    			
	    			if (customerServiceGUI != null)
	        		{
	    				customerServiceGUI.setUser(user);
	    				customerServiceGUI.setClinet(Client.client);
	    				client.setUI(customerServiceGUI);
	        			FormController.primaryStage.setScene(customerServiceGUI.getScene());
	        		}
	    			break;
	    		}
	    		
	    		case "STORE_MANAGER":
	    		{
	    			if (storeManagerGUI != null)
	        		{
	    				storeManagerGUI.setUser(user);
		    			storeManagerGUI.setClinet(Client.client);
		    			client.setUI(storeManagerGUI);
		        		FormController.primaryStage.setScene(storeManagerGUI.getScene());

	        		}
	    			break;
	    		}
	  		  default:
				  System.out.println("Error Invalid message received");
				  break;
    		}

    		//reset login text boxes:
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
	
		
	/**
	 * updates the config file, when some one chooses to use the remember me function 
	 * @param configPath	file's path
	 * @param user			username
	 * @param pass			password
	 * @param isSelected	if selected
	 */
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