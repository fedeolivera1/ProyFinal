package gpd.presentacion.controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.helper.HlpProducto;
import gpd.dominio.producto.AplicaIva;
import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Unidad;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PresentacionException;
import gpd.manager.producto.ManagerProducto;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.presentacion.formulario.FrmProducto;
import gpd.presentacion.generic.CnstPresExceptions;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmDeposito;
import gpd.presentacion.popup.IfrmTipoProd;
import gpd.presentacion.popup.IfrmUnidad;
import gpd.presentacion.popup.IfrmUtilidad;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public class CtrlFrmProducto extends CtrlGenerico implements CnstPresGeneric {

	private ManagerProducto mgrProd = new ManagerProducto();
	private ManagerTransaccion mgrTransac = new ManagerTransaccion();
	private FrmProducto frmProd;
	private IfrmTipoProd iFrmTp;
	private IfrmDeposito iFrmDep;
	private IfrmUtilidad iFrmUtil;
	private IfrmUnidad iFrmUni;
	private JDesktopPane deskPane;
	private HashMap<Integer, Lote> hashLotes;
	
	
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
		try {
			cbxTipoProd.removeAllItems();
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
	
	public void cargarCbxUnidad(JComboBox<Unidad> cbxProUni) {
		try {
			cbxProUni.removeAllItems();
			ArrayList<Unidad> listaUnidad = (ArrayList<Unidad>) mgrProd.obtenerListaUnidad();
			if(listaUnidad != null && !listaUnidad.isEmpty()) {
				for(Unidad uni : listaUnidad) {
					cbxProUni.addItem(uni);
				}
				cbxProUni.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	public void cargarCbxAplicaIva(JComboBox<AplicaIva> cbxProAplIva) {
		try {
			cbxProAplIva.removeAllItems();
			for(AplicaIva aplIva : AplicaIva.values()) {
				cbxProAplIva.addItem(aplIva);
			}
			cbxProAplIva.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarListTipoProd(JList<TipoProd> jlTipoProd) {
		try {
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
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarListUnidad(JList<Unidad> jlUnidad) {
		try {
			DefaultListModel<Unidad> dlm = new DefaultListModel<>();
			dlm.clear();
			ArrayList<Unidad> listaUnidad = (ArrayList<Unidad>) mgrProd.obtenerListaUnidad();
			if(listaUnidad != null && !listaUnidad.isEmpty()) {
				for(Unidad unidad : listaUnidad) {
					dlm.addElement(unidad);
				}
				jlUnidad.setModel(dlm);
				jlUnidad.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesTipoProd(JTextField txtTpDesc, JList<TipoProd> jlTipoProd) {
		try {
			if(controlDatosObl(jlTipoProd)) {
				TipoProd tp = (TipoProd) jlTipoProd.getSelectedValue();
				txtTpDesc.setText(tp.getDescripcion());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesUnidad(JTextField txtUniNom, JList<Unidad> jlUni) {
		try {
			if(controlDatosObl(jlUni)) {
				Unidad uni = (Unidad) jlUni.getSelectedValue();
				txtUniNom.setText(uni.getNombre());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//prod
	public void cargarJtProd(List<Producto> listaProd) {
		try {
			JTable tabla = frmProd.getJtProd();
			clearTable(tabla);
			if(listaProd != null && !listaProd.isEmpty()) {
				DefaultTableModel modeloJtProd = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
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
					public void mouseClicked(MouseEvent me) {
						try {
							int fila = tabla.rowAtPoint(me.getPoint());
							if (fila > -1) {
								Integer idProd = (Integer) tabla.getModel().getValueAt(fila, 0);
								Producto prod = mgrProd.obtenerProductoPorId(idProd);
								Container containerJTable = tabla.getParent().getParent().getParent();
								cargarControlesProducto(prod, containerJTable);
							}
						} catch (PresentacionException e) {
							enviarError(CnstPresExceptions.DB, e.getMessage());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, null);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxFiltroLote(JComboBox<EstadoTran> cbxFiltroLote) {
		try {
			cbxFiltroLote.removeAllItems();
			for(EstadoTran estado : EstadoTran.values()) {
				cbxFiltroLote.addItem(estado);
			}
			cbxFiltroLote.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarLotesPorTransac(JComboBox<Transaccion> cbxLoteTransac) {
		try {
			if(cbxLoteTransac.getSelectedIndex() > -1) {
				hashLotes = new HashMap<>();
				Transaccion transac = (Transaccion) cbxLoteTransac.getSelectedItem();
				List<Lote> listaLote = (ArrayList<Lote>) mgrProd.obtenerListaLotePorTransac(transac.getNroTransac());
				if(listaLote != null && !listaLote.isEmpty()) {
					for(Lote lote : listaLote) {
						hashLotes.put(lote.getIdLote(), lote);
					}
				}
				cargarJtLote();
				setContainerEnabled(frmProd.getPnlLoteDatos(), transac.getEstadoTran().equals(EstadoTran.P));
			} else {
				hashLotes = null;
				cargarJtLote();
				setContainerEnabled(frmProd.getPnlLoteDatos(), false);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtLote() {
		try {
			JTable tabla = frmProd.getJtLote();
			clearTable(tabla);
			deleteModelTable(tabla);
			if(hashLotes != null && !hashLotes.isEmpty()) {
				DefaultTableModel modeloJtLote = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				tabla.setModel(modeloJtLote);
				modeloJtLote.addColumn("Lote");
				modeloJtLote.addColumn("Producto");
				modeloJtLote.addColumn("Transaccion");
				modeloJtLote.addColumn("Stock");
				modeloJtLote.addColumn("Deposito");
				modeloJtLote.addColumn("Utilidad");
				modeloJtLote.addColumn("Venc");
				for(Lote lote : hashLotes.values()) {
					Object [] fila = new Object[7];
					fila[0] = lote.getIdLote();
					fila[1] = lote.getTranLinea().getProducto();
					fila[2] = lote.getTranLinea().getTransaccion().getNroTransac();
					fila[3] = lote.getStock();
					fila[4] = lote.getDeposito() != null ? lote.getDeposito() : N_A;
					fila[5] = lote.getUtilidad() != null ? lote.getUtilidad() : N_A;
					fila[6] = lote.getVenc() != null ? lote.getVenc().toString(Fecha.DMA) : N_A;
					modeloJtLote.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int fila = tabla.rowAtPoint(e.getPoint());
						if (fila > -1 && tabla.getColumnCount() > 1) {
							Integer idLote = (Integer) tabla.getModel().getValueAt(fila, 0);
							cargarControlesLote(hashLotes.get(idLote), getFrm().getPnlLoteDatos());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, JTABLE_SIN_LOTES);
				clearPanel(frmProd.getPnlLoteDatos());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarControlesLote(Lote lote, Container panel) {
		try {
//			Transaccion transac = (Transaccion) frmProd.getCbxLoteCompras().getSelectedItem();
			if(lote.getDeposito() != null && lote.getUtilidad() != null &&
					lote.getVenc() != null) {
				
				ComboBoxModel<Deposito> cbModelDep = frmProd.getCbxLoteDep().getModel();
				cbModelDep.setSelectedItem(lote.getDeposito());
				frmProd.getCbxLoteDep().setSelectedItem(cbModelDep.getSelectedItem());
	
				ComboBoxModel<Utilidad> cbModelUtil = frmProd.getCbxLoteUtil().getModel();
				cbModelUtil.setSelectedItem(lote.getUtilidad());
				frmProd.getCbxLoteUtil().setSelectedItem(cbModelUtil.getSelectedItem());
				
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(lote.getVenc().getTimeInMillis());
				getFrm().getDchLoteVenc().setCalendar(gc);
			} else {
				clearControlsInJPanel(panel);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	
	public void cargarControlesProducto(Producto prod, Container panel) {
		try {
			clearControlsInJPanel(panel);
			ComboBoxModel<TipoProd> cbModelTp = frmProd.getCbxTipoProd().getModel();
			cbModelTp.setSelectedItem(prod.getTipoProd());
			frmProd.getCbxTipoProd().setSelectedItem(cbModelTp.getSelectedItem());
			frmProd.getTxtProId().setText(String.valueOf(prod.getIdProducto()));
			frmProd.getTxtProCod().setText(prod.getCodigo());
			frmProd.getTxtProNom().setText(prod.getNombre());
			frmProd.getTxtProDesc().setText(prod.getDescripcion());
			frmProd.getFtxtProStockMin().setText(String.valueOf(prod.getStockMin()));
			frmProd.getFtxtProPres().setText(String.valueOf(prod.getCantUnidad()));
			ComboBoxModel<Unidad> cbModelUni = frmProd.getCbxProUni().getModel();
			cbModelUni.setSelectedItem(prod.getUnidad());
			frmProd.getCbxProUni().setSelectedItem(cbModelUni.getSelectedItem());
			ComboBoxModel<AplicaIva> cbModelAi = frmProd.getCbxProAplIva().getModel();
			cbModelAi.setSelectedItem(prod.getAplIva());
			frmProd.getCbxProAplIva().setSelectedItem(cbModelAi.getSelectedItem());
			frmProd.getFtxtProPrecio().setText(String.valueOf(prod.getPrecio()));
			//consulto manager para obtener stock actual
			ManagerProducto mgrProd = new ManagerProducto();
			HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProducto(prod.getIdProducto());
			if(hlpProd.getStock().doubleValue() < prod.getStockMin().doubleValue()) {
				frmProd.getFtxtProStockAct().setForeground(Color.RED);
			} else {
				frmProd.getFtxtProStockAct().setForeground(Color.BLACK);
			}
			frmProd.getFtxtProStockAct().setText(String.valueOf(hlpProd.getStock()));
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//dep
	public void cargarCbxDep(JComboBox<Deposito> cbxDep) {
		try {
			cbxDep.removeAllItems();
			List<Deposito> listaDep = (ArrayList<Deposito>) mgrProd.obtenerListaDeposito();
			if(listaDep != null && !listaDep.isEmpty()) {
				for(Deposito dep : listaDep) {
					cbxDep.addItem(dep);
				}
				cbxDep.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarListDeposito(JList<Deposito> jlDep) {
		try {
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
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesDep(JTextField txtDepNom, JList<Deposito> jlDep) {
		try {
			if(controlDatosObl(jlDep)) {
				Deposito dep = (Deposito) jlDep.getSelectedValue();
				txtDepNom.setText(dep.getNombre());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	
	//util
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
	
	public void cargarListUtil(JList<Utilidad> jlUtil) {
		try {
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
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesUtil(JTextField txtUtilDesc, JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		try {
			if(controlDatosObl(jlUtil)) {
				Utilidad util = (Utilidad) jlUtil.getSelectedValue();
				txtUtilDesc.setText(util.getDescripcion());
				txtUtilPorc.setText(String.valueOf(util.getPorc()));
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/
	
	//producto
	
	public void buscarProducto(JComboBox<TipoProd> cbxTp, JTextField txtProCod, JTextField txtProNom, JTextField txtProDesc) {
		try {
			List<Producto> listaProd = (ArrayList<Producto>) mgrProd.obtenerBusquedaProducto((TipoProd) cbxTp.getSelectedItem(), txtProCod.getText(), txtProNom.getText(), txtProDesc.getText());
			cargarJtProd(listaProd);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public Integer agregarProducto(JComboBox<TipoProd> cbxTp, JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, 
			JFormattedTextField ftxtProPres, JComboBox<Unidad> cbxProUni, JComboBox<AplicaIva> cbxProAplIva, JFormattedTextField precio) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxTp);
			genComp.setComp(codigo);
			genComp.setComp(nombre);
			genComp.setComp(stockMin);
			genComp.setComp(ftxtProPres);
			genComp.setComp(cbxProUni);
			genComp.setComp(cbxProAplIva);
			genComp.setComp(precio);
			if(controlDatosObl(genComp)) {
				Producto prod = new Producto();
				TipoProd tp = (TipoProd)cbxTp.getSelectedItem();
				prod.setTipoProd(tp);
				prod.setCodigo(codigo.getText());
				prod.setNombre(nombre.getText());
				prod.setDescripcion(descripcion.getText());
				prod.setStockMin(new Float(stockMin.getText()));
				prod.setCantUnidad(new Float(ftxtProPres.getText()));
				prod.setUnidad((Unidad) cbxProUni.getSelectedItem()); 
				prod.setAplIva((AplicaIva) cbxProAplIva.getSelectedItem()); 
				prod.setPrecio(new Double(precio.getText()));
				mgrProd.guardarProducto(prod);
				clearForm(frmProd.getContentPane());
				List<Producto> lst = mgrProd.obtenerListaProductoPorTipoProd(tp);
				cargarJtProd(lst);
			} else {
				enviarWarning(PROD, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer modificarProducto(JComboBox<TipoProd> cbxTp, JTextField id, JTextField codigo, JTextField nombre, JTextField descripcion, JFormattedTextField stockMin, 
			JFormattedTextField ftxtProPres, JComboBox<Unidad> cbxProUni, JComboBox<AplicaIva> cbxProAplIva, JFormattedTextField precio) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxTp);
			genComp.setComp(id);
			genComp.setComp(codigo);
			genComp.setComp(nombre);
			genComp.setComp(stockMin);
			genComp.setComp(ftxtProPres);
			genComp.setComp(cbxProUni);
			genComp.setComp(cbxProAplIva);
			genComp.setComp(precio);
			if(controlDatosObl(genComp)) {
				Integer idInt = ctrlNumLong(id.getText()) ? new Integer(id.getText()) : null;
				if(mgrProd.checkExistProducto(idInt)) {
					TipoProd tp = (TipoProd) cbxTp.getSelectedItem();
					Producto prod = mgrProd.obtenerProductoPorId(idInt);
					prod.setTipoProd(tp);
					prod.setCodigo(codigo.getText());
					prod.setNombre(nombre.getText());
					prod.setDescripcion(descripcion.getText());
					prod.setStockMin(new Float(stockMin.getText()));
					prod.setCantUnidad(new Float(ftxtProPres.getText()));
					prod.setUnidad((Unidad) cbxProUni.getSelectedItem()); 
					prod.setAplIva((AplicaIva) cbxProAplIva.getSelectedItem()); 
					prod.setPrecio(new Double(precio.getText()));
					mgrProd.modificarProducto(prod);
					clearForm(frmProd.getContentPane());
					List<Producto> lst = mgrProd.obtenerListaProductoPorTipoProd(tp);
					cargarJtProd(lst);
				} else {
					enviarWarning(PROD, PROD_NOEXIST);
				}
			} else {
				enviarWarning(PROD, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer desactivarProducto(JTextField id) {
		try {
			if(controlDatosObl(id)) {
				Integer idInt = ctrlNumLong(id.getText()) ? new Integer(id.getText()) : null;
				if(mgrProd.checkExistProducto(idInt)) {
					clearForm(frmProd.getContentPane());
					Producto prod = mgrProd.obtenerProductoPorId(idInt);
					mgrProd.desactivarProducto(prod);
				} else {
					enviarWarning(PROD, PROD_NOEXIST);
				}
			} else {
				enviarWarning(PROD, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	//tipo prod
	
	public Integer agregarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		try {
			if(controlDatosObl(descripcion)) {
				TipoProd tipoProd = new TipoProd();
				tipoProd.setDescripcion(descripcion.getText());
				mgrProd.guardarTipoProd(tipoProd);
				clearForm(getiFrmTp().getContentPane());
				cargarListTipoProd(jlTp);
				enviarInfo(TP, TP_ING_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer modificarTipoProd(JTextField descripcion, JList<TipoProd> jlTp) {
		try {
			if(controlDatosObl(descripcion, jlTp)) {
				TipoProd tp = (TipoProd) jlTp.getSelectedValue();
				tp.setDescripcion(descripcion.getText());
				mgrProd.modificarTipoProd(tp);
				clearForm(getiFrmTp().getContentPane());
				cargarListTipoProd(jlTp);
				enviarInfo(TP, TP_MOD_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer eliminarTipoProd(JList<TipoProd> jlTp) {
		try {
			if(controlDatosObl(jlTp)) {
				TipoProd tp = (TipoProd) jlTp.getSelectedValue();
				if(mgrProd.eliminarTipoProd(tp)) {
					clearForm(getiFrmTp().getContentPane());
					cargarListTipoProd(jlTp);
					enviarInfo(TP, TP_ELI_OK);
				} else {
					enviarWarning(TP, TP_ELI_CON_UTIL);
				}
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}


	public void abrirIFrmTp() {
		try {
			IfrmTipoProd ifrmTp = new IfrmTipoProd(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmTp);
			//
			Component comp = getFrm().getContentPane().getComponent(1);
			comp.setVisible(false);//FIXME: ver si no existe solucion mejor
			//
			ifrmTp.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmTp() {
		try {
			Component comp = getFrm().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//unidad
	
	public Integer agregarUnidad(JTextField nombre, JList<Unidad> jlUni) {
		try {
			if(controlDatosObl(nombre)) {
				Unidad unidad = new Unidad();
				unidad.setNombre(nombre.getText());
				mgrProd.guardarUnidad(unidad);
				clearForm(getiFrmUni().getContentPane());
				cargarListUnidad(jlUni);
				enviarInfo(UNI, UNI_ING_OK);
			} else {
				enviarWarning(UNI, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer modificarUnidad(JTextField nombre, JList<Unidad> jlUni) {
		try {
			if(controlDatosObl(nombre, jlUni)) {
				Unidad uni = (Unidad) jlUni.getSelectedValue();
				uni.setNombre(nombre.getText());
				mgrProd.modificarUnidad(uni);
				clearForm(getiFrmUni().getContentPane());
				cargarListUnidad(jlUni);
				enviarInfo(UNI, UNI_MOD_OK);
			} else {
				enviarWarning(UNI, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer eliminarUnidad(JList<Unidad> jlUni) {
		try {
			if(controlDatosObl(jlUni)) {
				Unidad uni = (Unidad) jlUni.getSelectedValue();
				if(mgrProd.eliminarUnidad(uni)) {
					clearForm(getiFrmUni().getContentPane());
					cargarListUnidad(jlUni);
					enviarInfo(UNI, UNI_ELI_OK);
				} else {
					enviarWarning(UNI, UNI_ELI_CON_UTIL);
				}
			} else {
				enviarWarning(UNI, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}


	public void abrirIFrmUni() {
		try {
			IfrmUnidad ifrmUni = new IfrmUnidad(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmUni);
			//
			Component comp = getFrm().getContentPane().getComponent(1);
			comp.setVisible(false);//FIXME: ver si no existe solucion mejor
			//
			ifrmUni.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmUni() {
		try {
			Component comp = getFrm().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//deposito
	
	public Integer agregarDeposito(JTextField nombre, JList<Deposito> jlDep) {
		try {
			if(controlDatosObl(nombre)) {
				Deposito dep = new Deposito();
				dep.setNombre(nombre.getText());
				mgrProd.guardarDeposito(dep);
				clearForm(getiFrmDep().getContentPane());
				cargarListDeposito(jlDep);
				enviarInfo(DEP, DEP_ING_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer modificarDeposito(JTextField nombre, JList<Deposito> jlDep) {
		try {
			if(controlDatosObl(nombre, jlDep)) {
				Deposito dep = (Deposito) jlDep.getSelectedValue();
				dep.setNombre(nombre.getText());
				mgrProd.modificarDeposito(dep);
				clearForm(getiFrmDep().getContentPane());
				cargarListDeposito(jlDep);
				enviarInfo(DEP, DEP_MOD_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public Integer eliminarDeposito(JList<Deposito> jlDep) {
		try {
			if(controlDatosObl(jlDep)) {
				Deposito dep = (Deposito) jlDep.getSelectedValue();
				mgrProd.eliminarDeposito(dep);
				clearForm(getiFrmDep().getContentPane());
				cargarListDeposito(jlDep);
				enviarInfo(DEP, DEP_ELI_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public void abrirIFrmDep() {
		try {
			IfrmDeposito ifrmDep = new IfrmDeposito(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmDep);
			//
			Component comp = getFrm().getContentPane().getComponent(1);
			comp.setVisible(false);//FIXME: ver si no existe solucion mejor
			//
			ifrmDep.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmDep() {
		try {
			Component comp = getFrm().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//utilidad
	
	public Integer agregarUtilidad(JTextField txtUtilDesc, JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		try {
			if(controlDatosObl(txtUtilDesc, txtUtilPorc)) {
				Utilidad util = new Utilidad();
				util.setDescripcion(txtUtilDesc.getText());
				util.setPorc(new Float(txtUtilPorc.getText()));
				mgrProd.guardarUtilidad(util);
				clearForm(getiFrmUtil().getContentPane());
				cargarListUtil(jlUtil);
				enviarInfo(UTIL, UTIL_ING_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	public Integer modificarUtilidad(JTextField txtUtilDesc,  JFormattedTextField txtUtilPorc, JList<Utilidad> jlUtil) {
		try {
			if(controlDatosObl(txtUtilDesc, txtUtilPorc, jlUtil)) {
				Utilidad util = (Utilidad) jlUtil.getSelectedValue();
				util.setDescripcion(txtUtilDesc.getText());
				util.setPorc(new Float(txtUtilPorc.getText()));
				mgrProd.modificarUtilidad(util);
				clearForm(getiFrmUtil().getContentPane());
				cargarListUtil(jlUtil);
				enviarInfo(UTIL, UTIL_MOD_OK);
			} else {
				enviarWarning(TP, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	public Integer eliminarUtilidad(JList<Utilidad> jlUtil) {
		try {
			if(controlDatosObl(jlUtil)) {
				Utilidad util = (Utilidad) jlUtil.getSelectedValue();
				mgrProd.eliminarUtilidad(util);
				clearForm(getiFrmUtil().getContentPane());
				cargarListUtil(jlUtil);
				enviarInfo(UTIL, UTIL_ELI_OK);
			} else {
				enviarWarning(UTIL, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return null;
	}
	
	public void abrirIFrmUtil() {
		try {
			IfrmUtilidad ifrmUtil = new IfrmUtilidad(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmUtil);
			//
			Component comp = getFrm().getContentPane().getComponent(1);
			comp.setVisible(false);
			//
			ifrmUtil.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmUtil() {
		try {
			Component comp = getFrm().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//lote
	
	public void obtenerTransac(JComboBox<EstadoTran> cbxLoteEt, JDateChooser dchLoteFini, JDateChooser dchLoteFfin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxLoteEt);
			genComp.setComp(dchLoteFini);
			genComp.setComp(dchLoteFfin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchLoteFini.getDate());
				Fecha fechaFin = new Fecha(dchLoteFfin.getDate());
				if(controlFechas(fechaIni, fechaFin)) {
					List<Transaccion> listaTransac = (ArrayList<Transaccion>) mgrTransac.obtenerListaTransaccionPorPeriodo(TipoTran.C, (EstadoTran) cbxLoteEt.getSelectedItem(), fechaIni, fechaFin);
					cargarCbxLoteTransac(getFrm().getCbxLoteCompras(), listaTransac);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarCbxLoteTransac(JComboBox<Transaccion> cbxLoteTransac, List<Transaccion> listaTransac) {
		try {
			cbxLoteTransac.removeAllItems();
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					cbxLoteTransac.addItem(transac);
				}
				cbxLoteTransac.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void actualizarLote(JTable jtLote, JComboBox<Deposito> cbxLoteDep, JComboBox<Utilidad> cbxLoteUtil, JDateChooser dchLoteVenc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(jtLote);
			genComp.setComp(cbxLoteDep);
			genComp.setComp(cbxLoteUtil);
			genComp.setComp(dchLoteVenc);
			if(controlDatosObl(genComp)) {
				Integer idLote = (Integer) jtLote.getModel().getValueAt(jtLote.getSelectedRow(), 0);
				if(hashLotes != null && !hashLotes.isEmpty() && 
						hashLotes.containsKey(idLote)) {
					ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
					Integer diasParaVenc = Integer.valueOf(cfgDrv.getDiasParaVenc());
					Fecha fechaVenc = new Fecha(dchLoteVenc.getDate().getTime());
					Fecha fechaActual =new Fecha(Fecha.AMD);
					long diff = fechaVenc.getTimeInMillis() - fechaActual.getTimeInMillis();
					long diasDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					if(diasDiff > diasParaVenc) {
						Lote lote = hashLotes.get(idLote);
						lote.setDeposito((Deposito) cbxLoteDep.getSelectedItem());
						lote.setUtilidad((Utilidad) cbxLoteUtil.getSelectedItem());
						lote.setVenc(new Fecha(dchLoteVenc.getDate().getTime()));
						//ver de actualizar la tabla para cada uno de los datos
						cargarJtLote();
					} else {
						String msj = LOTES_VENC_NOTOL.replace(REPL, String.valueOf(diasParaVenc));
						enviarWarning(LOTE, msj);
					}
				}
			} else {
				enviarWarning(LOTE, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void confirmarCompra(JComboBox<Transaccion> cbxLoteCompras) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxLoteCompras);
			if(controlDatosObl(genComp)) {
				Transaccion transac = (Transaccion) cbxLoteCompras.getSelectedItem();
				if(transac.getEstadoTran().equals(EstadoTran.P) && 
						hashLotes != null && !hashLotes.isEmpty()) {
					Boolean lotesValidos = true;
					for(Lote lote : hashLotes.values()) {
						if(lote.getDeposito() == null || lote.getUtilidad() == null ||
								lote.getVenc() == null) {
							lotesValidos = false;
						}
					}
					if(lotesValidos) {
						List<Lote> listaLote = new ArrayList<>(hashLotes.values());
						mgrTransac.modificarTransaccionCompra(transac, listaLote);
						clearControlsInJPanel(getFrm().getPnlLoteDatos());
						clearControlsInJPanel(getFrm().getScrollPaneProd());
						enviarInfo(LOTE, LOTES_ACT_OK);
					} else {
						enviarWarning(LOTE, LOTES_NO_COMPLETADOS);
					}
				}
			} else {
				enviarWarning(LOTE, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
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

	public IfrmUnidad getiFrmUni() {
		return iFrmUni;
	}
	public void setiFrmUni(IfrmUnidad iFrmUni) {
		this.iFrmUni = iFrmUni;
	}


	
}
