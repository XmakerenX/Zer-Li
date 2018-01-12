package order;

import java.io.Serializable;
import java.time.LocalDate;

public class OrderComplaint implements Serializable {

	private static final long serialVersionUID = 65L;

	private long complaintID;
	private long customerID;
	private String complaintDescription;
	private LocalDate complaintDate;
	private String complaintTime;
	private float complaintCompensation;
	private String complaintStatus;
	
	//A constructor for adding a new complaint to the date base
	public OrderComplaint(long customerID, String complaint, LocalDate date, String time)
	{
		setCustomerID(customerID);
		setComplaintDescription(complaint);
		setComplaintDate(date);
		setComplaintTime(time);
	}
	
	//A constructor for receiving complaint info from data base
	public OrderComplaint(long complaintID, long customerID, String complaint, LocalDate date, String time, float amountOfCompensation, String complaintStatus)
	{
		setComplaintID(complaintID);
		setCustomerID(customerID);
		setComplaintDescription(complaint);
		setComplaintDate(date);
		setComplaintTime(time);
		setComplaintCompensation(amountOfCompensation);
		setComplaintStatus(complaintStatus);
	}

	public long getComplaintID() {
		return complaintID;
	}

	public void setComplaintID(long complaintID) {
		this.complaintID = complaintID;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getComplaintDescription() {
		return complaintDescription;
	}

	public void setComplaintDescription(String complaintDescription) {
		this.complaintDescription = complaintDescription;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate conplaintDate) {
		this.complaintDate = conplaintDate;
	}

	public String getComplaintTime() {
		return complaintTime;
	}

	public void setComplaintTime(String complaintTime) {
		this.complaintTime = complaintTime;
	}

	public float getComplaintCompensation() {
		return complaintCompensation;
	}

	public void setComplaintCompensation(float complaintCompensation) {
		this.complaintCompensation = complaintCompensation;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	
}
