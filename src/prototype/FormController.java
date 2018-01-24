package prototype;

import java.io.IOException;
import java.net.URL;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
	
    //*************************************************************************************************
	/**
	 * An method to display error message to user
	 * @param message - specific message to display
	 */
    //*************************************************************************************************
	public void showErrorMessage(String message)
	{
    	// show failure  
		Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
	
    //*************************************************************************************************
	/**
	 * An method to display information message to user
	 * @param message - specific message to display
	 */
    //*************************************************************************************************
	public void showInformationMessage(String message)
	{
    	// show success  
		Alert alert = new Alert(AlertType.INFORMATION, message , ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
	
    //*************************************************************************************************
	/**
	 * A method to display warning message to user
	 * @param message - specific message to display
	 */
    //*************************************************************************************************
	public void showWarningMessage(String message)
	{
    	// show warning  
		Alert alert = new Alert(AlertType.WARNING, message , ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
	
    //*************************************************************************************************
	/**
	 * A method to display Confirmation dialog to user
	 * @param message - specific message to display
	 */
    //*************************************************************************************************
	public ButtonType showConfirmationDialog(String title, String message)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		return alert.showAndWait().get();
	}
	
    //*************************************************************************************************
	/**
	* As said, this functions wait for the server's reply to continue.
	*/
    //*************************************************************************************************
	    protected void waitForResponse()
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
}
