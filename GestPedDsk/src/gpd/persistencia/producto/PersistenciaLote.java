package gpd.persistencia.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryLote;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Lote;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersLote;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.transaccion.PersistenciaTranLinea;
import gpd.types.Fecha;

public class PersistenciaLote extends Conector implements IPersLote, CnstQryLote {

	private static final Logger logger = Logger.getLogger(PersistenciaLote.class);
	private ResultSet rs;
	
	@Override
	public Lote obtenerLotePorId(Integer idLote) throws PersistenciaException {
		Lote lote = null;
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaUtilidad pu = new PersistenciaUtilidad();
		PersistenciaDeposito pd = new PersistenciaDeposito();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_LOTE_XID);
			genType.setParam(idLote);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				lote = new Lote();
				lote.setIdLote(rs.getInt("id_lote"));
				Integer nroTransaccion = rs.getInt("nro_transac");
				Integer idProducto = rs.getInt("id_producto");
				lote.setTranLinea(ptl.obtenerTranLineaPorId(nroTransaccion, idProducto));
				Fecha venc = new Fecha(rs.getDate("venc"));
				if(!rs.wasNull()) {
					lote.setVenc(venc);
				}
				Integer idUtil = rs.getInt("id_util");
				if(!rs.wasNull()) {
					lote.setUtilidad(pu.obtenerUtilidadPorId(idUtil));
				}
				Integer nroDep = rs.getInt("nro_dep");
				if(!rs.wasNull()) {
					lote.setDeposito(pd.obtenerDepositoPorId(nroDep));
				}
				lote.setStock(rs.getInt("stock"));
			}
		} catch (ConectorException | SQLException | PersistenciaException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerLotePorTransacProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return lote;
	}
	
	@Override
	public Lote obtenerLoteVtaPorTransacProd(Integer nroTransac, Integer idProd) throws PersistenciaException {
		Lote lote = null;
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaUtilidad pu = new PersistenciaUtilidad();
		PersistenciaDeposito pd = new PersistenciaDeposito();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_LOTEVTA_XTRANSACPROD);
			genType.setParam(nroTransac);
			genType.setParam(idProd);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				lote = new Lote();
				lote.setIdLote(rs.getInt("id_lote"));
				Integer nroTransaccion = rs.getInt("nro_transac");
				Integer idProducto = rs.getInt("id_producto");
				lote.setTranLinea(ptl.obtenerTranLineaPorId(nroTransaccion, idProducto));
				Fecha venc = new Fecha(rs.getDate("venc"));
				if(!rs.wasNull()) {
					lote.setVenc(venc);
				}
				Integer idUtil = rs.getInt("id_util");
				if(!rs.wasNull()) {
					lote.setUtilidad(pu.obtenerUtilidadPorId(idUtil));
				}
				Integer nroDep = rs.getInt("nro_dep");
				if(!rs.wasNull()) {
					lote.setDeposito(pd.obtenerDepositoPorId(nroDep));
				}
				lote.setStock(rs.getInt("stock"));
			}
		} catch (ConectorException | SQLException | PersistenciaException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerLotePorTransacProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return lote;
	}
	
	@Override
	public List<Lote> obtenerListaLotePorTransac(Integer nroTransac) throws PersistenciaException {
		List<Lote> listaLote = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_LOTES_XTRANSAC);
			genType.setParamCharIfNull(nroTransac);
			rs = (ResultSet) runGeneric(genType);
			listaLote = cargarLoteDesdeRs(rs);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaLote;
	}
	
//	@Override
//	public Long obtenerStockLotePorProd(Integer idProducto, Integer diasParaVenc) throws PersistenciaException {
//		Long totalStock = new Long(0);
//		try {
//			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_STOCK);
//			genType.setParam(idProducto);
//			genType.setParam(diasParaVenc);
//			rs = (ResultSet) runGeneric(genType);
//			if(rs.next()) {
//				totalStock = rs.getLong("stock");
//			}
//		} catch (ConectorException | SQLException e) {
//			Conector.rollbackConn();
//			logger.fatal("Excepcion al obtenerStockLotePorProd: " + e.getMessage(), e);
//			throw new PersistenciaException(e);
//		} finally {
//			closeRs(rs);
//		}
//		return totalStock;
//	}
	
	@Override
	public List<Lote> obtenerListaLotePorProd(Integer idProd, Integer diasParaVenc) throws PersistenciaException {
		List<Lote> listaLote = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_LOTES_XPROD);
			genType.setParam(idProd);
			genType.setParam(diasParaVenc);
			rs = (ResultSet) runGeneric(genType);
			listaLote = cargarLoteDesdeRs(rs);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaLote;
	}

	@Override
	public Integer guardarListaLote(List<Lote> listaLote) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_LOTE);
		ArrayList<Object> paramList = null;
		for(Lote lote : listaLote) {
			paramList = new ArrayList<>();
			paramList.add(lote.getTranLinea().getTransaccion().getNroTransac());
			paramList.add(lote.getTranLinea().getProducto().getIdProducto());
			paramList.add(lote.getStock());
			genExec.setParamList(paramList);
		}
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarListaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer guardarLote(Lote lote) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer actualizarLote(Lote lote) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_LOTE);
		genExec.setParam(lote.getVenc());
		genExec.setParam(lote.getDeposito().getNroDep());
		genExec.setParam(lote.getUtilidad().getIdUtil());
		genExec.setParam(lote.getIdLote());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al actualizarLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer actualizarStockLote(Integer idLote, Integer stock) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_STOCK_LOTE);
		genExec.setParam(stock);
		genExec.setParam(idLote);
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al actualizarLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarLote(Lote lote) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***************************************************/
	/* METODOS GENERICOS */
	/***************************************************/
	
	/**
	 * metodo que recibe el resultset de la consulta, y carga la lista de lotes
	 * @param rs
	 * @throws PersistenciaException 
	 */
	private List<Lote> cargarLoteDesdeRs(ResultSet rs) throws PersistenciaException {
		List<Lote> listaLote = new ArrayList<>();
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaUtilidad pu = new PersistenciaUtilidad();
		PersistenciaDeposito pd = new PersistenciaDeposito();
		try {
			while(rs.next()) {
				Lote lote = new Lote();
				lote.setIdLote(rs.getInt("id_lote"));
				Integer nroTransaccion = rs.getInt("nro_transac");
				Integer idProducto = rs.getInt("id_producto");
				lote.setTranLinea(ptl.obtenerTranLineaPorId(nroTransaccion, idProducto));
				Fecha venc = new Fecha(rs.getDate("venc"));
				if(!rs.wasNull()) {
					lote.setVenc(venc);
				}
				Integer idUtil = rs.getInt("id_util");
				if(!rs.wasNull()) {
					lote.setUtilidad(pu.obtenerUtilidadPorId(idUtil));
				}
				Integer nroDep = rs.getInt("nro_dep");
				if(!rs.wasNull()) {
					lote.setDeposito(pd.obtenerDepositoPorId(nroDep));
				}
				lote.setStock(rs.getInt("stock"));
				listaLote.add(lote);
			}
		} catch (SQLException | PersistenciaException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al cargarRsConPf: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaLote;
	}

}
