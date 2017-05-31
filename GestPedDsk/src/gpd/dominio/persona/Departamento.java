package gpd.dominio.persona;

import java.io.Serializable;

public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idDepartamento;
	private String nombreDepartamento;
	
	
	public Integer getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	
	@Override
	public String toString() {
		return nombreDepartamento;
	}
	
}
