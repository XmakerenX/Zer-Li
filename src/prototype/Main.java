package prototype;
	
import client.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Client client;
	
	// called on program start
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowProduct.fxml"));
			BorderPane root = (BorderPane)loader.load();
			
			ShowProductController controller = loader.<ShowProductController>getController();
			client = new Client("localhost", 5555, controller );
			controller.initData(client);
			
			Scene scene = new Scene(root,525,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// called when program ends
	@Override
	public void stop()
	{
		client.quit();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
