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
	public OrderComplaintView(long complaintID, String name, String phone, long customerID, String complaint, LocalDate date, String time,
			float amountOfCompensation, String complaintStatus, int storeID, float maxCompensationAmount) {
		super(complaintID, name, phone, customerID, complaint, date, time, amountOfCompensation, complaintStatus, storeID,
				maxCompensationAmount);
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
