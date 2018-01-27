package serverAPI;

import java.util.ArrayList;

//*************************************************************************************************
/**
*  a Request for the server to check existing of a certain entity by its primary keys
*  Stores the data for the CheckExistsRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class CheckExistsRequest extends Request
{
	private static final long serialVersionUID = 6135571085283599438L;
	private String table;
	private ArrayList<String> primaryKeys;
	
	//*************************************************************************************************
	/**
	 *  Creates an CheckExistsRequest with the following parameters
	 *  @param table the table to add to 
	 *  @param keys the primary keys of the entity
	 */
	//*************************************************************************************************
	public CheckExistsRequest(String table, ArrayList<String> keys) 
	{
		super("CheckExistsRequest");
		this.table = table;
		this.primaryKeys = keys;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the CheckExistsRequest table
	 *  @return the CheckExistsRequest table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}
	
	//*************************************************************************************************
	/**
	 *  sets the CheckExistsRequest table
	 *  @param table the table to be set  
	 */
	//*************************************************************************************************
	public void setTable(String table) {
		this.table = table;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the CheckExistsRequest primaryKeys
	 *  @return the CheckExistsRequest primaryKeys 
	 */
	//*************************************************************************************************
	public ArrayList<String> getPrimaryKey() {
		return primaryKeys;
	}

	//*************************************************************************************************
	/**
	 *  sets the CheckExistsRequest primaryKeys
	 *  @param primaryKey table the primaryKeys to be set  
	 */
	//*************************************************************************************************
	public void setPrimaryKey(ArrayList<String> primaryKey) {
		this.primaryKeys = primaryKey;
	}
  
}
