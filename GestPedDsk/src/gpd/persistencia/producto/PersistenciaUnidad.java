package gpd.persistencia.producto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUnidad;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Unidad;
import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersUnidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaTipoDoc;

public class PersistenciaUnidad extends Conector implements IPersUnidad, CnstQryUnidad {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoDoc.class);
	private ResultSet rs;
	
	
	@Override
	public Unidad obtenerUnidadPorId(Integer id) throws PersistenciaException {
		Unidad unidad = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UNI_X_ID);
			genType.setParam(id);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				unidad = new Unidad();
				unidad.setIdUnidad(rs.getInt("id_unidad"));
				unidad.setNombre(rs.getString("nombre"));
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				unidad.setSinc(sinc);
				unidad.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerUnidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return unidad;
	}
	
	@Override
	public List<Unidad> obtenerListaUnidad() throws PersistenciaException {
		List<Unidad> listaUnidad = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UNI);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Unidad unidad = new Unidad();
				unidad.setIdUnidad(rs.getInt("id_unidad"));
				unidad.setNombre(rs.getString("nombre"));
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				unidad.setSinc(sinc);
				unidad.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
				listaUnidad.add(unidad);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaUnidad;
	}

	@Override
	public Integer guardarUnidad(Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_UNI);
		genExec.setParam(unidad.getNombre());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUnidad(Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_UNI);
		genExec.setParam(unidad.getNombre());
		genExec.setParam(unidad.getIdUnidad());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarSincUnidad(Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_SINC_UNI);
		genExec.setParam(unidad.getSinc().getAsChar());
		genExec.setParam(unidad.getIdUnidad());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer eliminarUnidad(Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_UNI);
		genExec.setParam(unidad.getIdUnidad());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al eliminarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
