package serverAPI;

import java.io.Serializable;

import utils.ImageData;

//*************************************************************************************************
/**
*  a Request for the server to upload a new image
*  Stores the data for the UploadImageRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class UploadImageRequest extends Request implements Serializable
{

	private static final long serialVersionUID = 3570987984590962400L;
	private ImageData image;

	//*************************************************************************************************
	/**
	 *  Creates an UploadImageRequest with the following parameters
	 *  @param imageData  the image data to uplaod
	 */
	//*************************************************************************************************
	public UploadImageRequest(ImageData imageData) 
	{
		super("UploadImageRequest");
		this.image = imageData;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the UploadImageRequest ImageData
	 *  @returns the UploadImageRequest ImageData 
	 */
	//*************************************************************************************************
	public ImageData getImage() 
	{
		return image;
	}
}