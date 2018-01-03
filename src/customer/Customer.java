package customer;

import java.io.Serializable;

public class Customer implements Serializable {
	
	public enum PayType {CASH, CREDIT_CARD, SUBSCRIPTION}
	
	long ID;
	String name;
	String phoneNumber;
	PayType payMethod;
	float accountBalance;
	String creditCardNumber;
	
	public Customer(long ID, String name, String phoneNumber, PayType payMethod, float accountBalance, String creditCardNumber)
	{
		setID(ID);
		setName(name);
		setPhoneNumber(phoneNumber);
		setPayMethod(payMethod);
		setAccountBalance(accountBalance);
		setCreditCardNumber(creditCardNumber);
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
	
	
}
