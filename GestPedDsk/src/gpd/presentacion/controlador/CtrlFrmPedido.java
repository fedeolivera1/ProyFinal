package gpd.presentacion.controlador;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
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
import gpd.dominio.persona.Persona;
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
//	private Persona persSel;
//	private IfrmPersBuscador ifrmPb;
	private Pedido pedidoActual;
	private CtrlFrmPersBuscador ctrlPb;
	private HashMap<KeyMapLp, PedidoLinea> mapLineasPedido;
	private StringBuilder strInfoPedido;
	

	public CtrlFrmPedido(FrmPedido frmPed, UsuarioDsk usr) {
		super();
		this.setFrm(frmPed);
		this.setUsr(usr);
		ctrlPb = new CtrlFrmPersBuscador();
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
								setPedidoActual(pedido);
								ctrlPb.cargarPersonaSeleccionada(getFrm().getTxtPersDesc());
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
					fila[4] = pl.getPrecioUnit() * pl.getCantidad();//fixme revisar decimales
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
			Pedido pedido = new Pedido();
			pedido.setFechaHora(new Fecha(Fecha.AMDHMS));
			pedido.setPersona(ctrlPb.getPersSel());
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
		ctrlPb.setPersSel(null);
		//
		ctrlPb.cargarPersonaSeleccionada(getFrm().getTxtPersDesc());
		setContainerEnabled(getFrm().getPnlPedBus(), false);
	}
	private void activarControlesPedExistente() {
		clearForm(getFrm().getContentPane());
		//
		ctrlPb.cargarPersonaSeleccionada(getFrm().getTxtPersDesc());
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
					getPedidoActual().setPersona(ctrlPb.getPersSel());
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
	
	public void modificarItemAPedido(JTable jtPedidoLin, JComboBox<Producto> cbxPedProd, JFormattedTextField ftxtPedCant) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(jtPedidoLin);
			genComp.setComp(cbxPedProd);
			genComp.setComp(ftxtPedCant);
			genComp.setComp(getFrm().getTxtPersDesc());
			if(controlDatosObl(genComp)) {
				if(getPedidoActual() != null) {
					Producto prod = (Producto) jtPedidoLin.getValueAt(jtPedidoLin.getSelectedRow(), 0);//posi prod
					Fecha fechaHora = (Fecha) jtPedidoLin.getValueAt(jtPedidoLin.getSelectedRow(), 1);//posi fecha-hora pedido
					
					KeyMapLp key = new KeyMapLp(getPedidoActual().getPersona().getIdPersona(), 
							fechaHora.getAsNumber(Fecha.AMDHMS), 
							prod.getIdProducto());
					if(mapLineasPedido.containsKey(key)) {
						PedidoLinea pl = mapLineasPedido.get(key);
						Double precioCalcProd = Double.valueOf(getFrm().getFtxtPedLotePrecio().getText());
						ConfigDriver cfg = new ConfigDriver();
						Float ivaDeProd = Float.valueOf(cfg.getIva(pl.getProducto().getAplIva().getAplIvaProp()));
						
						pl.setProducto((Producto) cbxPedProd.getSelectedItem());
						pl.setCantidad(Integer.valueOf(ftxtPedCant.getText()));
						pl.setIva(Converters.obtenerIvaDePrecio(precioCalcProd, ivaDeProd));
						pl.setPrecioUnit(Double.valueOf(getFrm().getFtxtPedLotePrecio().getText()));
						cargarJtPedidoLin(); 
					} else {
						enviarWarning(PED, PEDIDO_LINEA_NO_EXISTE);
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
					Long idPersona = ctrlPb.getPersSel() != null ? mgrPers.obtenerIdPersonaGenerico(ctrlPb.getPersSel()) : null;
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
	
	public void actualizarPedido(JDateChooser dchPedFec, JFormattedTextField ftxtPedHora, JTable jtPedido) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchPedFec);
			genComp.setComp(ftxtPedHora);
			genComp.setComp(jtPedido);
			if(controlDatosObl(genComp)) {
				if(getPedidoActual() != null && (getPedidoActual().getEstado().equals(EstadoPedido.P) || getPedidoActual().getEstado().equals(EstadoPedido.R)) 
						&& mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
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
					mgrPed.actualizarPedido(pedido, EstadoPedido.P);
					enviarInfo(PED, PEDIDO_ACT_OK);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarVenta(JTable jtPedido) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(jtPedido);
			if(controlDatosObl(genComp)) {
				Persona pers = (Persona) jtPedido.getModel().getValueAt(jtPedido.getSelectedRow(), 0);
				Fecha fechaHora = (Fecha) jtPedido.getModel().getValueAt(jtPedido.getSelectedRow(), 1);
				Pedido pedido = mgrPed.obtenerPedidoPorId(pers.getIdPersona(), fechaHora);
				if(enviarConfirm(PED, VTA_CONF_GEN) == CONFIRM_OK) {
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
	
	public void abrirIfrmPersBuscador(Container contentPane, JDesktopPane deskPane, JTextField txtPersDesc) {
		ctrlPb.abrirBuscadorPers(contentPane, deskPane, txtPersDesc);
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
	
//	public Persona getPersSel() {
//		return persSel;
//	}
//	public void setPersSel(Persona persSel) {
//		this.persSel = persSel;
//	}
	
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
	
//	public IfrmPersBuscador getIfrmPb() {
//		return ifrmPb;
//	}
//	public void setIfrmPb(IfrmPersBuscador ifrmPb) {
//		this.ifrmPb = ifrmPb;
//	}

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
