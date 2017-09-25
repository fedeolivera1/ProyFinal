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

import com.toedter.calendar.JDateChooser;

import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PresentacionException;
import gpd.manager.persona.ManagerPersona;
import gpd.manager.producto.ManagerProducto;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.presentacion.formulario.FrmMovimiento;
import gpd.presentacion.generic.CnstPresExceptions;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.types.ErrorLogico;
import gpd.types.Fecha;

public class CtrlFrmMovimiento extends CtrlGenerico implements CnstPresGeneric {

	private ManagerTransaccion mgrTran = new ManagerTransaccion();
	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerPersona mgrPers = new ManagerPersona();
	private FrmMovimiento frmMov;
	private Transaccion transac;
	private HashMap<Integer, TranLinea> mapLineasTran;
	
	
	public CtrlFrmMovimiento(FrmMovimiento frmMov) {
		super();
		this.frmMov = frmMov;
	}


	/*****************************************************************************************************************************************************/
	/* CONTROLES */
	/*****************************************************************************************************************************************************/
	
	public void cargarCbxProveedor(JComboBox<PersonaJuridica> cbxCompraProv) {
		try {
			cbxCompraProv.removeAllItems();
			List<PersonaJuridica> listaPj;
			listaPj = (ArrayList<PersonaJuridica>) mgrPers.obtenerListaProveedor();
			if(listaPj != null && !listaPj.isEmpty()) {
				for(PersonaJuridica pj : listaPj) {
					cbxCompraProv.addItem(pj);
				}
				cbxCompraProv.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxUtil(JComboBox<Utilidad> cbxUtil) {
		try {
			cbxUtil.removeAllItems();
			List<Utilidad> listaUtil = (ArrayList<Utilidad>) mgrProd.obtenerListaUtilidad();
			if(listaUtil != null && !listaUtil.isEmpty()) {
				for(Utilidad util : listaUtil) {
					cbxUtil.addItem(util);
				}
				cbxUtil.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		try {
			ArrayList<TipoProd> listaTipoProd = (ArrayList<TipoProd>) mgrProd.obtenerListaTipoProd();
			if(listaTipoProd != null && !listaTipoProd.isEmpty()) {
				for(TipoProd tipoProd : listaTipoProd) {
					cbxTipoProd.addItem(tipoProd);
				}
				cbxTipoProd.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxProd(JComboBox<TipoProd> cbxTipoProd, JComboBox<Producto> cbxProd) {
		try {
			cbxProd.removeAllItems();
			if(controlDatosObl(cbxTipoProd)) {
				TipoProd tipoProd = (TipoProd) cbxTipoProd.getSelectedItem();
				ArrayList<Producto> listaProd = (ArrayList<Producto>) mgrProd.obtenerListaProductoPorTipoProd(tipoProd);
				for(Producto prod : listaProd) {
					cbxProd.addItem(prod);
				}
				cbxProd.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarControlesCompra(TranLinea tl, Container containerJTable) {
		try {
			clearControlsInJPanel(containerJTable);
			ComboBoxModel<TipoProd> cbModelTp = frmMov.getCbxCompraTp().getModel();
			cbModelTp.setSelectedItem(tl.getProducto().getTipoProd());
			frmMov.getCbxCompraTp().setSelectedItem(cbModelTp.getSelectedItem());
			ComboBoxModel<Producto> cbModelProd = frmMov.getCbxCompraProd().getModel();
			cbModelProd.setSelectedItem(tl.getProducto());
			frmMov.getCbxCompraTp().setSelectedItem(cbModelTp.getSelectedItem());
			frmMov.getFtxtCompraCant().setText(String.valueOf(tl.getCantidad()));
			frmMov.getFtxtCompraPu().setText(String.valueOf(tl.getPrecioUnit()));
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtCompraItems() {
		try {
			JTable tabla = frmMov.getJtCompraItems();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(mapLineasTran != null && !mapLineasTran.isEmpty()) {
				DefaultTableModel modeloJtCompra = new DefaultTableModel() {
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
				modeloJtCompra.addColumn("");
				modeloJtCompra.addColumn("Producto");
				modeloJtCompra.addColumn("Proveedor");
				modeloJtCompra.addColumn("Cantidad");
				modeloJtCompra.addColumn("Precio Unit");
				tabla.setModel(modeloJtCompra);
				for(TranLinea tl : mapLineasTran.values()) {
					Object [] fila = new Object[5];
					fila[0] = new Boolean(false);
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
				cargarJTableVacia(tabla, JTABLE_SIN_ITEMS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtComprasPend(JComboBox<PersonaJuridica> cbxCompraPj) {
		try {
			JTable tabla = frmMov.getJtComprasPend();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(cbxCompraPj.getSelectedIndex() > -1) {
				PersonaJuridica pj = (PersonaJuridica) cbxCompraPj.getSelectedItem();
				List<Transaccion> listaTransac = mgrTran.obtenerListaTransaccionPorPersona(pj.getRut(), TipoTran.C, EstadoTran.P);
				if(listaTransac != null && !listaTransac.isEmpty()) {
					DefaultTableModel modeloJtComprasPend = new DefaultTableModel() {
						private static final long serialVersionUID = 1L;
						@Override
					    public boolean isCellEditable (int fila, int columna) {
					        return false;
					    }
					};
					modeloJtComprasPend.addColumn("Id Transac");
					modeloJtComprasPend.addColumn("Proveedor");
					modeloJtComprasPend.addColumn("Fecha - Hora");
					modeloJtComprasPend.addColumn("Items (prod|cant)");
					tabla.setModel(modeloJtComprasPend);
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
						public void mouseClicked(MouseEvent me) {
							try {
								int fila = tabla.rowAtPoint(me.getPoint());
								int cols = tabla.getModel().getColumnCount();
								if (fila > -1 && cols > 1) {
									Integer nroTransac = (Integer) tabla.getModel().getValueAt(fila, 0);
									cargarTransaccion(mgrTran.obtenerTransaccionPorId(nroTransac));
									cargarJtCompraItems();
								}
							} catch (PresentacionException  e) {
								enviarError(CnstPresExceptions.DB, e.getMessage());
							}
						}
					});
//					packColumn(tabla, 4, 0); //FIXME ver este metodo para reorganizacion de cols
				} else {
					cargarJTableVacia(tabla, JTABLE_SIN_COMPRAS);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarPrecioProd(JComboBox<Producto> cbxCompraProd) {
		try {
			if(cbxCompraProd.getSelectedItem() != null) {
				Producto prod = (Producto) cbxCompraProd.getSelectedItem();
				clearComponent(frmMov.getFtxtCompraCant());
				frmMov.getFtxtCompraPu().setText(String.valueOf(prod.getPrecio()));
			} else {
				clearComponent(frmMov.getFtxtCompraPu());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//VENTAS
	
	public void cargarJtVenta(List<Transaccion> listaTransac) {
		try {
			JTable tabla = getFrm().getJtVenta();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				DefaultTableModel modeloJtVenta = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				modeloJtVenta.addColumn("Id Transac");
				modeloJtVenta.addColumn("Cliente");
				modeloJtVenta.addColumn("Fecha - Hora");
				modeloJtVenta.addColumn("Items (prod|cant)");
				modeloJtVenta.addColumn("SubTotal");
				modeloJtVenta.addColumn("Iva");
				modeloJtVenta.addColumn("Total");
				tabla.setModel(modeloJtVenta);
				for(Transaccion transac : listaTransac) {
					Object [] fila = new Object[7];
					String datoPersona = null;
					if(transac.getPersona() instanceof PersonaFisica) {
						PersonaFisica pf = (PersonaFisica) transac.getPersona();
						datoPersona = "[CLI] " + pf.toString();
					} else if(transac.getPersona() instanceof PersonaJuridica) {
						PersonaJuridica pj = (PersonaJuridica) transac.getPersona();
						datoPersona = "[EMP] " + pj.toString();
					}
					
					fila[0] = transac.getNroTransac();
					fila[1] = datoPersona;
					fila[2] = transac.getFechaHora().toString(Fecha.AMDHMS);
					fila[3] = transac.toStringLineas(); 
					fila[4] = transac.getSubTotal(); 
					fila[5] = transac.getIva(); 
					fila[6] = transac.getTotal(); 
					modeloJtVenta.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						try {
							int fila = tabla.rowAtPoint(me.getPoint());
							int cols = tabla.getModel().getColumnCount();
							if (fila > -1 && cols > 1) {
								Integer nroTransac = (Integer) tabla.getModel().getValueAt(fila, 0);
								Transaccion transac = mgrTran.obtenerTransaccionPorId(nroTransac);
								clearComponent(getFrm().getScrollPaneVentaLin());
								clearComponent(getFrm().getScrollPaneVentaLinLote());
								cargarJtVentaItems(transac);
							}
						} catch (PresentacionException  e) {
							enviarError(CnstPresExceptions.DB, e.getMessage());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_VENTAS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtVentaItems(Transaccion transac) {
		try {
			JTable tabla = getFrm().getJtVentaLin();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(transac != null && transac.getListaTranLinea() != null && 
					!transac.getListaTranLinea().isEmpty()) {
				DefaultTableModel modeloJtVentaLin = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				modeloJtVentaLin.addColumn("NroTx");
				modeloJtVentaLin.addColumn("Producto");
				modeloJtVentaLin.addColumn("Cantidad");
				modeloJtVentaLin.addColumn("Iva");
				modeloJtVentaLin.addColumn("Precio Unit");
				tabla.setModel(modeloJtVentaLin);
				for(TranLinea tl : transac.getListaTranLinea()) {
					Object [] fila = new Object[5];
					fila[0] = tl.getTransaccion().getNroTransac();
					fila[1] = tl.getProducto();
					fila[2] = tl.getCantidad();
					fila[3] = tl.getIva();
					fila[4] = tl.getPrecioUnit();
					modeloJtVentaLin.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						int fila = tabla.rowAtPoint(me.getPoint());
						int cols = tabla.getModel().getColumnCount();
						if (fila > -1 && cols > 1) {
							Integer nroTransac = (Integer) tabla.getModel().getValueAt(fila, 0);
							Producto prod = (Producto) tabla.getModel().getValueAt(fila, 1);
							cargarJtVentaItemsLotes(nroTransac, prod.getIdProducto());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_ITEMS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtVentaItemsLotes(Integer nroTransac, Integer idProd) {
		try {
			JTable tabla = getFrm().getJtVentaLinLote();
			clearTable(tabla);
			deleteModelTable(tabla);
			List<TranLineaLote> listaTll = mgrTran.obtenerListaTranLineaLote(nroTransac, idProd);
			if(listaTll != null && !listaTll.isEmpty()) {
				DefaultTableModel modeloJtVentaLinLote = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable (int fila, int columna) {
						return false;
					}
				};
				modeloJtVentaLinLote.addColumn("Id Lote");
				modeloJtVentaLinLote.addColumn("Deposito");
				modeloJtVentaLinLote.addColumn("Cnt. Vta");
				modeloJtVentaLinLote.addColumn("Cnt. Stock");
				tabla.setModel(modeloJtVentaLinLote);
				for(TranLineaLote tll : listaTll) {
					Object [] fila = new Object[4];
					fila[0] = tll.getLote().getIdLote();
					fila[1] = tll.getLote().getDeposito();
					fila[2] = tll.getCantidad();
					fila[3] = tll.getLote().getStock();
					modeloJtVentaLinLote.addRow(fila);
				}
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_ITEMS);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	public void iniciarCompra(FrmMovimiento frm) {
		clearForm(frm.getContentPane());
		nuevaTransaccion();
		setContainerEnabled(frm.getPnlCompraProv(), true);
		setContainerEnabled(frm.getPnlCompraDatos(), false);
	}
	
	public void limpiarCompra(FrmMovimiento frm, Boolean confirma) {
		try {
			if(confirma ? enviarConfirm(MOV, CONF_LIMPIAR) == CONFIRM_OK : true) {
				setContainerEnabled(frm.getPnlCompraDatos(), false);
				setContainerEnabled(frm.getPnlCompraProv(), false);
				clearForm(frm.getContentPane());
				this.setTransac(null);
				mapLineasTran = null;
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void agregarItemCompra(JComboBox<PersonaJuridica> cbxCompraPj, JComboBox<Producto> cbxCompraProd, JFormattedTextField ftxtCompraCant,
			JFormattedTextField ftxtCompraPu) {
		try {
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
					TranLinea tl = new TranLinea(getTransac());
					tl.setProducto(prod);
					tl.setCantidad(new Integer(ftxtCompraCant.getText()));
					tl.setPrecioUnit(new Double(ftxtCompraPu.getText()));
					mapLineasTran.put(prod.getIdProducto(), tl);
					//cargo tabla con lista de lineas
					cargarJtCompraItems();
				} else {
					enviarWarning(MOV, COMPRA_PROD_YA_ING);
				}
			} else {
				enviarWarning(MOV, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void nuevaTransaccion() {
		setTransac(new Transaccion(TipoTran.C));
		mapLineasTran = new HashMap<>();
		//se HABILITA generar compra ya que selecciona una existente
		setContainerEnabled(getFrm().getPnlGenerarCompra(), true);
	}
	
	private void cargarTransaccion(Transaccion transac) {
		try {
			if(transac != null) {
				//se deshabilita genrar compra ya que selecciona una existente
				setContainerEnabled(getFrm().getPnlGenerarCompra(), false);
				setTransac(transac);
				if(transac.getListaTranLinea() != null && !transac.getListaTranLinea().isEmpty()) {
					mapLineasTran = new HashMap<>();
					for(TranLinea tl : transac.getListaTranLinea()) {
						mapLineasTran.put(tl.getProducto().getIdProducto(), tl);
					}
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarCompra() {
		try {
			if(getTransac() != null) {
				if(mapLineasTran != null && !mapLineasTran.isEmpty()) {
					getTransac().getListaTranLinea().addAll(mapLineasTran.values());
					mgrTran.generarTransaccionCompra(getTransac());
					limpiarCompra(getFrm(), false);
					enviarInfo(MOV, COMPRA_GENERADA);
				} else {
					enviarWarning(MOV, COMPRA_SIN_LINEAS);
				}
			} else {
				enviarWarning(MOV, COMPRA_SIN_TRANSAC);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void anularCompra() {
		try {
			GenCompType gct = new GenCompType();
			gct.setComp(frmMov.getJtComprasPend());
			if(controlDatosObl(gct) && getTransac() != null) {
				if(enviarConfirm(MOV, COMPRA_ANU_CONF) == CONFIRM_OK) {
					mgrTran.anularTransaccionCompra(getTransac());
					cargarJtComprasPend(getFrm().getCbxCompraProv());
					clearPanel(getFrm().getPnlCompraDatos());
					clearPanel(getFrm().getPnlCompraItems());
					enviarInfo(MOV, COMPRA_ANULADA);
				}
			} else {
				enviarWarning(MOV, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void modificarItemCompra(JComboBox<PersonaJuridica> cbxCompraPj, JComboBox<Producto> cbxCompraProd, JFormattedTextField ftxtCompraCant,
			JFormattedTextField ftxtCompraPu) {
		try {
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
					enviarWarning(MOV, COMPRA_NOMODIF_PROD);
				}
				cargarJtCompraItems();
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void eliminarItemCompra() {
		try {
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
				clearPanel(frmMov.getPnlCompraDatos());
				cargarJtCompraItems();
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	public void obtenerVentas(JDateChooser dchVtaIni, JDateChooser dchVtaFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchVtaIni);
			genComp.setComp(dchVtaFin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchVtaIni.getDate());
				Fecha fechaFin = new Fecha(dchVtaFin.getDate());
				List<Transaccion> listaTransac = mgrTran.obtenerListaTransaccionPorPeriodo(TipoTran.V, EstadoTran.C, fechaIni, fechaFin);
				clearScrollPane(getFrm().getScrollPaneVentaLin());
				cargarJtVenta(listaTransac);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void anularVenta() {
		try {
			GenCompType gct = new GenCompType();
			gct.setComp(frmMov.getJtVenta());
			if(controlDatosObl(gct)) {
				if(enviarConfirm(VTA, VTA_CONF_ANULA) == CONFIRM_OK) {
					Integer nroTransac = (Integer) frmMov.getJtVenta().getModel().getValueAt(frmMov.getJtVenta().getSelectedRow(), 0);
					Transaccion transac = (Transaccion) mgrTran.obtenerTransaccionPorId(nroTransac);
					ErrorLogico error = mgrTran.anularTransaccionVenta(transac);
					if(error == null) {
						limpiarVenta();
						enviarInfo(VTA, VTA_ANULADA);
					} else {
						enviarWarning(VTA, error.getDescripcion());
					}
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void limpiarVenta() {
		clearForm(getFrm().getContentPane());
	}
	
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmMovimiento getFrm() {
		return frmMov;
	}
	public void setFrm(FrmMovimiento frm) {
		this.frmMov = frm;
	}


	public Transaccion getTransac() {
		return transac;
	}
	public void setTransac(Transaccion transac) {
		this.transac = transac;
	}

	
}
