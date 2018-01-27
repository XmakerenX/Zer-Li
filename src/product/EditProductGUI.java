package product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import serverAPI.CheckExistsRequest;
import serverAPI.Response;
import utils.FormController;

/*
 * Provides a GUI to edit products info
 */
public class EditProductGUI extends NewProductCreationGUI 
{
	Product prod;
	
	public void initWindow(Product prod)
	{
		this.prod = prod;
		//init window:
		this.backBtn.setVisible(false);
		this.amountFIeld.setText(Integer.toString(prod.getAmount()));
		this.nameFIeld.setText(prod.getName());
		this.idField.setText(Long.toString(prod.getID()));
		this.priceField.setText(Float.toString(prod.getPrice()));
		this.colorComboBox.setValue(prod.getColor());
		this.createProductBtn.setText("Save");
		
		idField.setEditable(false);
		
		/*addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) 
		    {
		        if (!newValue.matches("\\d*")) 
		        {
		        	idField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});*/
	}
	
	//*************************************************************************************************
    /**
     * Init the EditProductGUI to be read only showing the product given 
  	*  @param prod the product information to load 
  	*/
    //*************************************************************************************************
	public void initWindowReadOnly(Product prod)
	{
		initWindow(prod);
		this.backBtn.setVisible(true);
		this.amountFIeld.setEditable(false);
		this.nameFIeld.setEditable(false);
		this.idField.setEditable(false);
		this.priceField.setEditable(false);
		this.colorComboBox.setDisable(true);
		this.createProductBtn.setVisible(false);
		this.typeComboBox.setDisable(true);
	}
	
	@FXML
    void onCreateProduct(ActionEvent event) 
    {
      String id = idField.getText();
      String name = nameFIeld.getText();
      String type = typeComboBox.getValue();
      String price = priceField.getText();
      String amount = amountFIeld.getText();
      String color = colorComboBox.getValue();
      
      
      String fields = "Incomplete form, please enter the following fields :";
      ArrayList<String> FieldsRequired = new ArrayList<String>();
     
      if(id.equals("")) FieldsRequired.add("id");
      if(name.equals("")) FieldsRequired.add("name");
      if(price.equals("")) FieldsRequired.add("price");
      if(amount.equals("")) FieldsRequired.add("amount");
      
      //if more fields are required to fill:
      if(FieldsRequired.size() >0)
      {
    	  Alert alert = new Alert(AlertType.INFORMATION);
      	  alert.setTitle("Can not edit product");
      	  alert.setHeaderText(fields+FieldsRequired.toString());
      	  alert.showAndWait(); 
      }
      else
      {
    	  //create a new product
    	  Product newProd = new Product(Long.parseLong(id) , 
    			  			name,type, Float.parseFloat(price), Integer.parseInt(amount),color); 
    	if(super.alreadyExists(id))
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("About to update Product");
    		alert.setContentText("Are you sure?");

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK)
    		{
    			super.updateProductAndWait();
    		} else return;
    	}
    	else
    	{
    		addProductAndWait(newProd);
    	}
    	 
      }
    }
//----------------------------------------------------------------------------------
	@Override
	public void display(Object message) 
	{
		Response res = (Response)message;
		this.response = res;
		synchronized(this)
		{
			this.notifyAll();
		}
	}
	
}
