package serverAPI;

public class GetJoinedTablesWhereRequest extends GetJoinedTablesRequest {

	String checkColomn;
	String condition;
	
	public GetJoinedTablesWhereRequest(String table , String joinedTable, String checkColomn, String condition)
	{
		super(table, joinedTable);
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
