package gpd.dominio.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

public abstract class Converters {
	
	private static Logger logger = Logger.getLogger(Converters.class);
	private static final int MULT_1 = 1;

	
	public static Float convertirIva(Float iva) {
		Float ivaRet = null;
		try {
			ivaRet = MULT_1 + (iva / 100);
		} catch (NumberFormatException ne) {
			logger.fatal("Error al operar con numeros en método convertirIva: " + ne.getMessage(), ne);
		}
		return ivaRet;
	}
	
	public static Double redondearDosDec(Double numero) {
		BigDecimal nroBd = new BigDecimal(numero);
		try {
			nroBd = nroBd.setScale(2, RoundingMode.HALF_UP);
		} catch (NumberFormatException ne) {
			logger.fatal("Error al operar con numeros en método redondearDosDec: " + ne.getMessage(), ne);
		}
		return nroBd.doubleValue();
	}
}
