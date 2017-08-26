package gpd.persistencia.usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUsuario;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;

/**
 * COMENTARIO GENERAL PARA LA CLASE PersistenciaUsuario:
 * no se utilizaran los metodos genericos de persistencia, ya que se requiere NO
 * loguear los datos de passwords de los usuarios.  
 */
public class PersistenciaUsuario extends Conector implements IPersUsuario, CnstQryUsuario {

	private static final Logger logger = Logger.getLogger(PersistenciaUsuario.class);
	
	
	@Override
	public UsuarioDsk obtenerUsuario(Connection conn, String nombreUsuario, String passwd) throws PersistenciaException {
		logger.info("Ejecucion de obtenerUsuario para: " + nombreUsuario);
		UsuarioDsk usuario = null;
		try {
			PreparedStatement ps = conn.prepareStatement(QRY_LOGIN);
			ps.setString(1, nombreUsuario);
			ps.setString(2, passwd);
			try (ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					usuario = new UsuarioDsk();
					usuario.setNomUsu(nombreUsuario);
					usuario.setPass(passwd);
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					TipoUsr tipo = TipoUsr.getTipoUsrPorChar(tipoChar[0]);
					usuario.setTipoUsr(tipo);
				}
			}
		} catch (SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return usuario;
	}
	
	@Override
	public UsuarioDsk obtenerUsuarioPorId(Connection conn, String nombreUsuario) throws PersistenciaException {
		logger.info("Ejecucion de obtenerUsuario para: " + nombreUsuario);
		UsuarioDsk usuario = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_USR_XID);
			genType.setParam(nombreUsuario);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genType)) {
				if(rs.next()) {
					usuario = new UsuarioDsk();
					usuario.setNomUsu(nombreUsuario);
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					TipoUsr tipo = TipoUsr.getTipoUsrPorChar(tipoChar[0]);
					usuario.setTipoUsr(tipo);
				}
			}
		} catch (SQLException | IOException | ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerUsuarioPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerUsuarioPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return usuario;
	}
	
	@Override
	public List<UsuarioDsk> obtenerListaUsuario(Connection conn) throws PersistenciaException {
		ArrayList<UsuarioDsk> listaUsuario = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryUsuario.QRY_SELECT_USR);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaUsuario = new ArrayList<UsuarioDsk>();
				while(rs.next()) {
					UsuarioDsk usuario= new UsuarioDsk();
					usuario.setNomUsu(rs.getString(1));
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					TipoUsr tipo = TipoUsr.getTipoUsrPorChar(tipoChar[0]);
					usuario.setTipoUsr(tipo);
					listaUsuario.add(usuario);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaUsuario;
	}

	@Override
	public Integer guardarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException {
		logger.info("Ejecucion de guardarUsuario para: " + usuario.getNomUsu());
		Integer resultado = null;
		try {
			PreparedStatement ps = conn.prepareStatement(QRY_INSERT_USR);
			ps.setString(1, usuario.getNomUsu());
			ps.setString(2, usuario.getPass());
			ps.setObject(3, usuario.getTipoUsr().getAsChar(), java.sql.Types.CHAR);
			resultado = ps.executeUpdate();
		} catch (SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException {
		logger.info("Ejecucion de modificarUsuario para: " + usuario.getNomUsu());
		Integer resultado = null;
		try {
			PreparedStatement ps = conn.prepareStatement(QRY_UPDATE_USR);
			ps.setString(1, usuario.getPass());
			ps.setObject(2, usuario.getTipoUsr().getAsChar(), java.sql.Types.CHAR);
			ps.setString(3, usuario.getNomUsu());
			resultado = ps.executeUpdate();
		} catch (SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException {
		logger.info("Ejecucion de eliminarUsuario para: " + usuario.getNomUsu());
		Integer resultado = null;
		try {
			PreparedStatement ps = conn.prepareStatement(QRY_DELETE_USR);
			ps.setString(1, usuario.getNomUsu());
			resultado = ps.executeUpdate();
		} catch (SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
