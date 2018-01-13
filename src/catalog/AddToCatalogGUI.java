package catalog;

import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import client.Client;
import client.ClientInterface;
import customer.CustomerGUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import order.CreateOrderGUI;
import product.CatalogItem;
import product.Product;
import prototype.FormController;
import serverAPI.Response;
import serverAPI.UploadImageRequest;
import user.LoginGUI;
import utils.ImageData;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddToCatalogGUI extends FormController implements ClientInterface 
{
	int storeID;
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
	
    public CatalogItem getCatItem() {
		return catItem;
	}

	public void setCatItem(CatalogItem catItem) {
		this.catItem = catItem;
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
    		image = new ImageData(file.getAbsolutePath());
	    	imageField.setText(file.getAbsolutePath());

		} catch (IOException e) 
    	{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Could not open image!");
			alert.showAndWait();
			e.printStackTrace();
		}
    }

    @FXML
    void cancelBTN(ActionEvent event) 
    {
    	// get a handle to the stage
        Stage stage = (Stage)cancelBTN.getScene().getWindow();
        // close the window
        stage.close();   
    }

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
    		String imagePath = imageField.getText();//this is the absoloute path
    		byte[] checkSum = image.getSha256();
			
    		String ImageName = image.getFileName();
			
    		catItem = new CatalogItem(prod, salesPrice, ImageName, checkSum, storeID);
    		imagePath = imagePath.replaceAll("/", "//");
    		Client myClient = getClinet();
    		CatalogController.addCatalogProductToDataBase(catItem,myClient);
    		
			try 
			{
	    		ImageData imageToUpload;
				imageToUpload = new ImageData(imagePath);
				
				client.handleMessageFromClientUI(new UploadImageRequest(imageToUpload));
			} 
			catch (IOException e)    {e.printStackTrace();} }
    }
    
    
    private void waitForResponse()
    {
    	synchronized(this)
    	{
    		try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    //----------------------------------------
    /*
     * prints which fields are missing in the gui form
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
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Form is not fully filled, please enter the \nfollowing fields: "+missingField);
    		alert.showAndWait();
    	}
    
    }
    //------------------------------------------
    /*
     * return true if input is valid(meaning none is missing)
     * else false
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
    
    /*
      
    */
    public void setSalesPrice(String price)
    {
    	this.salesPriceField.setText(price);
    }
    
   
  //make salesField numberic only.
	 ChangeListener salesPriceFieldChangeListener =  new ChangeListener<String>() 
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
	
	
	
	
    public void doInit()
    {
    	imageField.setText("");
    	salesPriceField.setText("");
    	onSale.setSelected(false);
    	imageField.setEditable(false);
    	salesPriceField.setDisable(true);
    	
    	salesPriceField.textProperty().addListener(salesPriceFieldChangeListener);
    	//inti text fields : make imageField uneditable and  price field to be of the form %.2f
		
       
        //salesPriceField.setText(Float.toString(prod.getPrice()));
        
       /*
        *  salesPriceField.textProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) 
		    {
		    	//clean input:
		    	newValue.replaceAll("[^\\d.]","");
		    	
		    	//allow empty input(for filling only)
		    	if(newValue.equals("")) return;
		    	
		    	else//try to convert it into float
		    	{
			    	try
			    	{
			    	  float floatInput = Float.parseFloat(newValue);//first, check format
			    	  //enorfce float policy : ".2f":
			    	  int i;
			    	  int len =newValue.length();
			    	  salesPriceField.setText(newValue);
			    	  for(i=0;i<len;i++)
			    	  {
			    		  if( newValue.charAt(i) == '.' )
			    		  {
			    			  try
			    			  {
			    			  String numbersAfterDot = newValue.substring(i+1,len-1);
			    			  if(numbersAfterDot.length()>=2)
								{
			    				  salesPriceField.setText(newValue.substring(0,i+3));
								} 
			    			    break;
			    			  }
			    			  catch(Exception e)
			    			  {
			    				  break;
			    			  }
			    		  }
			    	  }
			    	}
			    	
			    	catch(Exception e)//if every last check or try to fix the input failed, 
			    					  //simply ignore the change.
			    	{
			    		salesPriceField.setText(oldValue);
			    	}
		    	}
		    }
    	});		
        */
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
		// TODO Auto-generated method stub

	}

	
	
}