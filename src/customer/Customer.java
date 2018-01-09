package customer;

import java.io.Serializable;

public class Customer implements Serializable {
	
	public class  CustomerException extends Exception {

		public CustomerException(String message)
		{
			super(message);
		}
	}
	
	public enum PayType {CREDIT_CARD, SUBSCRIPTION}
	
	private long ID;
	private String name;
	private String phoneNumber;
	private PayType payMethod;
	private float accountBalance;
	private String creditCardNumber;
	private boolean accountStatus;
	
	public Customer(long ID, String name, String phoneNumber, PayType payMethod, float accountBalance,
							String creditCardNumber, boolean accountStatus) throws CustomerException
	{
		setID(ID);
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

	public void setID(long iD) {
		ID = iD;
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
	
	
}
