package user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import product.ProdcutController;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import javax.swing.event.HyperlinkEvent.EventType;

import client.Client;
import client.ClientInterface;
import customer.CustomerGUI;
import prototype.FormController;
import prototype.ProductInfoFormController;
import serverAPI.Response;

public class LoginGUI extends FormController implements ClientInterface  {

	// holds the last replay we got from server
	private Response replay = null;
	
	CustomerGUI customerGUI;
	
    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button exitBtn;
    
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	try
    	{
    		customerGUI = FormController.<CustomerGUI, AnchorPane>loadFXML(getClass().getResource("/customer/CustomerGUI.fxml"), this);
    	} catch(IOException e)
    	{
    		System.out.println("Failed to load CustomerGUI.fxml");
    		e.printStackTrace();
    		customerGUI = null;
    	}
    }
    
    @FXML
    void onExit(ActionEvent event) {
    	client.quit();
    	System.exit(0);
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
    			this.wait();
    		}
    	
    		if (replay == null)
    			return;
    		
    	// show success 
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		// clear replay
    		replay = null;
    		if (customerGUI != null)
    		{
    			customerGUI.setClinet(client);
    			FormController.primaryStage.setScene(customerGUI.getScene());
    		}
    		
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
