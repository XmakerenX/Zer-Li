package serverAPI;

import java.util.ArrayList;

public class GetRequestByKey extends GetRequest {

	ArrayList<String> Keys;
	
	public GetRequestByKey(String table, ArrayList<String> keys)
	{
		super(table);
		this.Keys = keys;
		this.type = "GetRequestByKey";
	}

	public ArrayList<String> getKey() {
		return Keys;
	}
	
}