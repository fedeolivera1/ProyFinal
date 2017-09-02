package gpd.persistencia.transaccion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Lote;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.types.Fecha;

public class PersistenciaTransaccion extends Conector implements IPersTransaccion, CnstQryTransaccion {

	private static final Logger logger = Logger.getLogger(PersistenciaTransaccion.class);
	
	
	@Override
	public Integer guardarTransaccionCompra(Connection conn, Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANSACCION);
			PersonaJuridica pj = (PersonaJuridica) transaccion.getPersona();
			genExec.setParam(transaccion.getNroTransac());
			genExec.setParam(pj.getRut());
			genExec.setParam(transaccion.getTipoTran().getAsChar());
			genExec.setParam(transaccion.getFechaHora());
			genExec.setParam(transaccion.getSubTotal());
			genExec.setParam(transaccion.getIva());
			genExec.setParam(transaccion.getTotal());
			genExec.setParam(transaccion.getEstadoTran().getAsChar());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarTransaccionCompra: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarTransaccionCompra: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer guardarTransaccionVenta(Connection conn, Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANSACCION);
		genExec.setParam(transaccion.getNroTransac());
		genExec.setParam(transaccion.getPersona().getIdPersona());
		genExec.setParam(transaccion.getTipoTran().getAsChar());
		genExec.setParam(transaccion.getFechaHora());
		genExec.setParam(transaccion.getSubTotal());
		genExec.setParam(transaccion.getIva());
		genExec.setParam(transaccion.getTotal());
		genExec.setParam(transaccion.getEstadoTran().getAsChar());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarTransaccionVenta: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarTransaccionVenta: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarEstadoTransaccion(Connection conn, Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_TRAN_EST);
		genExec.setParam(transaccion.getEstadoTran().getAsChar());
		genExec.setParam(transaccion.getNroTransac());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al modificarEstadoTransaccion: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarEstadoTransaccion: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}


	@Override
	public Transaccion obtenerTransaccionPorId(Connection conn, Integer idTransac) throws PersistenciaException {
		Transaccion transac = null;
		PersistenciaPersona pp = new PersistenciaPersona();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRAN_XID);
			genSel.setParam(idTransac);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					transac = new Transaccion(null);
					transac.setNroTransac(rs.getInt("nro_transac"));
					transac.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					char[] tipo = new char[1];
					rs.getCharacterStream("operacion").read(tipo);
					TipoTran tipoTx = TipoTran.getTipoTranPorChar(tipo[0]);
					transac.setTipoTran(tipoTx);
					char[] estado = new char[1];
					rs.getCharacterStream("estado_act").read(estado);
					EstadoTran estadoTx = EstadoTran.getEstadoTranPorChar(estado[0]);
					transac.setEstadoTran(estadoTx);
					transac.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					transac.setSubTotal(rs.getDouble("sub_total"));
					transac.setIva(rs.getDouble("iva"));
					transac.setTotal(rs.getDouble("total"));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return transac;
	}
	
	@Override
	public List<Transaccion> obtenerListaTransaccionPorPersona(Connection conn, Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) throws PersistenciaException {
		List<Transaccion> listaTransac = new ArrayList<>();
		try {
			PersistenciaPersona pp = new PersistenciaPersona();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRAN_XPERS);
			genSel.setParam(idPersona);
			genSel.setParamCharIfNull(tipoTran.getAsChar());
			genSel.setParamCharIfNull(tipoTran.getAsChar());
			genSel.setParamCharIfNull(estadoTran.getAsChar());
			genSel.setParamCharIfNull(estadoTran.getAsChar());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Transaccion transac = new Transaccion(null);
					transac.setNroTransac(rs.getInt("nro_transac"));
					transac.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					char[] tipo = new char[1];
					rs.getCharacterStream("operacion").read(tipo);
					TipoTran tipoTx = TipoTran.getTipoTranPorChar(tipo[0]);
					transac.setTipoTran(tipoTx);
					char[] estado = new char[1];
					rs.getCharacterStream("estado_act").read(estado);
					EstadoTran estadoTx = EstadoTran.getEstadoTranPorChar(estado[0]);
					transac.setEstadoTran(estadoTx);
					transac.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					transac.setSubTotal(rs.getDouble("sub_total"));
					transac.setIva(rs.getDouble("iva"));
					transac.setTotal(rs.getDouble("total"));
					listaTransac.add(transac);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTransac;
	}
	
	@Override
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(Connection conn, TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) throws PersistenciaException {
		List<Transaccion> listaTransac = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRAN_XPERIODO);
			genSel.setParamCharIfNull(tipoTran.getAsChar());
			genSel.setParamCharIfNull(tipoTran.getAsChar());
			genSel.setParamCharIfNull(estadoTran.getAsChar());
			genSel.setParamCharIfNull(estadoTran.getAsChar());
			genSel.setParam(fechaIni);
			genSel.setParam(fechaFin);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Transaccion transac = new Transaccion(null);
					transac.setNroTransac(rs.getInt("nro_transac"));
					transac.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					char[] tipo = new char[1];
					rs.getCharacterStream("operacion").read(tipo);
					TipoTran tipoTx = TipoTran.getTipoTranPorChar(tipo[0]);
					transac.setTipoTran(tipoTx);
					char[] estado = new char[1];
					rs.getCharacterStream("estado_act").read(estado);
					EstadoTran estadoTx = EstadoTran.getEstadoTranPorChar(estado[0]);
					transac.setEstadoTran(estadoTx);
					transac.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					transac.setSubTotal(rs.getDouble("sub_total"));
					transac.setIva(rs.getDouble("iva"));
					transac.setTotal(rs.getDouble("total"));
					listaTransac.add(transac);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTransac;
	}
	
	/*****************************************************************************************************************************************************/
	
	@Override
	public Integer guardarTranEstado(Connection conn, Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANESTADO);
			genExec.setParam(transaccion.getNroTransac());
			genExec.setParam(transaccion.getEstadoTran().getAsChar());
			genExec.setParam(transaccion.getFechaHora());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarTranEstado: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarTranEstado: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public EstadoTran obtenerUltTranEstadoPorId(Connection conn, Integer idTransac) throws PersistenciaException {
		EstadoTran estadoTran = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_ULT_TRANESTADO_XID);
			genSel.setParam(idTransac);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					char[] estadoChar = new char[1];
					rs.getCharacterStream("estado").read(estadoChar);
					estadoTran = EstadoTran.getEstadoTranPorChar(estadoChar[0]);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerUltTranEstadoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerUltTranEstadoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return estadoTran;
	}

	/*****************************************************************************************************************************************************/
	
	@Override
	public List<TranLineaLote> obtenerListaTranLineaLote(Connection conn, Integer nroTransac, Integer idProducto) throws PersistenciaException {
		List<TranLineaLote> listaTll = new ArrayList<>();
		try {
			PersistenciaTranLinea ptl = new PersistenciaTranLinea();
			PersistenciaLote pl = new PersistenciaLote();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRA_VTA_LOTE_XID);
			genSel.setParam(nroTransac);
			genSel.setParam(idProducto);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					TranLinea tl = ptl.obtenerTranLineaPorId(conn, nroTransac, idProducto);
					Lote lote = pl.obtenerLotePorId(conn, rs.getInt("id_lote"));
					TranLineaLote tll = new TranLineaLote(tl, lote, rs.getInt("cantidad"));
					listaTll.add(tll);
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTll;
	}
	
	@Override
	public Integer guardarListaTranLineaLote(Connection conn, List<TranLineaLote> listaTll) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRA_VTA_LOTE);
			ArrayList<Object> paramList = null;
			for(TranLineaLote tll : listaTll) {
				paramList = new ArrayList<>();
				paramList.add(tll.getTranLinea().getTransaccion().getNroTransac());
				paramList.add(tll.getTranLinea().getProducto().getIdProducto());
				paramList.add(tll.getLote().getIdLote());
				paramList.add(tll.getCantidad());
				genExec.setParamList(paramList);
			}
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarListaTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarListaTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTranLineaLote(Connection conn, Integer nroTransac) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TRA_VTA_LOTE);
			genExec.setParam(nroTransac);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
