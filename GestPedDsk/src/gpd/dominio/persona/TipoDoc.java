package gpd.dominio.persona;

import java.io.Serializable;

public class TipoDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idTipoDoc;
	private String nombre;
	
	
	public Integer getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(Integer idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
