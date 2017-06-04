package gpd.persistencia.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUtilidad;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Utilidad;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersUtilidad;
import gpd.persistencia.conector.Conector;

public class PersistenciaUtilidad extends Conector implements IPersUtilidad {
	
	private static final Logger logger = Logger.getLogger(PersistenciaTipoProd.class);
	

	@Override
	public Utilidad obtenerUtilidadPorId(Integer id) throws PersistenciaException {
		Utilidad utilidad = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryUtilidad.QRY_SELECT_UTIL_X_ID);
			genType.setParam(id);
			ResultSet rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				utilidad = new Utilidad();
				utilidad.setIdUtil(rs.getInt("id_util"));
				utilidad.setDescripcion(rs.getString("descripcion"));
				utilidad.setPorc(rs.getFloat("porc"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerUtilidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return utilidad;
	}

	@Override
	public List<Utilidad> obtenerListaUtilidad() throws PersistenciaException {
		List<Utilidad> listaUtilidad = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryUtilidad.QRY_SELECT_UTIL);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Utilidad utilidad = new Utilidad();
				utilidad.setIdUtil(rs.getInt("id_util"));
				utilidad.setDescripcion(rs.getString("descripcion"));
				utilidad.setPorc(rs.getFloat("porc"));
				listaUtilidad.add(utilidad);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaUtilidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaUtilidad;
	}

	@Override
	public Integer guardarUtilidad(Utilidad utilidad) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificarUtilidad(Utilidad utilidad) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminarUtilidad(Utilidad utilidad) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
