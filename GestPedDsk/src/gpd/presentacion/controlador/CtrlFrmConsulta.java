package gpd.presentacion.controlador;

import java.util.Map;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.formulario.FrmConsulta;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.reports.GeneradorReportes;

public class CtrlFrmConsulta extends CtrlGenerico implements CnstPresGeneric {

	private FrmConsulta frm;
	private UsuarioDsk usr;
	private Map<String, Object> mapParamsJr;
	

	public CtrlFrmConsulta(FrmConsulta frmCon, UsuarioDsk usr) {
		super();
		this.setFrm(frmCon);
		this.setUsr(usr);
	}
	
	public void generarReporte(JComboBox<?> cbxConsFiltro, JDateChooser dchConsIni, JDateChooser dchConsFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxConsFiltro);
			genComp.setComp(dchConsIni);
			genComp.setComp(dchConsFin);
			if(controlDatosObl(genComp)) {
				//FIXME terminar
				mapParamsJr.put("id_persona", null);
				mapParamsJr.put("fecha_ini", null);
				mapParamsJr.put("fecha_fin", null);
				GeneradorReportes.abrirReporte("rptTransacPorFecha.jrxml", mapParamsJr);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
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
