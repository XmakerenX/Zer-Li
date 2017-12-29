package serverAPI;

public class GetJoinedTablesRequest extends GetRequest {

	String joinedTable;
	
	public GetJoinedTablesRequest(String table , String joinedTable)
	{
		super(table);
		this .joinedTable = joinedTable;
		this.type = "GetJoinedTablesRequest";
	}
	
	public String getJoinedTable() {
		return joinedTable;
	}
}
