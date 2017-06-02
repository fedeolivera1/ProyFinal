package gpd.presentacion.controlador;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
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
	private IfrmTipoProd iFrm;
	private JDesktopPane deskPane;
	private TipoProd tpSel;
	
	public CtrlFrmProducto(IfrmTipoProd iFrm) {
		super();
		this.setiFrm(iFrm);
	}
	
	public CtrlFrmProducto(FrmProducto frm) {
		super();
		this.setFrm(frm);
	}
	
	/*****************************************************************************************************************************************************/
	/* CONTROLES */
	/*****************************************************************************************************************************************************/
	
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		cbxTipoProd.removeAllItems();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		for(TipoProd tipoProd : listaTipoProd) {
			cbxTipoProd.addItem(tipoProd);
		}
	}
	
	public void cargarListTipoProd(JList<TipoProd> jlTipoProd) {
		DefaultListModel<TipoProd> dlm = new DefaultListModel<>();
		dlm.clear();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		for(TipoProd tipoProd : listaTipoProd) {
			dlm.addElement(tipoProd);
		}
		jlTipoProd.setModel(dlm);
		jlTipoProd.setSelectedIndex(-1);
	}
	
	public void cargarControlesTipoProd(JTextField txtTpDesc, JList<TipoProd> jlTipoProd) {
		if(controlDatosObl(jlTipoProd)) {
			TipoProd tp = (TipoProd) jlTipoProd.getSelectedValue();
			txtTpDesc.setText(tp.getDescripcion());
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public Integer agregarProducto(JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, JFormattedTextField precio) {
		if(controlDatosObl(codigo, nombre, stockMin, precio)) {
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ING_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		}
		return null;
	}
	
	public Integer modificarProducto(JTextField id, JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, JFormattedTextField precio) {
		if(controlDatosObl(id, codigo, nombre, stockMin, precio)) {
			return 1;
		}
		return null;
	}
	
	public Integer eliminarProducto(JTextField id) {
		if(controlDatosObl(id)) {
			return 1;
		}
		return null;
	}
	
	public Integer agregarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		if(controlDatosObl(descripcion)) {
			TipoProd tipoProd = new TipoProd();
			tipoProd.setDescripcion(descripcion.getText());
			mgrProd.guardarTipoProd(tipoProd);
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ING_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
			clearForm(getiFrm().getContentPane());
		} else {
			//FIXME: aca ver de pasar mensajes de error como excepciones
			JOptionPane.showMessageDialog(null, "ERROR", CnstPresGeneric.TP, JOptionPane.ERROR);
		}
		return null;
	}
	
	public Integer modificarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		if(controlDatosObl(descripcion, jlTp)) {
			TipoProd tp = (TipoProd) jlTp.getSelectedValue();
			tp.setDescripcion(descripcion.getText());
			mgrProd.modificarTipoProd(tp);
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_MOD_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
			clearForm(getiFrm().getContentPane());
		} else {
			//FIXME: aca ver de pasar mensajes de error como excepciones
			JOptionPane.showMessageDialog(null, "ERROR", CnstPresGeneric.TP, JOptionPane.ERROR);
		}
		return null;
	}
	
	public Integer eliminarTipoProd(JList<TipoProd> jlTp) {
		if(controlDatosObl(jlTp)) {
			TipoProd tp = (TipoProd) jlTp.getSelectedValue();
			mgrProd.eliminarTipoProd(tp);
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ELI_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
			clearForm(getiFrm().getContentPane());
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


	public TipoProd getTpSel() {
		return tpSel;
	}
	public void setTpSel(TipoProd tpSel) {
		this.tpSel = tpSel;
	}

	public IfrmTipoProd getiFrm() {
		return iFrm;
	}
	public void setiFrm(IfrmTipoProd iFrm) {
		this.iFrm = iFrm;
	}


	
}
