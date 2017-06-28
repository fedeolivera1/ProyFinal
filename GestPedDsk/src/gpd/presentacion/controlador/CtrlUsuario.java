package gpd.presentacion.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.NuevaContraseniaException;
import gpd.exceptions.UsuarioNoExisteException;
import gpd.manager.usuario.ManagerUsuario;
import gpd.presentacion.generic.CnstPresGeneric;

public class CtrlUsuario extends CtrlGenerico {

	
	public void cambiarCont(UsuarioDsk usu, char[] cont1, char[] cont2) throws NuevaContraseniaException{
		String contUno=String.valueOf(cont1);
		String contDos=String.valueOf(cont2);
		
		if(contUno.equals(null)){
			throw new NuevaContraseniaException(CnstPresGeneric.USR_PASS_NO_INGRESADO);
		}else{
			if(contUno.equals("")){
				throw new NuevaContraseniaException(CnstPresGeneric.USR_PASS_NO_INGRESADO);
			}else{
				if(!contUno.equals(contDos)){
					throw new NuevaContraseniaException(CnstPresGeneric.USR_PASS_REP);
				}else{
					String hashPass = getMD5(String.valueOf(cont1));				
					ManagerUsuario mgrUsr = new ManagerUsuario();
					usu.setPass(hashPass);
					mgrUsr.modificarUsuario(usu);
				}
			}
			
		}
	}
	
	public void eliminarUsuario(UsuarioDsk usu){
		ManagerUsuario mgrUsr = new ManagerUsuario();
		mgrUsr.eliminarUsuario(usu);
		
	}
	
	//Trae todos los usuarios guardados para usar en combobox
	public ArrayList<UsuarioDsk> traerTodo(){
		ManagerUsuario mgrUsr = new ManagerUsuario();

		return mgrUsr.obtenerTodosLosUsuarios();
				
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
