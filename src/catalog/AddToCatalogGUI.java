package catalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import client.Client;
import client.ClientInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import product.CatalogItem;
import product.Product;
import serverAPI.Response;
import serverAPI.UploadImageRequest;
import utils.FormController;
import utils.ImageData;

/**
 * provides a GUI to handle the process of adding a product to a store's catalog
 */
public class AddToCatalogGUI extends FormController implements ClientInterface 
{
	
	int storeID;
	Stage stage;
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) 
	{
		this.storeID = storeID;
	}

	Response response;
	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public ImageData getImage() {
		return image;
	}

	public void setImage(ImageData image) {
		this.image = image;
	}

	Product  prod;
	ImageData image;
	CatalogItem catItem;
	
	@FXML
	protected ImageView catalogItemImage;

    public ImageView getCatalogItemImage() {
		return catalogItemImage;
	}

	public void setCatalogItemImage(ImageView catalogItemImage) {
		this.catalogItemImage = catalogItemImage;
	}

	public CatalogItem getCatItem() {
		return catItem;
	}

	public void setCatItem(EditableCatalogItemView catItem) 
	{
		this.catItem = catItem;
		
	}

	public TextField getImageField() {
		return imageField;
	}

	public void setImageField(String string) {
		this.imageField.setText(string);
	}

	@FXML
    private Label salesLabel;

    @FXML
    private Label imageLabel;

    @FXML
    private Button BrowseBTN;

    @FXML
    private Label headLabel;

    @FXML
	protected TextField imageField;

    @FXML
	protected TextField salesPriceField;

    @FXML
    private Button cancelBTN;

    @FXML
    private Button okBTN;

    
    @FXML
	protected CheckBox onSale;
	
	
    /**
     * Allows or denies to enter sale price depends on "OnSale" check box value
     * @param event - "on sale" check box is clicked
     */
    @FXML
    void onSale(ActionEvent event) 
    {
    	if(onSale.isSelected())
    	{
    		salesPriceField.setDisable(false);
    	}
    	else
    	{
    		salesPriceField.setDisable(true);
    	}
    }
	 
    /**
     * Opens file chooser window and allows to choose images
     * @param event - "Browse" button is clicked
     */
    @FXML
    void BrowseBTN(ActionEvent event) 
    {
    	Stage newWindow = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg");
    	fileChooser.getExtensionFilters().add(extFilter);
    	
    	fileChooser.setTitle("select image File");
    	File file = fileChooser.showOpenDialog(newWindow);
    	//try to open image:
    	try 
    	{
    		Image newImage = new Image(new FileInputStream(file));
    		image = new ImageData(file.getAbsolutePath());
    		imageField.setText(file.getAbsolutePath());
	    	catalogItemImage.setImage(newImage);
	    	

		} catch (IOException e) 
    	{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Could not open image!");
			alert.showAndWait();
			e.printStackTrace();
		}
    }

    /**
     * Closes current window and returns to previous one
     * @param event - "Cancel" button is clicked
     */
    @FXML
    void cancelBTN(ActionEvent event) 
    {
    	// get a handle to the stage
        Stage stage = (Stage)cancelBTN.getScene().getWindow();
        // close the window
        stage.close();   
    }

    
    /**
     * Approves the input and adds product to catalog with image and sale, if they were chosen
     * @param event - "OK" button is clicked
     */
    @FXML
    void okBTN(ActionEvent event) 
    {
    	

    	if(isInputValid()==false)
    	{
    		printErrorMessege();
    	}
    	
    	//if(input is good):
    	else
    	{
    		float salesPrice;
    		if(onSale.isSelected())
        	{
    			salesPrice = Float.parseFloat(salesPriceField.getText());
        	}
    		else
    		{
    			salesPrice = -1; //default value meaning there is not sale on the catalog item
    		}
    		
    		System.out.println(salesPrice);
    		String imagePath = imageField.getText();//this is the absolute path
    		byte[] checkSum = image.getSha256();
			
    		String ImageName = image.getFileName();
			
    		catItem = new CatalogItem(prod, salesPrice, ImageName, checkSum, storeID);
    		//imagePath = imagePath.replaceAll("/", "//");
    		CatalogController.addCatalogProductToDataBase(catItem,Client.client);
    		
			try 
			{
	    		ImageData imageToUpload;
				imageToUpload = new ImageData(imagePath);
				
				client.handleMessageFromClientUI(new UploadImageRequest(imageToUpload));
				synchronized(this)
				{	
					this.wait();
				}
				cancelBTN(null);
			} 
			catch (Exception e)    {e.printStackTrace();} }
    }
    
    //----------------------------------------
    /**
     * Prints which fields are missing in the GUI form
     */
    private void printErrorMessege()
    {
    	String missingField ="";
    	 
    	if(onSale.isSelected())
    	{
	    	if(salesPriceField.getText().equals(""))
	    	{
	    		missingField+="Sales Price";
	    	}
    	}
    	if(imageField.getText().equals(""))
    	{
    		missingField+=",Image";
    	}
    	if(!missingField.equals(""))
    		showErrorMessage("Form is not fully filled, please enter the \nfollowing fields: "); 	
    
    }
    //------------------------------------------
    /**
     * Returns true if input is valid(meaning none is missing)
     * else, false
     */
    private boolean isInputValid()
    {
    	if(salesPriceField.getText().equals(""))
    	{
    		if(onSale.isSelected())
    		return false;
    	}
    	if(imageField.getText().equals(""))
    	{
    		return false;
    	}
    	return true;
    }
    //------------------------------------------
    
    public void setSalesPrice(String price)
    {
    	this.salesPriceField.setText(price);
    }
    
   
  //Makes salesField numeric only.
	 ChangeListener<String> salesPriceFieldChangeListener =  new ChangeListener<String>() 
	 {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) 
	    {
	    	if(newValue.equals(""))
	    	{
		    	   salesPriceField.setText(newValue);
		    	   return;
	    	}
	       try
	       {
	    	   float value = Float.parseFloat(newValue);
	    	   if((value<=0) || value>=(getProd().getPrice()))
	    	   {
	    		   throw new Exception();
	    	   }
	    	   salesPriceField.setText(newValue);
	       }
	       catch(Exception e)
	       {
	    	   salesPriceField.setText(oldValue);
	       }
	    }
	};
	
	
	
	/**
	 * Initiates fields of the GUI
	 */
    public void doInit()
    {
    	//imageField.setText("");
    	salesPriceField.setText("");
    	onSale.setSelected(false);
    	imageField.setEditable(false);
    	salesPriceField.setDisable(true);
    	
    	salesPriceField.textProperty().addListener(salesPriceFieldChangeListener);
    	
    }



 

//*************************************************************************************************
    public void display(Object message)
    {
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	response = (Response)message;

		synchronized(this)
		{
			this.notify();
		}
    	    	
    }
    
    @Override
	public void setClinet(Client client)
	{
    	super.setClinet(client);
    	
	}
    public Client getClinet()
	{
    	return super.client;
	}
    
	@Override
	public void onSwitch(Client newClient) {

	}

	
	
}