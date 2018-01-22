package order;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * a class that holds all the  information about order complaints 
 * @author dk198
 *
 */
public class OrderComplaint implements Serializable 
{
	private long complaintID;
	private long customerID;
	private String complaintDescription;
	private LocalDate complaintDate;
	private String complaintTime;
	private float complaintCompensation;
	private float maxCompensationAmount;
	private String complaintStatus;
	private int storeID;
	private String customerPhoneNum;
	private String customerName;
	
	//A constructor for adding a new complaint to the date base
	public OrderComplaint(long customerID, String name, String phone, String complaint, LocalDate date, String time, 
			int storeID, float maxCompensationAmount)
	{
		setCustomerID(customerID);
		setComplaintDescription(complaint);
		setComplaintDate(date);
		setComplaintTime(time);
		setStoreID(storeID);
		setMaxCompensationAmount(maxCompensationAmount);
		setComplaintStatus("NEW");
		setCustomerName(name);
		setCustomerPhoneNum(phone);
	}
	
	//A constructor for receiving complaint info from data base
	public OrderComplaint(long complaintID, long customerID, String name, String phone, int storeID, String complaint, LocalDate date, String time, 
			float amountOfCompensation, float maxCompensationAmount, String complaintStatus)
	{
		setComplaintID(complaintID);
		setCustomerID(customerID);
		setComplaintDescription(complaint);
		setComplaintDate(date);
		setComplaintTime(time);
		setComplaintCompensation(amountOfCompensation);
		setComplaintStatus(complaintStatus);
		setStoreID(storeID);
		setMaxCompensationAmount(maxCompensationAmount);
		setCustomerName(name);
		setCustomerPhoneNum(phone);
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

	public float getMaxCompensationAmount() {
		return maxCompensationAmount;
	}

	public void setMaxCompensationAmount(float maxCompensationAmount) {
		this.maxCompensationAmount = maxCompensationAmount;
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}

	public String getCustomerPhoneNum() {
		return customerPhoneNum;
	}

	public void setCustomerPhoneNum(String customerPhoneNum) {
		this.customerPhoneNum = customerPhoneNum;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public OrderComplaint getOrderComplaint() 
	{
		OrderComplaint complaint = new OrderComplaint(getComplaintID(), getCustomerID(), getCustomerName(), getCustomerPhoneNum(), getStoreID(), 
				getComplaintDescription(), getComplaintDate(), getComplaintTime(), getComplaintCompensation(), 
				getMaxCompensationAmount(), getComplaintStatus());
		return complaint;
	}
	
}
