package gpd.presentacion.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.UsuarioNoExisteException;
import gpd.manager.usuario.ManagerUsuario;
import gpd.presentacion.generic.CnstPresGeneric;

public class CtrlUsuario extends CtrlGenerico {

	
	public void cambiarCont(String usu, char[] nuCont){
	
	}
	
	public void eliminarUsuario(String usu){
	
	}
	
	public void registrarUsuario(String usu, String passwd, TipoUsr tipoUs) throws UsuarioNoExisteException{
		if((usu == null || usu == "") || (passwd == null || passwd == "")) {
			throw new UsuarioNoExisteException(CnstPresGeneric.USR_DATOS);
		} else {
			String hashPass = getMD5(passwd);
			ManagerUsuario mgrUsr = new ManagerUsuario();
			UsuarioDsk usr = new UsuarioDsk(usu, hashPass, tipoUs);
			mgrUsr.guardarUsuario(usr);
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
