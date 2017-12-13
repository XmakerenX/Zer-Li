package prototype;
	
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
		catch(Exception e)
		{
			e.printStackTrace();
			//exit program:
			primaryStage.close();
			Platform.exit();
		}
	}
//*************************************************************************************************	
	
	
//----------------------------------------------------------------------
	
	private void initClient() throws NullPointerException
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
					throw new NullPointerException();
				}
	}
//----------------------------------------------------------------------
	private void openNewClientGui(Stage primaryStage) throws IOException
	{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
				BorderPane root = (BorderPane)loader.load();

				MainFormController controller = loader.<MainFormController>getController();
				// add controller to client
				client.setUI(controller);
				controller.initData(client);

				Scene scene = new Scene(root,273,200);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
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
		String host = "";
		int port = DEFAULT_PORT;
		Config clientConf = new Config("client.properties");


		serverIP =clientConf.getProperty("SERVER_IP");
		serverPort = clientConf.getProperty("CLIENT_PORT");
		
		
		
		if(serverIP.equals("local"))   	   {host = "localhost";}
		if(serverPort.equals("default"))   {port = DEFAULT_PORT;}
		else
		{
			port = Integer.parseInt(serverPort);
		}
		
		ArrayList<Object> output = new ArrayList<Object>();
		output.add(host);
		output.add(port);
		
		return output;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
