package store;

import java.io.Serializable;

public class Store implements Serializable {

	long storeID;
	String storeAddress;
	
	public Store(long storeID, String storeAddress)
	{
		this.storeID = storeID;
		this.storeAddress = storeAddress;
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	
	
}
