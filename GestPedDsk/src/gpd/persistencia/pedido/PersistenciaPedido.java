package gpd.persistencia.pedido;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPedido;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
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
				pedido.setFechaProg(new Fecha(rs.getDate("fecha_prog")));
				pedido.setHoraProg(new Fecha(rs.getTime("hora_prog")));
				char[] origenChar = new char[1];
				rs.getCharacterStream("origen").read(origenChar);
				Origen origen = Origen.getOrigenPorChar(origenChar[0]);
				pedido.setOrigen(origen);
				pedido.setTotal(rs.getDouble("total"));
				String nomUsu = rs.getString("nom_usu");
				if(!rs.wasNull()) {
					pedido.setUsuario(pu.obtenerUsuarioPorId(nomUsu));
				}
				Long nroTransac = rs.getLong("nro_transac");
				if(!rs.wasNull()) {
					pedido.setTransaccion(pt.obtenerTransaccionPorId(nroTransac));
				}
				pedido.setTotal(rs.getDouble("total"));
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pedido.setSinc(sinc);
				pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return pedido;
	}
	
	@Override
	public List<Pedido> obtenerListaPedido(EstadoPedido ep, Long idPersona, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Pedido> listaPedido = new ArrayList<>();
		PersistenciaPersona pp = new PersistenciaPersona();
		PersistenciaUsuario pu = new PersistenciaUsuario();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_PEDIDO);
			genType.setParamEmptyAsNumber(idPersona);
			genType.setParamEmptyAsNumber(idPersona);
			genType.setParamCharIfNull(ep.getAsChar());
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
				pedido.setFechaProg(new Fecha(rs.getDate("fecha_prog")));
				pedido.setHoraProg(new Fecha(rs.getTime("hora_prog")));
				char[] origenChar = new char[1];
				rs.getCharacterStream("origen").read(origenChar);
				Origen origen = Origen.getOrigenPorChar(origenChar[0]);
				pedido.setOrigen(origen);
				pedido.setTotal(rs.getDouble("total"));
				String nomUsu = rs.getString("nom_usu");
				if(!rs.wasNull()) {
					pedido.setUsuario(pu.obtenerUsuarioPorId(nomUsu));
				}
				Long nroTransac = rs.getLong("nro_transac");
				if(!rs.wasNull()) {
					pedido.setTransaccion(pt.obtenerTransaccionPorId(nroTransac));
				}
				pedido.setTotal(rs.getDouble("total"));
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pedido.setSinc(sinc);
				pedido.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				//
				listaPedido.add(pedido);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaPedido;
	}

	@Override
	public Integer guardarPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminarPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}


}