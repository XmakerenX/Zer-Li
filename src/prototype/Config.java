package prototype;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
   Properties configFile;
   public Config(String confName)
   {
	   configFile = new java.util.Properties();
	   try {
		   InputStream  configStream = getClass().getClassLoader().getResourceAsStream("./config/"+confName);
		   if (configStream!= null)
			   configFile.load(getClass().getClassLoader().getResourceAsStream("./config/"+confName));
		   else
		   {
			   configFile = null;
			   System.out.println("could not load config file");
		   }
	   }catch(IOException | IllegalArgumentException eta)
	   {
		   System.out.println("could not load config file");
		   eta.printStackTrace();
	   }
   }

   public String getProperty(String key)
   {
	   if (configFile != null)
		   return this.configFile.getProperty(key);
	   else
		   return "";
   }
}