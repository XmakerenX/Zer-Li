package serverAPI;

import java.util.ArrayList;

//*************************************************************************************************
/**
*  a Request for the server to remove entry form table
*  Stores the data for the RemoveRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class RemoveRequest extends Request
{

	private static final long serialVersionUID = 1L;
	String table;
	ArrayList<String> keys;
	
	//*************************************************************************************************
	/**
	 * Creates an RemoveRequest with the following parameters
	 * @param table the table to remove from
	 * @param keys the primary keys of the entry to remove
	 */
	//*************************************************************************************************
	public RemoveRequest(String table, ArrayList<String> keys)
	{
	   super("RemoveRequest");
	   this.table = table;
	   this.keys = keys;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the RemoveRequest table
	 *  @return the RemoveRequest table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}

	//*************************************************************************************************
	/**
	 *  sets the RemoveOrderRequest table
	 *  @param table the table to be set 
	 */
	//*************************************************************************************************
	public void setTable(String table) {
		this.table = table;
	}

	//*************************************************************************************************
	/**
	 *  Returns the RemoveRequest keys
	 *  @return the RemoveRequest keys 
	 */
	//*************************************************************************************************
	public ArrayList<String> getKey() {
		return keys;
	}

	//*************************************************************************************************
	/**
	 *  sets the RemoveRequest keys
	 *  @param keys the keys to be set 
	 */
	//*************************************************************************************************
	public void setKey(ArrayList<String> keys) {
		this.keys = keys;
	}
}
