package order;

public class Receipt 
{
	String receiptDetails= "";
	int accountID;
	
	public Receipt()
	{
	
		
	}

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
