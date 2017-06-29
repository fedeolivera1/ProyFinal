package gpd.dominio.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

public abstract class Converters {
	
	private static Logger logger = Logger.getLogger(Converters.class);
	private static final int MULT_1 = 1;

	
	public static Float convertirPorcAMult(Float porc) {
		Float nroConv = null;
		try {
			nroConv = MULT_1 + (porc / 100);
		} catch (NumberFormatException ne) {
			logger.fatal("Error al operar con numeros en método convertirIva: " + ne.getMessage(), ne);
		}
		return nroConv;
	}
	
//	public static Float convertirPorcSustraccion(Float porc) {
//		Float nroConv = null;
//		try {
//			nroConv = ((100 - porc) / 100);
//		} catch (NumberFormatException ne) {
//			logger.fatal("Error al operar con numeros en método convertirIva: " + ne.getMessage(), ne);
//		}
//		return nroConv;
//	}
//	
	public static Double redondearDosDec(Double numero) {
		BigDecimal nroBd = new BigDecimal(numero);
		try {
			nroBd = nroBd.setScale(2, RoundingMode.HALF_UP);
		} catch (NumberFormatException ne) {
			logger.fatal("Error al operar con numeros en método redondearDosDec: " + ne.getMessage(), ne);
		}
		return nroBd.doubleValue();
	}
	
	public static Double obtenerIvaDePrecio(Double precio, Float iva) {
		Double ivaDePrecio = new Double(0);
		if(iva > 0) {
			Float ivaMult = convertirPorcAMult(iva);
			ivaDePrecio = precio - (precio / ivaMult);
			ivaDePrecio = redondearDosDec(ivaDePrecio);
		}
		return ivaDePrecio;
	}
	
	public static Double obtenerPrecioMasIva(Double precio, Float iva) {
		Double precioConIva = new Double(0);
		if(iva > 0) {
			Float ivaMult = convertirPorcAMult(iva);
			precioConIva = precio * ivaMult;
			precioConIva = redondearDosDec(precioConIva);
		} else {
			precioConIva = precio;
		}
		return precioConIva;
	}
}
