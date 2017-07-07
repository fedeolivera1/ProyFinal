package gpd.presentacion.controlador;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.usuario.ManagerUsuario;
import gpd.presentacion.formulario.FrmPrincipal;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.presentacion.popup.IfrmCambioPass;
import gpd.presentacion.popup.IfrmUsuario;

public class CtrlFrmUsuario extends CtrlGenerico implements CnstPresGeneric {

	private JDesktopPane deskPane;
	private ManagerUsuario mgrUsr = new ManagerUsuario();
	private FrmPrincipal frmPpal;
	private IfrmUsuario ifrmUsr;
	
	
	
	public CtrlFrmUsuario(FrmPrincipal frmPpal) {
		super();
		this.frmPpal = frmPpal;
	}
	
	
	public void cargarCbxTipoUsr(JComboBox<TipoUsr> cbxTipoUsr) {
		try {
			List<TipoUsr> listaTipoUsr = new ArrayList<TipoUsr>(EnumSet.allOf(TipoUsr.class));
			for(TipoUsr tu : listaTipoUsr) {
				cbxTipoUsr.addItem(tu);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarListUsuario(JList<UsuarioDsk> jlUsuario) {
		try {
			DefaultListModel<UsuarioDsk> dlm = new DefaultListModel<>();
			dlm.clear();
			List<UsuarioDsk> listaUsuario = (ArrayList<UsuarioDsk>) mgrUsr.obtenerListaUsuarios();
			if(listaUsuario != null && !listaUsuario.isEmpty()) {
				for(UsuarioDsk usr : listaUsuario) {
					dlm.addElement(usr);
				}
				jlUsuario.setModel(dlm);
				jlUsuario.setSelectedIndex(-1);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		
	}
	
	public void cargarControlesUsuario(JTextField txtNomUsu, JComboBox<TipoUsr> cbxTipoUsr, JList<UsuarioDsk> jlUsuario) {
		try {
			if(controlDatosObl(jlUsuario)) {
				UsuarioDsk usr = (UsuarioDsk) jlUsuario.getSelectedValue();
				txtNomUsu.setText(usr.getNomUsu());
				ComboBoxModel<TipoUsr> cbModel = cbxTipoUsr.getModel();
				cbModel.setSelectedItem(usr.getTipoUsr());
				cbxTipoUsr.setSelectedItem(cbModel.getSelectedItem());
				setContainerEnabled(getIfrmUsr().getPwfUsuPass1(), false);
				setContainerEnabled(getIfrmUsr().getPwfUsuPass2(), false);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void registrarUsuario(JTextField txtNomUsu, JPasswordField txtPasswd, JPasswordField txtPasswd2, JComboBox<TipoUsr> cbxTipoUsr) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtNomUsu);
			genComp.setComp(txtPasswd);
			genComp.setComp(txtPasswd2);
			genComp.setComp(cbxTipoUsr);
			if(controlDatosObl(genComp)) {
				String contUno = String.valueOf(txtPasswd.getPassword());
				String contDos = String.valueOf(txtPasswd2.getPassword());
				if(controlIgualdadPass(contUno, contDos)) {
					String cont = getMD5(contUno);
					UsuarioDsk usr = new UsuarioDsk(txtNomUsu.getText(), cont, cbxTipoUsr.getItemAt(cbxTipoUsr.getSelectedIndex()));
					mgrUsr.guardarUsuario(usr);
					clearControlsInJPanel(getIfrmUsr().getPnlDatosUsu());
					cargarListUsuario(getIfrmUsr().getJlUsuario());
					enviarInfo(USR, USR_ING_OK);
				} else {
					enviarWarning(USR, USR_PASS_REP);
				}
			} else {
				enviarWarning(USR, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void modificarUsuario(JTextField txtNomUsu, JComboBox<TipoUsr> cbxTipoUsr, JList<UsuarioDsk> jlUsuario) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtNomUsu);
			genComp.setComp(cbxTipoUsr);
			genComp.setComp(jlUsuario);
			if(controlDatosObl(genComp)) {
				UsuarioDsk usr = (UsuarioDsk) jlUsuario.getSelectedValue();
				usr.setTipoUsr((TipoUsr) cbxTipoUsr.getSelectedItem());
				mgrUsr.modificarUsuario(usr);
				setContainerEnabled(getIfrmUsr().getPwfUsuPass1(), true);
				setContainerEnabled(getIfrmUsr().getPwfUsuPass2(), true);
				clearControlsInJPanel(getIfrmUsr().getPnlDatosUsu());
				cargarListUsuario(getIfrmUsr().getJlUsuario());
				enviarInfo(USR, USR_MOD_OK);
			} else {
				enviarWarning(USR, DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void eliminarUsuario(JList<UsuarioDsk> jlUsuario) {
		try {
			// Pregunto si quiere eliminar el usuario
			GenCompType genComp = new GenCompType();
			genComp.setComp(jlUsuario);
			if(controlDatosObl(genComp)) {
				UsuarioDsk usuario = jlUsuario.getSelectedValue();
				if(enviarConfirm(USR, USR_ELI_CONFIRM + usuario.getNomUsu() + QUESTION) == CONFIRM_OK) {
					mgrUsr.eliminarUsuario(usuario);
					cargarListUsuario(getIfrmUsr().getJlUsuario());
					enviarInfo(USR, USR_ELI_OK);
				}
			} else {
				enviarWarning(USR, DATOS_OBLIG);
			}	
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cambiarCont(JPasswordField passAct, JPasswordField passNueva1, JPasswordField passNueva2) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(passAct);
			genComp.setComp(passNueva1);
			genComp.setComp(passNueva2);
			if(controlDatosObl(genComp)) {
				String contNueva1 = String.valueOf(passNueva1.getPassword());
				String contNueva2 = String.valueOf(passNueva2.getPassword());
				if(contNueva1.length() < 6 || contNueva2.length() < 6) {
					enviarError(USR, USR_PASS_LARGO);
				} else {
					if(!controlIgualdadPass(contNueva1, contNueva2)) {
						enviarError(USR, USR_PASS_REP);
					} else {
						String hashPassActual = getMD5(String.valueOf(passAct.getPassword()));
						String hashPassNueva = getMD5(String.valueOf(contNueva1));
						UsuarioDsk usrLogueado = getFrmPpal().getUsrLogueado();
						//controlo que la password que inserta como actual es correcta...
						UsuarioDsk usrControl = mgrUsr.obtenerUsuario(usrLogueado.getNomUsu(), hashPassActual);
						if(usrControl != null) {
							usrControl.setPass(hashPassNueva);
							mgrUsr.modificarUsuario(usrControl);
							enviarInfo(USR, USR_CONFIRM_CC);
							cerrarIFrmCp();
						} else {
							enviarError(USR, USR_PASS_VAL);
						}
					}
				}
			} else {
				enviarWarning(USR, DATOS_OBLIG);
			}	
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	//Trae todos los usuarios guardados para usar en combobox
	public ArrayList<UsuarioDsk> obtenerListaUsuarios() {
		ArrayList<UsuarioDsk> listaUsuario = null;
		try {
			ManagerUsuario mgrUsr = new ManagerUsuario();
			listaUsuario = (ArrayList<UsuarioDsk>) mgrUsr.obtenerListaUsuarios();
				
		} catch(Exception e) {
			manejarExcepcion(e);
		}
		return listaUsuario;
	}
	
	public Boolean controlIgualdadPass(String contUno, String contDos) {
		return contUno.equals(contDos);
	}
	
	
	//Usuarios
	
	public void abrirIFrmUsu() {
		try {
			IfrmUsuario ifrmUsu = new IfrmUsuario(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmUsu);
			//
			Component comp = getFrmPpal().getContentPane().getComponent(1);
			comp.setVisible(false);
			//
			ifrmUsu.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmUsu() {
		try {
			Component comp = getFrmPpal().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void abrirIFrmCp() {
		try {
			IfrmCambioPass ifrmCp = new IfrmCambioPass(this);
			getDeskPane().setBounds(0, 0, 784, 565);
			getDeskPane().add(ifrmCp);
			//
			Component comp = getFrmPpal().getContentPane().getComponent(1);
			comp.setVisible(false);
			//
			ifrmCp.show();
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cerrarIFrmCp() {
		try {
			Component comp = getFrmPpal().getContentPane().getComponent(1);
			getDeskPane().setBounds(0, 0, 0, 0);
			comp.setVisible(true);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public FrmPrincipal getFrmPpal() {
		return frmPpal;
	}
	public void setFrmPpal(FrmPrincipal frmPpal) {
		this.frmPpal = frmPpal;
	}
	
	public JDesktopPane getDeskPane() {
		return deskPane;
	}
	public void setDeskPane(JDesktopPane deskPane) {
		this.deskPane = deskPane;
	}
	
	public IfrmUsuario getIfrmUsr() {
		return ifrmUsr;
	}
	public void setIfrmUsr(IfrmUsuario ifrmUsr) {
		this.ifrmUsr = ifrmUsr;
	}
	
}
