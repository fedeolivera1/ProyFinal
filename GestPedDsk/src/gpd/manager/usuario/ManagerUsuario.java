package gpd.manager.usuario;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.usuario.PersistenciaUsuario;

public class ManagerUsuario {

	private static final Logger logger = Logger.getLogger(ManagerUsuario.class);
	
	
	public UsuarioDsk obtenerUsuario(String nombreUsuario, String passwd) {
		UsuarioDsk usuario = null;
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			usuario = interfaceUsuario.obtenerUsuario(nombreUsuario, passwd);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearLogin", null);
		return usuario;
	}

	public void guardarUsuario(UsuarioDsk usuario) {
		
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			
			interfaceUsuario.guardarUsuario(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearSiSeGuardo", null);
	}

	public UsuarioDsk modificarUsuario(UsuarioDsk usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	public UsuarioDsk eliminarUsuario(UsuarioDsk usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
