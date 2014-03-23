package uk.co.jacobusmeulen.minerunner.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameSettings
{
  public static boolean DEBUG = true;
  private static Properties m_prop;
  
  public static void load()
  {
     // TODO: Read file
     DebugLogger.log("info", "GameSettings", "Loading Settings from File");
     m_prop = new Properties();
     InputStream input = null;

     try {
       input = new FileInputStream("GameSettings.conf");

       // load a properties file
       m_prop.load(input);
       
       if( m_prop.getProperty("debug").equals("true") )
         DEBUG = true;
       else if( m_prop.getProperty("debug").equals("false") )
         DEBUG = false;
       
     }
     catch(IOException io){
       DebugLogger.log("error", "GameSettings", "Could not find configuration file");
       io.printStackTrace();
     }
     finally {
       if( input != null )
       {
	 try {	
	     input.close();
	 } 
	 catch (IOException e) {
	     e.printStackTrace();
	 }
       }	
     }
  }
  
  public static double getDouble(String id)
  {
     DebugLogger.log("info", "GameSettings", "Getting " + id);
     return Double.parseDouble( m_prop.getProperty(id) );
  }
  
  public static int getInt(String id)
  {
     DebugLogger.log("info", "GameSettings", "Getting " + id);
     return Integer.parseInt( m_prop.getProperty(id) );
  }
}
