package gpd.dominio.producto;

import java.io.Serializable;

public class Deposito implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer nroDep;
	private String nombre;
	
	
	public Integer getNroDep() {
		return nroDep;
	}
	public void setNroDep(Integer nroDep) {
		this.nroDep = nroDep;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nroDep + " | " + nombre;
	}
	
}
