package gpd.persistencia.pedido;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPedido;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.persistencia.usuario.PersistenciaUsuario;
import gpd.types.Fecha;

public class PersistenciaPedido extends Conector implements IPersPedido, CnstQryPedido {

	private static final Logger logger = Logger.getLogger(PersistenciaPedido.class);
	
	
	@Override
	public Pedido obtenerPedidoPorId(Connection conn, Long idPersona, Fecha fechaHora) throws PersistenciaException {
		Pedido pedido = null;
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PEDIDO_XID);
			genSel.setParam(idPersona);
			genSel.setParam(fechaHora);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					pedido = new Pedido();
					pedido.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					pedido.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					char[] estadoChar = new char[1];
					rs.getCharacterStream("estado").read(estadoChar);
					EstadoPedido estado = EstadoPedido.getEstadoPedidoPorChar(estadoChar[0]);
					pedido.setEstado(estado);
					Date fechaProg = rs.getDate("fecha_prog");
					if(!rs.wasNull()) {
						pedido.setFechaProg(new Fecha(fechaProg));
					}
					Time horaProg = rs.getTime("hora_prog");
					if(!rs.wasNull()) {
						pedido.setHoraProg(new Fecha(horaProg));
					}
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origenPed = Origen.getOrigenPorChar(origenChar[0]);
					pedido.setOrigen(origenPed);
					pedido.setSubTotal(rs.getDouble("sub_total"));
					pedido.setIva(rs.getDouble("iva"));
					pedido.setTotal(rs.getDouble("total"));
					String nomUsu = rs.getString("nom_usu");
					if(!rs.wasNull()) {
						pedido.setUsuario(pu.obtenerUsuarioPorId(conn, nomUsu));
					}
					Integer nroTransac = rs.getInt("nro_transac");
					if(!rs.wasNull()) {
						pedido.setTransaccion(pt.obtenerTransaccionPorId(conn, nroTransac));
					}
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pedido.setSinc(sinc);
					pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					//obtiene lista de lineas y asigna a pedido
					List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(conn, pedido);
					pedido.setListaPedidoLinea(listaLineas);
					//
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return pedido;
	}
	
	@Override
	public Pedido obtenerPedidoPorTransac(Connection conn, Integer transac) throws PersistenciaException {
		Pedido pedido = null;
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PEDIDO_XTRANSAC);
			genSel.setParam(transac);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					pedido = new Pedido();
					pedido.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					pedido.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					char[] estadoChar = new char[1];
					rs.getCharacterStream("estado").read(estadoChar);
					EstadoPedido estado = EstadoPedido.getEstadoPedidoPorChar(estadoChar[0]);
					pedido.setEstado(estado);
					Date fechaProg = rs.getDate("fecha_prog");
					if(!rs.wasNull()) {
						pedido.setFechaProg(new Fecha(fechaProg));
					}
					Time horaProg = rs.getTime("hora_prog");
					if(!rs.wasNull()) {
						pedido.setHoraProg(new Fecha(horaProg));
					}
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origenPed = Origen.getOrigenPorChar(origenChar[0]);
					pedido.setOrigen(origenPed);
					pedido.setSubTotal(rs.getDouble("sub_total"));
					pedido.setIva(rs.getDouble("iva"));
					pedido.setTotal(rs.getDouble("total"));
					String nomUsu = rs.getString("nom_usu");
					if(!rs.wasNull()) {
						pedido.setUsuario(pu.obtenerUsuarioPorId(conn, nomUsu));
					}
					Integer nroTransac = rs.getInt("nro_transac");
					if(!rs.wasNull()) {
						pedido.setTransaccion(pt.obtenerTransaccionPorId(conn, nroTransac));
					}
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pedido.setSinc(sinc);
					pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					//obtiene lista de lineas y asigna a pedido
					List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(conn, pedido);
					pedido.setListaPedidoLinea(listaLineas);
					//
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return pedido;
	}
	
	@Override
	public List<Pedido> obtenerListaPedido(Connection conn, EstadoPedido ep, Long idPersona, Origen origen, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Pedido> listaPedido = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PEDIDO);
			genSel.setParamEmptyAsNumber(idPersona);
			genSel.setParamEmptyAsNumber(idPersona);
			genSel.setParam(ep.getAsChar());
			genSel.setParamCharIfNull(origen != null ? origen.getAsChar() : null);
			genSel.setParamCharIfNull(origen != null ? origen.getAsChar() : null);
			genSel.setParam(fechaDesde);
			genSel.setParam(fechaHasta);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Pedido pedido = new Pedido();
					pedido.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					pedido.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					char[] estadoChar = new char[1];
					rs.getCharacterStream("estado").read(estadoChar);
					EstadoPedido estado = EstadoPedido.getEstadoPedidoPorChar(estadoChar[0]);
					pedido.setEstado(estado);
					Date fechaProg = rs.getDate("fecha_prog");
					if(!rs.wasNull()) {
						pedido.setFechaProg(new Fecha(fechaProg));
					}
					Time horaProg = rs.getTime("hora_prog");
					if(!rs.wasNull()) {
						pedido.setHoraProg(new Fecha(horaProg));
					}
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origenPed = Origen.getOrigenPorChar(origenChar[0]);
					pedido.setOrigen(origenPed);
					pedido.setSubTotal(rs.getDouble("sub_total"));
					pedido.setIva(rs.getDouble("iva"));
					pedido.setTotal(rs.getDouble("total"));
					String nomUsu = rs.getString("nom_usu");
					if(!rs.wasNull()) {
						pedido.setUsuario(pu.obtenerUsuarioPorId(conn, nomUsu));
					}
					Integer nroTransac = rs.getInt("nro_transac");
					if(!rs.wasNull()) {
						pedido.setTransaccion(pt.obtenerTransaccionPorId(conn, nroTransac));
					}
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pedido.setSinc(sinc);
					pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					//obtiene lista de lineas y asigna a pedido
					List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(conn, pedido);
					pedido.setListaPedidoLinea(listaLineas);
					//
					listaPedido.add(pedido);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPedido;
	}
	
	/**
	 * metodo solamente para pedidos web, no levanta ni usuario ni transaccion!
	 */
	@Override
	public List<Pedido> obtenerListaPedidoNoSincWeb(Connection conn, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Pedido> listaPedido = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PEDIDO_NS_WEB);
			genSel.setParam(fechaDesde);
			genSel.setParam(fechaHasta);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Pedido pedido = new Pedido();
					pedido.setPersona(pp.obtenerPersGenerico(conn, rs.getLong("id_persona")));
					pedido.setFechaHora(new Fecha(rs.getTimestamp("fecha_hora")));
					char[] estadoChar = new char[1];
					rs.getCharacterStream("estado").read(estadoChar);
					EstadoPedido estado = EstadoPedido.getEstadoPedidoPorChar(estadoChar[0]);
					pedido.setEstado(estado);
					Date fechaProg = rs.getDate("fecha_prog");
					if(!rs.wasNull()) {
						pedido.setFechaProg(new Fecha(fechaProg));
					}
					Time horaProg = rs.getTime("hora_prog");
					if(!rs.wasNull()) {
						pedido.setHoraProg(new Fecha(horaProg));
					}
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origenPed = Origen.getOrigenPorChar(origenChar[0]);
					pedido.setOrigen(origenPed);
					pedido.setSubTotal(rs.getDouble("sub_total"));
					pedido.setIva(rs.getDouble("iva"));
					pedido.setTotal(rs.getDouble("total"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pedido.setSinc(sinc);
					pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					//obtiene lista de lineas y asigna a pedido
					List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(conn, pedido);
					pedido.setListaPedidoLinea(listaLineas);
					//
					listaPedido.add(pedido);
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
		return listaPedido;
	}

	@Override
	public Integer guardarPedido(Connection conn, Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PEDIDO);
			genExec.setParam(pedido.getPersona().getIdPersona());
			genExec.setParam(pedido.getFechaHora());
			genExec.setParam(pedido.getEstado().getAsChar());
			genExec.setParam(pedido.getFechaProg());
			genExec.setParam(pedido.getHoraProg());
			genExec.setParam(pedido.getOrigen().getAsChar());
			genExec.setParam(pedido.getSubTotal());
			genExec.setParam(pedido.getIva());
			genExec.setParam(pedido.getTotal());
			if(pedido.getOrigen().equals(Origen.D)) {
				genExec.setParam(pedido.getUsuario().getNomUsu());
				genExec.setParam(pedido.getTransaccion().getNroTransac());
			} else {
				genExec.setParam(null);
				genExec.setParam(null);
			}
			genExec.setParam(pedido.getSinc().getAsChar());
			genExec.setParam(pedido.getUltAct());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarPedido(Connection conn, Pedido pedido) throws PersistenciaException  {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PEDIDO);
		genExec.setParam(pedido.getEstado().getAsChar());
		genExec.setParam(pedido.getFechaProg());
		genExec.setParam(pedido.getHoraProg());
		genExec.setParam(pedido.getSubTotal());
		genExec.setParam(pedido.getIva());
		genExec.setParam(pedido.getTotal());
		genExec.setParam(pedido.getSinc().getAsChar());
		genExec.setParam(pedido.getUltAct());
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al modificarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPedido(Connection conn, Pedido pedido) throws PersistenciaException  {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PEDIDO);
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al eliminarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Boolean checkExistPedido(Connection conn, Pedido pedido) throws PersistenciaException  {
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_CHK_EXIST_PEDIDO);
			genSel.setParam(pedido.getPersona().getIdPersona());
			genSel.setParam(pedido.getFechaHora());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					return true;
				}
			}
		} catch (ConectorException | SQLException e) {
			logger.error("Excepcion al checkExistePedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al checkExistePedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return false;
	}

	@Override
	public Integer modificarSincUltActPedido(Connection conn, Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_ACT_SINC_ULTACT_PEDIDO);
		genExec.setParam(pedido.getSinc().getAsChar());
		genExec.setParam(pedido.getUltAct());
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarSincUltActPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarSincUltActPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarEstadoPedido(Connection conn, Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_ACT_ESTADO_PEDIDO);
		genExec.setParam(pedido.getEstado().getAsChar());
		genExec.setParam(pedido.getSinc().getAsChar());
		genExec.setParam(pedido.getUltAct());
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarSincUltActPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarSincUltActPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
