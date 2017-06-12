package gpd.persistencia.producto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryLote;
import gpd.db.generic.GenSqlExecType;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersLote;
import gpd.persistencia.conector.Conector;

public class PersistenciaLote extends Conector implements IPersLote, CnstQryLote {

	private static final Logger logger = Logger.getLogger(PersistenciaLote.class);
	
	
	@Override
	public List<Lote> obtenerLotesActivosPorProducto(Producto prod) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
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
	public Integer modificarLote(Lote lote) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminarLote(Lote lote) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
