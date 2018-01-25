package product;

import java.util.ArrayList;
import java.util.Optional;

import serverAPI.Response;

import client.Client;
import client.ClientInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import networkGUI.NetworkWorkerGUI;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import product.*;
import prototype.FormController;
import serverAPI.CheckExistsRequest;

/*
 * Provides a gui for the creation of a new product
 */
public class NewProductCreationGUI extends  FormController implements ClientInterface
{

	public Response response;
	ChangeListener idFieldChangeListener;

	
	
	/**
	 * init combo-box and Textfields:
	 */
	 @FXML
	    public void initialize() 
	 {
				 typeComboBox.getItems().addAll(
										"Bouquet",
										"Bridal Bouquet",
										"Pot");
				typeComboBox.getSelectionModel().selectFirst();


				colorComboBox.getItems().addAll(
										"Black",
										"White",
										"Purple",
										"Violet",
										"Blue",
										"red",
										"Yellow");
				colorComboBox.getSelectionModel().selectFirst();
				
				//make idField numberic only.
				 idFieldChangeListener =  new ChangeListener<String>() {
				    @Override
				    public void changed(ObservableValue<? extends String> observable, String oldValue, 
				        String newValue) 
				    {
				        if (!newValue.matches("\\d*")) 
				        {
				        	idField.setText(newValue.replaceAll("[^\\d]", ""));
				        }
				    }
				};
				
				
				
				idField.textProperty().addListener(idFieldChangeListener);
				
				
				
				
				//make amountField numberic only.
				amountFIeld.textProperty().addListener(new ChangeListener<String>() {
				    @Override
				    public void changed(ObservableValue<? extends String> observable, String oldValue, 
				        String newValue) 
				    {
				        if (!newValue.matches("\\d*")) 
				        {
				        	amountFIeld.setText(newValue.replaceAll("[^\\d]", ""));
				        }
				    }
				});
							
				
				//make priceField float numbers only.
				priceField.textProperty().addListener(new ChangeListener<String>() 
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
					    	  priceField.setText(newValue);
					    	  for(i=0;i<len;i++)
					    	  {
					    		  if( newValue.charAt(i) == '.' )
					    		  {
					    			  try
					    			  {
					    			  String numbersAfterDot = newValue.substring(i+1,len-1);
					    			  if(numbersAfterDot.length()>=2)
										{
											priceField.setText(newValue.substring(0,i+3));
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
					    	  priceField.setText(oldValue);
					    	}
				    	}
				    }
		    	});				
	 }
	 /*
	  * A dirty fixup solution  to remove
	  */
	 protected void removeListenerFromId()
	 {
		 idField.textProperty().removeListener(idFieldChangeListener);
	 }
    @FXML
    protected Label descriptionLbl;

    @FXML
	public TextField idField;

    @FXML
	public TextField nameFIeld;

    @FXML
	public TextField priceField;

    @FXML
    protected Label idLbl;

    @FXML
    protected Label nameLbl;

    @FXML
    protected Label typeLbl;

    @FXML
    protected Label priceLbl;

    @FXML
    protected Button createProductBtn;

    @FXML
    public Button backBtn;

    @FXML
	public ComboBox<String> typeComboBox;

    @FXML
    protected Label colorLbl;

    @FXML
    protected Label amountLbl;

    @FXML
	public TextField amountFIeld;

    @FXML
	public ComboBox<String> colorComboBox;


    void setNameText(String value)
    {
    	this.nameFIeld.setText(value);
    }
    @FXML
    void onBack(ActionEvent event) 
    {
    	NetworkWorkerGUI netWorkerGUI = (NetworkWorkerGUI)parent;
    	client.setUI(netWorkerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
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
      	  alert.setTitle("Can not create new product");
      	  alert.setHeaderText(fields+FieldsRequired.toString());
      	  alert.showAndWait(); 
      }
      else
      {
    	  //create a new product
    	  Product newProd = new Product(Long.parseLong(id) , 
    			  			name,type, Float.parseFloat(price), Integer.parseInt(amount),color); 
    	if(alreadyExists(id))
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Product creation dilema");
    		alert.setHeaderText("A product with this id already exists");
    		alert.setContentText("Replace it?");

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK)
    		{
    			updateProductAndWait();
    		} else return;
    	}
    	else
    	{
    		addProductAndWait(newProd);
    	}
      }
    }
   protected void updateProductAndWait()
   {
	   synchronized(this)
		{
		   Product updatedProd = new Product(Long.parseLong(idField.getText()), nameFIeld.getText(), typeComboBox.getValue(),
				   Float.parseFloat(priceField.getText()), Integer.parseInt(amountFIeld.getText()), colorComboBox.getValue());
		   ProdcutController.updateProduct(Long.parseLong(idField.getText()), updatedProd, client);
		   try
			{
			this.wait();
			} 
			catch (InterruptedException e) 
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
   }
    protected void removeProductAndWait(long id)
    {
    	synchronized(this)
		{
		  ProdcutController.removeProductFromDataBase(Long.toString(id), client);

			try
			{
			this.wait();
			} 
		catch (InterruptedException e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	   }
    }
    	
    protected void addProductAndWait(Product newProd )
    {
    	synchronized(this)
		{
		  ProdcutController.addProductToDataBase(newProd, client);

			try
			{
			this.wait();
		} 
		catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			String outputMsg;
			if(response.getType().name().equals("SUCCESS"))
			{
				outputMsg = "product was created";
			}
			else
			{
				outputMsg = "Could not create product";
			}
			   Alert alert = new Alert(AlertType.INFORMATION);
	      	  alert.setTitle("Action result");
	      	  alert.setHeaderText(outputMsg);
	      	  alert.showAndWait();
		}
    }
    protected boolean alreadyExists(String id)
    {
    	synchronized(this)
    	{
    		ArrayList<String> primaryKey = new ArrayList<String>();
    		primaryKey.add(id);
    		client.handleMessageFromClientUI(new CheckExistsRequest("Product",primaryKey));
    		
    		try 
    		{
    			this.wait();
    		}
    		catch (InterruptedException e) 
    		{
    			e.printStackTrace();
    		}

    		if(response.getType().name().equals("ERROR"))
    		{
    			return false;
    		}
    		else
    		{
    			return true;
    		}
    	}
    	 
   
    }
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
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
