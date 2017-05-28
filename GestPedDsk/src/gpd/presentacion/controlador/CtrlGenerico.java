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
				retornoOk = ctrlNumInt(obj.toString());
			}
			if(obj instanceof Double) {
				retornoOk = ctrlNumDec(obj.toString());
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
	
	
	/**
	* Método que limpia los datos de todos los componentes
	* susceptibles de ser puestos en blanco, como los “JTextField”
	* “jTextAreas”, etc…, que pertenezcan a un “JPanel” o “JScrollPane”,
	* es recursivo, asi que si dentro del panel hay otro panel con
	* más componentes susceptibles también los pondrá en blanco.
	*/
	public void clearForm(Object panel) {
		// Obtenemos el nombre de la clase
		String nombre_clase = panel.getClass().getName();
		if (nombre_clase.equals("javax.swing.JPanel")) {
			// Estamos en el caso de un JPanel
			clearPanel((javax.swing.JPanel) panel);
		} else if (nombre_clase.equals("javax.swing.JScrollPane")) {
			// Estamos en el caso de un JScrollPane
			clearScrollPane((javax.swing.JScrollPane) panel);
		}
	}
	
	/**
	 * metodo recursivo para limpiar JPanel
	 * si existiera panel interno a panel tambien lo limpia
	 * llamando a clearComponent
	 */
	public void clearPanel(javax.swing.JPanel panel) {
	// Obtenemos todos los componentes que cuelgan del panel
		java.awt.Component[] componentes = panel.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			clearComponent(componentes[i]);
		}
	}
	
	/**
	 * metodo recursivo para limpiar componentes JScrollPane
	 * si existiera panel interno a panel tambien lo limpia.
	 * llamando a clearComponent
	 */
	public void clearScrollPane(javax.swing.JScrollPane panel) {
		// Obtenemos todos los componentes que cuelgan del panel
		java.awt.Component[] componentes = panel.getViewport().getComponents();
		for (int i = 0; i < componentes.length; i++) {
			clearComponent(componentes[i]);
		}
	}

	/**
	 * limpieza de componentes recursivo
	 * recibe componente, consulta de que clase es y realiza accion
	 * segun definido por el control (en blanco, clear o indice 0).
	 */
	public void clearComponent(java.awt.Component comp) {
		// Nombre de la clase del componente
		String nombre_clase = comp.getClass().getName();
		if (nombre_clase.equals("javax.swing.JTextField")) {
			// Es un JTextField asi que lo ponemos en blanco
			((javax.swing.JTextField) comp).setText("");
		} else if (nombre_clase.equals("javax.swing.JComboBox")) {
			// Es un JComboBox asi que ponemos el primer elemento
			((javax.swing.JComboBox<?>) comp).setSelectedIndex(0);
		} else if (nombre_clase.equals("javax.swing.JTextArea")) {
			// Es un JTextArea asi que lo ponemos en blanco
			((javax.swing.JTextArea) comp).setText("");
		} else if (nombre_clase.equals("javax.swing.JPanel")) {
			// Es un JPanel asi que llamamos a clearPanel
			clearPanel((javax.swing.JPanel) comp);
		} else if (nombre_clase.equals("javax.swing.JScrollPane")) {
			// Es un JScrollPane asi que llamamos a clearScrollPane
			clearScrollPane((javax.swing.JScrollPane) comp);
		}
	}
}
