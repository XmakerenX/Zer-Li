package serverAPI;

import java.util.ArrayList;

//*************************************************************************************************
/**
*  a Request for the server to get images data
*  Stores the data for the ImageRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class ImageRequest extends Request {

	private static final long serialVersionUID = 227540254884656007L;
	private ArrayList<String> imageNames;
	
	//*************************************************************************************************
	/**
	 * Creates an ImageRequest with the following parameters
	 * @param imageNames the images names to get form the server
	 */
	//*************************************************************************************************
	public ImageRequest(ArrayList<String> imageNames)
	{
		super("ImageRequest");
		this.imageNames = imageNames;
	}

	//*************************************************************************************************
	/**
	 *  Returns the ImageRequest imageNames
	 *  @return the ImageRequest imageNames 
	 */
	//*************************************************************************************************
	public ArrayList<String> getImageNames() {
		return imageNames;
	}

	//*************************************************************************************************
	/**
	 *  sets the ImageRequest imageNames
	 *  @param imageNames the imageNames to set
	 */
	//*************************************************************************************************
	public void setImageNames(ArrayList<String> imageNames) {
		this.imageNames = imageNames;
	}
	
	
}
