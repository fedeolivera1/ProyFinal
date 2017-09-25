package gpd.presentacion.controlador;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
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

	private static final String ESC = "\n";
	private FrmPedido frm; 
	private UsuarioDsk usr;
	private JDesktopPane deskPane;
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private ManagerPedido mgrPed = new ManagerPedido();
	private Pedido pedidoInt;
	private Boolean esNuevo = false;
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
	
	public void cargarCbxPedidoOrigen(JComboBox<Origen> cbxPedidoOrig) {
		try {
			cbxPedidoOrig.removeAllItems();
			for(Origen origen : Origen.values()) {
				cbxPedidoOrig.addItem(origen);
			}
			cbxPedidoOrig.setSelectedIndex(-1);
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
				modeloJtPed.addColumn("Origen");
				modeloJtPed.addColumn("Estado");
				modeloJtPed.addColumn("Total");
				for(Pedido pedido : listaPedido) {
					Object [] fila = new Object[6];
					fila[0] = pedido.getPersona();
					fila[1] = pedido.getFechaHora();
					fila[2] = pedido.getListaPedidoLinea().size();
					fila[3] = pedido.getOrigen().getOrigen();
					fila[4] = pedido.getEstado().getEstadoPedido();
					Double precioTotal = Converters.redondearDosDec(pedido.getTotal()); 
					fila[5] = precioTotal;
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
								setPedidoInt(pedido);
								ctrlPb.setPersSel(pedido.getPersona());
								ctrlPb.cargarPersonaSeleccionada(getFrm().getTxtPersDesc());
								cargarMapDesdePedido(pedido);
								cargarJtPedidoLin();
								setearInfoPedido();
								manejarControlesPorEstado(pedido);
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
						return columna == 0 ? true : false;
				    }
					@Override
					public Class<?> getColumnClass(int column) {
						return column == 0 ? Boolean.class : Object.class;
					}
				};
				tabla.setModel(modeloJtPed);
				modeloJtPed.addColumn("");
				modeloJtPed.addColumn("Producto");
				modeloJtPed.addColumn("Fecha-Hora");
				modeloJtPed.addColumn("Precio Unit");
				modeloJtPed.addColumn("Cantidad");
				modeloJtPed.addColumn("SubTotal");
				for(PedidoLinea pl : mapLineasPedido.values()) {
					Object [] fila = new Object[6];
					fila[0] = new Boolean(false);
					fila[1] = pl.getProducto();
					fila[2] = pl.getPedido().getFechaHora();
					fila[3] = Converters.redondearDosDec(pl.getPrecioUnit());
					fila[4] = pl.getCantidad();
					fila[5] = Converters.redondearDosDec(pl.getPrecioUnit() * pl.getCantidad());
					modeloJtPed.addRow(fila);
				}
				
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						int fila = tabla.rowAtPoint(me.getPoint());
						int cols = tabla.getModel().getColumnCount();
						if (fila > -1 && cols > 1) {
							Producto prod = (Producto) tabla.getModel().getValueAt(fila, 1);
							Integer cant = (Integer) tabla.getModel().getValueAt(fila, 4);
							cargarDatosPedidoLin(prod, cant);
						}
					}
				});
				setearInfoPedido();
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
		if(esNuevo) {
			strInfoPedido.append("Pedido nuevo.").append(ESC);
		} else {
			strInfoPedido.append("Pedido existente.");
			if(getPedidoInt() != null) {
				strInfoPedido.append(" [").append(getPedidoInt().getEstado().getEstadoPedido()).append("]");
			}
			strInfoPedido.append(ESC);
		}
		if(mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
			strInfoPedido.append("Items:").append(ESC);
			for(PedidoLinea pl : mapLineasPedido.values()) {
				strInfoPedido.append(pl.getProducto().toString()).append(ESC);
			}
		}
		clearComponent(getFrm().getTxtPedInfo());
		getFrm().getTxtPedInfo().setText(strInfoPedido.toString());
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void controlarPedido() {
		try {
			if(getFrm().getTglbtnPedNuevo().isSelected()) {
				esNuevo = true;
				activarControlesPedNuevo();
				//ingresa a nuevo pedido
				Pedido pedido = new Pedido();
				pedido.setFechaHora(new Fecha(Fecha.AMDHMS));
				pedido.setPersona(ctrlPb.getPersSel());
				pedido.setEstado(EstadoPedido.P);
				pedido.setSinc(Sinc.S);//seteo sinc en 'S' ya que será un pedido dsk que no tendra necesidad de sinc.
				setPedidoInt(pedido);
				mapLineasPedido = null;
				setearInfoPedido();
				manejarControlesPorEstado(pedido);
			} else {
				esNuevo = false;
				activarControlesPedExistente();
				setPedidoInt(null);
				mapLineasPedido = null;
				setearInfoPedido();
			}
			mapLineasPedido = new HashMap<>();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
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
		ctrlPb.setPersSel(null);
		setContainerEnabled(getFrm().getPnlPedBus(), true);
	}
	
	private void manejarControlesPorEstado(Pedido pedido) {
		if(pedido != null) {
			if( EstadoPedido.A.equals(pedido.getEstado()) || 
					EstadoPedido.C.equals(pedido.getEstado()) ) {
				//estado Anulado[A] | Confirmado[C]
				getFrm().getBtnPedGenVenta().setEnabled(false);
				getFrm().getBtnPedGenPedido().setEnabled(false);
				getFrm().getBtnPedActPedido().setEnabled(false);
				getFrm().getBtnPedAnuPedido().setEnabled(false);
				setContainerEnabled(getFrm().getPnlDatosPedido(), false);
				setContainerEnabled(getFrm().getPnlPedidoLin(), false);
				getFrm().getJtPedidoLin().setEnabled(false);
			} else if( EstadoPedido.R.equals(pedido.getEstado()) ) {
				//estado Revision[R]
				getFrm().getBtnPedGenVenta().setEnabled(true);
				getFrm().getBtnPedGenPedido().setEnabled(false);
				getFrm().getBtnPedActPedido().setEnabled(true);
				getFrm().getBtnPedAnuPedido().setEnabled(true);
				setContainerEnabled(getFrm().getPnlDatosPedido(), true);
				setContainerEnabled(getFrm().getPnlPedidoLin(), true);
				getFrm().getJtPedidoLin().setEnabled(true);
				if(Origen.W.equals(pedido.getOrigen()) && 
						Sinc.S.equals(pedido.getSinc())) { //casos web
					getFrm().getBtnPedGenVenta().setEnabled(false);
					getFrm().getBtnPedActPedido().setEnabled(false);
					getFrm().getBtnPedAnuPedido().setEnabled(false);
					setContainerEnabled(getFrm().getPnlPedidoLin(), false);
					getFrm().getJtPedidoLin().setEnabled(false);
				} else if(Origen.W.equals(pedido.getOrigen()) && 
						Sinc.N.equals(pedido.getSinc())) {
					getFrm().getBtnPedGenVenta().setEnabled(false);
				}
			} else if(EstadoPedido.P.equals(pedido.getEstado())) {
				//estado Pendiente[P]
				getFrm().getBtnPedGenVenta().setEnabled(true);
				getFrm().getBtnPedGenPedido().setEnabled(true);
				getFrm().getBtnPedAnuPedido().setEnabled(true);
				getFrm().getBtnPedActPedido().setEnabled(true);
				setContainerEnabled(getFrm().getPnlDatosPedido(), true);
				setContainerEnabled(getFrm().getPnlPedidoLin(), true);
				getFrm().getJtPedidoLin().setEnabled(true);
			} else if(EstadoPedido.F.equals(pedido.getEstado())) {
				//estado PreConfirmado[F] (permite solo generar la venta o anularlo)
				getFrm().getBtnPedGenVenta().setEnabled(true);
				getFrm().getBtnPedGenPedido().setEnabled(false);
				getFrm().getBtnPedAnuPedido().setEnabled(true);
				getFrm().getBtnPedActPedido().setEnabled(false);
				setContainerEnabled(getFrm().getPnlDatosPedido(), false);
				setContainerEnabled(getFrm().getPnlPedidoLin(), false);
				getFrm().getJtPedidoLin().setEnabled(false);
			} else if(EstadoPedido.X.equals(pedido.getEstado())) {
				//estado Rechazado[X] < solo web
				getFrm().getBtnPedGenVenta().setEnabled(false);
				getFrm().getBtnPedGenPedido().setEnabled(false);
				getFrm().getBtnPedAnuPedido().setEnabled(false);
				getFrm().getBtnPedActPedido().setEnabled(false);
				setContainerEnabled(getFrm().getPnlDatosPedido(), false);
				setContainerEnabled(getFrm().getPnlPedidoLin(), false);
				getFrm().getJtPedidoLin().setEnabled(false);
			} else {
				getFrm().getBtnPedGenVenta().setEnabled(true);
				getFrm().getBtnPedGenPedido().setEnabled(true);
				getFrm().getBtnPedAnuPedido().setEnabled(true);
				getFrm().getBtnPedActPedido().setEnabled(true);
				setContainerEnabled(getFrm().getPnlDatosPedido(), true);
				setContainerEnabled(getFrm().getPnlPedidoLin(), true);
				getFrm().getJtPedidoLin().setEnabled(true);
			}
		} else {
			getFrm().getBtnPedGenVenta().setEnabled(true);
			getFrm().getBtnPedGenPedido().setEnabled(true);
			getFrm().getBtnPedAnuPedido().setEnabled(true);
			getFrm().getBtnPedActPedido().setEnabled(true);
			setContainerEnabled(getFrm().getPnlDatosPedido(), true);
			setContainerEnabled(getFrm().getPnlPedidoLin(), true);
			getFrm().getJtPedidoLin().setEnabled(true);
		}
	}

	public void agregarItemAPedido(JComboBox<Producto> cbxPedProd, JFormattedTextField ftxtPedCant) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPedProd);
			genComp.setComp(ftxtPedCant);
			genComp.setComp(getFrm().getTxtPersDesc());
			if(controlDatosObl(genComp)) {
				if(getPedidoInt() != null) {
					getPedidoInt().setPersona(ctrlPb.getPersSel());
					PedidoLinea pl = new PedidoLinea(getPedidoInt());
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
				if(getPedidoInt() != null) {
					Producto prod = (Producto) jtPedidoLin.getValueAt(jtPedidoLin.getSelectedRow(), 1);//posi prod
					Fecha fechaHora = (Fecha) jtPedidoLin.getValueAt(jtPedidoLin.getSelectedRow(), 2);//posi fecha-hora pedido
					
					KeyMapLp key = new KeyMapLp(getPedidoInt().getPersona().getIdPersona(), 
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
						if(Origen.W.equals(pl.getPedido().getOrigen())) {
							//luego de modificar una linea de un pedido web, no permitirá generar la venta (debe sinc)
							getFrm().getBtnPedGenVenta().setEnabled(false);
						}
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
	
	public void eliminarItemPedido() {
		try {
			if(this.getFrm().getJtPedidoLin().getModel().getRowCount() > 0 && 
					this.getFrm().getJtPedidoLin().getModel().getColumnCount() > 1) {
				for(int i=0; i< getFrm().getJtPedidoLin().getModel().getRowCount(); i++) {
					Boolean checked = (Boolean) getFrm().getJtPedidoLin().getModel().getValueAt(i, 0);
					if (checked) {
						Producto prod = (Producto) getFrm().getJtPedidoLin().getModel().getValueAt(i, 1);//posi prod
						Fecha fechaHora = (Fecha) getFrm().getJtPedidoLin().getModel().getValueAt(i, 2);//posi fecha-hora pedido
						KeyMapLp key = new KeyMapLp(getPedidoInt().getPersona().getIdPersona(), 
								fechaHora.getAsNumber(Fecha.AMDHMS), 
								prod.getIdProducto());
						if(mapLineasPedido.containsKey(key)) {
							mapLineasPedido.remove(key);
						}
					}
				}
				clearPanel(frm.getPnlDatosPedido());
				cargarJtPedidoLin();
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	public void obtenerPedidos(JComboBox<EstadoPedido> cbxPedidoEstado, JTextField txtPedidoPers, JComboBox<Origen> cbxPedidoOrig, JDateChooser dchPedFecIni, JDateChooser dchPedFecFin) {
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
					Origen orig = (Origen) cbxPedidoOrig.getSelectedItem();
					Long idPersona = ctrlPb.getPersSel() != null ? mgrPers.obtenerIdPersonaGenerico(ctrlPb.getPersSel()) : null;
					List<Pedido> listaPedido = mgrPed.obtenerListaPedidoPorPeriodo(ep, idPersona, orig, fechaIni, fechaFin);
					cargarJtPedido(listaPedido);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void limpiarPedido() {
		clearForm(getFrm().getContentPane());
		if(ctrlPb.getPersSel() != null) {
			ctrlPb.setPersSel(null);
		}
		mapLineasPedido.clear();
		manejarControlesPorEstado(null);
		getFrm().getTglbtnPedNuevo().setSelected(false);
		getFrm().getTglbtnPedExist().setSelected(false);
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
				if(pedido.getFechaProg() != null) {
					getFrm().getDchPedFecha().setDate(pedido.getFechaProg().getTime());
				} else {
					clearComponent(getFrm().getDchPedFecha());
				}
				if(pedido.getHoraProg() != null) {
					getFrm().getFtxtPedHora().setText(pedido.getHoraProg().toString(Fecha.HM));
				} else {
					clearComponent(getFrm().getFtxtPedHora());
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void obtenerDatosLotePorProducto(JComboBox<Producto> cbxPedidoProd) {
		try {
			if(controlDatosObl(cbxPedidoProd)) {
				if(cbxPedidoProd.getSelectedIndex() > -1) {
					Producto prod = (Producto) cbxPedidoProd.getSelectedItem();
					HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProducto(prod.getIdProducto());
					cargarDatosProd(hlpProd);
				} else {
					cargarDatosProd(null);
				}
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
		try {
			ComboBoxModel<TipoProd> cbModelTp = getFrm().getCbxPedidoTp().getModel();
			cbModelTp.setSelectedItem(prod.getTipoProd());
			getFrm().getCbxPedidoTp().setSelectedItem(cbModelTp.getSelectedItem());
			
			ComboBoxModel<Producto> cbModelProd = getFrm().getCbxPedidoProd().getModel();
			cbModelProd.setSelectedItem(prod);
			getFrm().getCbxPedidoProd().setSelectedItem(cbModelProd.getSelectedItem());
			
			getFrm().getTxtPedCant().setText(String.valueOf(cant));
			HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProducto(prod.getIdProducto());
			cargarDatosProd(hlpProd);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
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
				if(mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
					Pedido pedido = getPedidoInt();
					ArrayList<PedidoLinea> listaPl = new ArrayList<>();
					listaPl.addAll(mapLineasPedido.values());
					pedido.setListaPedidoLinea(listaPl);
					pedido.setUsuario(getUsr());
					if(getFrm().getDchPedFecha() != null) {
						Fecha fechaProg = new Fecha(getFrm().getDchPedFecha().getDate());
						pedido.setFechaProg(fechaProg);
					}
					if(getFrm().getFtxtPedHora().getText().trim() != ":") {
						Fecha horaProg = convertirHoraDesdeTxt(getFrm().getFtxtPedHora().getText());
						pedido.setHoraProg(horaProg);
					}
					Integer res = mgrPed.generarNuevoPedido(pedido);
					if(res > 0) {
						enviarInfo(PED, PEDIDO_GEN_OK);
						limpiarPedido();
					}
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
				if(enviarConfirm(PED, PEDIDO_ACT_CONF) == CONFIRM_OK) {
					if(getPedidoInt() != null && (getPedidoInt().getEstado().equals(EstadoPedido.P) || getPedidoInt().getEstado().equals(EstadoPedido.R)) 
							&& mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
						Pedido pedido = getPedidoInt();
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
						String res = mgrPed.actualizarPedido(pedido, EstadoPedido.R, getUsr());
						if(null == res) {
							enviarInfo(PED, PEDIDO_ACT_OK);
							limpiarPedido();
						}
					} else {
						enviarWarning(PED, PEDIDO_SIN_LINEAS);
					}
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void anularPedido(JTable jtPedido) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(jtPedido);
			if(controlDatosObl(genComp)) {
				if(enviarConfirm(PED, PEDIDO_ANU_CONF) == CONFIRM_OK) {
					Pedido pedido = getPedidoInt();
					String res = mgrPed.actualizarPedido(pedido, EstadoPedido.A, getUsr());
					if(null == res) {
						enviarInfo(PED, PEDIDO_ANU_OK);
						limpiarPedido();
					}
				} else {
					
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
				if(controlPedidoParaVenta()) {
					if(enviarConfirm(PED, VTA_CONF_GEN) == CONFIRM_OK) {
						String control = mgrPed.actualizarPedido(pedido, EstadoPedido.C, getUsr());
						if(null == control) {
							enviarInfo(VTA, VTA_GENERADA_OK);
							limpiarPedido();
						} else {
							enviarWarning(PED, control);
						}
					}
				} else {
					enviarWarning(PED, PEDIDO_ITEMS_ACT);
				}
			} else {
				enviarWarning(PED, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private Boolean controlPedidoParaVenta() {
		if(mapLineasPedido != null && !mapLineasPedido.isEmpty()) {
			for(PedidoLinea pl : mapLineasPedido.values()) {
				if(pl.getPrecioUnit().doubleValue() == new Double(0)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void abrirIfrmPersBuscador(Container contentPane, JDesktopPane deskPane, JTextField txtPersDesc) {
		try {
			ctrlPb.abrirBuscadorPers(contentPane, deskPane, txtPersDesc);
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
	
	public Pedido getPedidoInt() {
		return pedidoInt;
	}
	public void setPedidoInt(Pedido pedidoInt) {
		this.pedidoInt = pedidoInt;
	}

	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
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
