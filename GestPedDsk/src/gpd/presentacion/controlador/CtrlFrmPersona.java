package gpd.presentacion.controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;

import gpd.dominio.persona.TipoDoc;
import gpd.manager.persona.ManagerPersona;
import gpd.presentacion.formulario.FrmPersona;

public class CtrlFrmPersona extends CtrlGenerico {

	private ManagerPersona mgrPersona;
	private FrmPersona frmPers;
	
	public CtrlFrmPersona(FrmPersona frmPers) {
		super();
		this.setFrmPers(frmPers);
	}

	
	public FrmPersona getFrmPers() {
		return frmPers;
	}
	public void setFrmPers(FrmPersona frmPers) {
		this.frmPers = frmPers;
	}


	public void cargarCbxTipoDoc(JComboBox<TipoDoc> cbxPfTipoDoc) {
		cbxPfTipoDoc.removeAllItems();
		ArrayList<TipoDoc> listaTipoDoc = (ArrayList<TipoDoc>) mgrPersona.obtenerListaTipoDoc();
		for(TipoDoc tipoDoc : listaTipoDoc) {
			cbxPfTipoDoc.addItem(tipoDoc);
		}
		
	}

}
