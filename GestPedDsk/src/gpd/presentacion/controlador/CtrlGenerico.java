package gpd.presentacion.controlador;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
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
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.exceptions.NoInetConnectionException;
import gpd.exceptions.PresentacionException;
import gpd.presentacion.generic.CnstPresExceptions;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.CompValidador;
import gpd.presentacion.generic.GenCompType;
import gpd.types.Fecha;

public abstract class CtrlGenerico {
	
	private static final Logger logger = Logger.getLogger(CtrlGenerico.class);
	private static CompValidador instanceCv;
	
	public static final String CERO = "0";
	public static String STR_VACIO = "";
	public static int CONFIRM_OK = 0;
	public static int CONFIRM_CANCEL = 2;
	public static int LENGTH_DDMMAAAA = 10;
	public static int LENGTH_HHMM = 5;
	public static String SDF_HHMM = "HH:mm";
	
	
	public static CompValidador getCompVal() {
		if(instanceCv == null) {
			logger.info("Se genera nueva instancia de CompValidador en CtrlGenerico ");
			instanceCv = new CompValidador();
		}
		return instanceCv;
	}
	
	/**
	 * metodo para manejar y mostrar excepciones en presentacion, verifica
	 * instancia de la ecxepcion para mostrar determinado msj.
	 * @param Exception e
	 */
	protected void manejarExcepcion(Exception e) {
		if(e instanceof PresentacionException) {
			logger.error("Excepcion lanzada desde Controladores: " + e.getMessage(), e);
			enviarError(CnstPresExceptions.DB, e.getMessage());
		} else if(e instanceof NoInetConnectionException) {
			logger.error("Excepcion lanzada desde Controladores: " + e.getMessage(), e);
			enviarError(CnstPresExceptions.DB, e.getMessage());
		} else {
			logger.error("Excepcion generica lanzada desde Controladores: " + e.getMessage(), e);
			enviarError(CnstPresExceptions.GEN, CnstPresExceptions.ENC + e.getMessage());
		}
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
	
	/**
	 * 
	 * @param formatter
	 * @return
	 */
	public MaskFormatter mascNumerica(String formatter) {
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter(formatter);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return mascara;
    }
	
	/**
	 * 
	 * @param dato
	 * @param mascara
	 * @return
	 */
	public String removerMascara(String dato, String mascara) {
		try {
			if(dato != null && !dato.isEmpty() && dato.contains(mascara)) {
				dato = dato.replaceAll(mascara, STR_VACIO);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dato;
	}
	
	/**
	 * recibe una fecha en formato texto, y parsea con dateformat
	 * @param comp
	 */
	@SuppressWarnings("unused")
	public void formatoFechaEnTxt(Component comp) {
		JTextField fechaComp = (JTextField) comp;
		String datoStr = fechaComp.getText();
		try {
			if(datoStr != null && datoStr.trim().length() == LENGTH_DDMMAAAA) {
				java.text.DateFormat df = java.text.DateFormat.getInstance(); 
				java.text.DateFormat formatter = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT); 
				formatter.setLenient(false); 
				java.util.Date date = formatter.parse(datoStr);
			}
		} catch (ParseException ex) {
			clearComponent(comp);
			JOptionPane.showMessageDialog(null, "La fecha ingresada no es valida.", "Formato Fecha", JOptionPane.WARNING_MESSAGE);
			comp.requestFocus();
		}
	}
	
	/**
	 * 
	 * @param fecha en texto a convertir
	 * @return Fecha
	 * toma un dato string con formato fecha xx/xx/xxxx y lo transforma a un objeto Fecha
	 */
	protected Fecha convertirFechaDesdeTxt(String fecha) {
		Fecha fechaRet = null;
		if(fecha != null && fecha.trim().length() == LENGTH_DDMMAAAA) {
			String delims = "/";
			String[] tokens = fecha.split(delims);
			if(tokens.length == 3) {
				fechaRet = new Fecha(Integer.valueOf(tokens[2]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[0]));
			}
		}
		return fechaRet;
	}
	
	/**
	 * recibe una hora en formato texto, y parsea con simpledateformat
	 * @param comp
	 */
	@SuppressWarnings("deprecation")
	public void formatoHoraEnTxt(Component comp) {
		JTextField fechaComp = (JTextField) comp;
		String horaStr = fechaComp.getText();
		if(horaStr != null && horaStr.trim().length() == LENGTH_HHMM) {
			try {
				SimpleDateFormat sdfHora = new SimpleDateFormat(SDF_HHMM);
				sdfHora.setLenient(false);
				Date hora = sdfHora.parse(horaStr);
				if(hora.getMinutes() != 0 && hora.getMinutes() != 30) {
					clearComponent(comp);
					enviarWarning("Formato Hora", "El rango de hora debe ser cada media hora.");
				}
			} catch (ParseException ex) { 
				clearComponent(comp);
				enviarWarning("Formato Hora", "La hora ingresada no es valida.");
			}
		}
	}
	
	/**
	 * 
	 * @param hora en texto a convertir
	 * @return Fecha
	 * toma un dato string con formato hora HH:mm y lo transforma a un objeto Fecha
	 */
	protected Fecha convertirHoraDesdeTxt(String hora) {
		Fecha fechaRet = null;
		if(hora != null && hora.trim().length() == LENGTH_HHMM) {
			String delims = ":";
			String[] tokens = hora.split(delims);
			if(tokens.length == 3) {
				fechaRet = new Fecha(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
				fechaRet.setFormato(Fecha.HM);
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
			retornoOk = !jtf.getText().isEmpty();
		} else if(nombreClase.equals("javax.swing.JFormattedTextField")) {
			JFormattedTextField jftf = (javax.swing.JFormattedTextField) comp;
			retornoOk = !jftf.getText().isEmpty();
		} else if(nombreClase.equals("javax.swing.JPasswordField")) {
			JPasswordField jpf = (javax.swing.JPasswordField) comp;
			retornoOk = jpf.getPassword() != null && 
					jpf.getPassword().length > 0;
		} else if(nombreClase.equals("javax.swing.JTextArea")) {
			JTextArea jta = (javax.swing.JTextArea) comp;
			retornoOk = !jta.getText().isEmpty();
		} else if(nombreClase.equals("javax.swing.JList")) {
			JList<?> jl = (javax.swing.JList<?>) comp;
			retornoOk = !jl.isSelectionEmpty();
		} else if(nombreClase.equals("javax.swing.JTable")) {
			JTable jt = (javax.swing.JTable) comp;
			retornoOk = jt.getSelectedRow() > -1;
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			JComboBox<?> jcb = (javax.swing.JComboBox<?>) comp;
			retornoOk = jcb.getSelectedItem() != null;
		} else if (nombreClase.equals("com.toedter.calendar.JDateChooser")) {
			JDateChooser jdc = (com.toedter.calendar.JDateChooser) comp;
			retornoOk = jdc.getDate() != null && !jdc.getDate().toString().isEmpty();
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
	
	protected Boolean controlDatosObl(GenCompType genComp) throws Exception {
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
			throw new Exception("GenCompType ha sido mal implementado!");
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
	 * 
	 * @param obj
	 * metodo generico para controlar inputs para tipos numericos
	 */
	public Boolean controlFechas(Fecha fini, Fecha ffin) {
		if(fini.compareTo(ffin) > 0) {
			enviarWarning("Control fechas", "La fecha de inicio no puede ser posterior a la fecha de fin.");
			return false;
		}
		return true;
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
	 * limpia las filas de la lista
	 * @param tabla
	 */
	public void clearList(JList<?> lista) {
		DefaultListModel<?> modelo = (DefaultListModel<?>) lista.getModel();
		modelo.removeAllElements();
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
			clearTable((javax.swing.JTable) comp);
		} else if (nombreClase.equals("javax.swing.JFormattedTextField")) {
			// Es un JFormattedTextField asi que lo ponemos en blanco
			((javax.swing.JFormattedTextField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JList")) {
			// Es un JFormattedTextField asi que lo ponemos en blanco
			clearList(((javax.swing.JList<?>) comp));
		} else if (nombreClase.equals("com.toedter.calendar.JDateChooser")) {
			//JDateChooser pruebo metodo cleanup
			((com.toedter.calendar.JDateChooser) comp).setCalendar(new GregorianCalendar());
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
		} else if (nombreClase.equals("javax.swing.JPasswordField")) {
			// Es un JFormattedTextField asi que lo ponemos en blanco
			((javax.swing.JPasswordField) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			// Es un JComboBox asi que ponemos el primer elemento
			((javax.swing.JComboBox<?>) comp).setSelectedIndex(-1);
		} else if (nombreClase.equals("javax.swing.JTextArea")) {
			// Es un JTextArea asi que lo ponemos en blanco
			((javax.swing.JTextArea) comp).setText("");
		} else if (nombreClase.equals("javax.swing.JCheckBox")) {
			// Es un JCheckBox asi que lo desmarcamos
			((javax.swing.JCheckBox) comp).setSelected(false);
		} else if (nombreClase.equals("com.toedter.calendar.JDateChooser")) {
			//JDateChooser pruebo metodo cleanup
			((com.toedter.calendar.JDateChooser) comp).setCalendar(null);
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
	 * 
	 * @param string cabezal
	 * @param string mensaje
	 * genera un MessageDialog con mensaje de warning por defecto
	 */
	protected void enviarInfo(String cab, String msg) {
		JOptionPane.showMessageDialog(null, msg, cab, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	/**
	 * genera una fila de informacion de que no se ha podido cargar la tabla
	 * @param tabla
	 * @param [opcional] mensaje a mostrar cuando se carga vacia, por def carga predefinido
	 */
	public void cargarJTableVacia(JTable tabla, String displayMsg) {
		deleteModelTable(tabla);
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("");
        Object [] fila = new Object[1];
        fila[0] = displayMsg != null ? displayMsg : CnstPresGeneric.JTABLE_EMPTY;
        modelo.addRow(fila);
        tabla.setModel(modelo);
        tabla.setEnabled(false);
    }
	
	//FIXME revisar esto (packColumn)
	public static void packColumn(JTable table, int colIndex, int margin) { 
//	    TableModel model = table.getModel(); 
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel(); 
        TableColumn col = colModel.getColumn(colIndex); 
        int width = 0; 
        // Get width of column header 
        TableCellRenderer renderer = col.getHeaderRenderer(); 
        if (renderer == null) { 
            renderer = table.getTableHeader().getDefaultRenderer(); 
        } 
        Component comp = renderer.getTableCellRendererComponent( 
                table, col.getHeaderValue(), false, false, 0, 0); 
        width = comp.getPreferredSize().width; 
        // Get maximum width of column data 
        for (int r=0; r<table.getRowCount(); r++) { 
            renderer = table.getCellRenderer(r, colIndex); 
            comp = renderer.getTableCellRendererComponent( 
                    table, table.getValueAt(r, colIndex), false, false, r, colIndex); 
            width = Math.max(width, comp.getPreferredSize().width); 
        } 
        // Add margin 
        width += 2 * margin; 
        // Set the width 
        col.setPreferredWidth(width); 
    }   
	
}
