package gpd.manager.usuario;

import java.sql.Connection;
import java.sql.SQLException;
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
		try (Connection conn = Conector.getConn()) {
			usuario = getInterfaceUsuario().obtenerUsuario(conn, nombreUsuario, passwd);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > obtenerUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > obtenerUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return usuario;
	}
	
	public List<UsuarioDsk> obtenerListaUsuarios() throws PresentacionException{
		List<UsuarioDsk> listaUsuario = new ArrayList<UsuarioDsk>();
		
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try (Connection conn = Conector.getConn()) {
			listaUsuario = interfaceUsuario.obtenerListaUsuario(conn);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > obtenerListaUsuarios: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > obtenerListaUsuarios: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		
		return listaUsuario;
	}

	public Integer guardarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa guardarUsuario");
		try (Connection conn = Conector.getConn()) {
			if(usuario != null) {
				resultado = getInterfaceUsuario().guardarUsuario(conn, usuario);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > guardarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > guardarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}

	public Integer modificarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa modificarUsuario");
		try (Connection conn = Conector.getConn()) {
			if(usuario != null) {
				resultado = getInterfaceUsuario().modificarUsuario(conn, usuario);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > modificarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > modificarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer modificarUsuarioSinPass(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa modificarUsuario");
		try (Connection conn = Conector.getConn()) {
			if(usuario != null) {
				resultado = getInterfaceUsuario().modificarUsuarioSinPass(conn, usuario);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > modificarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > modificarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}

	public Integer eliminarUsuario(UsuarioDsk usuario) throws PresentacionException {
		logger.info("Ingresa eliminarUsuario");
		try (Connection conn = Conector.getConn()) {
			if(usuario != null) {
				resultado = getInterfaceUsuario().eliminarUsuario(conn, usuario);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > eliminarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > eliminarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Boolean checkExistUsuario(String nombreUsuario) throws PresentacionException {
		logger.info("Ingresa eliminarUsuario");
		Boolean exist = false;
		try (Connection conn = Conector.getConn()) {
			if(nombreUsuario != null) {
				exist = getInterfaceUsuario().checkExistUsuario(conn, nombreUsuario);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerUsuario > eliminarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerUsuario > eliminarUsuario: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return exist;
	}
	
}
