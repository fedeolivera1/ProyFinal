package gpd.ws.parsers;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Unidad;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ParsersException;
import gpd.types.Fecha;
import gpw.webservice.proxy.ParamProductoASinc;
import gpw.webservice.proxy.ParamRecProductosASinc;
import gpw.webservice.proxy.ParamTipoProdASinc;
import gpw.webservice.proxy.ParamUnidadASinc;

public class ParserParamProducto {
	
	private static final Logger logger = Logger.getLogger(ParserParamProducto.class);
	private static HashSet<Integer> setTp = new HashSet<>();
	private static HashSet<Integer> setUni = new HashSet<>();
	
	public static ParamRecProductosASinc parseParamRecProductosASinc(List<Producto> listaProd) throws ParsersException {
		ParamRecProductosASinc param = null;
		try {
			if(listaProd != null && !listaProd.isEmpty()) {
				param = new ParamRecProductosASinc();
				for(Producto prod : listaProd) {
					logger.debug("Inicia parseo para el producto: " + prod.getIdProducto());
					TipoProd tp = prod.getTipoProd();
					Unidad uni = prod.getUnidad();
					//se controla que no esté sincronizado y que ya no se haya agregado a sinc
					if(Sinc.N.equals(tp.getSinc()) && !setTp.contains(tp.getIdTipoProd())) {
						logger.debug("Inicia parseo para el tipo prod: " + tp.getIdTipoProd());
						ParamTipoProdASinc paramTpASinc = new ParamTipoProdASinc();
						paramTpASinc.setIdTipoProd(tp.getIdTipoProd());
						paramTpASinc.setDescripcion(tp.getDescripcion());
						paramTpASinc.setEstado(tp.getEstado().getAsInt());
						paramTpASinc.setSinc(tp.getSinc().getSinc());
						param.getListaTipoProd().add(paramTpASinc);
						setTp.add(tp.getIdTipoProd());
					}
					//se controla que no esté sincronizado y que ya no se haya agregado a sinc
					if(Sinc.N.equals(uni.getSinc()) && !setUni.contains(uni.getIdUnidad())) {
						logger.debug("Inicia parseo para la unidad: " + uni.getIdUnidad());
						ParamUnidadASinc paramUniASinc = new ParamUnidadASinc();
						paramUniASinc.setIdUnidad(uni.getIdUnidad());
						paramUniASinc.setNombre(uni.getNombre());
						paramUniASinc.setEstado(uni.getEstado().getAsInt());
						paramUniASinc.setSinc(uni.getSinc().getSinc());
						param.getListaUnidad().add(paramUniASinc);
						setUni.add(uni.getIdUnidad());
					}
					ParamProductoASinc paramProdASinc = new ParamProductoASinc();
					paramProdASinc.setIdProducto(prod.getIdProducto());
					paramProdASinc.setTipoProd(tp.getIdTipoProd());
					paramProdASinc.setCodigo(prod.getCodigo());
					paramProdASinc.setNombre(prod.getNombre());
					paramProdASinc.setDescripcion(prod.getDescripcion());
					paramProdASinc.setStockMin(prod.getStockMin());
					paramProdASinc.setUnidad(uni.getIdUnidad());
					paramProdASinc.setCantUnidad(prod.getCantUnidad());
					paramProdASinc.setAplIva(prod.getAplIva().getAplIvaDesc());
					paramProdASinc.setPrecio(prod.getPrecio());
					paramProdASinc.setSinc(prod.getSinc().getSinc());
					paramProdASinc.setUltAct(prod.getUltAct().getAsXMLGregorianCalendar(Fecha.AMDHMS));
					paramProdASinc.setEstadoProd(prod.getEstadoProd().getAsInt());
					
					param.getListaProducto().add(paramProdASinc);
				}
			}
		} catch(Exception e) {
			logger.error("Error al parsear el Parametro ParamRecProductosASinc: " + e.getMessage());
			throw new ParsersException(e);
		}
		return param;
		
	}
}
