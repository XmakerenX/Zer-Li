package serverAPI;

import java.util.ArrayList;

public class ImageRequest extends Request {

	private ArrayList<String> imageNames;
	
	public ImageRequest(ArrayList<String> imageNames)
	{
		super("ImageRequest");
		this.imageNames = imageNames;
	}

	public ArrayList<String> getImageNames() {
		return imageNames;
	}

	public void setImageNames(ArrayList<String> imageNames) {
		this.imageNames = imageNames;
	}
	
	
}
