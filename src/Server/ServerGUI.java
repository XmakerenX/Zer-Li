package Server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;

import Server.ProtoTypeServer2;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import prototype.Config;
import prototype.MainFormController;




	public class ServerGUI extends Application {

	    @FXML
	    private Button exitBtn;

	    @FXML
	    private Button setupBtn;

	    @FXML
	    void setupConnection(ActionEvent event) {
    		ProtoTypeServer2 sv = new ProtoTypeServer2();
	    }

	    @FXML
	    void exitWindow(ActionEvent event) {
	    	System.out.println("Disconnected");
	    	System.exit(0);

	    }

	

	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{

		System.out.print("start");
	
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerGUI.fxml"));
		BorderPane root = (BorderPane)loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Prototype");
		primaryStage.show();

	
	}
	public static void main(String[] args) {
		launch(args);
	}

}


