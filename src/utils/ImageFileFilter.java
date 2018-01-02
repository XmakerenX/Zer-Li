package utils;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

	private final String[] imaageFileExtensions = new String[] {"jpg", "png"};
	
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
