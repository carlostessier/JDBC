package actividad01;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

/**
 *  @descrition Clase que prueba la conexion a una BD utilizando la clase Driver Manager
 * @author Laura y Carlos
 * @date 1/1/2020
 * @version 1.2
 * @license GPLv3
 */

public class PruebaUtilidades {

	private static final String PROPERTIES_FILE=System.getProperty("user.dir")+"\\resources\\sqlite-properties.xml";

	 public static void main(String[] args) {
		 System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		    Utilidades myConexionDriverManager;
		    Connection myConnection = null;
		    if (args.length == 0) {
		      //System.err.println("Properties file not specified at command line");
		    	try {
					myConexionDriverManager = new Utilidades(PROPERTIES_FILE);
			      } catch (FileNotFoundException e) {
				        System.err.println("No encuentra el fichero " + PROPERTIES_FILE);
				        e.printStackTrace();
				        return;
			      } catch (InvalidPropertiesFormatException e) {
			    	  return;
				} catch (IOException e) {		
					return;
				}
		    		
		    } else {
		      try {
		        System.out.println("Reading properties file " + args[0]);
		        myConexionDriverManager = new Utilidades(args[0]);
		      } catch (Exception e) {
		        System.err.println("Problem reading properties file " + args[0]);
		        e.printStackTrace();
		        return;
		      }
		    }

		    try {
		    	System.out.println("Probando conexión");
		      myConnection = myConexionDriverManager.getConnection();   
		      if (myConnection == null)
					System.out.println("No conectado");
		    } catch (SQLException e) {
		    	e.printStackTrace(System.err);
		    } catch (Exception e) {
		      e.printStackTrace(System.err);
		    } finally {
		      Utilidades.closeConnection(myConnection);
		    }

		  }

}


