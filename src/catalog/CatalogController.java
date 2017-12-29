package catalog;

import client.Client;
import serverAPI.GetJoinedTablesRequest;

public class CatalogController {

	public static void requestCatalogItems(Client client)
	{
		client.handleMessageFromClientUI(new GetJoinedTablesRequest("Product", "CatalogProduct"));
	}
}
