package gpd.persistencia.conector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.exceptions.ConectorException;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public abstract class Conector {
	
	private static final Logger logger = Logger.getLogger(Conector.class);
	private static Connection conn = null;
	
	/**
	 * obtiene la conexion con info de la base de datos
	 */
	public static void getConn() {
		try {
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Class.forName(cfgDrv.getDbDriver());
			conn = DriverManager.getConnection(cfgDrv.getDbUrl()+cfgDrv.getDbName(), cfgDrv.getDbUser(), cfgDrv.getDbPass());
			conn.setAutoCommit(false);
			logger.debug("Se abre conexion db... Thread: " + Thread.currentThread().getId());
		} catch (SQLException | ClassNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("Error al hacer rollback: " + e.getMessage(), e);
			}
			logger.fatal("Error al conectar a la base de datos: " + e.getMessage(), e);
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		} finally {
			logger.info("Conector instanciado correctamente...");
		}
	}
	
	
	/**
	 * Hace commit de la conexion activa
	 *
	 */
	private static void commitConn() {
		try {
			conn.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Hace rollback de la conexion activa
	 */
	public static void rollbackConn() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @param nameOp
	 * @param ResultSet
	 * cierra la conexion activa, y hace commit de la misma
	 * el parametro ResultSet se pasa 'null' en casos de selects
	 */
	public static void closeConn(String nameOp, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(conn != null && !conn.isClosed()) {
				commitConn();
				conn.close();
			}
			logger.debug("Se cierra la conexion en el metodo - " + nameOp + ". Thread: " + Thread.currentThread().getId());
		} catch (SQLException e) {
			logger.error("ERROR - Conector al cerrar conexion en el metodo - " + nameOp + ". Error al cerrar las conexiones a BD." + e.getMessage(), e);
		}
	}
	
	/**
	 * @param GenSqlSelectType
	 * 	recibe un tipo de dato GenSqlSelectType, para obtener el statement y los datos de 
	 * 	la condicion
	 * @return ResultSet con resultado de consulta
	 * @throws ConectorException 
	 */
	protected static ResultSet selectGeneric(GenSqlSelectType genType) throws ConectorException {
		logger.info("Ejecucion selectGeneric");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(genType.getStatement());
			for (Integer key : genType.getSelectDatosCond().keySet()) {
				Object value = (Object) genType.getSelectDatosCond().get(key);
				fillPreparedStatement(ps, key, value);
			}
			logger.debug("Ejecucion query: " + ps.toString());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			logger.error("Excepcion de SQL al ejecutar 'selectGeneric': " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Excepcion genérica al ejecutar 'selectGeneric': " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		}
		return rs;
	}
	
	/**
	 * @param GenSqlSelectType
	 * 	recibe un tipo de dato GenSqlExecType, para obtener el statement y los datos de 
	 * 	insercion, modificacion o eliminacion para una tupla
	 * @return Integer con la cantidad de tuplas afectadas
	 * @throws ConectorException 
	 */
	protected static Integer executeNonQuery(GenSqlExecType genType) throws ConectorException {
		logger.info("Ejecucion executeNonQuery");
		Integer retorno = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(genType.getStatement());
			
 			for(Integer key : genType.getExecuteDatosCond().keySet()) {
				Object value = (Object) genType.getExecuteDatosCond().get(key);
				fillPreparedStatement(ps, key, value);
			}
 			logger.debug("Ejecucion query: " + genType.getStatement());
			retorno = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Excepcion de SQL al ejecutar 'executeNonQuery': " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Excepcion genérica al ejecutar 'executeNonQuery': " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		}
		return retorno;
	}
	
	/**
	 * @param GenSqlSelectType genType
	 * 	recibe un tipo de dato GenSqlExecType, para obtener el statement y la lista de datos de 
	 * 	insercion, modificacion o eliminacion. Para cada juego de datos, llama al executeNonQuery 
	 * 	unitario.
	 * @return Integer con la cantidad de tuplas afectadas
	 * @throws ConectorException 
	 */
	protected static Integer executeNonQueryList(GenSqlExecType genType) throws ConectorException {
		logger.debug("Ejecucion executeNonQueryList");
		Integer retorno = 0;
		if(genType.getListaExecuteDatosCond() != null &&
				!genType.getListaExecuteDatosCond().isEmpty()) {
			for(HashMap<Integer, Object> hashDatos : genType.getListaExecuteDatosCond()) {
				GenSqlExecType genTypeAux = new GenSqlExecType(genType.getStatement(), hashDatos);
				retorno += executeNonQuery(genTypeAux);
			}
		}
		return retorno;
	}
	
	/**
	 * 
	 * @param PreparedStatement ps
	 * @param Integer key
	 * @param Object data
	 * setea un preparedStatement a partir de datos genericos
	 * @throws ConectorException 
	 */
	protected static void fillPreparedStatement(PreparedStatement ps, Integer key, Object data) throws ConectorException {
		try {
			if(null == data) {
				ps.setObject(key, null, java.sql.Types.NULL);
				logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.NULL");
			} else {
				if (data instanceof String) {
					ps.setObject(key, data, java.sql.Types.VARCHAR);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.VARCHAR: " + data);
				} else if (data instanceof Integer) {
					ps.setObject(key, data, java.sql.Types.INTEGER);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.INTEGER: " + data);
				} else if (data instanceof Fecha) {
					Fecha fecha = (Fecha) data;
					if(fecha.esTimeStamp()) {
						ps.setObject(key, fecha.getTimestampSql(), java.sql.Types.TIMESTAMP);
						logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.TIMESTAMP: " + data);
					} else if(fecha.esHM()) {
						ps.setObject(key, fecha.getTimeSql(), java.sql.Types.TIME);
						logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.TIME: " + data);
					} else {
						ps.setObject(key, fecha.getDateSql(), java.sql.Types.DATE);
						logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.DATE: " + data);
					}
				} else if ((data instanceof java.util.Date) || (data instanceof java.util.GregorianCalendar)) {
					ps.setObject(key, data, java.sql.Types.DATE);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.DATE: " + data);
				} else if (data instanceof Character) {
					ps.setObject(key, data, java.sql.Types.CHAR);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.CHAR: " + data);
				} else if (data instanceof Boolean) {
					ps.setObject(key, data, java.sql.Types.BOOLEAN);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.BOOLEAN: " + data);
				} else if (data instanceof Long) {
					ps.setObject(key, data, java.sql.Types.BIGINT);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.BIGINT: " + data);
				} else if (data instanceof Double) {
					ps.setObject(key, data, java.sql.Types.DOUBLE);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.DOUBLE: " + data);
				} else if (data instanceof BigDecimal) {
					ps.setObject(key, data, java.sql.Types.DECIMAL);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.DECIMAL: " + data);
				} else if (data instanceof Float) {
					ps.setObject(key, data, java.sql.Types.FLOAT);
					logger.debug("seteo param en posi " + key + " del tipo java.sql.Types.FLOAT: " + data);
				} else {
					logger.error("Error: Tipo de parametro no encontrado!");
				}
			}
		} catch (SQLException | ClassCastException e) {
			logger.fatal("Excepcion en fillPreparedStatement : " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en fillPreparedStatement: " + e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param <T> genType puede ser GenSqlSelectType (genérico para select) o 
 	 * GenSqlExecType (generico para ejecuciones insert, update o delete)
 	 * Este método se encarga de resolver con cual generico es invocado y
 	 * llamar dependiendo de los paramtros serteados.
 	 * @Return <T> Object depende de que método ejecute.
	 * @throws GenericException
	 * @throws ConectorException 
	 */
	protected static <T> Object runGeneric(T genType) throws ConectorException {
		Object resultado;
		try {
			if(genType instanceof GenSqlSelectType) {
				resultado = (ResultSet) selectGeneric((GenSqlSelectType) genType);
			} else if(genType instanceof GenSqlExecType) {
				GenSqlExecType genExec = (GenSqlExecType) genType;
				if(!genExec.getListaExecuteDatosCond().isEmpty()) {
					resultado = (Integer) executeNonQueryList(genExec);
				} else {
					resultado = (Integer) executeNonQuery(genExec);
				}
			} else {
				rollbackConn();
				throw new ConectorException("executeGeneric ha sido mal implementado.");
			}
		} catch (ConectorException e) {
			logger.error(e.getMessage(), e);
			throw new ConectorException(e.getMessage(), e);
		}
		
		return resultado;
	}
	
	
}
