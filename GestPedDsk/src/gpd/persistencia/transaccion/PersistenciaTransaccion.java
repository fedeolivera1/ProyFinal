package gpd.persistencia.transaccion;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.db.generic.GenSqlExecType;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.persistencia.conector.Conector;

public class PersistenciaTransaccion extends Conector implements IPersTransaccion, CnstQryTransaccion {

	private static final Logger logger = Logger.getLogger(PersistenciaTransaccion.class);
	
	
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
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTransaccionVenta: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
