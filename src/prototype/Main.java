package prototype;
	
import java.io.IOException;
import java.util.ArrayList;
import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	
	//vars:
	final public static int DEFAULT_PORT = 5555;
	private Client client = null;

			
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
	
	
//----------------------------------------------------------------------
	
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
//----------------------------------------------------------------------
	private void openNewClientGui(Stage primaryStage) throws IOException
	{
				System.out.println("pills here!");
				
				FormController.primaryStage = primaryStage;
				MainFormController controller = FormController.<MainFormController, BorderPane>loadFXML(getClass().getResource("MainForm.fxml"), null);
				
				client.setUI(controller);
				controller.initData(client);

				primaryStage.setScene(controller.getScene());
				primaryStage.setTitle("Prototype");


				primaryStage.show();
	}
//----------------------------------------------------------------------	
	// called when program ends
	@Override
	public void stop()
	{
		if (client != null)
			client.quit();
	}
//----------------------------------------------------------------------	
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
