package gpd.dominio.producto;

import java.io.Serializable;

import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;

public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idUnidad;
	private String nombre;
	private Sinc sinc;
	private Estado estado;
	
	
	public Integer getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return nombre;
	}
	
}
