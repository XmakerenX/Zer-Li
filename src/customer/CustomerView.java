package customer;

import javafx.scene.control.Button;
import product.EditableProductVIew;

public class CustomerView extends Customer{
//===========================================================================================================
	public class CustomerViewButton extends Button
	{
		CustomerView origin;
		public CustomerViewButton(CustomerView origin,String name)
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
				customer.getAccountStatus());
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
		return "CustomerView: name: "+this.getName()+" ,phone: "+this.getPhoneNumber()+" ,store: "+this.getStoreID();
	}
}
