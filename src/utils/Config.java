package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//*************************************************************************************************
/**
* Represent the config file , help with loading the config from the config files
*/
//*************************************************************************************************
public class Config
{
   public Properties configFile;
   
   //*************************************************************************************************
   /**
    *  Constructs a new Config
    *  @param confName path to the config file
    */
   //*************************************************************************************************
   public Config(String confName)
   {
	   configFile = new java.util.Properties();
	   try {
		   // verify the file exists
		   File f = new File(confName);
		   if (f.isFile())
		   {
			   InputStream  configStream = new FileInputStream(confName);
			   configFile.load(configStream);
			   configStream.close();
		   }
		   else
		   {
			   System.out.println("could not load config file");   
			   configFile = null;
		   }

	   }catch(IOException | IllegalArgumentException eta)
	   {
		   System.out.println("could not load config file");
		   configFile = null;
		   eta.printStackTrace();
	   }
   }

   //*************************************************************************************************
   /**
    * @param key the property key
    * @return the value connected to that property key
    * @see Properties
    */
   //*************************************************************************************************
   public String getProperty(String key)
   {
	   if (configFile != null)
		   return this.configFile.getProperty(key);
	   else
		   return "";
   }
}