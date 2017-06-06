package gpd.presentacion.controlador;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.CompValidador;
import gpd.presentacion.generic.GenCompType;
import gpd.types.Fecha;

public abstract class CtrlGenerico {
	
	private static final Logger logger = Logger.getLogger(CtrlGenerico.class);
	private static CompValidador instanceCv;
	
	public static int CONFIRM_OK = 0;
	public static int CONFIRM_CANCEL = 2;
	
	
	public static CompValidador getCompVal() {
		if(instanceCv == null) {
			logger.info("Se genera nueva instancia de CompValidador en CtrlGenerico ");
			instanceCv = new CompValidador();
		}
		return instanceCv;
	}
	
	/**
	 * metodo que transforma una password sin cifrar a una cifrada con el metodo
	 * MD5. 
	 * @param input
	 * @return string con hash md5 de password
	 */
	protected static String getMD5(String input) {
		 try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(input.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 String hashtext = number.toString(16);
		 
			 while (hashtext.length() < 32) {
			 	hashtext = "0" + hashtext;
		 	}
		 	return hashtext;
	 	}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public MaskFormatter mascNumerica(String formatter) {
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter(formatter);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return mascara;
    }
	
	@SuppressWarnings("unused")
	public void formatoFechaEnTxt(Component comp) {
		JTextField fechaComp = (JTextField) comp;
		String fechaStr = fechaComp.getText();
		if(fechaStr != null && fechaStr.trim().length() == 10) {
			java.text.DateFormat df = java.text.DateFormat.getInstance(); 
			try {
				java.text.DateFormat formatter = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT); 
				formatter.setLenient(false); 
				java.util.Date date = formatter.parse(fechaStr);
			} catch (ParseException ex) { 
				clearComponent(comp);
				JOptionPane.showMessageDialog(null, "La fecha ingresada no es valida.", "Formato Fecha", JOptionPane.WARNING_MESSAGE); 
			}
//		} else if(fechaStr.trim().length()) {
//			ver para otros formatos, por ej con hora	
		}
	}
	
	/**
	 * 
	 * @param fecha
	 * @return Fecha
	 * toma un dato string con formato fecha xx/xx/xxxx y lo transforma a un objeto Fecha
	 */
	protected Fecha convertirFechaDesdeTxt(String fecha) {
		Fecha fechaRet = null;
		if(fecha != null && fecha.trim().length() == 10) {
			String delims = "/";
			String[] tokens = fecha.split(delims);
			if(tokens.length == 3) {
				fechaRet = new Fecha(Integer.valueOf(tokens[2]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[0]));
			}
		}
		return fechaRet;
	}

	
	/**
	 * 
	 * @param obj
	 * @return boolean si valida controles
	 * método generico para validar controles obligatorios, para tipo de datos numericos
	 * deben ser invocados los controles para numericos
	 */
	protected Boolean controlDatosObl(Component comp) {
		String nombreClase = comp.getClass().getName();
		Boolean retornoOk = true;
		if(comp != null && (nombreClase.equals("javax.swing.JTextField"))) {
			JTextField jtf = (javax.swing.JTextField) comp;
			retornoOk = !jtf.getText().equals("");
		} else if(nombreClase.equals("javax.swing.JFormattedTextField")) {
			JFormattedTextField jftf = (javax.swing.JFormattedTextField) comp;
			retornoOk = !jftf.getText().equals("");
		} else if(nombreClase.equals("javax.swing.JPasswordField")) {
			JPasswordField jpf = (javax.swing.JPasswordField) comp;
			retornoOk = jpf.getPassword() != null && 
					jpf.getPassword().length > 0;
		} else if(nombreClase.equals("javax.swing.JTextArea")) {
			JTextArea jta = (javax.swing.JTextArea) comp;
			retornoOk = !jta.getText().equals("");
		} else if(nombreClase.equals("javax.swing.JList")) {
			JList<?> jl = (javax.swing.JList<?>) comp;
			retornoOk = !jl.isSelectionEmpty();
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			JComboBox<?> jcb = (javax.swing.JComboBox<?>) comp;
			retornoOk = jcb.getSelectedItem() != null;
		}
		if(!retornoOk) {
			getCompVal().addBorder(comp);
		}
		return retornoOk;
	}
	
	
	
