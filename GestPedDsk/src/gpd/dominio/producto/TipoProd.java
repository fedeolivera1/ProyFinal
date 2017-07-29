package gpd.dominio.producto;

import java.io.Serializable;

import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;

public class TipoProd implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idTipoProd;
	private String descripcion;
	private Sinc sinc;
	private Estado estado;
	
	
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
	
	public Sinc getSinc() {
		return sinc;
	}
	public void setSinc(Sinc sinc) {
		this.sinc = sinc;
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return idTipoProd + " | " + descripcion;
	}
}
