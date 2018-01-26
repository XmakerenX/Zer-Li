package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to get table data 
*  Stores the data for the GetRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetRequest extends Request {

	private static final long serialVersionUID = 3200101440299699234L;
	private String table;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetRequest with the following parameters
	 *  @param table the table to get data from
	 */
	//*************************************************************************************************
	public GetRequest(String table)
	{
		super("GetRequest");
		this.table = table;;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the GetRequest table
	 *  @returns the GetRequest table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}
}
