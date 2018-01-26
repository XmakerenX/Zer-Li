package order;
/**
 * a class that holds all the information needed for the receipt entity
 * @author dk198
 *
 */
public class Receipt 
{
	String receiptDetails= "";
	int accountID;
	/**
	 * constructor
	 */
	public Receipt()
	{
	
		
	}
//getters and setters:
	
	public String getReceiptDetails() 
	{
		return receiptDetails;
	}

	public void setReceiptDetails(String receiptDetails) 
	{
		this.receiptDetails = receiptDetails;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
}
