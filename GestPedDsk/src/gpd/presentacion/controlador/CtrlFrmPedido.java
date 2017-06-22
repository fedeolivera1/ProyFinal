package gpd.presentacion.controlador;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PresentacionException;
import gpd.manager.pedido.ManagerPedido;
import gpd.manager.persona.ManagerPersona;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmPedido;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmPersBuscador;
import gpd.types.Fecha;
import gpd.util.KeyMapLp;

public class CtrlFrmPedido extends CtrlGenerico {

	private FrmPedido frm; 
	private JDesktopPane deskPane;
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private ManagerPedido mgrPed = new ManagerPedido();
	private Persona persSel;
	private IfrmPersBuscador ifrmPb;
	private HashMap<KeyMapLp, PedidoLinea> mapLineasPedido;
	

	public CtrlFrmPedido(FrmPedido frmPed) {
		super();
		this.setFrm(frmPed);
	}
	
	/*****************************************************************************************************************************************************/
	/* CONTROLES */
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
	
	public void cargarJtPedido(List<Pedido> listaPedido) {
		try {
			JTable tabla = frm.getJtPedido();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(listaPedido != null && !listaPedido.isEmpty()) {
				DefaultTableModel modeloJtPed = new DefaultTableModel();
				tabla.setModel(modeloJtPed);
				modeloJtPed.addColumn("Persona");
				modeloJtPed.addColumn("Fecha - Hora");
				modeloJtPed.addColumn("Items (prod|cant)");
				modeloJtPed.addColumn("Estado pedido");
				modeloJtPed.addColumn("Total");
				for(Pedido pedido : listaPedido) {
					Object [] fila = new Object[5];
					fila[0] = pedido.getPersona();
					fila[1] = pedido.getFechaHora();
					fila[2] = pedido.getListaPedidoLinea().size();
					fila[3] = pedido.getEstado().getEstadoPedido();
					fila[4] = pedido.getTotal(); 
					modeloJtPed.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						int fila = tabla.rowAtPoint(me.getPoint());
						int cols = tabla.getModel().getColumnCount();
						if (fila > -1 && cols > 1) {
							Persona pers = (Persona) tabla.getModel().getValueAt(fila, 0);
							Fecha fechaHora = (Fecha) tabla.getModel().getValueAt(fila, 1);
							try {
								Pedido pedido = mgrPed.obtenerPedidoPorId(pers.getIdPersona(), fechaHora);
								cargarMapDesdePedido(pedido);
								//ver de cargar tabla con lineas de pedido
							} catch (PresentacionException e) {
								manejarExcepcion(e);
							}
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, CnstPresGeneric.JTABLE_SIN_COMPRAS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtLineaPedido() {
		try {
			//hacer
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void obtenerPedidos(JComboBox<EstadoPedido> cbxPedidoEstado, JTextField txtPedidoPers, JDateChooser dchPedFecIni, JDateChooser dchPedFecFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPedidoEstado);
			genComp.setComp(dchPedFecIni);
			genComp.setComp(dchPedFecFin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchPedFecIni.getDate());
				Fecha fechaFin = new Fecha(dchPedFecFin.getDate());
				if(controlFechas(fechaIni, fechaFin)) {
					EstadoPedido ep = (EstadoPedido) cbxPedidoEstado.getSelectedItem();
					Long idPersona = getPersSel() != null ? mgrPers.obtenerIdPersonaGenerico(getPersSel()) : null;
					List<Pedido> listaPedido = mgrPed.obtenerListaPedidoPorPeriodo(ep, idPersona, fechaIni, fechaFin);
					cargarJtPedido(listaPedido);
				}
			} else {
				enviarWarning(CnstPresGeneric.PED, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarMapDesdePedido(Pedido pedido) {
		if(pedido != null && pedido.getListaPedidoLinea() != null && !pedido.getListaPedidoLinea().isEmpty()) {
			mapLineasPedido = new HashMap<>();
			for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
				KeyMapLp key = new KeyMapLp(pedido.getPersona().getIdPersona(), 
								pedido.getFechaHora().getAsNumber(Fecha.AMDHMS), 
								pl.getProducto().getIdProducto());
				mapLineasPedido.put(key, pl);
			}
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* BUSCADOR PERS */
	/*****************************************************************************************************************************************************/
	
	public void abrirBuscadorPers(UsuarioDsk usr) {
		try {
			IfrmPersBuscador ifrmPb = new IfrmPersBuscador(this, getPersSel());
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmPb);
			//
			Component comp = getFrm().getContentPane().getComponent(1);
			comp.setVisible(false);
			//
			ifrmPb.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarBuscadorPers(Persona pers) {
		try {
			if(pers != null) {
				setPersSel(pers);
				cargarPersonaSeleccionada();
			}
			Component comp = getFrm().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxTipoPers(JComboBox<TipoPersona> cbxPbTp) {
		try {
			cbxPbTp.removeAllItems();
			List<TipoPersona> listaTp = new ArrayList<TipoPersona>(EnumSet.allOf(TipoPersona.class));
			for(TipoPersona tp : listaTp) {
				cbxPbTp.addItem(tp);
			}
			cbxPbTp.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxDep(JComboBox<Departamento> cbxDep) {
		try {
			cbxDep.removeAllItems();
			ArrayList<Departamento> listaDep = (ArrayList<Departamento>) mgrPers.obtenerListaDepartamento();
			for(Departamento dep : listaDep) {
				cbxDep.addItem(dep);
			}
			cbxDep.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxLoc(JComboBox<Departamento> cbxDep, JComboBox<Localidad> cbxLoc) {
		try {
			cbxLoc.removeAllItems();
			if(controlDatosObl(cbxDep)) {
				Departamento dep = (Departamento) cbxDep.getSelectedItem();
				ArrayList<Localidad> listaLoc = (ArrayList<Localidad>) mgrPers.obtenerListaLocalidadPorDep(dep.getIdDepartamento());
				for(Localidad loc : listaLoc) {
					cbxLoc.addItem(loc);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarJlPersona(List<Persona> listaPersona) {
		try {
			DefaultListModel<Persona> dlm = new DefaultListModel<>();
			getIfrmPb().getJlPersBusq().setModel(dlm);
			clearList(getIfrmPb().getJlPersBusq());
			if(listaPersona != null && !listaPersona.isEmpty()) {
				for(Persona pers : listaPersona) {
					dlm.addElement(pers);
				}
				getIfrmPb().getJlPersBusq().setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarPersonaDesdeJList(JList<Persona> jlPers) {
		if(controlDatosObl(jlPers)) {
			getIfrmPb().setVisible(false);
			getIfrmPb().dispose();
		}
	}
	
	public void buscarPersona(JComboBox<TipoPersona> cbxPbTipoPers, JTextField txtPbFiltro, JComboBox<Localidad> cbxPbLoc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPbTipoPers);
			genComp.setComp(txtPbFiltro);
			if(controlDatosObl(genComp)) {
				TipoPersona tp = (TipoPersona) cbxPbTipoPers.getSelectedItem();
				Localidad loc = (Localidad) cbxPbLoc.getSelectedItem();
				List<Persona> listaPersona = mgrPers.obtenerBusquedaPersona(tp, txtPbFiltro.getText(), loc);
				cargarJlPersona(listaPersona);
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch (Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarPersonaSeleccionada() {
		String toString = null;
		if(getPersSel() instanceof PersonaFisica) {
			PersonaFisica pf = (PersonaFisica) getPersSel();
			toString = pf.toString();
		} else if(getPersSel() instanceof PersonaJuridica) {
			PersonaJuridica pj = (PersonaJuridica) getPersSel();
			toString = pj.toString();
		}
		if(toString != null) {
			getFrm().getTxtPersDesc().setText(toString);
		} else {
			clearComponent(getFrm().getTxtPersDesc());
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
	
	public Persona getPersSel() {
		return persSel;
	}
	public void setPersSel(Persona persSel) {
		this.persSel = persSel;
	}

	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
	}
	
	public IfrmPersBuscador getIfrmPb() {
		return ifrmPb;
	}
	public void setIfrmPb(IfrmPersBuscador ifrmPb) {
		this.ifrmPb = ifrmPb;
	}



	
}
