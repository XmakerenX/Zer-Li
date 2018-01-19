package serverAPI;

public class GetJoinedTablesRequest extends GetRequest {

	String joinedTable;
	int keyIndex;
	
	public GetJoinedTablesRequest(String table , String joinedTable, int keyIndex)
	{
		super(table);
		this .joinedTable = joinedTable;
		this.type = "GetJoinedTablesRequest";
		this.keyIndex = keyIndex;
	}
	
	public String getJoinedTable() {
		return joinedTable;
	}

	public int getKeyIndex() {
		return keyIndex;
	}
	
}
