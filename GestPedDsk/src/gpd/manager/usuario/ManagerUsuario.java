package gpd.manager.usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.usuario.PersistenciaUsuario;

public class ManagerUsuario {

	private static final Logger logger = Logger.getLogger(ManagerUsuario.class);
	private static IPersUsuario interfaceUsuario;
	private Integer resultado;
	
	private static IPersUsuario getInterfaceUsuario() {
		if(interfaceUsuario == null) {
			interfaceUsuario = new PersistenciaUsuario();
		}
		return interfaceUsuario;
	}
	
	/*****************************************************************************************************************************************************/
	/** USUARIO */
	/*****************************************************************************************************************************************************/
	
	public UsuarioDsk obtenerUsuario(String nombreUsuario, String passwd) throws PresentacionException {
		logger.info("Se ingresa a obtenerUsuario para " + nombreUsuario);
		UsuarioDsk usuario = null;
		try {
			Conector.getConn();
			usuario = getInterfaceUsuario().obtenerUsuario(nombreUsuario, passwd);
			Conector.closeConn("obtenerUsuario");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerUsuario > obtenerUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return usuario;
	}
	
	public List<UsuarioDsk> obtenerListaUsuarios() throws PresentacionException{
		List<UsuarioDsk> listaUsuario = new ArrayList<UsuarioDsk>();
		
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			Conector.getConn();
			listaUsuario = interfaceUsuario.obtenerListaUsuario();
			Conector.closeConn("obtenerTodosLosUsuarios");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerUsuario > obtenerUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		
		return listaUsuario;
	}

	public Integer guardarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa guardarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUsuario().guardarUsuario(usuario);
				Conector.closeConn("guardarUsuario");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerUsuario > guardarUsuario: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer modificarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa modificarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUsuario().modificarUsuario(usuario);
				Conector.closeConn("modificarUsuario");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerUsuario > modificarUsuario: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer eliminarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa eliminarUsuario");
		if(usuario != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUsuario().eliminarUsuario(usuario);
				Conector.closeConn("eliminarUsuario");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerUsuario > eliminarUsuario: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
}
