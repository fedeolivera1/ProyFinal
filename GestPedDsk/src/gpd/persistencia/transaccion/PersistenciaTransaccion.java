package gpd.persistencia.transaccion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.PersonaFisica;
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
	private ResultSet rs;
	
	
	@Override
	public Integer guardarTransaccionCompra(Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
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
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTransaccionCompra: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer guardarTransaccionVenta(Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANSACCION);
		genExec.setParam(transaccion.getNroTransac());
		Long idPersona = null;
		if(transaccion.getPersona() instanceof PersonaFisica) {
			PersonaFisica pf = (PersonaFisica) transaccion.getPersona();
			idPersona = pf.getDocumento();
		} else if(transaccion.getPersona() instanceof PersonaJuridica) {
			PersonaJuridica pj = (PersonaJuridica) transaccion.getPersona();
			idPersona = pj.getRut();
		}
		genExec.setParam(idPersona);
		genExec.setParam(transaccion.getTipoTran().getAsChar());
		genExec.setParam(transaccion.getFechaHora());
		genExec.setParam(transaccion.getSubTotal());
		genExec.setParam(transaccion.getIva());
		genExec.setParam(transaccion.getTotal());
		genExec.setParam(transaccion.getEstadoTran().getAsChar());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTransaccionVenta: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarEstadoTransaccion(Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_TRAN_EST);
		genExec.setParam(transaccion.getEstadoTran().getAsChar());
		genExec.setParam(transaccion.getNroTransac());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTransaccionVenta: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}


	@Override
	public Transaccion obtenerTransaccionPorId(Integer idTransac) throws PersistenciaException {
		Transaccion transac = null;
		PersistenciaPersona pp = new PersistenciaPersona();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRAN_XID);
			genType.setParam(idTransac);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				transac = new Transaccion(null);
				transac.setNroTransac(rs.getInt("nro_transac"));
				transac.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return transac;
	}
	
	@Override
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) throws PersistenciaException {
		List<Transaccion> listaTransac = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRAN_XPERS);
			genType.setParam(idPersona);
			genType.setParamCharIfNull(tipoTran.getAsChar());
			genType.setParamCharIfNull(tipoTran.getAsChar());
			genType.setParamCharIfNull(estadoTran.getAsChar());
			genType.setParamCharIfNull(estadoTran.getAsChar());
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Transaccion transac = new Transaccion(null);
				transac.setNroTransac(rs.getInt("nro_transac"));
				transac.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaTransac;
	}
	
	@Override
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) throws PersistenciaException {
		List<Transaccion> listaTransac = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRAN_XPERIODO);
			genType.setParamCharIfNull(tipoTran.getAsChar());
			genType.setParamCharIfNull(tipoTran.getAsChar());
			genType.setParamCharIfNull(estadoTran.getAsChar());
			genType.setParamCharIfNull(estadoTran.getAsChar());
			genType.setParam(fechaIni);
			genType.setParam(fechaFin);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Transaccion transac = new Transaccion(null);
				transac.setNroTransac(rs.getInt("nro_transac"));
				transac.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaTransac;
	}
	
	/*****************************************************************************************************************************************************/
	
	@Override
	public Integer guardarTranEstado(Transaccion transaccion) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANESTADO);
		genExec.setParam(transaccion.getNroTransac());
		genExec.setParam(transaccion.getEstadoTran().getAsChar());
		genExec.setParam(transaccion.getFechaHora());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarTranEstado: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public EstadoTran obtenerUltTranEstadoPorId(Integer idTransac) throws PersistenciaException {
		EstadoTran estadoTran = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_ULT_TRANESTADO_XID);
			genType.setParam(idTransac);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				char[] estadoChar = new char[1];
				rs.getCharacterStream("estado").read(estadoChar);
				estadoTran = EstadoTran.getEstadoTranPorChar(estadoChar[0]);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerUltTranEstadoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return estadoTran;
	}

	/*****************************************************************************************************************************************************/
	
	@Override
	public List<TranLineaLote> obtenerListaTranLineaLote(Integer nroTransac, Integer idProducto) throws PersistenciaException {
		List<TranLineaLote> listaTll = new ArrayList<>();
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaLote pl = new PersistenciaLote();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRA_VTA_LOTE_XID);
			genType.setParam(nroTransac);
			genType.setParam(idProducto);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				TranLinea tl = ptl.obtenerTranLineaPorId(nroTransac, idProducto);
				Lote lote = pl.obtenerLotePorId(rs.getInt("id_lote"));
				TranLineaLote tll = new TranLineaLote(tl, lote, rs.getInt("cantidad"));
				listaTll.add(tll);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaTll;
	}
	
	@Override
	public Integer guardarListaTranLineaLote(List<TranLineaLote> listaTll) throws PersistenciaException {
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
				resultado = (Integer) runGeneric(genExec);
			}
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarListaTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTranLineaLote(Integer nroTransac) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TRA_VTA_LOTE);
			genExec.setParam(nroTransac);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al eliminarTranLineaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
