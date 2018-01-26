package catalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import product.CatalogItem;
import serverAPI.UploadImageRequest;
import utils.ImageData;
import java.io.IOException;
//*************************************************************************************************
	/**
	*  Provides a GUI that enable editing a catalog's store
	*/
//*************************************************************************************************
public class EditCatalogItemGUI extends AddToCatalogGUI 
{
	CatalogItem eCatProd;
	
	/**
	 * Initiates window's fields
	 * @param eCatalogProd - selected product from catalog
	 */
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
     * prints which fields are missing in the GUI form
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
    		showErrorMessage("Form is not fully filled, please enter the \nfollowing fields: ");

    }
    //------------------------------------------
    /*
     * returns true if input is valid(meaning none is missing)
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