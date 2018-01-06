package serverAPI;

public class GetRequestWhere extends Request {
	String table;
	String checkColomn;
	String condition;
	/**
	 * 
	 * @param table name of the table we are looking at
	 * @param checkColomn we will search a condition
	 * @param condition
	 */
	public GetRequestWhere(String table, String checkColomn, String condition)
	{
		super("GetRequestWhere");
		this.table = table;
		this.checkColomn = checkColomn;
		this.condition = condition;
	}
	
	public String getTable() {
		return table;
	}
	
	public String getCheckColomn() {
		return checkColomn;
	}

	public String getCondition() {
		return condition;
	}


}
