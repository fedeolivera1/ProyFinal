package gpd.presentacion.controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.helper.HlpProducto;
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
import gpd.dominio.util.Converters;
import gpd.exceptions.PresentacionException;
import gpd.manager.pedido.ManagerPedido;
import gpd.manager.persona.ManagerPersona;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmPedido;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmPersBuscador;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;
import gpd.util.KeyMapLp;

public class CtrlFrmPedido extends CtrlGenerico implements CnstPresGeneric {

	private FrmPedido frm; 
	private UsuarioDsk usr;
	private JDesktopPane deskPane;
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private ManagerPedido mgrPed = new ManagerPedido();
	private Persona persSel;
	private Pedido pedidoActual;
	private IfrmPersBuscador ifrmPb;
	private HashMap<KeyMapLp, PedidoLinea> mapLineasPedido;
	private StringBuilder strInfoPedido;
	

	public CtrlFrmPedido(FrmPedido frmPed, UsuarioDsk usr) {
		super();
		this.setFrm(frmPed);
		this.setUsr(usr);
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
				DefaultTableModel modeloJtPed = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
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
								cargarJtPedidoLin();
								setearInfoPedido();
							} catch (PresentacionException e) {
								manejarExcepcion(e);
							}
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_PEDIDOS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtPedidoLin() {
		try {
			JTable tabla = frm.getJtPedidoLin();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
				DefaultTableModel modeloJtPed = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				tabla.setModel(modeloJtPed);
				modeloJtPed.addColumn("Producto");
				modeloJtPed.addColumn("Fecha-Hora Pedido");
				modeloJtPed.addColumn("Precio Unit");
				modeloJtPed.addColumn("Cantidad");
				modeloJtPed.addColumn("SubTotal");
				for(PedidoLinea pl : mapLineasPedido.values()) {
					Object [] fila = new Object[5];
					fila[0] = pl.getProducto();
					fila[1] = pl.getPedido().getFechaHora();
					fila[2] = pl.getPrecioUnit();
					fila[3] = pl.getCantidad();
					fila[4] = pl.getPrecioUnit()*pl.getCantidad();//fixme revisar decimales
					modeloJtPed.addRow(fila);
				}
				
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						int fila = tabla.rowAtPoint(me.getPoint());
						int cols = tabla.getModel().getColumnCount();
						if (fila > -1 && cols > 1) {
							Producto prod = (Producto) tabla.getModel().getValueAt(fila, 0);
//							Fecha fechaHora = (Fecha) tabla.getModel().getValueAt(fila, 1);
							Integer cant = (Integer) tabla.getModel().getValueAt(fila, 3);
							cargarDatosPedidoLin(prod, cant);
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_LINEASPED);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void setearInfoPedido() {
		clearComponent(getFrm().getTxtPedInfo());
		setStrInfoPedido(new StringBuilder());
		if(getPedidoActual() != null) {
			strInfoPedido.append("Pedido nuevo. \n");
		} else {
			strInfoPedido.append("Pedido existente. \n");
		}
		if(mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
			strInfoPedido.append("Items: \n");
			for(PedidoLinea pl : mapLineasPedido.values()) {
				strInfoPedido.append(pl.getProducto().toString() + "\n");
			}
		}
		clearComponent(getFrm().getTxtPedInfo());
		getFrm().getTxtPedInfo().setText(strInfoPedido.toString());
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void controlarPedido(JToggleButton tglBtnPedido) { 
		Border line;
		if(tglBtnPedido.isSelected()) {
			line = BorderFactory.createLineBorder(Color.BLUE, 1);
			activarControlesPedNuevo();
			//ingresa a nuevo pedido
//			mapLineasPedido = new HashMap<>();
			Pedido pedido = new Pedido();
			pedido.setFechaHora(new Fecha(Fecha.AMDHMS));
			pedido.setPersona(getPersSel());
			pedido.setEstado(EstadoPedido.P);
			setPedidoActual(pedido);
			setearInfoPedido();
		} else {
			line = BorderFactory.createLineBorder(Color.lightGray, 1);
			activarControlesPedExistente();
			setPedidoActual(null);
			setearInfoPedido();
		}
		mapLineasPedido = new HashMap<>();
		getFrm().getTglbtnPedNuevo().setBorder(line);
	}
	
	private void activarControlesPedNuevo() {
		clearForm(getFrm().getContentPane());
		//
		cargarPersonaSeleccionada();
		setContainerEnabled(getFrm().getPnlPedBus(), false);
	}
	private void activarControlesPedExistente() {
		clearForm(getFrm().getContentPane());
		//
		cargarPersonaSeleccionada();
		setContainerEnabled(getFrm().getPnlPedBus(), true);
	}

	public void agregarItemAPedido(JComboBox<Producto> cbxPedProd, JFormattedTextField ftxtPedCant) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPedProd);
			genComp.setComp(ftxtPedCant);
			genComp.setComp(getFrm().getTxtPersDesc());
			if(controlDatosObl(genComp)) {
				if(getPedidoActual() != null) {
					getPedidoActual().setPersona(getPersSel());
					PedidoLinea pl = new PedidoLinea(getPedidoActual());
					pl.setProducto((Producto) cbxPedProd.getSelectedItem());
					pl.setCantidad(Integer.valueOf(ftxtPedCant.getText()));
					Double precioCalcProd = Double.valueOf(getFrm().getFtxtPedLotePrecio().getText());
					
					ConfigDriver cfg = new ConfigDriver();
					Float ivaDeProd = Float.valueOf(cfg.getIva(pl.getProducto().getAplIva().getAplIvaProp()));
					pl.setIva(Converters.obtenerIvaDePrecio(precioCalcProd, ivaDeProd));
					pl.setPrecioUnit(Double.valueOf(getFrm().getFtxtPedLotePrecio().getText()));
					KeyMapLp key = new KeyMapLp(pl.getPedido().getPersona().getIdPersona(), 
												pl.getPedido().getFechaHora().getAsNumber(Fecha.AMDHMS), 
												pl.getProducto().getIdProducto());
					if(!mapLineasPedido.containsKey(key)) {
						mapLineasPedido.put(key, pl);
						cargarJtPedidoLin();
					} else {
						enviarWarning(PED, PEDIDO_LINEA_EXISTE);
					}
				} else {
					enviarWarning(PED, PEDIDO_INEXISTENTE);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

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
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarMapDesdePedido(Pedido pedido) {
		try {
			if(pedido != null && pedido.getListaPedidoLinea() != null && !pedido.getListaPedidoLinea().isEmpty()) {
				mapLineasPedido = new HashMap<>();
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					KeyMapLp key = new KeyMapLp(pedido.getPersona().getIdPersona(), 
									pedido.getFechaHora().getAsNumber(Fecha.AMDHMS), 
									pl.getProducto().getIdProducto());
					mapLineasPedido.put(key, pl);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void obtenerDatosLotePorProducto(JComboBox<Producto> cbxPedidoProd) {
		try {
			if(controlDatosObl(cbxPedidoProd)) {
				Producto prod = (Producto) cbxPedidoProd.getSelectedItem();
				HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProducto(prod.getIdProducto());
				cargarDatosProd(hlpProd);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarDatosProd(HlpProducto hlpProd) {
		if(hlpProd != null) {
			getFrm().getFtxtPedLoteStock().setText(String.valueOf(hlpProd.getStock()));
			getFrm().getFtxtPedLotePrecio().setText(String.valueOf(hlpProd.getPrecioVta()));
		} else {
			getFrm().getFtxtPedLoteStock().setText(CtrlGenerico.CERO);
			getFrm().getFtxtPedLotePrecio().setText(CtrlGenerico.CERO);
		}
	}
	
	private void cargarDatosPedidoLin(Producto prod, Integer cant) {
		ComboBoxModel<TipoProd> cbModelTp = getFrm().getCbxPedidoTp().getModel();
		cbModelTp.setSelectedItem(prod.getTipoProd());
		getFrm().getCbxPedidoTp().setSelectedItem(cbModelTp.getSelectedItem());
		
		ComboBoxModel<Producto> cbModelProd = getFrm().getCbxPedidoProd().getModel();
		cbModelProd.setSelectedItem(prod);
		getFrm().getCbxPedidoProd().setSelectedItem(cbModelProd.getSelectedItem());
		
		getFrm().getTxtPedCant().setText(String.valueOf(cant));
	}
	
	/**
	 * funcion para generar el pedidio nuevo > ManagerPedido
	 */
	public void generarPedido(JDateChooser dchPedFec, JFormattedTextField ftxtPedHora) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchPedFec);
			genComp.setComp(ftxtPedHora);
			if(controlDatosObl(genComp)) {
				if(getPedidoActual() != null && mapLineasPedido != null &&
						!mapLineasPedido.isEmpty()) {
					Pedido pedido = getPedidoActual();
					ArrayList<PedidoLinea> listaPl = new ArrayList<>();
					listaPl.addAll(mapLineasPedido.values());
					pedido.setListaPedidoLinea(listaPl);
					pedido.setUsuario(getUsr());
					if(getFrm().getDchPedFecha() != null) {
						Fecha fechaProg = new Fecha(getFrm().getDchPedFecha().getDate());
						pedido.setFechaProg(fechaProg);
					}
					if(getFrm().getFtxtPedHora().getText() != ":") {
						Fecha horaProg = convertirHoraDesdeTxt(getFrm().getFtxtPedHora().getText());
						pedido.setHoraProg(horaProg);
					}
					mgrPed.generarNuevoPedido(getPedidoActual());
					enviarInfo(PED, PEDIDO_GEN_OK);
				} else {
					enviarWarning(PED, PEDIDO_NO_GENERADO);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void actualizarPedido() {
		// TODO Auto-generated method stub
		
	}
	
	public void generarVenta(JTable jtPedido) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(jtPedido);
			if(controlDatosObl(genComp)) {
				Persona pers = (Persona) jtPedido.getModel().getValueAt(jtPedido.getSelectedRow(), 0);
				Fecha fechaHora = (Fecha) jtPedido.getModel().getValueAt(jtPedido.getSelectedRow(), 1);
				Pedido pedido = mgrPed.obtenerPedidoPorId(pers.getIdPersona(), fechaHora);
				if(enviarConfirm(PED, PEDIDO_CONF_VTA) == CONFIRM_OK) {
					mgrPed.actualizarPedido(pedido, EstadoPedido.C);
					enviarInfo(VTA, VTA_GENERADA_OK);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* BUSCADOR PERS */
	/*****************************************************************************************************************************************************/
	
	public void abrirBuscadorPers() {
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
				enviarWarning(PERS, DATOS_OBLIG);
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
			getCompVal().removeBorder(getFrm().getTxtPersDesc());
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
	
	public Pedido getPedidoActual() {
		return pedidoActual;
	}
	public void setPedidoActual(Pedido pedidoActual) {
		this.pedidoActual = pedidoActual;
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

	public UsuarioDsk getUsr() {
		return usr;
	}
	public void setUsr(UsuarioDsk usr) {
		this.usr = usr;
	}
	
	public StringBuilder getStrInfoPedido() {
		return strInfoPedido;
	}
	public void setStrInfoPedido(StringBuilder strInfoPedido) {
		this.strInfoPedido = strInfoPedido;
	}

	
}
