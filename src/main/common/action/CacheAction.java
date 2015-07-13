package main.common.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class CacheAction {

	public static Properties props =new Properties();
	
	static{
		try {
			loadProperties(CacheAction.class.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " "));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void loadProperties(String path) throws FileNotFoundException, IOException{
		props.load(new FileInputStream(path+ "/resource/Log4j.properties"));
		PropertyConfigurator.configure(props);
		System.out.println("loadProperties success!");
	}
	
}
