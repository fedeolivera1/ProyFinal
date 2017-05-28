package gpd.presentacion.controlador;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gpd.dominio.producto.TipoProd;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmProducto;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.popup.IfrmTipoProd;

public class CtrlFrmProducto extends CtrlGenerico {

	private ManagerProducto mgrProd = new ManagerProducto();
	private FrmProducto frm;
	private JDesktopPane deskPane;
	
	public CtrlFrmProducto(FrmProducto frm) {
		super();
		this.setFrm(frm);
	}
	
	
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		cbxTipoProd.removeAllItems();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		for(TipoProd tipoProd : listaTipoProd) {
			cbxTipoProd.addItem(tipoProd);
		}
	}
	
	public void cargarListTipoProd(DefaultListModel<TipoProd> dmTipoProd) {
		dmTipoProd.clear();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		for(TipoProd tipoProd : listaTipoProd) {
			dmTipoProd.addElement(tipoProd);
		}
	}
	
	public Integer agregarProducto(JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, JFormattedTextField precio) {
		if(controlDatosObl(codigo.getText(), nombre.getText(), stockMin.getText(), precio.getText())) {
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ING_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		}
		return null;
	}
	
	public Integer modificarProducto(JTextField id, JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, JFormattedTextField precio) {
		if(controlDatosObl(id.getText(), codigo.getText(), nombre.getText(), stockMin.getText(), precio.getText())) {
			return 1;
		}
		return null;
	}
	
	public Integer eliminarProducto(JTextField id) {
		if(controlDatosObl(id.getText())) {
			return 1;
		}
		return null;
	}
	
	public Integer agregarTipoProd(JTextField descripcion, DefaultListModel<TipoProd> dlm) {
		if(controlDatosObl(descripcion.getText())) {
			TipoProd tipoProd = new TipoProd();
			tipoProd.setDescripcion(descripcion.getText());
			mgrProd.guardarTipoProd(tipoProd);
			cargarListTipoProd(dlm);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ING_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			//FIXME: aca ver de pasar mensajes de error como excepciones
			JOptionPane.showMessageDialog(null, "ERROR", CnstPresGeneric.TP, JOptionPane.ERROR);
		}
		return null;
	}
	
	public Integer modificarTipoProd(TipoProd tipoProd, DefaultListModel<TipoProd> dlm) {
		if(controlDatosObl(tipoProd.getDescripcion())) {
			mgrProd.modificarTipoProd(tipoProd);
			cargarListTipoProd(dlm);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_MOD_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			//FIXME: aca ver de pasar mensajes de error como excepciones
			JOptionPane.showMessageDialog(null, "ERROR", CnstPresGeneric.TP, JOptionPane.ERROR);
		}
		return null;
	}
	
	public Integer eliminarTipoProd(TipoProd tipoProd, DefaultListModel<TipoProd> dlm) {
		if(tipoProd != null && controlDatosObl(tipoProd.getIdTipoProd())) {
			mgrProd.eliminarTipoProd(tipoProd);
			cargarListTipoProd(dlm);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ELI_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			//FIXME: aca ver de pasar mensajes de error como excepciones
			JOptionPane.showMessageDialog(null, "ERROR", CnstPresGeneric.TP, JOptionPane.ERROR);
		}
		return null;
	}


	public void abrirInternalFrame() {
		IfrmTipoProd ifrmTp = new IfrmTipoProd(this);
		getDeskPane().setBounds(0, 0, 784, 565);
		getDeskPane().add(ifrmTp);
		//
		Component comp = getFrm().getContentPane().getComponent(1);
		comp.setVisible(false);//FIXME: ver si no existe solucion mejor
		//
		ifrmTp.show();
	}
	
	public void cerrarInternalFrame() {
		Component comp = getFrm().getContentPane().getComponent(1);
		getDeskPane().setBounds(773, 11, 1, 1);
		comp.setVisible(true);
	}
	
	public FrmProducto getFrm() {
		return frm;
	}
	public void setFrm(FrmProducto frm) {
		this.frm = frm;
	}
	
	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
	}
}
