package uk.co.jacobusmeulen.minerunner.core;

public class DebugLogger
{
   public static void log(String type, String file, String message)
   {
      //TODO: Log properly
      if(GameSettings.DEBUG)
      System.out.println("["+type.toUpperCase()+"]"
      			 + " In " + file + ", line " + 
		Thread.currentThread().getStackTrace()[2].getLineNumber() 
		+ ": "
	        + message);
   }
}
