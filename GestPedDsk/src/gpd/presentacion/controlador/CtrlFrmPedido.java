package gpd.presentacion.controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmPedido;

public class CtrlFrmPedido extends CtrlGenerico {

	private FrmPedido frm; 
	private ManagerProducto mgrProd = new ManagerProducto();
	
	
	public CtrlFrmPedido(FrmPedido frmPed) {
		super();
		this.setFrm(frmPed);
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void cargarCbxPedidoEstado(JComboBox<EstadoPedido> cbxPedidoEstado) {
		try {
			cbxPedidoEstado.removeAllItems();
			for(EstadoPedido estado : EstadoPedido.values()) {
				cbxPedidoEstado.addItem(estado);
			}
			cbxPedidoEstado.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxPedidoTp) {
		try {
			cbxPedidoTp.removeAllItems();
			ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
			if(listaTipoProd != null && !listaTipoProd.isEmpty()) {
				for(TipoProd tipoProd : listaTipoProd) {
					cbxPedidoTp.addItem(tipoProd);
				}
				cbxPedidoTp.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxProd(JComboBox<TipoProd> cbxPedidoTp, JComboBox<Producto> cbxPedidoProd) {
		try {
			cbxPedidoProd.removeAllItems();
			if(controlDatosObl(cbxPedidoTp)) {
				TipoProd tipoProd = (TipoProd) cbxPedidoTp.getSelectedItem();
				ArrayList<Producto> listaProd = (ArrayList<Producto>) mgrProd.obtenerListaProductoPorTipoProd(tipoProd);
				for(Producto prod : listaProd) {
					cbxPedidoProd.addItem(prod);
				}
				cbxPedidoProd.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
		
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmPedido getFrm() {
		return frm;
	}
	public void setFrm(FrmPedido frm) {
		this.frm = frm;
	}
	
}
