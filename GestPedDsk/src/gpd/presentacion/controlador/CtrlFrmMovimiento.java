package gpd.presentacion.controlador;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.manager.persona.ManagerPersona;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmMovimiento;
import gpd.presentacion.generic.CnstPresGeneric;

public class CtrlFrmMovimiento extends CtrlGenerico {

	//	private ManagerTransaccion mgrTran = new ManagerTransaccion();
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private FrmMovimiento frm;
	private Transaccion transac;
	
	
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
	
	private void cargarControlesCompra(PersonaJuridica pj, Container containerJTable) {
		// TODO Auto-generated method stub
		
	}
	
	public void cargarJtCompra(List<TranLinea> listaTran) {
		JTable tabla = frm.getJtCompra();
		clearTable(tabla);
		if(listaTran != null && !listaTran.isEmpty()) {
			DefaultTableModel modeloJtProd = new DefaultTableModel();
			tabla.setModel(modeloJtProd);
			modeloJtProd.addColumn("Id Prod");
			modeloJtProd.addColumn("Producto");
			modeloJtProd.addColumn("Proveedor");
			modeloJtProd.addColumn("Precio Unit");
			modeloJtProd.addColumn("Cantidad");
			for(TranLinea tl : listaTran) {
				Object [] fila = new Object[6];
				fila[0] = tl.getProducto().getIdProducto();
				fila[1] = tl.getProducto().getNombre();
				PersonaJuridica pj = (PersonaJuridica) tl.getTransaccion().getPersona(); 
				fila[2] = pj.getNombre();
				fila[3] = tl.getPrecioUnit();
				fila[4] = tl.getCantidad();
				modeloJtProd.addRow(fila);
			}
			tabla.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int fila = tabla.rowAtPoint(e.getPoint());
					if (fila > -1) {
//						Integer idProd = (Integer) tabla.getModel().getValueAt(fila, 0);
//						Producto prod = mgrProd.obtenerProductoPorId(idProd);
//						Container containerJTable = tabla.getParent().getParent().getParent();
//						cargarControlesCompra(null, containerJTable);
					}
				}
			});
		} else {
			cargarJTableVacia(tabla);
		}
	}

	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void iniciarCompra(FrmMovimiento frm) {
		setContainerEnabled(frm.getPnlCompraProv(), true);
		setContainerEnabled(frm.getPnlCompraDatos(), false);
		this.setTransac(new Transaccion());
		this.getTransac().setTipoTran(TipoTran.C);
	}
	
	public void limpiarCompra(FrmMovimiento frm) {
		if(enviarConfirm(CnstPresGeneric.MOV, CnstPresGeneric.COMPRA_CONF_LIMPIAR) == CONFIRM_OK) {
			setContainerEnabled(frm.getPnlCompraDatos(), false);
			setContainerEnabled(frm.getPnlCompraProv(), false);
			clearForm(frm.getContentPane());
			this.setTransac(null);
		}
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
