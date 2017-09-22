package gpd.presentacion.controlador;

import java.awt.Container;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.helper.HlpProducto;
import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmConsulta;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.reports.GeneradorReportes;
import gpd.reports.TipoReporte;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public class CtrlFrmConsulta extends CtrlGenerico implements CnstPresGeneric {

	private static final String ESC = "\n";
	
	private FrmConsulta frm;
	private UsuarioDsk usr;
	private JDesktopPane deskPane;
	private Map<String, Object> mapParamsJr;
	private CtrlFrmPersBuscador ctrlPb;
	

	public CtrlFrmConsulta(FrmConsulta frmCon, UsuarioDsk usr) {
		super();
		this.setFrm(frmCon);
		this.setUsr(usr);
		ctrlPb = new CtrlFrmPersBuscador();
	}
	
	public void abrirIfrmPersBuscador(Container contentPane, JDesktopPane deskPane, JTextField txtPersDesc) {
		ctrlPb.abrirBuscadorPers(contentPane, deskPane, txtPersDesc);
	}
	
	public void cargarTipoRep(JComboBox<TipoReporte> cbxConsFiltro) {
		try {
			cbxConsFiltro.removeAllItems();
			for(TipoReporte tr : TipoReporte.values()) {
				cbxConsFiltro.addItem(tr);
			}
			cbxConsFiltro.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarTipoTx(JComboBox<EstadoTran> cbxConsEstadoTx) {
		try {
			cbxConsEstadoTx.removeAllItems();
			for(EstadoTran estado : EstadoTran.values()) {
				cbxConsEstadoTx.addItem(estado);
			}
			cbxConsEstadoTx.setSelectedIndex(1);//selecciono confirmado por default
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarEstadoPedido(JComboBox<EstadoPedido> cbxConsEstadoPed) {
		try {
			cbxConsEstadoPed.removeAllItems();
			cbxConsEstadoPed.addItem(null);
			for(EstadoPedido estado : EstadoPedido.values()) {
				cbxConsEstadoPed.addItem(estado);
			}
			cbxConsEstadoPed.setSelectedIndex(0);//selecciono confirmado por default
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void manejarControles(JComboBox<TipoReporte> cbxConsFiltro) {
		if(cbxConsFiltro.getSelectedIndex() >= 0) {
			if(cbxConsFiltro.getSelectedItem().equals(TipoReporte.PR)) {
				getFrm().getDchConsIni().setEnabled(false);
				getFrm().getDchConsFin().setEnabled(false);
				getFrm().getTxtConsPersona().setText(STR_VACIO);
				getFrm().getBtnConsBp().setEnabled(false);
				getFrm().getChkConsTodo().setVisible(false);
				getFrm().getCbxConsEstadoTx().setVisible(false);
				getFrm().getCbxConsEstadoPed().setVisible(false);
			} else if( cbxConsFiltro.getSelectedItem().equals(TipoReporte.PF) || 
					cbxConsFiltro.getSelectedItem().equals(TipoReporte.PJ) ) {
				cargaCtrlDef();
				getFrm().getChkConsTodo().setVisible(true);
				getFrm().getTxtConsPersona().setText(STR_VACIO);
				getFrm().getBtnConsBp().setEnabled(false);
			} else if( cbxConsFiltro.getSelectedItem().equals(TipoReporte.C) ||
					cbxConsFiltro.getSelectedItem().equals(TipoReporte.V)) {
				cargaCtrlDef();
				ComboBoxModel<EstadoTran> cbModel = getFrm().getCbxConsEstadoTx().getModel();
				cbModel.setSelectedItem(EstadoTran.C);
				getFrm().getCbxConsEstadoTx().setSelectedItem(cbModel.getSelectedItem());
				getFrm().getCbxConsEstadoTx().setVisible(true);
			} else if(cbxConsFiltro.getSelectedItem().equals(TipoReporte.P)) {
				cargaCtrlDef();
				getFrm().getCbxConsEstadoPed().setVisible(true);
			} else {
				cargaCtrlDef();
			}
		}
	}
	private void cargaCtrlDef() {
		getFrm().getDchConsIni().setEnabled(true);
		getFrm().getDchConsFin().setEnabled(true);
		getFrm().getBtnConsBp().setEnabled(true);
		getFrm().getChkConsTodo().setVisible(false);
		getFrm().getCbxConsEstadoTx().setVisible(false);
		getFrm().getCbxConsEstadoPed().setVisible(false);
		getFrm().getTxtConsPersona().setText(STR_VACIO);
		getFrm().getBtnConsBp().setEnabled(true);
	}
	
	public void controlComponenteReporte(JComboBox<TipoReporte> cbxTipoRep) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxTipoRep);
			if(controlDatosObl(genComp)) {
				TipoReporte tr = (TipoReporte) cbxTipoRep.getSelectedItem();
				Long idPersona;
				Integer noFechas;
				switch(tr) {
					case C: case V:
						idPersona = ctrlPb.getPersSel() != null ? ctrlPb.getPersSel().getIdPersona() : -1;
						generarReporteTransac((TipoReporte.C.equals(tr) ? TipoTran.C : TipoTran.V), idPersona, getFrm().getDchConsIni(), getFrm().getDchConsFin(), 
								getFrm().getCbxConsEstadoTx());
						break;
					case P:
						idPersona = ctrlPb.getPersSel() != null ? ctrlPb.getPersSel().getIdPersona() : -1;
						generarReportePedido(idPersona, getFrm().getDchConsIni(), getFrm().getDchConsFin(), getFrm().getCbxConsEstadoPed());
						break;
					case PR:
						generarReporteProducto();
						break;
					case PF:
						noFechas = getFrm().getChkConsTodo().isSelected() ? 1 : 0; 
						generarReportePersona(getFrm().getDchConsIni(), getFrm().getDchConsFin(), noFechas, TipoPersona.F);
						break;
					case PJ:
						noFechas = getFrm().getChkConsTodo().isSelected() ? 1 : 0; 
						generarReportePersona(getFrm().getDchConsIni(), getFrm().getDchConsFin(), noFechas, TipoPersona.J);
						break;
				default:
					break;
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReporteTransac(TipoTran tipoTran, Long idPersona, JDateChooser dchConsIni, JDateChooser dchConsFin,
			JComboBox<EstadoTran> cbxEstadoTx) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchConsIni);
			genComp.setComp(dchConsFin);
			genComp.setComp(cbxEstadoTx);
			if(controlDatosObl(genComp)) {
				Date dateIni = dchConsIni.getDate();
				Date dateFin = dchConsFin.getDate();
				String estado = String.valueOf(((EstadoTran)cbxEstadoTx.getSelectedItem()).getAsChar());

				mapParamsJr = new HashMap<>();
				mapParamsJr.put("operacion", String.valueOf(tipoTran.getAsChar()));
				mapParamsJr.put("id_persona", idPersona);
				mapParamsJr.put("fecha_ini", dateIni);
				mapParamsJr.put("fecha_fin", dateFin);
				mapParamsJr.put("estado", estado);
				mapParamsJr.put("SUBREPORT_DIR", System.getProperty("file.separator"));
				GeneradorReportes.abrirReporte("rptTransacPorFecha", mapParamsJr);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReportePedido(Long idPersona, JDateChooser dchConsIni, JDateChooser dchConsFin, JComboBox<EstadoPedido> cbxEstadoPed) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchConsIni);
			genComp.setComp(dchConsFin);
			if(controlDatosObl(genComp)) {
				Date dateIni = dchConsIni.getDate();
				Date dateFin = dchConsFin.getDate();
				String estado = cbxEstadoPed.getSelectedItem() != null ? String.valueOf(((EstadoPedido)cbxEstadoPed.getSelectedItem()).getAsChar()) : "";
				
				mapParamsJr = new HashMap<>();
				mapParamsJr.put("id_persona", idPersona);
				mapParamsJr.put("fecha_ini", dateIni);
				mapParamsJr.put("fecha_fin", dateFin);
				mapParamsJr.put("estado", estado);
				mapParamsJr.put("SUBREPORT_DIR", System.getProperty("file.separator"));
				GeneradorReportes.abrirReporte("rptPedidosPorPers", mapParamsJr);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReporteProducto() {
		try {
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasVenc = Integer.valueOf(cfgDrv.getDiasParaVenc());
			mapParamsJr = new HashMap<>();
			mapParamsJr.put("dias_venc", diasVenc);
			mapParamsJr.put("SUBREPORT_DIR", System.getProperty("file.separator"));
			GeneradorReportes.abrirReporte("rptInvProductos", mapParamsJr);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReportePersona(JDateChooser dchConsIni, JDateChooser dchConsFin, Integer noFechas, TipoPersona tp) {
		try {
			Boolean valido = true;
			if(noFechas > 0) {
				GenCompType genComp = new GenCompType();
				genComp.setComp(dchConsIni);
				genComp.setComp(dchConsFin);
				if(!controlDatosObl(genComp)) {
					valido = false;
				}
			}
			if(valido) {
				Date dateIni = dchConsIni.getDate();
				Date dateFin = dchConsFin.getDate();
				mapParamsJr = new HashMap<>();
				mapParamsJr.put("fecha_ini", dateIni);
				mapParamsJr.put("fecha_fin", dateFin);
				mapParamsJr.put("no_fechas", noFechas);
				String reporte = "";
				if(TipoPersona.F.equals(tp)) {
					reporte = "rptPersFisicas";
				} else if(TipoPersona.J.equals(tp)) {
					reporte = "rptPersJuridicas";
				}
				GeneradorReportes.abrirReporte(reporte, mapParamsJr);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void limpiar() {
		clearForm(getFrm().getContentPane());
		ctrlPb.setPersSel(null);
		cargaCtrlDef();
	}
	
	public void cargarProdStockMin(JTextArea txtProdStockMin) {
		try {
			StringBuilder str = new StringBuilder();
			ManagerProducto mgrProd = new ManagerProducto();
			List<Producto> listaProdStockBajo = mgrProd.obtenerProductosStockMenorAMin();
			if(listaProdStockBajo != null && !listaProdStockBajo.isEmpty()) {
				for(Producto prod : listaProdStockBajo) {
					HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProducto(prod.getIdProducto());
					str.append("El producto con id ").append(prod.getIdProducto()).append(" | ").append(prod.getCodigo()).append(" | ")
						.append(prod.getNombre()).append(" tiene stock ").append(hlpProd.getStock()).append(" min [").append(prod.getStockMin()).append("]").append(ESC);
				}
				getFrm().getTxtProdStockMin().setText(str.toString());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarLotesPorVenc(JTextArea txtLotesAVenc, JTextArea txtLotesVenc) {
		try {
			StringBuilder strLpv = new StringBuilder();
			StringBuilder strLv = new StringBuilder();
			ManagerProducto mgrProd = new ManagerProducto();
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasParaVenc = Integer.valueOf(cfgDrv.getDiasParaVenc());
			List<Lote> listaLotesPv = mgrProd.obtenerLotesProxVenc(diasParaVenc);
			Fecha fechaAct = new Fecha(Fecha.AMD);
			if(listaLotesPv != null && !listaLotesPv.isEmpty()) {
				for(Lote lote : listaLotesPv) {
					Producto prod = lote.getTranLinea().getProducto();
					if(lote.getVenc().before(fechaAct)) {
						long diff = fechaAct.getTimeInMillis() - lote.getVenc().getTimeInMillis();
						long diasDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						if(diasDiff < 30) {
							strLv.append("El lote ").append(lote.getIdLote()).append(" || Dep:").append(lote.getDeposito()).append(" || Prod: ")
							.append(prod.getIdProducto()).append("|").append(prod.getCodigo()).append("|").append(prod.getNombre())
							.append(" ha vencido en la fecha: ").append(lote.getVenc().toString(Fecha.DMA)).append(ESC);
						}
					} else {
						long diff = lote.getVenc().getTimeInMillis() - fechaAct.getTimeInMillis();
						long diasDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						strLpv.append("El lote ").append(lote.getIdLote()).append(" || Dep:").append(lote.getDeposito()).append(" || Prod: ")
							.append(prod.getIdProducto()).append("|").append(prod.getCodigo()).append("|").append(prod.getNombre())
							.append(" se vencerá en ").append(diasDiff).append(" dias").append(ESC);
					}
				}
				getFrm().getTxtLotesVenc().setText(strLv.toString());
				getFrm().getTxtLotesAVenc().setText(strLpv.toString());
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	

	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmConsulta getFrm() {
		return frm;
	}
	public void setFrm(FrmConsulta frm) {
		this.frm = frm;
	}
	
	public UsuarioDsk getUsr() {
		return usr;
	}
	public void setUsr(UsuarioDsk usr) {
		this.usr = usr;
	}

	public Map<String, Object> getMapParamsJr() {
		return mapParamsJr;
	}
	public void setMapParamsJr(Map<String, Object> mapParamsJr) {
		this.mapParamsJr = mapParamsJr;
	}
	
	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
	}
}
