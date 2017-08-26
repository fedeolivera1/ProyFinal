package gpd.persistencia.producto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryDeposito;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Deposito;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersDeposito;
import gpd.persistencia.conector.Conector;

public class PersistenciaDeposito extends Conector implements IPersDeposito, CnstQryDeposito {

	private static final Logger logger = Logger.getLogger(PersistenciaDeposito.class);

	
	@Override
	public Deposito obtenerDepositoPorId(Connection conn, Integer id) throws PersistenciaException {
		Deposito deposito = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_DEPOSITO_X_ID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					deposito = new Deposito();
					deposito.setNroDep(rs.getInt("nro_dep"));
					deposito.setNombre(rs.getString("nombre"));
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerDepositoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerDepositoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return deposito;
	}

	@Override
	public List<Deposito> obtenerListaDeposito(Connection conn) throws PersistenciaException {
		List<Deposito> listaDeposito = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_DEPOSITO);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Deposito deposito = new Deposito();
					deposito.setNroDep(rs.getInt("nro_dep"));
					deposito.setNombre(rs.getString("nombre"));
					listaDeposito.add(deposito);
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaDeposito;
	}

	@Override
	public Integer guardarDeposito(Connection conn, Deposito deposito) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_DEPOSITO);
		genExec.setParam(deposito.getNombre());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarDeposito(Connection conn, Deposito deposito) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_DEPOSITO);
		genExec.setParam(deposito.getNombre());
		genExec.setParam(deposito.getNroDep());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarDeposito(Connection conn, Deposito deposito) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_DEPOSITO);
		genExec.setParam(deposito.getNroDep());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
