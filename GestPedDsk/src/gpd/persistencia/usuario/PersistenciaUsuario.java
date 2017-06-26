package gpd.persistencia.usuario;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUsuario;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;

public class PersistenciaUsuario extends Conector implements IPersUsuario {

	private static final Logger logger = Logger.getLogger(PersistenciaUsuario.class);
	
	@Override
	public UsuarioDsk obtenerUsuario(String nombreUsuario, String passwd) throws PersistenciaException {
		UsuarioDsk usuario = null;
		ResultSet resultado;
		GenSqlSelectType genSel = new GenSqlSelectType(CnstQryUsuario.QRY_LOGIN);
		genSel.setParam(nombreUsuario);
		genSel.setParam(passwd);
		try {
			resultado = (ResultSet) Conector.runGeneric(genSel);
			if(resultado.next()) {
				usuario = new UsuarioDsk();
				usuario.setNomUsu(nombreUsuario);
				usuario.setPass(passwd);
				char[] tipoChar = new char[1];
				resultado.getCharacterStream("tipo").read(tipoChar);
				TipoUsr tipo = TipoUsr.getTipoUsrPorChar(tipoChar[0]);
				usuario.setTipoUsr(tipo);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerUsuario: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage()); 
		}
		return usuario;
	}

	@Override
	public Integer guardarUsuario(UsuarioDsk usuario) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryUsuario.QRY_INSERT_USR);
		genExec.setParam(usuario.getNomUsu());
		genExec.setParam(usuario.getPass());
		genExec.setParam(usuario.getTipoUsr().getAsChar());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarUsuario: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUsuario(UsuarioDsk usuario) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryUsuario.QRY_UPDATE_USR);
		genExec.setParam( usuario.getPass());
		genExec.setParam(usuario.getNomUsu());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarUsuario: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarUsuario(UsuarioDsk usuario) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryUsuario.QRY_DELETE_USR);
		genExec.setParam(usuario.getNomUsu());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarUsuario: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public ArrayList<UsuarioDsk> obtenerTodo() {
		ArrayList<UsuarioDsk> usuarios = null;
		ResultSet resultado;
		GenSqlSelectType genSel = new GenSqlSelectType(CnstQryUsuario.QRY_TODO);
		
		try {
			resultado = (ResultSet) Conector.runGeneric(genSel);
			usuarios=new ArrayList<UsuarioDsk>();
			while(resultado.next()) {
				UsuarioDsk usuario= new UsuarioDsk();
				usuario.setNomUsu(resultado.getString(1));
				// no es necesario usuario.setPass(resultado.getString(1));
				char[] tipoChar = new char[1];
				resultado.getCharacterStream("tipo").read(tipoChar);
				TipoUsr tipo = TipoUsr.getTipoUsrPorChar(tipoChar[0]);
				usuario.setTipoUsr(tipo);
				usuarios.add(usuario);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerTodo: " + e.getMessage(), e);
		}
		return usuarios;
	}

}
