package gpd.ws.parsers;

import java.util.HashMap;
import java.util.Map;

import gpd.dominio.util.EstadoSinc;

public class HlpResultProd {

	private Map<Integer, EstadoSinc> mapTipoProd;
	private Map<Integer, EstadoSinc> mapUnidad;
	private Map<Integer, EstadoSinc> mapProd;
	
	public Map<Integer, EstadoSinc> getMapTipoProd() {
		if(mapTipoProd == null) {
			mapTipoProd = new HashMap<>();
		}
		return mapTipoProd;
	}
	public void setMapTipoProd(Map<Integer, EstadoSinc> mapTipoProd) {
		this.mapTipoProd = mapTipoProd;
	}
	public Map<Integer, EstadoSinc> getMapUnidad() {
		if(mapUnidad == null) {
			mapUnidad = new HashMap<>();
		}
		return mapUnidad;
	}
	public void setMapUnidad(Map<Integer, EstadoSinc> mapUnidad) {
		this.mapUnidad = mapUnidad;
	}
	public Map<Integer, EstadoSinc> getMapProd() {
		if(mapProd == null) {
			mapProd = new HashMap<>();
		}
		return mapProd;
	}
	public void setMapProd(Map<Integer, EstadoSinc> mapProd) {
		this.mapProd = mapProd;
	}
	
	
	
}
