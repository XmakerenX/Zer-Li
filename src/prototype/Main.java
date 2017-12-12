package prototype;
	
import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
	
	final public static int DEFAULT_PORT = 5555;
	private Client client = null;
	
	// called on program start
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// parse args and init clinet
			parseArgs();
			
			if (client != null)
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
			else
			{
				// Shutdown our app
				primaryStage.close();
				Platform.exit();
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// called when program ends
	@Override
	public void stop()
	{
		if (client != null)
			client.quit();
	}
	
	protected void parseArgs()
	{
		String host = "";
		int port = DEFAULT_PORT;
		// read command line parameters
		List<String> args = getParameters().getRaw();
		
		// set host(Server)
		try {
			host = args.get(0);
		}catch(IndexOutOfBoundsException e)
		{
			host = "localhost";
		}
		
		if (host != "")
		{
			// set port
			try {
				port = Integer.parseInt(args.get(1));
			}catch(IndexOutOfBoundsException | NumberFormatException e)
			{
				port = DEFAULT_PORT;
			}
		}
		
		// init client
		try
		{
			client = new Client(host, port);
		}
		catch(IOException e)
		{
			System.out.println("Failed to connect to "+host+":"+port);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
