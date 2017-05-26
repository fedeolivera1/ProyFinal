package gpd.presentacion.controlador;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class CtrlFrmProducto extends CtrlGenerico {

	
	public Integer agregarProducto(JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, JFormattedTextField precio) {
		if(controlDatosObl(codigo.getText(), nombre.getText(), stockMin.getText(), precio.getText())) {
			return 1;
		}
		return null;
	}
}
