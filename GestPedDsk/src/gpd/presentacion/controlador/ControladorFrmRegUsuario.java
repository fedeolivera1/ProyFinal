package gpd.presentacion.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.manager.usuario.ManagerUsuario;

public class ControladorFrmRegUsuario {

	public void registrarUsuario(UsuarioDsk usr){
		
		String cont= usr.getPass();
		usr.setPass(getMD5(cont));
		ManagerUsuario mgrUsr = ManagerUsuario.getManagerUsuario();
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
