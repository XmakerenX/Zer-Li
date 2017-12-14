package Server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import prototype.Config;

public class ServerGUI extends Application{

    @FXML // fx:id="shtDwnBtn"
    private Button shtDwnBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="startBtn"
    private Button startBtn; // Value injected by FXMLLoader

    @FXML
    void exitForm(ActionEvent event) {
    	
    }

    @FXML
    void startServer(ActionEvent event) {
    		ProtoTypeServer2 sv = new ProtoTypeServer2();

    }

    @FXML
    void shutDwnServer(ActionEvent event) {

    }

	
	@Override
	public void start(Stage arg0) throws Exception 
	{
		
	}

}


}
