package store;

import java.io.Serializable;

//*************************************************************************************************
/**
 * The store entity data
*/
//*************************************************************************************************
public class Store implements Serializable {

	private static final long serialVersionUID = -6235326277174512612L;
	private long storeID;
	private String storeAddress;
	
	//*************************************************************************************************
	/**
	 *  Creates an Store with the following parameters
	 *  @param storeID the store ID
	 *  @param storeAddress the store Address
	 */
	//*************************************************************************************************
	public Store(long storeID, String storeAddress)
	{
		this.storeID = storeID;
		this.storeAddress = storeAddress;
	}

	//*************************************************************************************************
	/**
	 *  Returns the Store storeID
	 *  @returns the Store storeID 
	 */
	//*************************************************************************************************
	public long getStoreID() {
		return storeID;
	}

	//*************************************************************************************************
	/**
	 *  sets the Store storeID
	 *  @param storeID the Store ID to set 
	 */
	//*************************************************************************************************	
	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}

	//*************************************************************************************************
	/**
	 *  Returns the Store storeAddress
	 *  @returns the Store storeAddress 
	 */
	//*************************************************************************************************
	public String getStoreAddress() {
		return storeAddress;
	}

	//*************************************************************************************************
	/**
	 *  sets the Store storeAddress
	 *  @param storeAddress the store Address to set 
	 */
	//*************************************************************************************************
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	
	
}
