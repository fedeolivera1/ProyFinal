package gpd.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public abstract class PropReader {
	
	private static final Logger logger = Logger.getLogger(PropReader.class);

	public static Hashtable<String, String> readProp(String nombre) {
		ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(nombre);
		Hashtable<String, String> hashProp = new Hashtable<>();
		try {
		    Enumeration<String> keyList = RESOURCE_BUNDLE.getKeys();
		    String name = null;
		    while (keyList.hasMoreElements()) {
		        name = keyList.nextElement();
		        hashProp.put(name, RESOURCE_BUNDLE.getString(name));
		    }
		    logger.info("Prop values leido: " + hashProp.size() + " para " + nombre);
		} catch (MissingResourceException e) {
		    logger.fatal("Clave no encontrada en arhivo de properties.");
		}
		return hashProp;
	}
	
}