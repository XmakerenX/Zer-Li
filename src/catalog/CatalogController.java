package catalog;

import java.io.File;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.CatalogItem;
import serverAPI.GetJoinedTablesRequest;
import serverAPI.ImageRequest;
import utils.ImageData;
import utils.ImageFileFilter;

public class CatalogController 
{
	
	public static void requestCatalogItems(Client client)
	{
		client.handleMessageFromClientUI(new GetJoinedTablesRequest("Product", "CatalogProduct"));
	}
	
	public static ArrayList<String> scanForMissingCachedImages(AbstractCollection<CatalogItem> catalogItems)
	{
		ArrayList<String> imagesToRequest = new ArrayList<String>();
		//File imageDir = new File ("Cache//");
		File imageDir = new File (ImageData.ClientImagesDirectory);
	    File[] files = imageDir.listFiles(new ImageFileFilter());
	    for (CatalogItem item : catalogItems)
	    {
	    	boolean found = false;

	    	if (!item.getImageName().equals(""))
	    	{
	    		// scan for the catalogItem image in the Client Cache folder
	    		for (File f : files)
	    		{
	    			if (item.getImageName().equals(f.getName()))
	    			{
	    				ImageData image = null;
	    				// try to read the image					
	    				try {
	    					image = new ImageData(f.getPath());
	    				} catch (IOException e) {
	    					System.out.println("CatalogController-scanForMissingCachedImages: ");
	    					System.out.println("Failed to read "+ f.getPath());
	    					image = null;
	    					e.printStackTrace();
	    				}

	    				if (image != null)
	    				{
	    					// check that we have the same image as the server
	    					if (Arrays.equals(image.getSha256(), item.getImageChecksum()))
	    					{
	    						found = true;
	    						break;
	    					}
	    				}
	    			}
	    		}

	    		if (!found)
	    			imagesToRequest.add(item.getImageName());
	    	}
	    }
	    
	    return imagesToRequest;
	}
	
	public static void requestCatalogImages(ArrayList<String> imagesToRequest, Client client)
	{
			client.handleMessageFromClientUI(new ImageRequest(imagesToRequest));
	}
	
	public static void saveCatalogImages(ArrayList<ImageData> imagesData)
	{
		for (ImageData image : imagesData)
		{
			image.saveToDisk("Cache//");
		}
	}
	
	public static void createCatalogItemsView(ArrayList<CatalogItem> catalogItems)
	{
		final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
				
		for (int i = 0; i < catalogItems.size(); i++)
		{
			itemData.add(new CatalogItemView(catalogItems.get(i), "Cache//"));
		}
	
	}
}
