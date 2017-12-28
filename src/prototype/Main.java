package prototype;
	
import java.io.IOException;
import java.util.ArrayList;
import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import user.*;
import utils.Config;

public class Main extends Application 
{
	
	//vars:
	final public static int DEFAULT_PORT = 5555;
	private Client client = null;

			
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
			initClient();
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
	*/
//*************************************************************************************************
	private void initClient()
	{
		ArrayList<Object> args = parseArgs();
		
		String host = (String) args.get(0);
		int port = (int) args.get(1);
		
				try
				{
					client = new Client(host, port);
				}
				catch(IOException e)
				{
					System.out.println("Failed to connect to "+host+":"+port);
					System.exit(0);
				}
	}
	
//*************************************************************************************************
	/**
	*  Load the main GUI and set as the primaryStage
	*  @param primaryStage the primary stage for this application, onto which the application scene can be set. The primary 
	 stage will be embedded in the browser if the application was launched as an applet. Applications may create other 
	 stages, if needed, but they will not be primary stages and will not be embedded in the browser.
	*/
//*************************************************************************************************
	private void openNewClientGui(Stage primaryStage) throws IOException
	{
				System.out.println("pills here!");
				
				FormController.primaryStage = primaryStage;
				//MainFormController controller = FormController.<MainFormController, BorderPane>loadFXML(getClass().getResource("MainForm.fxml"), null);
				LoginGUI controller = FormController.<LoginGUI, AnchorPane>loadFXML(getClass().getResource("../user/UserGUI.fxml"), null);
				//ShowProductController controller = FormController.<ShowProductController, BorderPane>loadFXML(getClass().getResource("ShowProduct.fxml"), null);
				
				
				client.setUI(controller);
				controller.setClinet(client);
				//controller.initData(client);

				primaryStage.setScene(controller.getScene());
				primaryStage.setTitle("Prototype");


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
		if (client != null)
			client.quit();
	}
	
//*************************************************************************************************
	/**
	* Parses the client config file
	* @return an ArrayList holding the host ip and port of the server
	*/
//*************************************************************************************************	
	protected ArrayList<Object> parseArgs()
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
	*/
//*************************************************************************************************
	public static void main(String[] args) 
	{
		launch(args);
	}
}
