package utils;

import java.io.Serializable;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageData implements Serializable {

	public static final String ClientImagesDirectory = "Cache//";
	public static final String ServerImagesDirectory = "Images//";
	
	
	private String Description = null;
	private String filePath = null;
	private String fileName = null;
	private int size = 0;
	public  byte[] mybytearray;
	private byte[] sha256CheckSum = null;
	
	public ImageData(String filePath) throws IOException
	{
		this.filePath = filePath;
		File imageFile = new File(filePath);
		fileName = imageFile.getName();

		mybytearray  = new byte [(int)imageFile.length()];
		
		try {
			DigestInputStream dis = new DigestInputStream(new FileInputStream(imageFile),  MessageDigest.getInstance("SHA-256"));

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
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public byte[] getSha256() {
		return sha256CheckSum;
	}	
	
	
}
