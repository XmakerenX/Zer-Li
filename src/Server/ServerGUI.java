package Server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

//*************************************************************************************************
/**
* This Class provides a GUI to show the server status
*/
//*************************************************************************************************
public class ServerGUI extends Application 
{
    @FXML
    private Button startBtn;

    @FXML
    private Button Stop;

    @FXML
    private  TextField portField;

    @FXML
    private  TextField usernameField;

    @FXML
    private  TextField passwordField;

    @FXML
    private Button saveBtn;

    @FXML
    private Label statusLabel;	
	
    private ProtoTypeServer server;
    //*************************************************************************************************
    /**
  	*  Called when the save button is pressed
  	*  Save the new config info entered
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onSaveBtn(ActionEvent event) 
    {
    	//get new requested configs:
    	String newPort = portField.getText();
    	String newUsername = usernameField.getText();
    	String newPass = passwordField.getText();
    	
    	//get current configs:
    	ArrayList<Object> serverArgs = ProtoTypeServer.parseConfigFile("server.properties");
    	String serverPort = ((Integer)serverArgs.get(0)).toString();
    	String serverUserName = (String)serverArgs.get(1);
    	String serverPass = (String)serverArgs.get(2);
    	
    	//only updates if there have been a change:
    	if( !serverPort.equals(newPort) ||                      
    		!serverUserName.equals(newUsername) ||
    		!serverPass.equals(newPass)     )
    	{
    	ProtoTypeServer.updateServerConfigFile("server.properties", newPort, newUsername, newPass);
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("new configurations saved");
    	alert.setHeaderText("new configurations were saved.\nTo apply changes,restart the server");
    	alert.showAndWait();
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the start button is pressed
  	*  starts the server so it listens to clients 
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onStartBtn(ActionEvent event) 
    {
    	ArrayList<Object> serverArgs = ProtoTypeServer.parseConfigFile("server.properties");
    	
		  try 
		  {
			  server = new ProtoTypeServer((int)serverArgs.get(0), (String)serverArgs.get(1), (String)serverArgs.get(2));
			  //Start listening for connections
			  server.start();
			  statusLabel.setText("Status: Running");
		  } 
		  catch (IOException ex) 
		  {
			  System.out.println("ERROR - Could not listen for clients!");
		  }
		
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the stop button is pressed
  	*  stops the server and it stops listening to clients 
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onStopBtn(ActionEvent event) 
    {
    		try 
    		{
    			server.stopServer();
				statusLabel.setText("Status: Offline");
			} 
    		
    		catch (Exception e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
	    		System.exit(-1);
			}
    }
    
 	//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*  Initializes The gui controls
  	*/
 	//*************************************************************************************************
    @FXML
    public void initialize() 
    {
    	try {
    		statusLabel.setText("Status: Offline");
    		
    		ArrayList<Object> serverArgs = ProtoTypeServer.parseConfigFile("server.properties");
       	 	String serverPort = ((Integer)serverArgs.get(0)).toString();
       	 	String serverUserName = (String)serverArgs.get(1);
       	 	String serverPass = (String)serverArgs.get(2);         	
    	 	
       	 	portField.setText(serverPort);
       	 	usernameField.setText(serverUserName);
       	 	passwordField.setText(serverPass);
       	 	
    		}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
    }
    
 	//*************************************************************************************************
    /**
     * Called on application start , Initialize the server gui form the fxml and shows it
  	*/
 	//*************************************************************************************************
	@Override
	public void start(Stage primaryStage) throws Exception 
	{		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerGui.fxml"));
		
		AnchorPane root = (AnchorPane)loader.load();
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Zer li Server");
			
		 //show
		primaryStage.show();
	}
	
 	//*************************************************************************************************
    /**
     * The application main function calls the application launch
     * @param args given from the command line
     * @see javafx.application.Application
  	*/
 	//*************************************************************************************************
	public static void main(String[] args) 
	{ 
		launch(args);
	}

}


