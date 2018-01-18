package order;

import java.time.LocalDate;

import javafx.scene.control.Button;
import order.OrderView.OrderViewButton;

public class OrderComplaintView extends OrderComplaint{

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
		private ComplaintViewButton selectButton;
	public OrderComplaintView(OrderComplaint complaint) {
		super(complaint.getComplaintID(), complaint.getCustomerID(), complaint.getCustomerName(), complaint.getCustomerPhoneNum(), complaint.getStoreID(), 
				complaint.getComplaintDescription(), complaint.getComplaintDate(), complaint.getComplaintTime(), complaint.getComplaintCompensation(),
				complaint.getMaxCompensationAmount(), complaint.getComplaintStatus());
		// TODO Auto-generated constructor stub
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
}
