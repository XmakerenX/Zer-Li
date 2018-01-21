package customer;

import java.io.Serializable;

import user.User.UserException;

//*************************************************************************************************
	/**
	*  A class to holds customer's data
	*/
//*************************************************************************************************
public class Customer implements Serializable {
	
	public class  CustomerException extends Exception {

		public CustomerException(String message)
		{
			super(message);
		}
	}
	
	public enum PayType {CREDIT_CARD, MONTHLY_SUBSCRIPTION, YEARLY_SUBSCRIPTION}
	
	private long ID;
	private long storeID;
	private String name;
	private String phoneNumber;
	private PayType payMethod;
	private float accountBalance;
	private String creditCardNumber;
	private boolean accountStatus;
	
	public Customer(long ID, long storeID,String name, String phoneNumber, PayType payMethod, float accountBalance,
							String creditCardNumber, boolean accountStatus) throws CustomerException
	{
		setID(ID);
		setStoreID(storeID);
		setName(name);
		setPhoneNumber(phoneNumber);
		setPayMethod(payMethod);
		setAccountBalance(accountBalance);
		setCreditCardNumber(creditCardNumber);
		setAccountStatus(accountStatus);
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) throws CustomerException  {
		if(iD > 0)
		{	
			ID = iD;
		}
		else
			throw new CustomerException("Person ID is invalid!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PayType getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(PayType payMethod) {
		this.payMethod = payMethod;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public boolean getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) throws CustomerException {
		if (storeID > 0)
			this.storeID = storeID;
		else
			throw new CustomerException(storeID+" is an invalid StoreID");
	}
	
	
}
