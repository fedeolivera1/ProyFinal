package gpd.dominio.producto;

import java.io.Serializable;

public class Utilidad implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idUtil;
	private String descripcion;
	private Float porc;
	
	
	public Integer getIdUtil() {
		return idUtil;
	}
	public void setIdUtil(Integer idUtil) {
		this.idUtil = idUtil;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Float getPorc() {
		return porc;
	}
	public void setPorc(Float porc) {
		this.porc = porc;
	}
	
	@Override
	public String toString() {
		return descripcion + " | " + porc + "%";
	}

}
