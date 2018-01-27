package customer;

import java.io.Serializable;
import java.util.Calendar;

//*************************************************************************************************
	/**
	*  A class to holds customer's data
	*/
//*************************************************************************************************
public class Customer implements Serializable {
	

	private static final long serialVersionUID = 1L;

	public class  CustomerException extends Exception {

		private static final long serialVersionUID = 1L;

		public CustomerException(String message)
		{
			super(message);
		}
	}
	
	//					   0%				10%						25%
	public enum PayType {CREDIT_CARD, MONTHLY_SUBSCRIPTION, YEARLY_SUBSCRIPTION}
	
	private long ID;
	private long storeID;
	private String name;
	private String phoneNumber;
	private PayType payMethod;
	private float accountBalance;
	private String creditCardNumber;
	private boolean accountStatus;
	private Calendar expirationDate;

	//*****************************************************************************************
	/**
	* Creates a new Customer with the following parameters
	* @param ID the customer ID
	* @param storeID the store that the customer belongs to
	* @param name  the customer name
	* @param phoneNumber  the customer phone-number
	* @param payMethod credit card or by subscription
	* @param accountBalance the customer account balance
	* @param creditCardNumber the customer credit card number
	* @param accountStatus the customer account status
	* @param expirationDate the subscription expiration date
	* @throws CustomerException when invalid parameters are given
	*/
	//*****************************************************************************************
	public Customer(long ID, long storeID,String name, String phoneNumber, PayType payMethod, float accountBalance,
							String creditCardNumber, boolean accountStatus,  Calendar expirationDate) throws CustomerException
	{
		setID(ID);
		setStoreID(storeID);
		setName(name);
		setPhoneNumber(phoneNumber);
		setPayMethod(payMethod);
		setAccountBalance(accountBalance);
		setCreditCardNumber(creditCardNumber);
		setAccountStatus(accountStatus);
		setExpirationDate(expirationDate);
	}

	//*************************************************************************************************
    /**
     * Returns the customer ID
  	*  @return the customer ID
  	*/
    //*************************************************************************************************
	public long getID() {
		return ID;
	}

    //*************************************************************************************************
    /**
     * Sets the customer ID
  	*  @param iD the customer ID to be set
  	*  @throws CustomerException when invalid date is given
  	*/
    //*************************************************************************************************
	public void setID(long iD) throws CustomerException  {
		if(iD > 0)
		{	
			ID = iD;
		}
		else
			throw new CustomerException("Person ID is invalid!");
	}

	//*************************************************************************************************
    /**
     * Returns the customer name
  	*  @return the customer name
  	*/
    //*************************************************************************************************
	public String getName() {
		return name;
	}

    //*************************************************************************************************
    /**
     * Sets the customer name
  	*  @param name the customer name to be set
  	*/
    //*************************************************************************************************
	public void setName(String name) {
		this.name = name;
	}

	//*************************************************************************************************
    /**
     * Returns the customer phone number
  	*  @return the customer phone number
  	*/
    //*************************************************************************************************
	public String getPhoneNumber() {
		return phoneNumber;
	}

    //*************************************************************************************************
    /**
     * Sets the customer phone number
  	*  @param phoneNumber the customer phone number to be set
  	*/
    //*************************************************************************************************
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	//*************************************************************************************************
    /**
     * Returns the customer payMethod
  	*  @return the customer payMethod
  	*/
    //*************************************************************************************************
	public PayType getPayMethod() {
		return payMethod;
	}

    //*************************************************************************************************
    /**
     * Sets the customer payMethod
  	*  @param payMethod the customer payMethod to be set
  	*/
    //*************************************************************************************************
	public void setPayMethod(PayType payMethod) {
		this.payMethod = payMethod;
	}

	//*************************************************************************************************
    /**
     * Returns the customer account balance
  	*  @return the customer account balance
  	*/
    //*************************************************************************************************
	public float getAccountBalance() {
		return accountBalance;
	}

    //*************************************************************************************************
    /**
     * Sets the customer account balance
  	*  @param accountBalance the customer account balance to be set
  	*/
    //*************************************************************************************************
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	//*************************************************************************************************
    /**
     * Returns the customer CreditCard number
  	*  @return the customer CreditCard number
  	*/
    //*************************************************************************************************
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

    //*************************************************************************************************
    /**
     * Sets the customer CreditCard number
  	*  @param creditCardNumber the customer CreditCard number to be set
  	*/
    //*************************************************************************************************
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	//*************************************************************************************************
    /**
     * Returns the customer CreditCard number
  	*  @return the customer CreditCard number
  	*/
    //*************************************************************************************************
	public boolean getAccountStatus() {
		return accountStatus;
	}

    //*************************************************************************************************
    /**
     * Sets the customer CreditCard number
  	*  @param accountStatus the customer CreditCard number to be set
  	*/
    //*************************************************************************************************
	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}

	//*************************************************************************************************
    /**
     * Returns the customer storeID
  	*  @return the customer storeID
  	*/
    //*************************************************************************************************
	public long getStoreID() {
		return storeID;
	}

    //*************************************************************************************************
    /**
     * Sets the customer storeID
  	*  @param storeID the customer storeID to be set
  	*  @throws CustomerException when invalid store id was given
  	*/
    //*************************************************************************************************
	public void setStoreID(long storeID) throws CustomerException {
		if (storeID > 0)
			this.storeID = storeID;
		else
			throw new CustomerException(storeID+" is an invalid StoreID");
	}
	
	//*************************************************************************************************
	/**
	 * Returns customer's subscription expiration date
	 * @return expiration date
	 */
	//*************************************************************************************************
	public Calendar getExpirationDate() {
		return expirationDate;
	}

	//*************************************************************************************************
	/**
	 * Sets customer's subscription expiration date
	 * @param expirationDate - subscription expiration date
	 */
	//*************************************************************************************************
	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	
}
