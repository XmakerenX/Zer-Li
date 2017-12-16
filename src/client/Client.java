package client;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.*;

public class Client  extends AbstractClient{
	
	private ClientInterface UI;
	
//*************************************************************************************************
    /**
  	*  Constructs a new Client
  	*  @param host the host ip
  	*  @param port the port with to connect to the server
  	*/
//*************************************************************************************************
	public Client(String host, int port)
	throws IOException
	{
		super(host, port);
		UI = null;
		// open connection to server
		// and handles messages from it in a new thread
		openConnection();
	}

//*************************************************************************************************	
	/**
	* This method handles all data that comes in from the server.
	*
	* @param msg The message from the server.
	*/
//*************************************************************************************************
	public void handleMessageFromServer(Object msg) 
	{
		if (UI != null)
			UI.display(msg);
	}

//*************************************************************************************************
	/**
	* This method handles all data coming from the UI            
	*
	* @param message The message from the UI.    
	*/
//*************************************************************************************************	
	public void handleMessageFromClientUI(ArrayList<String> message)  
	{
		try
		{
			sendToServer(message);
		}
		catch(IOException e)
		{
			System.out.println("Could not send message to server.  Terminating client.");
			quit();
		}
	}	
	
//*************************************************************************************************
    /**
  	*  Sets the UI property
  	*  @param newUI The current UI to forward messages to
  	*/
//*************************************************************************************************
	public void setUI(ClientInterface newUI)
	{
		UI = newUI;
	}

//*************************************************************************************************	
	/**
	 * This method terminates the client.
	 */
//*************************************************************************************************
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
