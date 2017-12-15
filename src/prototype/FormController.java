package prototype;

import java.util.ArrayList;

import client.ClientInterface;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class FormController {

	protected static Stage primaryStage;
	protected Scene thisScene;
	protected FormController parent;
	protected ArrayList<FormController> children = new ArrayList<FormController>();
	
	public void switchToParent()
	{
		primaryStage.setScene(parent.getScene());
	}
	
	public void switchToChild(int childIndex)
	{
		primaryStage.setScene(children.get(childIndex).getScene());
	}
	
	public void addChild(FormController child)
	{
		children.add(child);
	}
	
	public static void setPrimaryStage(Stage newPrimaryStage)
	{
		primaryStage = newPrimaryStage;
	}
	
	public void setScene(Scene scene)
	{
		thisScene = scene;
	}
	
	public Scene getScene()
	{
		return thisScene;
	}
	
	public void setParent(FormController parent)
	{
		this.parent = parent;
	}
}
