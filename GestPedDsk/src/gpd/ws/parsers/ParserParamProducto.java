package gpd.ws.parsers;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.helper.HlpProducto;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Unidad;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ParsersException;
import gpd.manager.producto.ManagerProducto;
import gpd.types.Fecha;
import gpw.webservice.proxy.ParamProductoASinc;
import gpw.webservice.proxy.ParamRecProductosASinc;
import gpw.webservice.proxy.ParamTipoProdASinc;
import gpw.webservice.proxy.ParamUnidadASinc;

public class ParserParamProducto {
	
	private static final Logger logger = Logger.getLogger(ParserParamProducto.class);
	
	public static ParamRecProductosASinc parseParamRecProductosASinc(Connection conn, List<Producto> listaProd) throws ParsersException {
		ParamRecProductosASinc param = null;
		try {
			if(listaProd != null && !listaProd.isEmpty()) {
				HashSet<Integer> setTp = new HashSet<>();
				HashSet<Integer> setUni = new HashSet<>();
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
					paramProdASinc.setUnidad(uni.getIdUnidad());
					paramProdASinc.setCantUnidad(prod.getCantUnidad());
					paramProdASinc.setAplIva(prod.getAplIva().getAplIvaDesc());
					/*
					 * obtengo el precio actual de venta para el producto, ya que al web
					 * le paso directamente el precio de venta.
					 */
					ManagerProducto mgrProd = new ManagerProducto();
					HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProductoNoConn(conn, prod.getIdProducto());
					paramProdASinc.setPrecioVta(hlpProd != null ? hlpProd.getPrecioVta() : new Double(0));
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
