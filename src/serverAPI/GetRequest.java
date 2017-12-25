package serverAPI;

public class GetRequest extends Request {

	String table;
	
	public GetRequest(String table)
	{
		super("GetRequest");
		this.table = table;;
	}
	
	public String getTable() {
		return table;
	}
}
