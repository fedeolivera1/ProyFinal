package gpd.presentacion.controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.formulario.FrmConsulta;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.reports.GeneradorReportes;
import gpd.reports.TipoReporte;

public class CtrlFrmConsulta extends CtrlGenerico implements CnstPresGeneric {

	private FrmConsulta frm;
	private UsuarioDsk usr;
	private Map<String, Object> mapParamsJr;
	

	public CtrlFrmConsulta(FrmConsulta frmCon, UsuarioDsk usr) {
		super();
		this.setFrm(frmCon);
		this.setUsr(usr);
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
	
	public void controlComponenteReporte(JComboBox<TipoReporte> cbxTipoRep) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxTipoRep);
			if(controlDatosObl(genComp)) {
				TipoReporte tr = (TipoReporte) cbxTipoRep.getSelectedItem();
				switch(tr) {
					case C: case V:
						generarReporteTransac((TipoReporte.C.equals(tr) ? TipoTran.C : TipoTran.V), getFrm().getDchConsIni(), getFrm().getDchConsFin());
						break;
					case PR:
						generarReporteProducto();
						break;
					case PE:
						generarReportePersona();
						break;
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReporteTransac(TipoTran tipoTran, JDateChooser dchConsIni, JDateChooser dchConsFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchConsIni);
			genComp.setComp(dchConsFin);
			if(controlDatosObl(genComp)) {
				Date dateIni = dchConsIni.getDate();
				Date dateFin = dchConsFin.getDate();

				mapParamsJr = new HashMap<>();
				mapParamsJr.put("tipo_transac", String.valueOf(tipoTran.getAsChar()));
				mapParamsJr.put("id_persona", new Long(-1));
				mapParamsJr.put("fecha_ini", dateIni);
				mapParamsJr.put("fecha_fin", dateFin);
			}
			GeneradorReportes.abrirReporte("rptTransacPorFecha", mapParamsJr);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void generarReporteProducto() {
		
	}
	
	public void generarReportePersona() {
		
	}
	
	public void cargarCbxConsFiltro() {
		getFrm().getCbxConsFiltro().setSelectedItem("Filtro1");
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
}