	protected Boolean controlDatosObl(Component obj1, Component obj2) {
		Boolean c1, c2;
		c1 = controlDatosObl(obj1); 
		c2 = controlDatosObl(obj2); 
		return c1 && c2;
	}
	
	protected Boolean controlDatosObl(Component obj1, Component obj2, Component obj3) {
		Boolean c1, c2, c3;
		c1 = controlDatosObl(obj1); 
		c2 = controlDatosObl(obj2); 
		c3 = controlDatosObl(obj3);
		return c1 && c2 && c3;
	}
	
	protected Boolean controlDatosObl(Component obj1, Component obj2, Component obj3, Component obj4) {
		Boolean c1, c2, c3, c4;
		c1 = controlDatosObl(obj1); 
		c2 = controlDatosObl(obj2); 
		c3 = controlDatosObl(obj3);
		c4 = controlDatosObl(obj4);
		return c1 && c2 && c3 && c4;
	}
	
	protected Boolean controlDatosObl(Component obj1, Component obj2, Component obj3, Component obj4, Component obj5) {
		Boolean c1, c2, c3, c4, c5;
		c1 = controlDatosObl(obj1); 
		c2 = controlDatosObl(obj2); 
		c3 = controlDatosObl(obj3);
		c4 = controlDatosObl(obj4); 
		c5 = controlDatosObl(obj5); 
		return c1 && c2 && c3 && c4 && c5;
	}
	
