package gpd.presentacion.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.UsuarioNoExisteException;
import gpd.manager.usuario.ManagerUsuario;

public class ControladorFrmLogin {

	public UsuarioDsk obtenerUsuario(String nombre, String passwd) throws UsuarioNoExisteException {
		UsuarioDsk usr = null;
		if(nombre == null || passwd == null) {
			throw new UsuarioNoExisteException("Debe proporcionar nombre de usuario y password.");
		} else {
			String hashPass = getMD5(passwd);
			ManagerUsuario mgrUsr = ManagerUsuario.getManagerUsuario();
			usr = mgrUsr.obtenerUsuario(nombre, hashPass);
		}
		return usr;
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
