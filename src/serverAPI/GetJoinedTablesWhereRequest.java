package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to get table data joined with another table with a condition
*  Stores the data for the GetJoinedTablesWhereRequest that will be sent to the server from the 
*  client 
*/
//*************************************************************************************************
public class GetJoinedTablesWhereRequest extends GetJoinedTablesRequest {

	private static final long serialVersionUID = 317399279045533618L;
	private String checkColumn;
	private String condition;
	
	//*************************************************************************************************
	/**
	 *  Creates an GetJoinedTablesWhereRequest with the following parameters
	 *  @param table the table to get data from
	 *  @param joinedTable the table to join with when getting the data
	 *  @param keyIndex the index of the primary key to do the join on
	 *  @param checkColomn the Column name to do the condition on
	 *  @param condition the value for the column
	 */
	//*************************************************************************************************
	public GetJoinedTablesWhereRequest(String table , String joinedTable, int keyIndex,String checkColumn, String condition)
	{
		super(table, joinedTable, keyIndex);
		this.checkColumn = checkColumn;
		this.condition = condition;
		this.type = "GetJoinedTablesWhereRequest";
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetJoinedTablesWhereRequest checkColumn
	 *  @returns the GetJoinedTablesWhereRequest checkColumn 
	 */
	//*************************************************************************************************
	public String getCheckColumn() {
		return checkColumn;
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetJoinedTablesWhereRequest condition
	 *  @returns the GetJoinedTablesWhereRequest condition 
	 */
	//*************************************************************************************************
	public String getCondition() {
		return condition;
	}
	
}