	protected Boolean controlDatosObl(GenCompType genComp) {
		if(genComp != null && genComp.getHsComp().size() > 0) {
			Boolean[] resultados = new Boolean[genComp.getHsComp().size()];
			for(Integer key : genComp.getHsComp().keySet()) {
				resultados[(key-1)] = controlDatosObl(genComp.getHsComp().get(key));
			}
			int i = 0;
			while(i < resultados.length) {
				if(!resultados[i]) {
					return false;
				}
				i++;
			}
			return true;
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @return boolean si valida numerico
	 * metodo generico para validar tipos nuericos Integer
	 */
	protected Boolean ctrlNumInt(Object obj) {
		try {
			if(obj != null) {
				Integer.parseInt((String) obj);
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param obj
	 * @return boolean si valida numerico
	 * metodo generico para validar tipos numericos Long
	 */
	protected Boolean ctrlNumLong(Object obj) {
		try {
			if(obj != null) {
				Long.parseLong((String) obj);
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param obj
	 * @return boolean si valida numerico
	 * metodo generico para validar tipos numericos Double
	 */
	protected Boolean ctrlNumDec(Object obj) {
		try {
			if(obj != null) {
				Double.parseDouble((String) obj);
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param obj
	 * metodo generico para controlar inputs para tipos numericos
	 */
	public void controlInputNum(KeyEvent e) {
		char caracter = e.getKeyChar();
		if(((caracter < '0') || (caracter > '9')) &&
				(caracter != '\b' && caracter != '.')) {
			e.consume();  // ignorar el evento de teclado
		}
	}
	
	
	/**
	 * @param obj
	* Método que limpia los datos de todos los componentes
	* susceptibles de ser puestos en blanco, como los “JTextField”
	* “jTextAreas”, etc…, que pertenezcan a un “JPanel” o “JScrollPane”,
	* es recursivo, asi que si dentro del panel hay otro panel con
	* más componentes susceptibles también los pondrá en blanco.
	*/
	public void clearForm(Object panel) {
		// Obtenemos el nombre de la clase
		String nombreClase = panel.getClass().getName();
		if (nombreClase.equals("javax.swing.JPanel")) {
			// Estamos en el caso de un JPanel
			clearPanel((javax.swing.JPanel) panel);
		} else if (nombreClase.equals("javax.swing.JScrollPane")) {
			// Estamos en el caso de un JScrollPane
			clearScrollPane((javax.swing.JScrollPane) panel);
		} else if (nombreClase.equals("javax.swing.JTabbedPane")) {
			// Estamos en el caso de un JScrollPane
			clearTabbedPane((javax.swing.JTabbedPane) panel);
		} 
	}
	
	/**
	 * 
	 * @param JPanel
	 * metodo que recibe JPanel y limpia controles del tipo Components
	 * (JTextfield, JTextarea, JComboBox, etc)
	 */
	public void clearControlsInJPanel(Container panel) {
		if(panel.getClass().getName().equals("javax.swing.JPanel")) {
			java.awt.Component[] componentes = panel.getComponents();
			for (int i = 0; i < componentes.length; i++) {
				clearOnlyControls(componentes[i]);
			}
		}
	}
	
	/**
	 * @param JPanel
	 * metodo recursivo para limpiar JPanel
	 * si existiera panel interno a panel tambien lo limpia
	 * llamando a clearComponent
	 */
	public void clearPanel(Container panel) {
	// Obtenemos todos los componentes que cuelgan del panel
		java.awt.Component[] componentes = panel.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			clearComponent(componentes[i]);
		}
	}
	
	/**
	 * @param JScrollPane
	 * metodo recursivo para limpiar componentes JScrollPane
	 * si existiera panel interno a panel tambien lo limpia.
	 * llamando a clearComponent
	 */
	public void clearScrollPane(JScrollPane panel) {
		// Obtenemos todos los componentes que cuelgan del panel
		java.awt.Component[] componentes = panel.getViewport().getComponents();
		for (int i = 0; i < componentes.length; i++) {
			clearComponent(componentes[i]);
		}
	}
	
	/**
	 * @param JScrollPane
	 * metodo recursivo para limpiar componentes JScrollPane
	 * si existiera panel interno a panel tambien lo limpia.
	 * llamando a clearComponent
	 */
	public void clearTabbedPane(JTabbedPane panel) {
		// Obtenemos todos los componentes que cuelgan del panel
		java.awt.Component[] componentes = panel.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			clearComponent(componentes[i]);
		}
	}

	/**
	 * limpia las filas de la tabla
	 * @param tabla
	 */
	public void clearTable(JTable tabla) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		tabla.setEnabled(true);
		int filas = modelo.getRowCount();
		for (int i = 0; i < filas; i++) {
			modelo.removeRow(0);
		}
	}
	
	/**
	 * 
	 * @param table
	 * limpia modelo de tabla (columnas y filas)
	 */
	protected void deleteModelTable(JTable table) {
		DefaultTableModel modelo = (DefaultTableModel) ((javax.swing.JTable) table).getModel();
		modelo.setColumnCount(0);
		modelo.setRowCount(0);
	}
	
	/**
	 * @param Component
	 * limpieza de componentes recursivo
	 * recibe componente, consulta de que clase es y realiza accion
	 * segun definido por el control (en blanco, clear o indice -1, quita bordes).
	 */
	protected void clearComponent(Component comp) {
		// Nombre de la clase del componente
		String nombreClase = comp.getClass().getName();
		if (nombreClase.equals("javax.swing.JTextField")) {
			// Es un JTextField asi que lo ponemos en blanco
			((javax.swing.JTextField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JFormattedTextField")) {
			// Es un JFormattedTextField asi que lo ponemos en blanco
			((javax.swing.JFormattedTextField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			// Es un JComboBox asi que ponemos el primer elemento
			((javax.swing.JComboBox<?>) comp).setSelectedIndex(-1);
		} else if (nombreClase.equals("javax.swing.JTextArea")) {
			// Es un JTextArea asi que lo ponemos en blanco
			((javax.swing.JTextArea) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JCheckBox")) {
			// Es un JCheckBox asi que lo desmarcamos
			((javax.swing.JCheckBox) comp).setSelected(false);
		} else if (nombreClase.equals("javax.swing.JPanel")) {
			// Es un JPanel asi que llamamos a clearPanel
			clearPanel((javax.swing.JPanel) comp);
		} else if (nombreClase.equals("javax.swing.JScrollPane")) {
			// Es un JScrollPane asi que llamamos a clearScrollPane
			clearScrollPane((javax.swing.JScrollPane) comp);
		} else if (nombreClase.equals("javax.swing.JTabbedPane")) {
			// Es un JTabbedPane asi que llamamos a clearTabbedPane
			clearTabbedPane((javax.swing.JTabbedPane) comp);
		} else if (nombreClase.equals("javax.swing.JTable")) {
			// Es un JTable asi que llamamos a deleteModelTable
			deleteModelTable((javax.swing.JTable) comp);
		}
		//se reinician los bordes para advertencias.
		getCompVal().removeBorder(comp);
	}
	
	/**
	 * 
	 * @param Component
	 * metodo para limpiar solamente controles de tipo components, no containers
	 */
	protected void clearOnlyControls(Component comp) {
		String nombreClase = comp.getClass().getName();
		if (nombreClase.equals("javax.swing.JTextField")) {
			// Es un JTextField asi que lo ponemos en blanco
			((javax.swing.JTextField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JFormattedTextField")) {
			// Es un JFormattedTextField asi que lo ponemos en blanco
			((javax.swing.JFormattedTextField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			// Es un JComboBox asi que ponemos el primer elemento
			((javax.swing.JComboBox<?>) comp).setSelectedIndex(-1);
		} else if (nombreClase.equals("javax.swing.JTextArea")) {
			// Es un JTextArea asi que lo ponemos en blanco
			((javax.swing.JTextArea) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JCheckBox")) {
			// Es un JCheckBox asi que lo desmarcamos
			((javax.swing.JCheckBox) comp).setSelected(false);
		} 
		//se reinician los bordes para advertencias.
		getCompVal().removeBorder(comp);
	}
	
	/**
	 * metodo para habilitar/deshabilitar contenedor y sus controles internos
	 * @param cont contenedor a hab/deshab
	 * @param band booleano para hab/deshab
	 */
	public void setContainerEnabled(Container cont, Boolean enabled) {
		 Component[] components = cont.getComponents();
		 cont.setEnabled(enabled);
		 for(int i = 0; i < components.length; i++) {            
			 components[i].setEnabled(enabled);
			 if(components[i] instanceof Container) {
				 setContainerEnabled((Container)components[i], enabled);
			 }
		 }
	}
	
	
	/**
	 * 
	 * @param string cabezal
	 * @param string mensaje
	 * genera un MessageDialog con mensaje de warning por defecto
	 */
	protected void enviarWarning(String cab, String msg) {
		JOptionPane.showMessageDialog(null, msg, cab, JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * 
	 * @param string cabezal
	 * @param string mensaje
	 * genera un MessageDialog con mensaje de error por defecto
	 */
	protected void enviarError(String cab, String msg) {
		JOptionPane.showMessageDialog(null, msg, cab, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * 
	 * @param string cabezal
	 * @param string mensaje
	 * genera un ConfirmDialog con opcion aceptar/cancelar
	 */
	protected int enviarConfirm(String cab, String msg) {
		return JOptionPane.showConfirmDialog(null, msg, cab, JOptionPane.OK_CANCEL_OPTION);
	}
	
	
	/**
	 * genera una fila de informacion de que no se ha podido cargar la tabla
	 * @param tabla
	 */
	public void cargarJTableVacia(JTable tabla) {
		deleteModelTable(tabla);
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Resultado");
        Object [] fila = new Object[1];
        fila[0] = CnstPresGeneric.JTABLE_EMPTY;
        modelo.addRow(fila);
        tabla.setEnabled(false);
    }
	
}
