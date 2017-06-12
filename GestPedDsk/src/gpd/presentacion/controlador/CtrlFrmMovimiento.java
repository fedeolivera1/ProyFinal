package gpd.presentacion.controlador;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.manager.persona.ManagerPersona;
import gpd.manager.producto.ManagerProducto;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.presentacion.formulario.FrmMovimiento;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.types.Fecha;

public class CtrlFrmMovimiento extends CtrlGenerico {

	private ManagerTransaccion mgrTran = new ManagerTransaccion();
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private FrmMovimiento frm;
	private Transaccion transac;
	private HashMap<Integer, TranLinea> mapLineasTran;
	
	
	public CtrlFrmMovimiento(FrmMovimiento frmMov) {
		super();
		this.frm = frmMov;
	}


	/*****************************************************************************************************************************************************/
	/* CONTROLES */
	/*****************************************************************************************************************************************************/
	
	public void cargarCbxProveedor(JComboBox<PersonaJuridica> cbxCompraProv) {
		cbxCompraProv.removeAllItems();
		List<PersonaJuridica> listaPj = (ArrayList<PersonaJuridica>) mgrPers.obtenerListaProveedor();
		if(listaPj != null && !listaPj.isEmpty()) {
			for(PersonaJuridica pj : listaPj) {
				cbxCompraProv.addItem(pj);
			}
			cbxCompraProv.setSelectedIndex(-1);
		}
	}
	
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
	
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
		if(listaTipoProd != null && !listaTipoProd.isEmpty()) {
			for(TipoProd tipoProd : listaTipoProd) {
				cbxTipoProd.addItem(tipoProd);
			}
			cbxTipoProd.setSelectedIndex(-1);
		}
	}
	
	public void cargarCbxProd(JComboBox<TipoProd> cbxTipoProd, JComboBox<Producto> cbxProd) {
		cbxProd.removeAllItems();
		if(controlDatosObl(cbxTipoProd)) {
			TipoProd tipoProd = (TipoProd) cbxTipoProd.getSelectedItem();
			ArrayList<Producto> listaProd = (ArrayList<Producto>) mgrProd.obtenerListaProductoPorTipoProd(tipoProd);
			for(Producto prod : listaProd) {
				cbxProd.addItem(prod);
			}
			cbxProd.setSelectedIndex(-1);
		}
	}
	
	private void cargarControlesCompra(TranLinea tl, Container containerJTable) {
		clearControlsInJPanel(containerJTable);
		ComboBoxModel<TipoProd> cbModelTp = frm.getCbxCompraTp().getModel();
		cbModelTp.setSelectedItem(tl.getProducto().getTipoProd());
		frm.getCbxCompraTp().setSelectedItem(cbModelTp.getSelectedItem());
		ComboBoxModel<Producto> cbModelProd = frm.getCbxCompraProd().getModel();
		cbModelProd.setSelectedItem(tl.getProducto());
		frm.getCbxCompraTp().setSelectedItem(cbModelTp.getSelectedItem());
		frm.getFtxtCompraCant().setText(String.valueOf(tl.getCantidad()));
		frm.getFtxtCompraPu().setText(String.valueOf(tl.getCantidad()));
	}
	
	public void cargarJtCompraItems() {
		JTable tabla = frm.getJtCompraItems();
		clearTable(tabla);
		deleteModelTable(tabla);
		if(mapLineasTran != null && !mapLineasTran.isEmpty()) {
			DefaultTableModel modeloJtCompra = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
	            public Class<?> getColumnClass(int column) {
	                return column == 0 ? Boolean.class : Object.class;
	            }
	        };
			tabla.setModel(modeloJtCompra);
			modeloJtCompra.addColumn("");
			modeloJtCompra.addColumn("Producto");
			modeloJtCompra.addColumn("Proveedor");
			modeloJtCompra.addColumn("Cantidad");
			modeloJtCompra.addColumn("Precio Unit");
			for(TranLinea tl : mapLineasTran.values()) {
				Object [] fila = new Object[5];
				fila[0] = false;
				fila[1] = tl.getProducto();
				fila[2] = (PersonaJuridica) tl.getTransaccion().getPersona();
				fila[3] = tl.getCantidad();
				fila[4] = tl.getPrecioUnit();
				modeloJtCompra.addRow(fila);
			}
			
			tabla.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int fila = tabla.rowAtPoint(e.getPoint());
					int cols = tabla.getModel().getColumnCount();
					if (fila > -1 && cols > 1) {
						Producto prod = (Producto) tabla.getModel().getValueAt(fila, 1);
						TranLinea tl = mapLineasTran.get(prod.getIdProducto());
						Container containerJTable = tabla.getParent().getParent().getParent();
						cargarControlesCompra(tl, containerJTable);
					}
				}
			});
		} else {
			cargarJTableVacia(tabla, CnstPresGeneric.JTABLE_SIN_ITEMS);
		}
	}
	
	public void cargarJtComprasPend(JComboBox<PersonaJuridica> cbxCompraPj) {
		JTable tabla = frm.getJtComprasPend();
		clearTable(tabla);
		deleteModelTable(tabla);
		if(cbxCompraPj.getSelectedIndex() > -1) {
			PersonaJuridica pj = (PersonaJuridica) cbxCompraPj.getSelectedItem();
			List<Transaccion> listaTransac = mgrTran.obtenerListaTransaccionPorPersona(pj.getRut(), TipoTran.C, EstadoTran.P);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				DefaultTableModel modeloJtComprasPend = new DefaultTableModel();
				tabla.setModel(modeloJtComprasPend);
				modeloJtComprasPend.addColumn("Id Transac");
				modeloJtComprasPend.addColumn("Persona");
				modeloJtComprasPend.addColumn("Fecha - Hora");
				modeloJtComprasPend.addColumn("Items (cod|nom|cant|$unit)");
				for(Transaccion transac : listaTransac) {
					Object [] fila = new Object[4];
					fila[0] = transac.getNroTransac();
					fila[1] = pj.getRut() + " " + pj.getNombre();
					fila[2] = transac.getFechaHora().toString(Fecha.AMDHMS);
					fila[3] = transac.toStringLineas(); 
					modeloJtComprasPend.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
				});
				packColumn(tabla, 4, 0);
			}
		} else {
			cargarJTableVacia(tabla, CnstPresGeneric.JTABLE_SIN_COMPRAS);
		}
	}
	
	public void cargarPrecioProd(JComboBox<Producto> cbxCompraProd) {
		if(cbxCompraProd.getSelectedItem() != null) {
			Producto prod = (Producto) cbxCompraProd.getSelectedItem();
			clearComponent(frm.getFtxtCompraCant());
			frm.getFtxtCompraPu().setText(String.valueOf(prod.getPrecio()));
		} else {
			clearComponent(frm.getFtxtCompraPu());
		}
	}

	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void iniciarCompra(FrmMovimiento frm) {
		nuevaTransaccion();
		setContainerEnabled(frm.getPnlCompraProv(), true);
		setContainerEnabled(frm.getPnlCompraDatos(), false);
	}
	
	public void limpiarCompra(FrmMovimiento frm, Boolean confirma) {
		if(confirma ? enviarConfirm(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_CONF_LIMPIAR) == CONFIRM_OK : true) {
			setContainerEnabled(frm.getPnlCompraDatos(), false);
			setContainerEnabled(frm.getPnlCompraProv(), false);
			clearForm(frm.getContentPane());
			this.setTransac(null);
			mapLineasTran = null;
		}
	}
	
	public void agregarItemCompra(JComboBox<PersonaJuridica> cbxCompraPj, JComboBox<Producto> cbxCompraProd, JFormattedTextField ftxtCompraCant,
			JFormattedTextField ftxtCompraPu) {
		GenCompType genComp = new GenCompType();
		genComp.setComp(cbxCompraPj);
		genComp.setComp(cbxCompraProd); 
		genComp.setComp(ftxtCompraCant);
		genComp.setComp(ftxtCompraPu);
		if(controlDatosObl(genComp)) {
			if(getTransac().equals(null)) {
				nuevaTransaccion();
			}
			Producto prod = (Producto) cbxCompraProd.getSelectedItem();
			if(!mapLineasTran.containsKey(prod.getIdProducto())) {
				getTransac().setPersona((PersonaJuridica) cbxCompraPj.getSelectedItem());
				TranLinea tl = new TranLinea();
				tl.setProducto(prod);
				tl.setCantidad(new Integer(ftxtCompraCant.getText()));
				tl.setPrecioUnit(new Double(ftxtCompraPu.getText()));
				tl.setTransaccion(getTransac());
				mapLineasTran.put(prod.getIdProducto(), tl);
				//cargo tabla con lista de lineas
				cargarJtCompraItems();
			} else {
				enviarWarning(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_PROD_YA_ING);
			}
		} else {
			enviarWarning(CnstPresGeneric.MOV, CnstPresGeneric.DATOS_OBLIG);
		}
	}
	
	private void nuevaTransaccion() {
		setTransac(new Transaccion(TipoTran.C));
		mapLineasTran = new HashMap<>();
	}
	
	public void generarCompra() {
		if(getTransac() != null) {
			if(mapLineasTran != null && !mapLineasTran.isEmpty()) {
				getTransac().getListaTranLinea().addAll(mapLineasTran.values());
				mgrTran.generarTransaccion(getTransac());
				limpiarCompra(getFrm(), false);
				enviarInfo(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_CONFIRMADA);
			} else {
				enviarWarning(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_SIN_LINEAS);
			}
		} else {
			enviarWarning(CnstPresGeneric.MOV, CnstPresGeneric.DATOS_OBLIG);
		}
	}
	
	public void modificarItemCompra(JComboBox<PersonaJuridica> cbxCompraPj, JComboBox<Producto> cbxCompraProd, JFormattedTextField ftxtCompraCant,
			JFormattedTextField ftxtCompraPu) {
		GenCompType genComp = new GenCompType();
		genComp.setComp(cbxCompraPj);
		genComp.setComp(cbxCompraProd); 
		genComp.setComp(ftxtCompraCant);
		genComp.setComp(ftxtCompraPu);
		if(controlDatosObl(genComp)) {
			Producto prod = (Producto) cbxCompraProd.getSelectedItem();
			if(getTransac() != null && mapLineasTran != null && 
					mapLineasTran.containsKey(prod.getIdProducto())) {
				TranLinea tl = mapLineasTran.get(prod.getIdProducto());
				tl.setProducto(prod);
				tl.setCantidad(new Integer(ftxtCompraCant.getText()));
				tl.setPrecioUnit(new Double(ftxtCompraPu.getText()));
				tl.setTransaccion(getTransac());
			} else {
				enviarWarning(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_NOMODIF_PROD);
			}
			cargarJtCompraItems();
		}
	}
	
	public void eliminarItemCompra() {
		if(this.getFrm().getJtCompraItems().getModel().getRowCount() > 0 && 
				this.getFrm().getJtCompraItems().getModel().getColumnCount() > 1) {
			for(int i=0; i<getFrm().getJtCompraItems().getModel().getRowCount(); i++) {
				Boolean checked = (Boolean) getFrm().getJtCompraItems().getModel().getValueAt(i, 0);
				if (checked) {
					Producto prod = (Producto) getFrm().getJtCompraItems().getModel().getValueAt(i, 1);
					if(mapLineasTran.containsKey(prod.getIdProducto())) {
						mapLineasTran.remove(prod.getIdProducto());
					}
				}
			}
			cargarJtCompraItems();
		}
		//FIXME completar con mensajes de error
	}

	
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmMovimiento getFrm() {
		return frm;
	}
	public void setFrm(FrmMovimiento frm) {
		this.frm = frm;
	}


	public Transaccion getTransac() {
		return transac;
	}
	public void setTransac(Transaccion transac) {
		this.transac = transac;
	}

	

	
}
