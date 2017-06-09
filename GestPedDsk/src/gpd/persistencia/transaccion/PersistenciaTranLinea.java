package gpd.persistencia.transaccion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTranLinea;
import gpd.db.generic.GenSqlExecType;
import gpd.dominio.transaccion.TranLinea;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.persistencia.conector.Conector;

public class PersistenciaTranLinea extends Conector implements IPersTranLinea, CnstQryTranLinea {

	private static final Logger logger = Logger.getLogger(PersistenciaTranLinea.class);
	
	
	@Override
	public Integer guardarListaTranLinea(List<TranLinea> tranLinea) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANLINEA);
		ArrayList<Object> paramList = null;
		for(TranLinea tl : tranLinea) {
			paramList = new ArrayList<>();
			paramList.add(tl.getTransaccion().getNroTransac());
			paramList.add(tl.getProducto().getIdProducto());
			paramList.add(tl.getCantidad());
			paramList.add(tl.getPrecioUnit());
			genExec.setParamList(paramList);
		}
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
