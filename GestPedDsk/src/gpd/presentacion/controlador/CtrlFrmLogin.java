package gpd.presentacion.controlador;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.usuario.ManagerUsuario;
import gpd.presentacion.formulario.FrmLogin;
import gpd.presentacion.formulario.FrmPrincipal;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;

public class CtrlFrmLogin extends CtrlGenerico {

	
	ManagerUsuario mgrUsr = new ManagerUsuario();
	private FrmLogin frmLogin;
	
	public CtrlFrmLogin(FrmLogin frmLogin) {
		super();
		this.frmLogin = frmLogin;
	}
	

	public UsuarioDsk obtenerUsuario(JTextField txtNombre, JPasswordField txtPass) {
		UsuarioDsk usr = null;
		GenCompType genComp = new GenCompType();
		genComp.setComp(txtNombre);
		genComp.setComp(txtPass);
		if(controlDatosObl(genComp)) {
			char[] passwsdChar = txtPass.getPassword(); 
			String passwd = String.valueOf(passwsdChar);
			String hashPass = getMD5(passwd);
			usr = mgrUsr.obtenerUsuario(txtNombre.getText(), hashPass);
			if(usr != null) {
				FrmPrincipal frmPpl = new FrmPrincipal(usr);
				frmPpl.setLocationRelativeTo(null);
				frmPpl.setDefaultCloseOperation(FrmPrincipal.EXIT_ON_CLOSE);
				frmPpl.setVisible(true);
				frmLogin.setVisible(false);
			} else {
				enviarError(CnstPresGeneric.USR, CnstPresGeneric.USR_NO_AUTENTICADO);
				
			}
		} else {
//			try{} catch(UsuarioNoExisteException e) {}
			enviarWarning(CnstPresGeneric.USR, CnstPresGeneric.USR_DATOS);
		}
		return usr;
	}
	
	
	public FrmLogin getFrmLogin() {
		return frmLogin;
	}
	public void setFrmLogin(FrmLogin frmLogin) {
		this.frmLogin = frmLogin;
	}
}
