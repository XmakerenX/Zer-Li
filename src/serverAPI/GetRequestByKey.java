package serverAPI;

import java.util.ArrayList;

//*************************************************************************************************
/**
*  a Request for the server to get table data with the given primary keys
*  Stores the data for the GetRequestByKey that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetRequestByKey extends GetRequest {

	private static final long serialVersionUID = -3084994662467115186L;
	private ArrayList<String> Keys;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetRequestByKey with the following parameters
	 *  @param table the table to get data from
	 *  @param keys the primary keys for the entry wanted
	 */
	//*************************************************************************************************
	public GetRequestByKey(String table, ArrayList<String> keys)
	{
		super(table);
		this.Keys = keys;
		this.type = "GetRequestByKey";
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetRequestByKey Keys
	 *  @returns the GetRequestByKey Keys 
	 */
	//*************************************************************************************************
	public ArrayList<String> getKey() {
		return Keys;
	}
	
}