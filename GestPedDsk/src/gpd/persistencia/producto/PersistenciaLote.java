package gpd.persistencia.producto;

import java.sql.Connection;
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

	
	@Override
	public Lote obtenerLotePorId(Connection conn, Integer idLote) throws PersistenciaException {
		Lote lote = null;
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaUtilidad pu = new PersistenciaUtilidad();
		PersistenciaDeposito pd = new PersistenciaDeposito();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_LOTE_XID);
			genSel.setParam(idLote);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					lote = new Lote();
					lote.setIdLote(rs.getInt("id_lote"));
					Integer nroTransaccion = rs.getInt("nro_transac");
					Integer idProducto = rs.getInt("id_producto");
					lote.setTranLinea(ptl.obtenerTranLineaPorId(conn, nroTransaccion, idProducto));
					Fecha venc = new Fecha(rs.getDate("venc"));
					if(!rs.wasNull()) {
						lote.setVenc(venc);
					}
					Integer idUtil = rs.getInt("id_util");
					if(!rs.wasNull()) {
						lote.setUtilidad(pu.obtenerUtilidadPorId(conn, idUtil));
					}
					Integer nroDep = rs.getInt("nro_dep");
					if(!rs.wasNull()) {
						lote.setDeposito(pd.obtenerDepositoPorId(conn, nroDep));
					}
					lote.setStock(rs.getInt("stock"));
				}
			}
		} catch (ConectorException | SQLException | PersistenciaException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerLotePorTransacProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return lote;
	}
	
	@Override
	public Lote obtenerLoteVtaPorTransacProd(Connection conn, Integer nroTransac, Integer idProd) throws PersistenciaException {
		Lote lote = null;
		PersistenciaTranLinea ptl = new PersistenciaTranLinea();
		PersistenciaUtilidad pu = new PersistenciaUtilidad();
		PersistenciaDeposito pd = new PersistenciaDeposito();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_LOTEVTA_XTRANSACPROD);
			genSel.setParam(nroTransac);
			genSel.setParam(idProd);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					lote = new Lote();
					lote.setIdLote(rs.getInt("id_lote"));
					Integer nroTransaccion = rs.getInt("nro_transac");
					Integer idProducto = rs.getInt("id_producto");
					lote.setTranLinea(ptl.obtenerTranLineaPorId(conn, nroTransaccion, idProducto));
					Fecha venc = new Fecha(rs.getDate("venc"));
					if(!rs.wasNull()) {
						lote.setVenc(venc);
					}
					Integer idUtil = rs.getInt("id_util");
					if(!rs.wasNull()) {
						lote.setUtilidad(pu.obtenerUtilidadPorId(conn, idUtil));
					}
					Integer nroDep = rs.getInt("nro_dep");
					if(!rs.wasNull()) {
						lote.setDeposito(pd.obtenerDepositoPorId(conn, nroDep));
					}
					lote.setStock(rs.getInt("stock"));
				}
			}
		} catch (ConectorException | SQLException | PersistenciaException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerLotePorTransacProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return lote;
	}
	
	@Override
	public List<Lote> obtenerListaLotePorTransac(Connection conn, Integer nroTransac) throws PersistenciaException {
		List<Lote> listaLote = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_LOTES_XTRANSAC);
			genSel.setParamCharIfNull(nroTransac);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaLote = cargarLoteDesdeRs(conn, rs);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
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
//			Conector.rollbackConn(conn);
//			logger.fatal("Excepcion al obtenerStockLotePorProd: " + e.getMessage(), e);
//			throw new PersistenciaException(e);
//		} finally {
//			closeRs(rs);
//		}
//		return totalStock;
//	}
	
	@Override
	public List<Lote> obtenerListaLotePorProd(Connection conn, Integer idProd, Integer diasParaVenc) throws PersistenciaException {
		List<Lote> listaLote = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_LOTES_XPROD);
			genSel.setParam(idProd);
			genSel.setParam(diasParaVenc);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaLote = cargarLoteDesdeRs(conn, rs);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaLote;
	}

	@Override
	public Integer guardarListaLote(Connection conn, List<Lote> listaLote) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_LOTE);
			ArrayList<Object> paramList = null;
			for(Lote lote : listaLote) {
				paramList = new ArrayList<>();
				paramList.add(lote.getTranLinea().getTransaccion().getNroTransac());
				paramList.add(lote.getTranLinea().getProducto().getIdProducto());
				paramList.add(lote.getStock());
				paramList.add(lote.getStockIni());
				genExec.setParamList(paramList);
			}
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarListaLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

//	@Override
//	public Integer guardarLote(Connection conn, Lote lote) throws PersistenciaException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Integer actualizarLote(Connection conn, Lote lote) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_LOTE);
		genExec.setParam(lote.getVenc());
		genExec.setParam(lote.getDeposito().getNroDep());
		genExec.setParam(lote.getUtilidad().getIdUtil());
		genExec.setParam(lote.getIdLote());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al actualizarLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer actualizarStockLote(Connection conn, Integer idLote, Integer stock) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_STOCK_LOTE);
			genExec.setParam(stock);
			genExec.setParam(idLote);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al actualizarLote: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	
	/***************************************************/
	/* METODOS GENERICOS */
	/***************************************************/
	
	/**
	 * metodo que recibe el resultset de la consulta, y carga la lista de lotes
	 * @param rs
	 * @throws PersistenciaException 
	 */
	private List<Lote> cargarLoteDesdeRs(Connection conn, ResultSet rs) throws PersistenciaException {
		List<Lote> listaLote = new ArrayList<>();
		try {
			PersistenciaTranLinea ptl = new PersistenciaTranLinea();
			PersistenciaUtilidad pu = new PersistenciaUtilidad();
			PersistenciaDeposito pd = new PersistenciaDeposito();
			while(rs.next()) {
				Lote lote = new Lote();
				lote.setIdLote(rs.getInt("id_lote"));
				Integer nroTransaccion = rs.getInt("nro_transac");
				Integer idProducto = rs.getInt("id_producto");
				lote.setTranLinea(ptl.obtenerTranLineaPorId(conn, nroTransaccion, idProducto));
				Fecha venc = new Fecha(rs.getDate("venc"));
				if(!rs.wasNull()) {
					lote.setVenc(venc);
				}
				Integer idUtil = rs.getInt("id_util");
				if(!rs.wasNull()) {
					lote.setUtilidad(pu.obtenerUtilidadPorId(conn, idUtil));
				}
				Integer nroDep = rs.getInt("nro_dep");
				if(!rs.wasNull()) {
					lote.setDeposito(pd.obtenerDepositoPorId(conn, nroDep));
				}
				lote.setStock(rs.getInt("stock"));
				listaLote.add(lote);
			}
		} catch (SQLException | PersistenciaException e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion al cargarLoteDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion GENERICA al cargarLoteDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaLote;
	}
	
	@Override
	public List<Lote> obtenerListaLoteProxVenc(Connection conn, Integer diasTol) throws PersistenciaException {
		List<Lote> listaLote = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_LOTES_PROX_VENC);
			genSel.setParam(diasTol);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaLote = cargarLoteDesdeRs(conn, rs);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarDeposito: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaLote;
	}

}
