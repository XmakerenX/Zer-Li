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
import serverAPI.AddRequest;
import serverAPI.GetJoinedTablesRequest;
import serverAPI.ImageRequest;
import serverAPI.RemoveRequest;
import utils.ImageData;
import utils.ImageFileFilter;

public class CatalogController 
{
	
	public static void requestCatalogItems(Client client)
	{
		client.handleMessageFromClientUI(new GetJoinedTablesRequest("Product", "CatalogProduct"));
	}
	
	
	/*
	 * input: primary key for CatalogProduct table, client
	 * output: the entry which had that key is removed from the CatalogProduct table
	 */
	public static void removeCatalogProductFromDataBase(long l, int storeID,Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(Long.toString(l));
		keys.add(Integer.toString(storeID));
		client.handleMessageFromClientUI(new RemoveRequest("CatalogProduct", keys));
	}
	
	/*
	 * input: catalogItem object, client
	 * output: catalogItem is added as a new entry in CatalogProducts table in database
	 */
	public static void addCatalogProductToDataBase(CatalogItem catItem,Client client)
	{
		
		client.handleMessageFromClientUI(new AddRequest("CatalogProduct", catItem));
	}
	
	/*
	 * checks if  there are any missing cached images, and does a checksum test to see
	 * if the images found on cache are the same as the ones on the server side
	 */
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
	
	/*
	 * return a list of the images from the server side
	 */
	public static void requestCatalogImages(ArrayList<String> imagesToRequest, Client client)
	{
			client.handleMessageFromClientUI(new ImageRequest(imagesToRequest));
	}
	
	/*
	 * save image to cache folder
	 */
	public static void saveCatalogImages(ArrayList<ImageData> imagesData)
	{
		for (ImageData image : imagesData)
		{
			image.saveToDisk("Cache//");
		}
	}
	/*
	 * create a catalog view for viewing the catalog
	 */
	public static void createCatalogItemsView(ArrayList<CatalogItem> catalogItems)
	{
		final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
				
		for (int i = 0; i < catalogItems.size(); i++)
		{
			itemData.add(new CatalogItemView(catalogItems.get(i), "Cache//"));
		}
	
	}
}
