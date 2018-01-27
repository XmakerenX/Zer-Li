package customer;

import javafx.scene.control.Button;


//*************************************************************************************************
	/**
	*  The class that holds the data of the customer to be shown in GUI  
	*/
//*************************************************************************************************

public class CustomerView extends Customer
{

	private static final long serialVersionUID = 1L;
	
	String StoreAddress;
	//===========================================================================================================
	/**
	 * Subclass that holds the data the customer view
	 */
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
	
	/**
	 * Customer's view constructor
	 * @param customer - specific customer
	 * @throws CustomerException when invalid paramenets are given
	 */
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
	/**
	 * Prints customer's view in to console
	 */
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
