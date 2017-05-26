package gpd.presentacion.controlador;

import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

public abstract class CtrlGenerico {
	
	private static final Logger logger = Logger.getLogger(CtrlGenerico.class);
	
	
	public MaskFormatter mascNumerica(String formatter) {
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter(formatter);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return mascara;
    }
	
	protected Boolean controlDatosObl(Object obj) {
		Boolean retornoOk = true;
		if(obj != null && obj instanceof String) {
			retornoOk = !obj.toString().equals("");
		} else {
			if(obj instanceof Integer) {
				ctrlNumInt(obj.toString());
			}
			if(obj instanceof Double) {
				ctrlNumDec(obj.toString());
			}
		}
		return retornoOk;
	}
	
	protected Boolean controlDatosObl(Object obj1, Object obj2) {
		return controlDatosObl(obj1) && controlDatosObl(obj2);
	}
	
	protected Boolean controlDatosObl(Object obj1, Object obj2, Object obj3) {
		return controlDatosObl(obj1) && controlDatosObl(obj2) && controlDatosObl(obj3);
	}
	
	protected Boolean controlDatosObl(Object obj1, Object obj2, Object obj3, Object obj4) {
		return controlDatosObl(obj1) && controlDatosObl(obj2) && controlDatosObl(obj3) && controlDatosObl(obj4);
	}
	
	protected Boolean controlDatosObl(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5) {
		return controlDatosObl(obj1) && controlDatosObl(obj2) && controlDatosObl(obj3) && controlDatosObl(obj4) && controlDatosObl(obj5);
	}
	
	protected Boolean ctrlNumInt(Object obj) {
		try {
			if(obj != null) {
				Integer.parseInt((String) obj);
				return true;
			}
		} catch (NumberFormatException nfe){
			return false;
		}
		return false;
	}
	
	protected Boolean ctrlNumDec(Object obj) {
		try {
			if(obj != null) {
				Double.parseDouble((String) obj);
				return true;
			}
		} catch (NumberFormatException nfe){
			return false;
		}
		return false;
	}
	
	public void controlInputNum(KeyEvent e) {
		char caracter = e.getKeyChar();
		if(((caracter < '0') || (caracter > '9')) &&
				(caracter != '\b' && caracter != '.')) {
			e.consume();  // ignorar el evento de teclado
		}
	}

}
