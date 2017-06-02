package gpd.manager.usuario;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.usuario.PersistenciaUsuario;

public class ManagerUsuario {

	private static final Logger logger = Logger.getLogger(ManagerUsuario.class);
	private IPersUsuario interfaceUsuario;
	private Integer resultado;
	

	public UsuarioDsk obtenerUsuario(String nombreUsuario, String passwd) {
		logger.info("Se ingresa a obtenerUsuario para " + nombreUsuario);
		UsuarioDsk usuario = null;
		interfaceUsuario = new PersistenciaUsuario();
		try {
			Conector.getConn();
			usuario = interfaceUsuario.obtenerUsuario(nombreUsuario, passwd);
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME controlar
		}
		return usuario;
	}

	public Integer guardarUsuario(UsuarioDsk usuario) {
		logger.info("Ingresa guardarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				interfaceUsuario = new PersistenciaUsuario();
				resultado = interfaceUsuario.guardarUsuario(usuario);
				Conector.closeConn("guardarUsuario", null);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME controlar
			}
		}
		return resultado;
	}

	public Integer modificarUsuario(UsuarioDsk usuario) {
		logger.info("Ingresa modificarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				interfaceUsuario = new PersistenciaUsuario();
				resultado = interfaceUsuario.modificarUsuario(usuario);
				Conector.closeConn("modificarUsuario", null);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME controlar
			}
		}
		return resultado;
	}

	public Integer eliminarUsuario(UsuarioDsk usuario) {
		logger.info("Ingresa eliminarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				interfaceUsuario = new PersistenciaUsuario();
				resultado = interfaceUsuario.eliminarUsuario(usuario);
				Conector.closeConn("eliminarUsuario", null);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME controlar
			}
		}
		return resultado;
	}
	
}
