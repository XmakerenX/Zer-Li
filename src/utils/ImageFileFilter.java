package utils;

import java.io.File;
import java.io.FileFilter;

//*************************************************************************************************
/**
 * Filter files to only show .jpg and .png files
*/
//*************************************************************************************************
public class ImageFileFilter implements FileFilter {

	private final String[] imaageFileExtensions = new String[] {"jpg", "png"};
	
	//*************************************************************************************************
	/**
	 * Handle what files to accept
	 * @param file the file to check if to filter
	 * @see java.io.FileFilter
	*/
	//*************************************************************************************************
	@Override
	public boolean accept(File file) {
	    for (String extension : imaageFileExtensions)
	    {
	      if (file.getName().toLowerCase().endsWith(extension))
	      {
	        return true;
	      }
	    }
	    return false;
	}

}
