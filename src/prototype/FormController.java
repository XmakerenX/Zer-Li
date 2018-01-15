package prototype;

import java.io.IOException;
import java.net.URL;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class FormController {

	protected static Stage primaryStage;
	protected Scene thisScene;
	protected FormController parent;
	protected Client client;
	
	public abstract void onSwitch(Client newClient);
			
	public static void setPrimaryStage(Stage newPrimaryStage)
	{
		primaryStage = newPrimaryStage;
	}
	
	public static Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public void setScene(Scene scene)
	{
		thisScene = scene;
		//Disables resizing option to primary stage
		primaryStage.setResizable(false);
	}
	
	public Scene getScene()
	{
		return thisScene;
	}
	
	public void setParent(FormController parent)
	{
		this.parent = parent;
	}
	
	public void setClinet(Client client)
	{
		this.client = client;
	}
	
	public static <ControllerType extends FormController, PaneType extends Pane> ControllerType loadFXML(URL res, FormController parent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(res);
			
			PaneType root = (PaneType)loader.load();
			ControllerType controller = loader.<ControllerType>getController();
			controller.setParent(parent);
			
			Scene scene = new Scene(root);	
			controller.setScene(scene);
			
			return controller;
		}catch(IOException e)
		{
    		System.out.println("Failed to load "+ res.getPath());
    		e.printStackTrace();
    		return null;
		}
	}
}
