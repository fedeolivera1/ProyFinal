package gpd.db.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PropReader {
	
	private static final Logger logger = Logger.getLogger(PropReader.class.getName());

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
		    logger.log(Level.INFO, "Prop values read: " + hashProp.size() + " for " + nombre);
		} catch (MissingResourceException e) {
		    logger.warning("Key not found in primary property.");
		}
		return hashProp;
	}
	
}