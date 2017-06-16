package gpd.presentacion.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.usuario.ManagerUsuario;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;

public class CtrlFrmRegUsuario extends CtrlGenerico {

	public void registrarUsuario(UsuarioDsk usr){
		
		String cont= usr.getPass();
		usr.setPass(getMD5(cont));
		ManagerUsuario mgrUsr = new ManagerUsuario();
	}
	
	public void registrarUsuario(JTextField txtNomUsu, JPasswordField txtPasswd, JPasswordField txtPasswd2, JComboBox<TipoUsr> cbxTipoUsr) {
		GenCompType genComp = new GenCompType();
		genComp.setComp(txtNomUsu);
		genComp.setComp(txtPasswd);
		genComp.setComp(txtPasswd2);
		genComp.setComp(cbxTipoUsr);
		if(controlDatosObl(genComp)) {
			//llamas a metodo de mgrUsr.agregar
			String cont=getMD5(String.valueOf(txtPasswd.getPassword()));
			UsuarioDsk usr = new UsuarioDsk(txtNomUsu.getText(), cont, cbxTipoUsr.getItemAt(cbxTipoUsr.getSelectedIndex()));
			ManagerUsuario mgrUsr= new ManagerUsuario();
			mgrUsr.guardarUsuario(usr);
		} else {
			enviarWarning(CnstPresGeneric.PERS, "Revise los datos marcados en rojo.");
		}
	}
	
	private static String getMD5(String input) {
		 try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(input.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 String hashtext = number.toString(16);
		 
			 while (hashtext.length() < 32) {
			 	hashtext = "0" + hashtext;
		 	}
		 	return hashtext;
	 	}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	 }
}
