package gpd.presentacion.controlador;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.sincronizador.ManagerSincronizador;
import gpd.presentacion.formulario.FrmSinc;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.types.Fecha;

public class CtrlFrmSinc extends CtrlGenerico implements CnstPresGeneric {

	private FrmSinc frm;
	private UsuarioDsk usr;
	private ManagerSincronizador mgrSinc;
	
	public CtrlFrmSinc(FrmSinc frmSinc, UsuarioDsk usr) {
		super();
		this.setFrm(frmSinc);
		this.setUsr(usr);
		mgrSinc = new ManagerSincronizador();
	}

	public void sincronizarPersona(JDateChooser dchFechaIni, JDateChooser dchFechaFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchFechaIni);
			genComp.setComp(dchFechaFin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchFechaIni.getDate());
				Fecha fechaFin = new Fecha(dchFechaFin.getDate());
				if(controlFechas(fechaIni, fechaFin)) {
					String strResultSinc = mgrSinc.sincronizarPersonas(fechaIni, fechaFin);
					getFrm().getTxtSincInfo().setText(strResultSinc);
				}
			} else {
				clearComponent(getFrm().getTxtSincInfo());
				enviarWarning(SINC, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void sincronizarProducto(JDateChooser dchFechaIni, JDateChooser dchFechaFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchFechaIni);
			genComp.setComp(dchFechaFin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchFechaIni.getDate());
				Fecha fechaFin = new Fecha(dchFechaFin.getDate());
				if(controlFechas(fechaIni, fechaFin)) {
					String strResultSinc = mgrSinc.sincronizarProductos(fechaIni, fechaFin);
					getFrm().getTxtSincInfo().setText(strResultSinc);
				}
			} else {
				clearComponent(getFrm().getTxtSincInfo());
				enviarWarning(SINC, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void sincronizarPedido(JDateChooser dchFechaIni, JDateChooser dchFechaFin) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(dchFechaIni);
			genComp.setComp(dchFechaFin);
			if(controlDatosObl(genComp)) {
				Fecha fechaIni = new Fecha(dchFechaIni.getDate());
				Fecha fechaFin = new Fecha(dchFechaFin.getDate());
				if(controlFechas(fechaIni, fechaFin)) {
					String strResultSinc = mgrSinc.sincronizarPedidos(fechaIni, fechaFin);
					getFrm().getTxtSincInfo().setText(strResultSinc);
				}
			} else {
				clearComponent(getFrm().getTxtSincInfo());
				enviarWarning(SINC, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmSinc getFrm() {
		return frm;
	}
	public void setFrm(FrmSinc frm) {
		this.frm = frm;
	}

	public UsuarioDsk getUsr() {
		return usr;
	}
	public void setUsr(UsuarioDsk usr) {
		this.usr = usr;
	}
	
}
