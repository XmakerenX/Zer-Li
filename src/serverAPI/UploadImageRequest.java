package serverAPI;

import java.io.Serializable;

import utils.ImageData;

public class UploadImageRequest extends Request implements Serializable
{

	/**
	 * 
	 */
	ImageData image;
	private static final long serialVersionUID = 3570987984590962400L;
	
	
		public UploadImageRequest(ImageData im) 
		{
			super("UploadImageRequest");
			this.image = im;
		}
		//------------------------------------------
		public ImageData getImage() 
		{
			return image;
		}
		//------------------------------------------
		
	}