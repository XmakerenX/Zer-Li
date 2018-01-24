package customer;

import java.util.ArrayList;

import client.Client;
import javafx.scene.control.Button;
import product.EditableProductView;
import serverAPI.GetRequestByKey;


//*************************************************************************************************
	/**
	*  The class that holds the data of the customer to be shown in gui  
	*/
//*************************************************************************************************

public class CustomerView extends Customer
{
	String StoreAddress;
	//===========================================================================================================
	public class CustomerViewButton extends Button
	{
		CustomerView origin;
		public CustomerViewButton(CustomerView origin, String name)
		{
			super(name);
			this.origin = origin;
		}
		
		public CustomerView getOrigin()
		{
			return this.origin;
		}
	}
	//===========================================================================================================
	private CustomerViewButton selectButton;
	
	public CustomerView(Customer customer)throws CustomerException
	{
		super(customer.getID(), customer.getStoreID(), customer.getName(), customer.getPhoneNumber(), 
				customer.getPayMethod(), customer.getAccountBalance(), customer.getCreditCardNumber(), 
				customer.getAccountStatus(), customer.getExpirationDate());
		this.selectButton = new CustomerViewButton(this, "Select");
	}
	//===========================================================================================================
	public Button getSelectButton()
	{
		return this.selectButton;
	}
	public void setSelectButton(CustomerViewButton button) 
	{
		this.selectButton = button;
	}
	//===========================================================================================================
	public String toString()
	{
		return "CustomerView: name: "+this.getName()+" ,phone: "+
				this.getPhoneNumber()+" ,store: "+this.getStoreID()+
				"Store address: "+getStoreAddress();
	}
	//===========================================================================================================
	public String getStoreAddress() {
		return StoreAddress;
	}
	public void setStoreAddress(String storeAddress) 
	{
		StoreAddress = storeAddress;
	}
}
