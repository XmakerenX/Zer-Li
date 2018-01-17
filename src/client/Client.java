package client;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.*;
import serverAPI.Request;

public class Client  extends AbstractClient{
	
	private ClientInterface UI;
	public static Client client = new Client();
	
//*************************************************************************************************
    /**
  	*  Constructs a new client
  	*/
//*************************************************************************************************
	public Client()
	{
		super("localhost", 5555);
		UI = null;
	}
	
//*************************************************************************************************
    /**
  	*  Constructs a new client
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
  	*  Open a connection to server
  	*  @param host the host ip
  	*  @param port the port with to connect to the server
  	*/
//*************************************************************************************************
	public void startConnection(String host, int port) throws IOException
	{
		this.setHost(host);
		this.setPort(port);
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
	//public void handleMessageFromClientUI(ArrayList<String> message)
	public void handleMessageFromClientUI(Request request)
	{
		try
		{
			System.out.println(request.getType());
			sendToServer(request);
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
	public ClientInterface getUI()
	{
		return this.UI;
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
			System.out.println("Closing clinet");
			closeConnection();
		}
		catch(IOException e) 
		{
			System.out.println("Failed to close client connection");
			e.printStackTrace();
			System.exit(0);	
		}
	}
	
	protected void connectionClosed() {
		System.out.println("connection closed");
	}
}
