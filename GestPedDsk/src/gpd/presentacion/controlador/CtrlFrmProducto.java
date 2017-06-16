package gpd.presentacion.controlador;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.EstadoTran;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmProducto;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmDeposito;
import gpd.presentacion.popup.IfrmTipoProd;
import gpd.presentacion.popup.IfrmUtilidad;

public class CtrlFrmProducto extends CtrlGenerico {

	private ManagerProducto mgrProd = new ManagerProducto();
	private FrmProducto frmProd;
	private IfrmTipoProd iFrmTp;
	private IfrmDeposito iFrmDep;
	private IfrmUtilidad iFrmUtil;
	private JDesktopPane deskPane;
	private TipoProd tpSel;
	
	
	public CtrlFrmProducto(FrmProducto frmProd) {
		super();
		this.setFrm(frmProd);
	}
	
	public CtrlFrmProducto(IfrmTipoProd iFrmTp) {
		super();
		this.setiFrmTp(iFrmTp);
	}
	
	public CtrlFrmProducto(IfrmDeposito iFrmDep) {
		super();
		this.setiFrmDep(iFrmDep);
	}
	
	/*****************************************************************************************************************************************************/
	/* CONTROLES */
	/*****************************************************************************************************************************************************/
	//tp
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		cbxTipoProd.removeAllItems();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		if(listaTipoProd != null && !listaTipoProd.isEmpty()) {
			for(TipoProd tipoProd : listaTipoProd) {
				cbxTipoProd.addItem(tipoProd);
			}
			cbxTipoProd.setSelectedIndex(-1);
		}
	}
	
	public void cargarListTipoProd(JList<TipoProd> jlTipoProd) {
		DefaultListModel<TipoProd> dlm = new DefaultListModel<>();
		dlm.clear();
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		if(listaTipoProd != null && !listaTipoProd.isEmpty()) {
			for(TipoProd tipoProd : listaTipoProd) {
				dlm.addElement(tipoProd);
			}
			jlTipoProd.setModel(dlm);
			jlTipoProd.setSelectedIndex(-1);
		}
	}
	
	public void cargarControlesTipoProd(JTextField txtTpDesc, JList<TipoProd> jlTipoProd) {
		if(controlDatosObl(jlTipoProd)) {
			TipoProd tp = (TipoProd) jlTipoProd.getSelectedValue();
			txtTpDesc.setText(tp.getDescripcion());
		}
	}
	
	//prod
	public void cargarJtProd(List<Producto> listaProd) {
		JTable tabla = frmProd.getJtProd();
		clearTable(tabla);
		if(listaProd != null && !listaProd.isEmpty()) {
			DefaultTableModel modeloJtProd = new DefaultTableModel();
			tabla.setModel(modeloJtProd);
			modeloJtProd.addColumn("Id");
			modeloJtProd.addColumn("Codigo");
			modeloJtProd.addColumn("Nombre");
			modeloJtProd.addColumn("Desc");
			modeloJtProd.addColumn("Stock Min");
			modeloJtProd.addColumn("Tipo");
			for(Producto prod : listaProd) {
				Object [] fila = new Object[6];
				fila[0] = prod.getIdProducto();
				fila[1] = prod.getCodigo();
				fila[2] = prod.getNombre();
				fila[3] = prod.getDescripcion();
				fila[4] = prod.getStockMin();
				fila[5] = prod.getTipoProd().toString();
				modeloJtProd.addRow(fila);
			}
			tabla.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int fila = tabla.rowAtPoint(e.getPoint());
					if (fila > -1) {
						Integer idProd = (Integer) tabla.getModel().getValueAt(fila, 0);
						Producto prod = mgrProd.obtenerProductoPorId(idProd);
						Container containerJTable = tabla.getParent().getParent().getParent();
						cargarControlesProducto(prod, containerJTable);
					}
				}
			});
		} else {
			cargarJTableVacia(tabla, null);
		}
	}
	
	public void cargarCbxFiltroLote(JComboBox<EstadoTran> cbxFiltroLote) {
		cbxFiltroLote.removeAllItems();
		for(EstadoTran estado : EstadoTran.values()) {
			cbxFiltroLote.addItem(estado);
		}
		cbxFiltroLote.setSelectedIndex(-1);
	}
	
	public void cargarJtLote(JComboBox<EstadoTran> cbxFiltroLote) {
		JTable tabla = frmProd.getJtLote();
		clearTable(tabla);
		if(cbxFiltroLote.getSelectedIndex() > -1) {
			EstadoTran estado = (EstadoTran) cbxFiltroLote.getSelectedItem();
			List<Lote> listaLote = (ArrayList<Lote>) mgrProd.obtenerListaLotePorEstado(estado);
			if(listaLote != null && !listaLote.isEmpty()) {
				DefaultTableModel modeloJtLote = new DefaultTableModel();
				tabla.setModel(modeloJtLote);
				modeloJtLote.addColumn("Lote");
				modeloJtLote.addColumn("Producto");
				modeloJtLote.addColumn("Transaccion");
				modeloJtLote.addColumn("Stock");
				for(Lote lote : listaLote) {
					Object [] fila = new Object[4];
					fila[0] = lote.getIdLote();
					fila[1] = lote.getTranLinea().getProducto();
					fila[2] = lote.getTranLinea().getTransaccion().getNroTransac();
					fila[3] = lote.getStock();
					modeloJtLote.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int fila = tabla.rowAtPoint(e.getPoint());
						if (fila > -1) {
							Integer idLote = (Integer) tabla.getModel().getValueAt(fila, 0);
//							Producto prod = mgrProd.obtenerProductoPorId(idProd);
//							Container containerJTable = tabla.getParent().getParent().getParent();
//							cargarControlesLote(prod, containerJTable);
							
							//FIXME terminar esta parte
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, CnstPresGeneric.JTABLE_SIN_LOTES);
			}
		}
	}
	
	public void cargarControlesProducto(Producto prod, Container panel) {
		clearControlsInJPanel(panel);
		ComboBoxModel<TipoProd> cbModelTp = frmProd.getCbxTipoProd().getModel();
		cbModelTp.setSelectedItem(prod.getTipoProd());
		frmProd.getCbxTipoProd().setSelectedItem(cbModelTp.getSelectedItem());
		frmProd.getTxtProId().setText(String.valueOf(prod.getIdProducto()));
		frmProd.getTxtProCod().setText(prod.getCodigo());
		frmProd.getTxtProNom().setText(prod.getNombre());
		frmProd.getTxtProDesc().setText(prod.getDescripcion());
		frmProd.getFtxtProStockMin().setText(String.valueOf(prod.getStockMin()));
		frmProd.getFtxtProPrecio().setText(String.valueOf(prod.getPrecio()));
	}
	
	//dep
	public void cargarCbxDep(JComboBox<Deposito> cbxDep) {
		cbxDep.removeAllItems();
		List<Deposito> listaDep = (ArrayList<Deposito>) mgrProd.obtenerListaDeposito();
		if(listaDep != null && !listaDep.isEmpty()) {
			for(Deposito dep : listaDep) {
				cbxDep.addItem(dep);
			}
			cbxDep.setSelectedIndex(-1);
		}
	}
	
	public void cargarListDeposito(JList<Deposito> jlDep) {
		DefaultListModel<Deposito> dlm = new DefaultListModel<>();
		dlm.clear();
		ArrayList<Deposito> listaDep = (ArrayList<Deposito>) mgrProd.obtenerListaDeposito();
		if(listaDep != null && !listaDep.isEmpty()) {
			for(Deposito dep : listaDep) {
				dlm.addElement(dep);
			}
			jlDep.setModel(dlm);
			jlDep.setSelectedIndex(-1);
		}
	}
	
	public void cargarControlesDep(JTextField txtDepNom, JList<Deposito> jlDep) {
		if(controlDatosObl(jlDep)) {
			Deposito dep = (Deposito) jlDep.getSelectedValue();
			txtDepNom.setText(dep.getNombre());
		}
	}

	
	//util
	public void cargarCbxUtil(JComboBox<Utilidad> cbxUtil) {
		cbxUtil.removeAllItems();
		List<Utilidad> listaUtil = (ArrayList<Utilidad>) mgrProd.obtenerListaUtilidad();
		if(listaUtil != null && !listaUtil.isEmpty()) {
			for(Utilidad util : listaUtil) {
				cbxUtil.addItem(util);
			}
			cbxUtil.setSelectedIndex(-1);
		}
	}
	
	public void cargarListUtil(JList<Utilidad> jlUtil) {
		DefaultListModel<Utilidad> dlm = new DefaultListModel<>();
		dlm.clear();
		List<Utilidad> listaUtil = (ArrayList<Utilidad>) mgrProd.obtenerListaUtilidad();
		if(listaUtil != null && !listaUtil.isEmpty()) {
			for(Utilidad util : listaUtil) {
				dlm.addElement(util);
			}
			jlUtil.setModel(dlm);
			jlUtil.setSelectedIndex(-1);
		}
	}
	
	public void cargarControlesUtil(JTextField txtUtilDesc, JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		if(controlDatosObl(jlUtil)) {
			Utilidad util = (Utilidad) jlUtil.getSelectedValue();
			txtUtilDesc.setText(util.getDescripcion());
			txtUtilPorc.setText(String.valueOf(util.getPorc()));
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	//producto
	
	public void buscarProducto(JComboBox<TipoProd> cbxTp, JTextField txtProCod, JTextField txtProNom, JTextField txtProDesc) {
		//FIXME chequear que no haya que poner campos obligatorios
		List<Producto> listaProd = (ArrayList<Producto>) mgrProd.obtenerBusquedaProducto((TipoProd) cbxTp.getSelectedItem(), txtProCod.getText(), txtProNom.getText(), txtProDesc.getText());
		cargarJtProd(listaProd);
	}
	
	public Integer agregarProducto(JComboBox<TipoProd> cbxTp, JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, 
			JFormattedTextField precio) {
		GenCompType genComp = new GenCompType();
		genComp.setComp(cbxTp);
		genComp.setComp(codigo);
		genComp.setComp(nombre);
		genComp.setComp(stockMin);
		genComp.setComp(precio);
		if(controlDatosObl(genComp)) {
			Producto prod = new Producto();
			TipoProd tp = (TipoProd)cbxTp.getSelectedItem();
			prod.setTipoProd(tp);
			prod.setCodigo(codigo.getText());
			prod.setNombre(nombre.getText());
			prod.setDescripcion(descripcion.getText());
			prod.setStockMin(new Float(stockMin.getText()));
			prod.setPrecio(new Double(precio.getText()));
			mgrProd.guardarProducto(prod);
			clearForm(frmProd.getContentPane());
			List<Producto> lst = mgrProd.obtenerListaProductoPorTipoProd(tp);
			cargarJtProd(lst);
		} else {
			enviarWarning(CnstPresGeneric.PROD, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer modificarProducto(JComboBox<TipoProd> cbxTp, JTextField id, JTextField codigo, JTextField nombre, JTextField descripcion, 
			JFormattedTextField stockMin, JFormattedTextField precio) {
		GenCompType genComp = new GenCompType();
		genComp.setComp(cbxTp);
		genComp.setComp(id);
		genComp.setComp(codigo);
		genComp.setComp(nombre);
		genComp.setComp(stockMin);
		genComp.setComp(precio);
		if(controlDatosObl(genComp)) {
			Integer idInt = ctrlNumLong(id.getText()) ? new Integer(id.getText()) : null;
			TipoProd tp = (TipoProd)cbxTp.getSelectedItem();
			Producto prod = mgrProd.obtenerProductoPorId(idInt);
			prod.setTipoProd(tp);
			prod.setCodigo(codigo.getText());
			prod.setNombre(nombre.getText());
			prod.setDescripcion(descripcion.getText());
			prod.setStockMin(new Float(stockMin.getText()));
			prod.setPrecio(new Double(precio.getText()));
			mgrProd.modificarProducto(prod);
			clearForm(frmProd.getContentPane());
			List<Producto> lst = mgrProd.obtenerListaProductoPorTipoProd(tp);
			cargarJtProd(lst);
		} else {
			enviarWarning(CnstPresGeneric.PROD, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer eliminarProducto(JTextField id) {
		if(controlDatosObl(id)) {
			Integer idInt = ctrlNumLong(id.getText()) ? new Integer(id.getText()) : null;
			clearForm(frmProd.getContentPane());
			Producto prod = new Producto();
			prod.setIdProducto(idInt);
			mgrProd.eliminarProducto(prod);
		} else {
			enviarWarning(CnstPresGeneric.PROD, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	//tipo prod
	
	public Integer agregarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		if(controlDatosObl(descripcion)) {
			TipoProd tipoProd = new TipoProd();
			tipoProd.setDescripcion(descripcion.getText());
			mgrProd.guardarTipoProd(tipoProd);
			clearForm(getiFrmTp().getContentPane());
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ING_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer modificarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		if(controlDatosObl(descripcion, jlTp)) {
			TipoProd tp = (TipoProd) jlTp.getSelectedValue();
			tp.setDescripcion(descripcion.getText());
			mgrProd.modificarTipoProd(tp);
			clearForm(getiFrmTp().getContentPane());
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_MOD_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer eliminarTipoProd(JList<TipoProd> jlTp) {
		if(controlDatosObl(jlTp)) {
			TipoProd tp = (TipoProd) jlTp.getSelectedValue();
			mgrProd.eliminarTipoProd(tp);
			clearForm(getiFrmTp().getContentPane());
			cargarListTipoProd(jlTp);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.TP_ELI_OK, CnstPresGeneric.TP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}


	public void abrirIFrmTp() {
		IfrmTipoProd ifrmTp = new IfrmTipoProd(this);
		getDeskPane().setBounds(0, 0, 784, 565);
		getDeskPane().add(ifrmTp);
		//
		Component comp = getFrm().getContentPane().getComponent(1);
		comp.setVisible(false);//FIXME: ver si no existe solucion mejor
		//
		ifrmTp.show();
	}
	
	public void cerrarIFrmTp() {
		Component comp = getFrm().getContentPane().getComponent(1);
		getDeskPane().setBounds(773, 11, 0, 0);
		comp.setVisible(true);
	}
	
	//deposito
	
	public Integer agregarDeposito(JTextField nombre, JList<Deposito> jlDep) {
		if(controlDatosObl(nombre)) {
			Deposito dep = new Deposito();
			dep.setNombre(nombre.getText());
			mgrProd.guardarDeposito(dep);
			clearForm(getiFrmDep().getContentPane());
			cargarListDeposito(jlDep);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.DEP_ING_OK, CnstPresGeneric.DEP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer modificarDeposito(JTextField nombre, JList<Deposito> jlDep) {
		if(controlDatosObl(nombre, jlDep)) {
			Deposito dep = (Deposito) jlDep.getSelectedValue();
			dep.setNombre(nombre.getText());
			mgrProd.modificarDeposito(dep);
			clearForm(getiFrmDep().getContentPane());
			cargarListDeposito(jlDep);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.DEP_MOD_OK, CnstPresGeneric.DEP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public Integer eliminarDeposito(JList<Deposito> jlDep) {
		if(controlDatosObl(jlDep)) {
			Deposito dep = (Deposito) jlDep.getSelectedValue();
			mgrProd.eliminarDeposito(dep);
			clearForm(getiFrmDep().getContentPane());
			cargarListDeposito(jlDep);
			JOptionPane.showMessageDialog(null, CnstPresGeneric.DEP_ELI_OK, CnstPresGeneric.DEP, JOptionPane.PLAIN_MESSAGE);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public void abrirIFrmDep() {
		IfrmDeposito ifrmDep = new IfrmDeposito(this);
		getDeskPane().setBounds(0, 0, 784, 565);
		getDeskPane().add(ifrmDep);
		//
		Component comp = getFrm().getContentPane().getComponent(1);
		comp.setVisible(false);//FIXME: ver si no existe solucion mejor
		//
		ifrmDep.show();
	}
	
	public void cerrarIFrmDep() {
		Component comp = getFrm().getContentPane().getComponent(1);
		getDeskPane().setBounds(773, 11, 0, 0);
		comp.setVisible(true);
	}
	
	//utilidad
	public Integer agregarUtilidad(JTextField txtUtilDesc, JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		if(controlDatosObl(txtUtilDesc, txtUtilPorc)) {
			Utilidad util = new Utilidad();
			util.setDescripcion(txtUtilDesc.getText());
			util.setPorc(new Float(txtUtilPorc.getText()));
			mgrProd.guardarUtilidad(util);
			clearForm(getiFrmUtil().getContentPane());
			cargarListUtil(jlUtil);
			enviarInfo(CnstPresGeneric.UTIL, CnstPresGeneric.UTIL_ING_OK);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	public Integer modificarUtilidad(JTextField txtUtilDesc,  JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		if(controlDatosObl(txtUtilDesc, txtUtilPorc, jlUtil)) {
			Utilidad util = (Utilidad) jlUtil.getSelectedValue();
			util.setDescripcion(txtUtilDesc.getText());
			util.setPorc(new Float(txtUtilPorc.getText()));
			mgrProd.modificarUtilidad(util);
			clearForm(getiFrmUtil().getContentPane());
			cargarListUtil(jlUtil);
			enviarInfo(CnstPresGeneric.UTIL, CnstPresGeneric.UTIL_MOD_OK);
		} else {
			enviarWarning(CnstPresGeneric.TP, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	public Integer eliminarUtilidad(JList<Utilidad> jlUtil) {
		if(controlDatosObl(jlUtil)) {
			Utilidad util = (Utilidad) jlUtil.getSelectedValue();
			mgrProd.eliminarUtilidad(util);
			clearForm(getiFrmUtil().getContentPane());
			cargarListUtil(jlUtil);
			enviarInfo(CnstPresGeneric.UTIL, CnstPresGeneric.UTIL_ELI_OK);
		} else {
			enviarWarning(CnstPresGeneric.UTIL, CnstPresGeneric.DATOS_OBLIG);
		}
		return null;
	}
	
	public void abrirIFrmUtil() {
		IfrmUtilidad ifrmUtil = new IfrmUtilidad(this);
		getDeskPane().setBounds(0, 0, 784, 565);
		getDeskPane().add(ifrmUtil);
		//
		Component comp = getFrm().getContentPane().getComponent(1);
		comp.setVisible(false);//FIXME: ver si no existe solucion mejor
		//
		ifrmUtil.show();
	}
	
	public void cerrarIFrmUtil() {
		Component comp = getFrm().getContentPane().getComponent(1);
		getDeskPane().setBounds(773, 11, 0, 0);
		comp.setVisible(true);
	}
	
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmProducto getFrm() {
		return frmProd;
	}
	public void setFrm(FrmProducto frm) {
		this.frmProd = frm;
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

	public IfrmTipoProd getiFrmTp() {
		return iFrmTp;
	}
	public void setiFrmTp(IfrmTipoProd iFrmTp) {
		this.iFrmTp = iFrmTp;
	}

	public IfrmDeposito getiFrmDep() {
		return iFrmDep;
	}
	public void setiFrmDep(IfrmDeposito iFrmDep) {
		this.iFrmDep = iFrmDep;
	}

	public IfrmUtilidad getiFrmUtil() {
		return iFrmUtil;
	}
	public void setiFrmUtil(IfrmUtilidad iFrmUtil) {
		this.iFrmUtil = iFrmUtil;
	}

	
}
