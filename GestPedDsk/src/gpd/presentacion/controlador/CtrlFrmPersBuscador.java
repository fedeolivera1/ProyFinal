package gpd.presentacion.controlador;

import java.awt.Container;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JTextField;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.TipoPersona;
import gpd.manager.persona.ManagerPersona;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmPersBuscador;

public class CtrlFrmPersBuscador extends CtrlGenerico implements CnstPresGeneric {

	private IfrmPersBuscador ifrmPb;
	private Persona persSel;
	private JDesktopPane deskPane;
	private ManagerPersona mgrPers = new ManagerPersona();
	private JTextField txtPersDescripcion;
	private Container contentPaneCaller;
	private JDesktopPane deskPaneCaller;
	
	
	public void abrirBuscadorPers(Container contentPane, JDesktopPane deskPane, JTextField txtPersDesc) {
		try {
			IfrmPersBuscador ifrmPb = new IfrmPersBuscador(this);
			deskPane.setBounds(0, 0, 784, 565);
			deskPane.add(ifrmPb);
			setDeskPaneCaller(deskPane);
			txtPersDescripcion = txtPersDesc;
			//
			contentPane.getComponent(1).setVisible(false);
			setContentPaneCaller(contentPane);
			//
			ifrmPb.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarBuscadorPers(Persona pers) {
		try {
			if(pers != null) {
				setPersSel(pers);
				cargarPersonaSeleccionada(txtPersDescripcion);
			}
			getDeskPaneCaller().setBounds(0, 0, 0, 0);
			getContentPaneCaller().getComponent(1).setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxTipoPers(JComboBox<TipoPersona> cbxPbTp) {
		try {
			cbxPbTp.removeAllItems();
			List<TipoPersona> listaTp = new ArrayList<TipoPersona>(EnumSet.allOf(TipoPersona.class));
			for(TipoPersona tp : listaTp) {
				cbxPbTp.addItem(tp);
			}
			cbxPbTp.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxDep(JComboBox<Departamento> cbxDep) {
		try {
			cbxDep.removeAllItems();
			ArrayList<Departamento> listaDep = (ArrayList<Departamento>) mgrPers.obtenerListaDepartamento();
			for(Departamento dep : listaDep) {
				cbxDep.addItem(dep);
			}
			cbxDep.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxLoc(JComboBox<Departamento> cbxDep, JComboBox<Localidad> cbxLoc) {
		try {
			cbxLoc.removeAllItems();
			if(controlDatosObl(cbxDep)) {
				Departamento dep = (Departamento) cbxDep.getSelectedItem();
				ArrayList<Localidad> listaLoc = (ArrayList<Localidad>) mgrPers.obtenerListaLocalidadPorDep(dep.getIdDepartamento());
				for(Localidad loc : listaLoc) {
					cbxLoc.addItem(loc);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	private void cargarJlPersona(List<Persona> listaPersona) {
		try {
			DefaultListModel<Persona> dlm = new DefaultListModel<>();
			getIfrmPb().getJlPersBusq().setModel(dlm);
			clearList(getIfrmPb().getJlPersBusq());
			if(listaPersona != null && !listaPersona.isEmpty()) {
				for(Persona pers : listaPersona) {
					dlm.addElement(pers);
				}
				getIfrmPb().getJlPersBusq().setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarPersonaDesdeJList(JList<Persona> jlPers) {
		if(controlDatosObl(jlPers)) {
			getIfrmPb().setVisible(false);
			getIfrmPb().dispose();
		}
	}
	
	public void buscarPersona(JComboBox<TipoPersona> cbxPbTipoPers, JTextField txtPbFiltro, JComboBox<Localidad> cbxPbLoc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPbTipoPers);
			genComp.setComp(txtPbFiltro);
			if(controlDatosObl(genComp)) {
				TipoPersona tp = (TipoPersona) cbxPbTipoPers.getSelectedItem();
				Localidad loc = (Localidad) cbxPbLoc.getSelectedItem();
				List<Persona> listaPersona = mgrPers.obtenerBusquedaPersona(tp, txtPbFiltro.getText(), loc);
				cargarJlPersona(listaPersona);
			} else {
				enviarWarning(PERS, DATOS_OBLIG);
			}
		} catch (Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarPersonaSeleccionada(JTextField txtPersDesc) {
		String toString = null;
		if(getPersSel() != null) {
			if(getPersSel() instanceof PersonaFisica) {
				PersonaFisica pf = (PersonaFisica) getPersSel();
				toString = pf.toString();
			} else if(getPersSel() instanceof PersonaJuridica) {
				PersonaJuridica pj = (PersonaJuridica) getPersSel();
				toString = pj.toString();
			}
		}
		if(toString != null) {
			getCompVal().removeBorder(txtPersDesc);
			txtPersDesc.setText(toString);
		} else {
			clearComponent(txtPersDesc);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	public IfrmPersBuscador getIfrmPb() {
		return ifrmPb;
	}
	public void setIfrmPb(IfrmPersBuscador ifrmPb) {
		this.ifrmPb = ifrmPb;
	}

	public Persona getPersSel() {
		return persSel;
	}
	public void setPersSel(Persona persSel) {
		this.persSel = persSel;
	}

	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
	}

	public JTextField getTxtPersDescripcion() {
		return txtPersDescripcion;
	}
	public void setTxtPersDescripcion(JTextField txtPersDescripcion) {
		this.txtPersDescripcion = txtPersDescripcion;
	}
	
	public Container getContentPaneCaller() {
		return contentPaneCaller;
	}
	public void setContentPaneCaller(Container contentPaneCaller) {
		this.contentPaneCaller = contentPaneCaller;
	}

	public JDesktopPane getDeskPaneCaller() {
		return deskPaneCaller;
	}
	public void setDeskPaneCaller(JDesktopPane deskPaneCaller) {
		this.deskPaneCaller = deskPaneCaller;
	}

}
