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

public class EditCatalogItemGUI extends AddToCatalogGUI 
{
	CatalogItem eCatProd;
	
	public void initWindow(CatalogItem eCatalogProd)
	{
		eCatProd = eCatalogProd;
		Boolean isOnSale = !(eCatalogProd.getSalePrice() ==-1);
		if(isOnSale)
		{
			this.onSale.setSelected(true);
			this.salesPriceField.setDisable(false);
			this.setSalesPrice(Float.toString(eCatalogProd.getSalePrice()));
		}
		else
		{
			this.onSale.setSelected(false);
			this.salesPriceField.setDisable(true);
		}
		this.imageField.setText("click browse to upload new image");

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
	    		
	    			String imagePath;
		    		byte[] checkSum;
		    		String ImageName;
		    		
		    		if(imageField.getText().equals("click browse to upload new image"))
					{
						
			    		checkSum = eCatProd.getImageChecksum();
			    		ImageName = eCatProd.getImageName();
			    		
					}
					else
					{
						
						try 
						{
							//upload new Image locally:
							image = new ImageData(imageField.getText());
							ImageName = image.getFileName();
				    		checkSum = image.getSha256();
				    		
				    		//upload Image to server
							client.handleMessageFromClientUI(new UploadImageRequest(image));

							//create new updated catItem:
				    		catItem = new CatalogItem(prod, salesPrice, ImageName, checkSum, storeID);

				    		
				    		CatalogController.removeCatalogProductFromDataBase(eCatProd.getID(), eCatProd.getStoreID(), client);
							
							CatalogController.addCatalogProductToDataBase(catItem, client);
						
						} catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
			    		
					}
		    		
		    		
		    	
	
	    			
	    			
	    		}
	    			
	    }		
	    		
	    		
	    		/*
	    		catItem = new CatalogItem(prod, salesPrice, ImageName, checkSum, storeID);
				imagePath = imageField.getText();//this is the absoloute path
	    		imagePath = imagePath.replaceAll("/", "//");
	    		CatalogController.removeCatalogProductFromDataBase(eCatProd.getID(), eCatProd.getStoreID(), client);
	    		CatalogController.addCatalogProductToDataBase(catItem, client);
	    		
				try 
				{
		    		ImageData imageToUpload;
					imageToUpload = new ImageData(imagePath);	
					client.handleMessageFromClientUI(new UploadImageRequest(imageToUpload));
				} 
				catch (IOException e)    {e.printStackTrace();} }*/
	    
	    
	    
	
    //----------------------------------------
    /*
     * prints which fields are missing in the gui form
     */
    private void printErrorMessege()
    {
    	String missingField ="";
    	 
    	if(onSale.isSelected())
    	{
	    	if(this.salesPriceField.getText().equals(""))
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
    	if(this.salesPriceField.getText().equals(""))
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
	
	
}