package prototype;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Config
{
   Properties configFile;
   public Config(String confName)
   {
	configFile = new java.util.Properties();
	try {

	  configFile.load(getClass().getClassLoader().getResourceAsStream("./config/"+confName));
	}catch(Exception eta)
	{
		System.out.println("could not load config file");
	    eta.printStackTrace();
	}
   }

   public String getProperty(String key)
   {
	String value = this.configFile.getProperty(key);
	return value;
   }
}