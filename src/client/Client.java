package client;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.*;
import serverAPI.Request;

//*************************************************************************************************
/**
*  The Client class contains all the methods needed to connect to server and handle messages 
*  to and from it
*  @see ocsf.client.AbstractClient     
*/
//*************************************************************************************************
public class Client extends AbstractClient{
	
	private ClientInterface UI;
	private static Client client = new Client();
	
	public static Client getInstance()
	{
		return client;
	}
	
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
  	*  @throws IOException thrown if client fails to connect to server
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
  	*  @throws IOException if client failed to connect to server
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
	* This method handles all the requests that are needed to be send to the server         
	*
	* @param request the request to send to server   
	*/
	//*************************************************************************************************	
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
    /**
  	*  Gets the current UI
  	*  @return the current UI
  	*/
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
	
	//*************************************************************************************************	
	/**
	 * This method closes the client connection.
	 */
	//*************************************************************************************************
	protected void connectionClosed() {
		System.out.println("connection closed");
	}
}
