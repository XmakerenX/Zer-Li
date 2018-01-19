package serverAPI;

public class GetJoinedTablesWhereRequest extends GetJoinedTablesRequest {

	String checkColomn;
	String condition;
	
	public GetJoinedTablesWhereRequest(String table , String joinedTable, int keyIndex,String checkColomn, String condition)
	{
		super(table, joinedTable, keyIndex);
		this.checkColomn = checkColomn;
		this.condition = condition;
		this.type = "GetJoinedTablesWhereRequest";
	}

	public String getCheckColomn() {
		return checkColomn;
	}

	public String getCondition() {
		return condition;
	}
	
}
