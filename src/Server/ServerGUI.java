package Server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import prototype.MainFormController;
import utils.Config;

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
	


	private ProtoTypeServer sv;

//-------------------------------------------------------------------------------
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
    	ProtoTypeServer.updateConfigFile("server.properties", newPort, newUsername, newPass);

    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("new configurations saved");
    	alert.setHeaderText("new configurations were saved.\nTo apply changes,restart the server");
    	alert.showAndWait();
    	}
    }
//-------------------------------------------------------------------------------
    @FXML
    void onStartBtn(ActionEvent event) 
    {
    	ArrayList<Object> serverArgs = ProtoTypeServer.parseConfigFile("server.properties");
		
		sv = new ProtoTypeServer((int)serverArgs.get(0), (String)serverArgs.get(1), (String)serverArgs.get(2));
		try
		{
			sv.listen();
			statusLabel.setText("Status: Running");
		}catch (IOException ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
    }
//-------------------------------------------------------------------------------
    @FXML
    void onStopBtn(ActionEvent event) 
    {
    	try
		{
			sv.close();
			statusLabel.setText("Status: Offline");
		}catch (IOException ex) 
		{
			System.out.println("ERROR - Could not  stop listening for clients!");
			System.exit(-1);
		}
    }
//-------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------
	public static void main(String[] args) 
	{ 
		launch(args);
	}

}


