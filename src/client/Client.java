package client;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.*;

public class Client  extends AbstractClient{
	
	private ClientInterface UI;
	
	public Client(String host, int port, ClientInterface UI)
	throws IOException
	{
		super(host, port);
		this.UI = UI;
		// open connection to server
		// and handles messages from it in a new thread
		openConnection();
	}

	/**
	This method handles all data that comes in from the server.
	*
	@param msg The message from the server.
	*/
	public void handleMessageFromServer(Object msg) 
	{
		UI.display(msg.toString());
	}
	/**
	* This method handles all data coming from the UI            
	*
	* @param message The message from the UI.    
	*/
	public void handleMessageFromClientUI(ArrayList<String> message)  
	{
		try
		{
			sendToServer(message);
		}
		catch(IOException e)
		{
//			UI.display
//			("Could not send message to server.  Terminating client.");
			System.out.println("Could not send message to server.  Terminating client.");
			quit();
		}
	}	
	
	  /**
	  * This method terminates the client.
	  */
	  public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }
}
