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

//*************************************************************************************************
	/**
	* Provides functions that Manages the Catalog 
	*/
//*************************************************************************************************
public class CatalogController 
{
	
	//*************************************************************************************************
		/**
		* Sends a Request to the Server to get the catalog Items
		* @param client the client to use to send the Request
		*/
	//*************************************************************************************************
	public static void requestCatalogItems(Client client)
	{
		client.handleMessageFromClientUI(new GetJoinedTablesRequest("Product", "CatalogProduct", 0));
	}
	
	
	//*************************************************************************************************
	/**
	* Sends a Remove Request to the Server to remove product from CatalogProduct table
	* @param productID the productID to remove 
	* @param storeID the store catalog to remove from
	* @param client the client to use to send the Request 
	*/
	//*************************************************************************************************
	public static void removeCatalogProductFromDataBase(long productID, int storeID,Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(Long.toString(productID));
		keys.add(Integer.toString(storeID));
		client.handleMessageFromClientUI(new RemoveRequest("CatalogProduct", keys));
	}
	
	//*************************************************************************************************
	/**
	* Sends a add Request to the Server to add product to the CatalogProduct table
	* @param catItem the catalogITem to add 
	* @param client the client to use to send the Request 
	*/
	//*************************************************************************************************
	public static void addCatalogProductToDataBase(CatalogItem catItem,Client client)
	{
		client.handleMessageFromClientUI(new AddRequest("CatalogProduct", catItem));
		System.out.println(catItem.getSalePrice());
	}
	
	//*************************************************************************************************
	/**
	* checks if there are any missing cached images, and does a checksum test to see
	* if the images found on cache are the same as the ones on the server side
	* @param catalogItems The items  Collection to check against
	* @return an ArrayList of all the images that are needed but are not in the client cache
	*/
	//*************************************************************************************************
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
	
	//*************************************************************************************************
	/**
	* Sends a Request to the server to send images
	* @param imagesToRequest the image names of the requested images
	* @param client the client to use to send the Request 
	*/
	//*************************************************************************************************
	public static void requestCatalogImages(ArrayList<String> imagesToRequest, Client client)
	{
			client.handleMessageFromClientUI(new ImageRequest(imagesToRequest));
	}
	
	//*************************************************************************************************
	/**
	* Saves the  gotten ImageData to Client Images Directory
	* @param imagesData the images data to save 
	*/
	//*************************************************************************************************
	public static void saveCatalogImages(ArrayList<ImageData> imagesData)
	{
		for (ImageData image : imagesData)
		{
			image.saveToDisk(ImageData.ClientImagesDirectory);
		}
	}
	
	//*************************************************************************************************
	/**
	* create a catalog view for viewing the catalog
	* @param catalogItems CatalogItems to add to the view 
	*/
	//*************************************************************************************************
	public static void createCatalogItemsView(ArrayList<CatalogItem> catalogItems)
	{
		final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
				
		for (int i = 0; i < catalogItems.size(); i++)
		{
			itemData.add(new CatalogItemView(catalogItems.get(i), "Cache//"));
		}
	
	}
}
