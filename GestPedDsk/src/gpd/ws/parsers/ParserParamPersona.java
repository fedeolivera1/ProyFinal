package gpd.ws.parsers;

import java.util.List;

import gpd.exceptions.SincronizadorException;
import gpd.types.Fecha;
import gpw.webservice.proxy.ParamObtPersonasNoSinc;
import gpw.webservice.proxy.ParamPersonaSinc;
import gpw.webservice.proxy.ParamRecPersonasSinc;

public class ParserParamPersona {

	public static ParamObtPersonasNoSinc parseParamObtPersNoSinc(Fecha fechaDesde, Fecha fechaHasta) throws SincronizadorException {
		ParamObtPersonasNoSinc param = new ParamObtPersonasNoSinc();
		if(fechaDesde != null && fechaHasta != null) {
			param.setFechaDesde(fechaDesde.getAsXMLGregorianCalendar());
			param.setFechaHasta(fechaHasta.getAsXMLGregorianCalendar());
		} else {
			throw new SincronizadorException("Los datos del tipo 'ParamObtPersonasNoSinc' son obligatorios.");
		}
		return param;
	}
	
	public static ParamRecPersonasSinc parseParamRecPersonasSinc(List<Long> listaPersona) throws SincronizadorException {
		ParamRecPersonasSinc param = null;
		if(listaPersona != null && !listaPersona.isEmpty()) {
			param = new ParamRecPersonasSinc();
			for(Long idPers : listaPersona) {
				ParamPersonaSinc paramPs = new ParamPersonaSinc();
				paramPs.setIdPersona(idPers);
				param.getListaPersSinc().add(paramPs);
			}
		} else {
			throw new SincronizadorException("Los datos del tipo 'ParamRecPersonasSinc' son obligatorios.");
		}
		return param;
	}
	
}
