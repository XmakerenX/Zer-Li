package networkGUI;

import catalog.ManageCatalogGUI;
import client.Client;
import client.ClientInterface;
import survey.ResultInputGUI;
import user.LoginGUI;
import user.User;
import user.UserController;
import utils.FormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

//*************************************************************************************************
	/**
	*  Provides a GUI to handle store worker actions : input surveys' results, manage catalog
	*/
//*************************************************************************************************

public class StoreWorkerGUI extends FormController implements ClientInterface{
	
	//Current user's name
	private User user;
	private int storeID;
	FormController thisParent;
	
	public FormController getThisParent() 
	{
		return thisParent;
	}
	
	public void setFormParent(FormController thisParent) 
	{
		this.thisParent = thisParent;
	}
	public int getStoreID() 
	{
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	private ResultInputGUI resultInputGUI;
	private ManageCatalogGUI manCatGui;

	
    @FXML
    private Button manageCatalogButton;

    @FXML
    private Button inputSurveyAnalysis;
    
    @FXML // fx:id="inputSurveyBtn"
    private Button inputSurveyBtn;

    @FXML // fx:id="logOutBtn"
    private Button logOutBtn;
  //===============================================================================================================
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}
	//===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	//addSurveyAnalysisToExistingSurveyGUI = FormController.<AddSurveyAnalysisToExistingSurveyGUI, AnchorPane>loadFXML(getClass().getResource("/survey/AddSurveyAnalysisToExistingSurveyGUI.fxml"), this);
    	resultInputGUI = FormController.<ResultInputGUI, AnchorPane>loadFXML(getClass().getResource("/survey/ResultInputGUI.fxml"), this);
        manCatGui = FormController.<ManageCatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/ManageCatalogGUI.fxml"), this);
    }
  //===============================================================================================================
    /**
     * Displays menu which allows to enter surveys' results
     * @param event - "Input survey results" button is clicked
     */
    @FXML
    void onInputSurvey(ActionEvent event) 
    {
    	if (resultInputGUI != null)
		{
    		Client.getInstance().setUI(resultInputGUI);
			resultInputGUI.initComboBox();
			resultInputGUI.setUser(user);
			resultInputGUI.setParent(this);
			FormController.primaryStage.setScene(resultInputGUI.getScene());
		}
    }
    //===============================================================================================================
    /**
     * Displays menu which allows to manage catalog
     * @param event - "Manage catalog" button is clicked
     */
    @FXML
    void onManageCatalog(ActionEvent event) 
    {
    	if (manCatGui != null)
		{
	    	ManageCatalogGUI manCatGui = FormController.<ManageCatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/ManageCatalogGUI.fxml"), this);
	    	Client.getInstance().setUI(manCatGui);
	    	manCatGui.setEmployeeStoreID(storeID);
	    	manCatGui.doInit(user);  
	    	manCatGui.setParent(this);
	    	FormController.primaryStage.setScene(manCatGui.getScene());
		}
    }
    
  //===============================================================================================================
    /**
     * Logs the user out of the system
     * @param event - "Log out" button is clicked
     */
    @FXML
    void onLogOut(ActionEvent event) 
    {
    	UserController.requestLogout(user, Client.getInstance());
    	LoginGUI loginGUi = (LoginGUI)thisParent;
    	Client.getInstance().setUI(loginGUi);
    	FormController.primaryStage.setScene(thisParent.getScene());

    }
    //===============================================================================================================
	public void setUser(User user)
	{
		this.user = user;
	}

}
