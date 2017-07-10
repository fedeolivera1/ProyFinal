package gpd.presentacion.controlador;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.formulario.FrmConsulta;
import gpd.reports.GeneradorReportes;

public class CtrlFrmConsulta {

	private FrmConsulta frm;
	private UsuarioDsk usr;
	

	public CtrlFrmConsulta(FrmConsulta frmCon, UsuarioDsk usr) {
		super();
		this.setFrm(frmCon);
		this.setUsr(usr);
	}
	
	public void generarReporte() {
		GeneradorReportes.abrirReporte("rptPrueba");
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
}
