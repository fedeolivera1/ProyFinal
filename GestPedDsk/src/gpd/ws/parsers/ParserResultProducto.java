package gpd.ws.parsers;

import org.apache.log4j.Logger;

import gpd.dominio.util.EstadoSinc;
import gpd.exceptions.ParsersException;
import gpw.webservice.proxy.ResultProductoASinc;
import gpw.webservice.proxy.ResultRecProductosASinc;
import gpw.webservice.proxy.ResultTipoProdASinc;
import gpw.webservice.proxy.ResultUnidadASinc;

public class ParserResultProducto {

	private static final Logger logger = Logger.getLogger(ParserResultProducto.class);
	
	public static HlpResultProd parseResultRecProductosASinc(ResultRecProductosASinc result) throws ParsersException {
		HlpResultProd hlprp = null;
		logger.debug("Ingresa a ParserResultProducto...");
		try {
			if(result != null && result.getErroresServ().isEmpty()) {
				hlprp = new HlpResultProd();
				if(!result.getListaTpSinc().isEmpty()) {
					for(ResultTipoProdASinc resultTpas : result.getListaTpSinc()) {
						Integer idTipoProd = resultTpas.getIdTipoProd();
						Integer estadoSinc = resultTpas.getEstadoSinc();
						logger.debug("Se retorna resultado de sinc para tipo prod: " + idTipoProd + " - resultado: " + estadoSinc);
						hlprp.getMapTipoProd().put(idTipoProd, EstadoSinc.getEstadoSincPorInt(estadoSinc));
					}
				}
				if(!result.getListaUnidadSinc().isEmpty()) {
					for(ResultUnidadASinc resultUas : result.getListaUnidadSinc()) {
						Integer idUni = resultUas.getIdUnidad();
						Integer estadoSinc = resultUas.getEstadoSinc();
						logger.debug("Se retorna resultado de sinc para unidad: " + idUni + " - resultado: " + estadoSinc);
						hlprp.getMapUnidad().put(idUni, EstadoSinc.getEstadoSincPorInt(estadoSinc));
					}
				}
				for(ResultProductoASinc resultPas : result.getListaProdSinc()) {
					Integer idProd = resultPas.getIdProducto();
					Integer estadoSinc = resultPas.getEstadoSinc();
					logger.debug("Se retorna resultado de sinc para producto: " + idProd + " - resultado: " + estadoSinc);
					hlprp.getMapProd().put(idProd, EstadoSinc.getEstadoSincPorInt(estadoSinc));
				}
			}
		} catch(Exception e) {
			logger.error("Excepcion al parsear en parseResultObtPersNoSinc: " + e.getMessage());
			throw new ParsersException(e);
		}
		return hlprp;
	}
}
