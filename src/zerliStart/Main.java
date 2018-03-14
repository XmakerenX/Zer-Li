package zerliStart;
	
import java.io.IOException;
import java.util.ArrayList;
import client.Client;
import client.ClientInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import user.*;
import utils.Config;
import utils.FormController;

//*************************************************************************************************
/**
 * Class that start our Application
 */
//*************************************************************************************************
public class Main extends Application 
{
	// the client default port
	final public static int DEFAULT_PORT = 5555;
			
	//*************************************************************************************************
	/**
	 * Called at application start and initialize main GUI and client
	 */
	//*************************************************************************************************	
	@Override
	public void start(Stage primaryStage) //--> this function is called on program start
	{
		try
		{
			openNewClientGui(primaryStage);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			//exit program:
			primaryStage.close();
			Platform.exit();
		}
	}
	
	//*************************************************************************************************
	/**
	 * Initialize Client from config file (client.properties)
	 * and open connection to server
	 * @param controller the controller GUI to replay to
	 * @throws IOException thrown when client fails to connect to server
	 */
	//*************************************************************************************************
	public static void initClient(ClientInterface controller) throws IOException
	{
		ArrayList<Object> args = parseArgs();
		
		String host = (String) args.get(0);
		int port = (int) args.get(1);
		System.out.println("Trying to connect to "+host+":"+port);
				try
				{
					//client = new Client(host, port);
					Client.getInstance().startConnection(host, port);
				}
				catch(IOException e)
				{
					System.out.println("Failed to connect to "+host+":"+port);
					throw e;
				}
				Client.getInstance().setUI(controller);
	}
	
	//*************************************************************************************************
	/**
	 *  Load the main GUI and set as the primaryStage
	 *  @param primaryStage the primary stage for this application, onto which the application scene can
	 *   be set.
	 *   The primary  stage will be embedded in the browser if the application was launched as an applet. 
	 *   Applications may create other stages, if needed, but they will not be primary stages and will
	 *    not be embedded in the browser.
	 */
	//*************************************************************************************************
	private void openNewClientGui(Stage primaryStage) throws IOException
	{				
				FormController.primaryStage = primaryStage;
				LoginGUI controller = FormController.<LoginGUI, AnchorPane>loadFXML(getClass().getResource("/user/UserGUI.fxml"), null);
				
				primaryStage.setScene(controller.getScene());
				primaryStage.setTitle("Zerli");
				

				primaryStage.show();
	}
	
	//*************************************************************************************************
	/**
	 * Called at application end
	 * Terminates client connection to server
	 */
	//*************************************************************************************************
	@Override
	public void stop()
	{
		Client.getInstance().quit();
	}
	
	//*************************************************************************************************
	/**
	 * Parses the client config file
	 * @return an ArrayList holding the host ip and port of the server
	 */
	//*************************************************************************************************	
	protected static ArrayList<Object> parseArgs()
	{
		String serverIP;
		String serverPort;
		int port = DEFAULT_PORT;
		Config clientConf = new Config("client.properties");


		serverIP =clientConf.getProperty("SERVER_IP").trim();
		serverPort = clientConf.getProperty("CLIENT_PORT");
		
		if(serverIP.equals("local") || serverIP.equals("")) 
			serverIP = "localhost";
		
		if(serverPort.equals("default"))
			port = DEFAULT_PORT;
		else
		{
			try
			{
				port = Integer.parseInt(serverPort);
			}
			catch (NumberFormatException e)
			{
				port = DEFAULT_PORT;
			}
		}
		
		ArrayList<Object> output = new ArrayList<Object>();
		output.add(serverIP);
		output.add(port);
		
		return output;
	}
	
	//*************************************************************************************************
	/**
	 * Starts the application 
	 * @param args arguments given from the command line
	 */
	//*************************************************************************************************
	public static void main(String[] args) 
	{
		launch(args);
	}
}
