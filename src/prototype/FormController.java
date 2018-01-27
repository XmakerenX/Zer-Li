package prototype;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import client.Client;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//*************************************************************************************************
	/**
	*  The abstract base class for all FormControllers 
	*  provides many common methods for the controllers
	*/
//*************************************************************************************************
public abstract class FormController {

	protected static Stage primaryStage;
	protected Scene thisScene;
	protected FormController parent;
	protected Client client;
	
	
	public abstract void onSwitch(Client newClient);
	
    //*************************************************************************************************
    /**
     * Sets the primaryStage for all the forms
  	*  @param newPrimaryStage the primary Stage be set
  	*/
    //*************************************************************************************************
	public static void setPrimaryStage(Stage newPrimaryStage)
	{
		
		primaryStage = newPrimaryStage;
	
	}
	
	//*************************************************************************************************
    /**
     * Returns the primary Stage
  	*  @return the primary Stage
  	*/
    //*************************************************************************************************
	public static Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
    //*************************************************************************************************
    /**
     * Sets the scene to used by this form
  	*  @param scene the scene be set
  	*/
    //*************************************************************************************************
	public void setScene(Scene scene)
	{
		thisScene = scene;
		//Disables resizing option to primary stage
		primaryStage.setResizable(false);
		
		
		EventHandler<WindowEvent> onClose = new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) 
	          {
	        	  Alert alert = new Alert(AlertType.CONFIRMATION);
	        	  alert.setTitle("Confirmation Dialog");
	        	  alert.setHeaderText("About to close Zer-Li program.");
	        	  alert.setContentText("Are you sure you want to exit?");

	        	  Optional<ButtonType> result = alert.showAndWait();
	        	  if (result.get() == ButtonType.OK)
	        	  {
	        	      // ... user chose OK
	        		  primaryStage.close();
	        		  System.exit(0);
	        	  } 
	        	  else 
	        	  {
	        	      we.consume();
	        	  }
	          }
	      };        
	      
		primaryStage.setOnCloseRequest(onClose);
		  
	}
	
	
	
	//*************************************************************************************************
    /**
     * Returns the scene used by this form
  	*  @return  the scene used by this form
  	*/
    //*************************************************************************************************
	public Scene getScene()
	{
		return thisScene;
	}
	
    //*************************************************************************************************
    /**
     * Sets the parent form for this form
  	*  @param parent the parent form of this form
  	*/
    //*************************************************************************************************
	public void setParent(FormController parent)
	{
		this.parent = parent;
	}
	
	public void setClinet(Client client)
	{
		this.client = client;
	}
	
    //*************************************************************************************************
    /**
     * Load an fxml file and initialize it's controller with his scene and parent 
  	*  @param res The url path for the fxml file
  	*  @param parent the form parent form
  	*  @param <ControllerType> the type of the form controller , need to extend FormController(this class)
  	*  @param <PaneType> the type of the root element for the fxml that 
  	*  @return the initialized controller for the loaded fxml form
  	*/
    //*************************************************************************************************
	public static <ControllerType extends FormController, PaneType extends Pane> ControllerType loadFXML(URL res, FormController parent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(res);
			
			@SuppressWarnings("unchecked")
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
	 * @param title the dialog title
	 * @param message  specific message to display
	 * @return which button was pressed in the confirmation dialog
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
