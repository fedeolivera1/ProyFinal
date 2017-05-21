package gpd.dominio.producto;

import java.io.Serializable;

public class TipoProd implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idTipoProd;
	private String descripcion;
	
	
	public Integer getIdTipoProd() {
		return idTipoProd;
	}
	public void setIdTipoProd(Integer idTipoProd) {
		this.idTipoProd = idTipoProd;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
