package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to get table data joined with another table
*  Stores the data for the GetJoinedTablesRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetJoinedTablesRequest extends GetRequest {

	private static final long serialVersionUID = 7339315554575082783L;
	private String joinedTable;
	private int keyIndex;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetJoinedTablesRequest with the following parameters
	 *  @param table the table to get data from
	 *  @param joinedTable the table to join with when getting the data
	 *  @param keyIndex the index of the primary key to do the join on
	 */
	//*************************************************************************************************
	public GetJoinedTablesRequest(String table , String joinedTable, int keyIndex)
	{
		super(table);
		this .joinedTable = joinedTable;
		this.type = "GetJoinedTablesRequest";
		this.keyIndex = keyIndex;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the GetJoinedTablesRequest joinedTable
	 *  @return the GetJoinedTablesRequest joinedTable 
	 */
	//*************************************************************************************************
	public String getJoinedTable() {
		return joinedTable;
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetJoinedTablesRequest keyIndex
	 *  @return the GetJoinedTablesRequest keyIndex 
	 */
	//*************************************************************************************************
	public int getKeyIndex() {
		return keyIndex;
	}
	
}
