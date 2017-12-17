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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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

	private ProtoTypeServer sv;

	@FXML
	void setupConnection(ActionEvent event) {

		ArrayList<Object> serverArgs = ProtoTypeServer.parseConfigFile("server.properties");

		sv = new ProtoTypeServer((int)serverArgs.get(0), (String)serverArgs.get(1), (String)serverArgs.get(2));
		try
		{
			sv.listen();
		}catch (IOException ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

	@FXML
	void exitWindow(ActionEvent event) {
		try
		{
			sv.close();
		}catch (IOException ex) 
		{
			System.out.println("ERROR - Could not  stop listening for clients!");
		}

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


