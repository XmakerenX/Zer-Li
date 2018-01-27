package utils;

import java.io.Serializable;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//*************************************************************************************************
/**
* Holds the image data 
* allows to load and save the image from and to the disk
*/
//*************************************************************************************************
public class ImageData implements Serializable {

	public static final String ClientImagesDirectory = "Cache//";
	public static final String ServerImagesDirectory = "Images//";
	
	private static final long serialVersionUID = -1941760645979467925L;
	private String Description = null;
	private String fileName = null;
	private int size = 0;
	public  byte[] mybytearray;
	private byte[] sha256CheckSum = null;
	
	//*************************************************************************************************
	/**
	 * Loads the image data in filePath to the class , and calculates the image checksum
	 *  @param filePath the path to the image to load
	 *  @throws IOException thrown when file fails to load
	 */
	//*************************************************************************************************
	public ImageData(String filePath) throws IOException
	{
		File imageFile = new File(filePath);
		fileName = imageFile.getName();

		mybytearray  = new byte [(int)imageFile.length()];
		
		try {
			DigestInputStream dis = new DigestInputStream(new FileInputStream(imageFile), 
					MessageDigest.getInstance("SHA-256"));

			initArray(mybytearray.length);
			setSize(mybytearray.length);
			dis.read(this.getMybytearray(),0,mybytearray.length);
			dis.close();
			// save image checksum
			sha256CheckSum = dis.getMessageDigest().digest();
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithem sha-256");
			e.printStackTrace();
		}
		
	}
	
	//*************************************************************************************************
	/**
	 * Saves the image Data to the given directory
	 *  @param directory path to the directory to save to
	 */
	//*************************************************************************************************
	public void saveToDisk(String directory)
	{
		try {
			DigestOutputStream out = new DigestOutputStream(new FileOutputStream(directory + getFileName()),
															MessageDigest.getInstance("SHA-256"));
			out.write(getMybytearray());
			out.close();
			// is there really a need to verify checksum on save ???
		} catch (IOException e) {
			System.out.println("Failed to write file to "+System.getProperty("user.dir"));
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("No such algorithem sha-256");
			e.printStackTrace();
		}
	}
	
	//*************************************************************************************************
	/**
	 * inits mybytearray to the given size
	 *  @param size the size to init to
	 */
	//*************************************************************************************************
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the ImageData fileName
	 *  @return the ImageData fileName 
	 */
	//*************************************************************************************************
	public String getFileName() {
		return fileName;
	}

	//*************************************************************************************************
	/**
	 *  sets the ImageData fileName
	 *  @param fileName the ImageData fileName to set 
	 */
	//*************************************************************************************************
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the ImageData size
	 *  @return the ImageData size 
	 */
	//*************************************************************************************************
	public int getSize() {
		return size;
	}

	//*************************************************************************************************
	/**
	 *  sets the ImageData size
	 *  @param size the ImageData size to set 
	 */
	//*************************************************************************************************
	public void setSize(int size) {
		this.size = size;
	}

	//*************************************************************************************************
	/**
	 *  Returns the ImageData mybytearray
	 *  @return the ImageData mybytearray 
	 */
	//*************************************************************************************************
	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the ImageData mybytearray at index i
	 *  @param i the index
	 *  @return the ImageData mybytearray  at index i
	 */
	//*************************************************************************************************
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	//*************************************************************************************************
	/**
	 *  sets the ImageData mybytearray
	 *  @param mybytearray the ImageData mybytearray to set 
	 */
	//*************************************************************************************************
	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	//*************************************************************************************************
	/**
	 *  Returns the ImageData Description
	 *  @return the ImageData Description 
	 */
	//*************************************************************************************************
	public String getDescription() {
		return Description;
	}

	//*************************************************************************************************
	/**
	 *  sets the ImageData description
	 *  @param description the ImageData description to set 
	 */
	//*************************************************************************************************
	public void setDescription(String description) {
		Description = description;
	}

	//*************************************************************************************************
	/**
	 *  Returns the Description sha256CheckSum
	 *  @return the Description sha256CheckSum 
	 */
	//*************************************************************************************************
	public byte[] getSha256() {
		return sha256CheckSum;
	}	
	
	
}
