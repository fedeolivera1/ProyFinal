package gpd.ws.parsers;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.exceptions.ParsersException;
import gpd.types.Fecha;
import gpw.webservice.proxy.ParamObtPersonasNoSinc;
import gpw.webservice.proxy.ParamPersonaSinc;
import gpw.webservice.proxy.ParamRecPersonasASinc;

public class ParserParamPersona {

	private static Logger logger = Logger.getLogger(ParserParamPersona.class);
	
	public static ParamObtPersonasNoSinc parseParamObtPersNoSinc(Fecha fechaDesde, Fecha fechaHasta) throws ParsersException {
		ParamObtPersonasNoSinc param = new ParamObtPersonasNoSinc();
		try {
			if(fechaDesde != null && fechaHasta != null) {
				param.setFechaDesde(fechaDesde.getAsXMLGregorianCalendar(Fecha.AMD));
				param.setFechaHasta(fechaHasta.getAsXMLGregorianCalendar(Fecha.AMD));
			} else {
				throw new ParsersException("Los datos del tipo 'ParamObtPersonasNoSinc' son obligatorios.");
			}
		} catch (Exception e) {
			logger.error("Excepcion al parsear en parseParamObtPersNoSinc: " + e.getMessage());
			throw new ParsersException(e);
		}
		return param;
	}
	
	public static ParamRecPersonasASinc parseParamRecPersonasSinc(List<Long> listaPersona) throws ParsersException {
		ParamRecPersonasASinc param = null;
		try {
			if(listaPersona != null && !listaPersona.isEmpty()) {
				param = new ParamRecPersonasASinc();
				for(Long idPers : listaPersona) {
					ParamPersonaSinc paramPs = new ParamPersonaSinc();
					paramPs.setIdPersona(idPers);
					param.getListaPersSinc().add(paramPs);
				}
			} else {
				throw new ParsersException("Los datos del tipo 'ParamRecPersonasASinc' son obligatorios.");
			}
		} catch (Exception e) {
			logger.error("Excepcion al parsear en parseParamRecPersonasSinc: " + e.getMessage());
			throw new ParsersException(e);
		}
		return param;
	}
	
}
