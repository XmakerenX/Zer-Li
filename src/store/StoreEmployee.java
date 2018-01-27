package store;

import java.io.Serializable;

//*************************************************************************************************
/**
* The StoreEmployee entity data
*/
//*************************************************************************************************
public class StoreEmployee implements Serializable {

	private static final long serialVersionUID = 5061569614569187330L;
	private String userName;
	private long storeID;
	private String permission;

	//*************************************************************************************************
	/**
	 *  Returns the StoreEmployee userName
	 *  @return the StoreEmployee userName 
	 */
	//*************************************************************************************************
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//*************************************************************************************************
	/**
	 *  Returns the Store Employee storeID
	 *  @return the Store Employee storeID 
	 */
	//*************************************************************************************************
	public long getStoreID() {
		return storeID;
	}

	//*************************************************************************************************
	/**
	 *  sets the Store Employee storeID
	 *  @param storeID the Store Employee ID to set 
	 */
	//*************************************************************************************************	
	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}

	//*************************************************************************************************
	/**
	 *  Returns the Store Employee permission
	 *  @return the Store Employee permission 
	 */
	//*************************************************************************************************
	public String getPermission() {
		return permission;
	}

	//*************************************************************************************************
	/**
	 *  sets the Store Employee permission
	 *  @param permission the Store Employee permission to set 
	 */
	//*************************************************************************************************
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
