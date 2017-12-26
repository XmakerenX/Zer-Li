package serverAPI;

public class GetRequestByKey extends GetRequest {

	String Key;
	
	public GetRequestByKey(String table, String key)
	{
		super(table);
		this.Key = key;
		this.type = "GetRequestByKey";
	}

	public String getKey() {
		return Key;
	}
	
}