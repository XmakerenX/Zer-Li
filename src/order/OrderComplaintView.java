package order;

import javafx.scene.control.Button;


//*************************************************************************************************
/**
 *  The class that holds the data to be shown in the order complaint TableView  
 */
//*************************************************************************************************
public class OrderComplaintView extends OrderComplaint{

	private static final long serialVersionUID = 1616786322131298474L;
	private ComplaintViewButton selectButton;

	//===========================================================================================================
	public class ComplaintViewButton extends Button
	{
		OrderComplaintView origin;
		public ComplaintViewButton(OrderComplaintView origin, String name)
		{
			super(name);
			this.origin = origin;
		}

		public OrderComplaintView getOrigin()
		{
			return this.origin;
		}
	}
	//===========================================================================================================
	public OrderComplaintView(OrderComplaint complaint) {
		super(complaint.getComplaintID(), complaint.getCustomerID(), complaint.getCustomerName(), complaint.getCustomerPhoneNum(), complaint.getStoreID(), 
				complaint.getComplaintDescription(), complaint.getComplaintDate(), complaint.getComplaintTime(), complaint.getComplaintCompensation(),
				complaint.getMaxCompensationAmount(), complaint.getComplaintStatus(),complaint.getOrderID(), complaint.getUserNameOfWhoeverAddedIt());
		this.selectButton = new ComplaintViewButton(this, "Select");
	}
	//===========================================================================================================
	public Button getSelectButton()
	{
		return this.selectButton;
	}
	public void setSelectButton(ComplaintViewButton button) 
	{
		this.selectButton = button;
	}
	//===========================================================================================================
	public OrderComplaint getOrderComplaint() 
	{
		OrderComplaint complaint = new OrderComplaint(getComplaintID(), getCustomerID(), getCustomerName(), getCustomerPhoneNum(), getStoreID(), 
				getComplaintDescription(), getComplaintDate(), getComplaintTime(), getComplaintCompensation(), 
				getMaxCompensationAmount(), getComplaintStatus(),this.getOrderID(), this.getUserNameOfWhoeverAddedIt());
		return complaint;
	}
}
