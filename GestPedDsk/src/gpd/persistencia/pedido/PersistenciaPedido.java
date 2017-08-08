package gpd.persistencia.pedido;

import java.io.IOException;
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
	private ResultSet rs;
	
	
	@Override
	public Pedido obtenerPedidoPorId(Long idPersona, Fecha fechaHora) throws PersistenciaException {
		Pedido pedido = null;
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_PEDIDO_XID);
			genType.setParam(idPersona);
			genType.setParam(fechaHora);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				pedido = new Pedido();
				pedido.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
					pedido.setUsuario(pu.obtenerUsuarioPorId(nomUsu));
				}
				Integer nroTransac = rs.getInt("nro_transac");
				if(!rs.wasNull()) {
					pedido.setTransaccion(pt.obtenerTransaccionPorId(nroTransac));
				}
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pedido.setSinc(sinc);
				pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				//obtiene lista de lineas y asigna a pedido
				List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(pedido);
				pedido.setListaPedidoLinea(listaLineas);
				//
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return pedido;
	}
	
	@Override
	public List<Pedido> obtenerListaPedido(EstadoPedido ep, Long idPersona, Origen origen, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Pedido> listaPedido = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_PEDIDO);
			genType.setParamEmptyAsNumber(idPersona);
			genType.setParamEmptyAsNumber(idPersona);
			genType.setParam(ep.getAsChar());
			genType.setParamCharIfNull(origen != null ? origen.getAsChar() : null);
			genType.setParamCharIfNull(origen != null ? origen.getAsChar() : null);
			genType.setParam(fechaDesde);
			genType.setParam(fechaHasta);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
					pedido.setUsuario(pu.obtenerUsuarioPorId(nomUsu));
				}
				Integer nroTransac = rs.getInt("nro_transac");
				if(!rs.wasNull()) {
					pedido.setTransaccion(pt.obtenerTransaccionPorId(nroTransac));
				}
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pedido.setSinc(sinc);
				pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				//obtiene lista de lineas y asigna a pedido
				List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(pedido);
				pedido.setListaPedidoLinea(listaLineas);
				//
				listaPedido.add(pedido);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaPedido;
	}
	
	/**
	 * metodo solamente para pedidos web, no levanta ni usuario ni transaccion!
	 */
	@Override
	public List<Pedido> obtenerListaPedidoNoSincWeb(EstadoPedido ep, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Pedido> listaPedido = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaPedidoLinea ppl = new PersistenciaPedidoLinea();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_PEDIDO_NS_WEB);
			genType.setParam(ep.getAsChar());
			genType.setParam(fechaDesde);
			genType.setParam(fechaHasta);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setPersona(pp.obtenerPersGenerico(rs.getLong("id_persona")));
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
				List<PedidoLinea> listaLineas = ppl.obtenerListaPedidoLinea(pedido);
				pedido.setListaPedidoLinea(listaLineas);
				//
				listaPedido.add(pedido);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaPedido;
	}

	@Override
	public Integer guardarPedido(Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PEDIDO);
//		Long idPersona = null;
//		if(pedido.getPersona() instanceof PersonaFisica) {
//			PersonaFisica pf = (PersonaFisica) pedido.getPersona();
//			idPersona = pf.getDocumento();
//		} else if(pedido.getPersona() instanceof PersonaJuridica) {
//			PersonaJuridica pj = (PersonaJuridica) pedido.getPersona();
//			idPersona = pj.getRut();
//		}
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
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarPedido(Pedido pedido) throws PersistenciaException  {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PEDIDO);
//		Long idPersona = null;
//		if(pedido.getPersona() instanceof PersonaFisica) {
//			PersonaFisica pf = (PersonaFisica) pedido.getPersona();
//			idPersona = pf.getDocumento();
//		} else if(pedido.getPersona() instanceof PersonaJuridica) {
//			PersonaJuridica pj = (PersonaJuridica) pedido.getPersona();
//			idPersona = pj.getRut();
//		}
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
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al modificarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPedido(Pedido pedido) throws PersistenciaException  {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PEDIDO);
//		Long idPersona = null;
//		if(pedido.getPersona() instanceof PersonaFisica) {
//			PersonaFisica pf = (PersonaFisica) pedido.getPersona();
//			idPersona = pf.getDocumento();
//		} else if(pedido.getPersona() instanceof PersonaJuridica) {
//			PersonaJuridica pj = (PersonaJuridica) pedido.getPersona();
//			idPersona = pj.getRut();
//		}
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al eliminarPedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Boolean checkExistPedido(Pedido pedido) throws PersistenciaException  {
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_CHK_EXIST_PEDIDO);
			genType.setParam(pedido.getPersona().getIdPersona());
			genType.setParam(pedido.getFechaHora());
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				return true;
			}
		} catch (ConectorException | SQLException e) {
			logger.error("Excepcion al checkExistePedido: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return false;
	}

	@Override
	public Integer modificarSincUltActPedido(Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_ACT_SINC_ULTACT_PEDIDO);
		genExec.setParam(pedido.getSinc().getAsChar());
		genExec.setParam(pedido.getUltAct());
		genExec.setParam(pedido.getPersona().getIdPersona());
		genExec.setParam(pedido.getFechaHora());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al desactivarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
