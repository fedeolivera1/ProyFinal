package gpd.dominio.persona;

import gpd.types.Fecha;

public class PersonaFisica extends Persona {

	private Long documento;
	private TipoDoc tipoDoc;
	private String apellido1;
	private String apellido2;
	private String nombre1;
	private String nombre2;
	private Fecha fechaNac;
	private Sexo sexo;
	private Long documentoAnt;
	
	
	public Long getDocumento() {
		return documento;
	}
	public void setDocumento(Long documento) {
		this.documento = documento;
	}
	
	public TipoDoc getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(TipoDoc tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	
	public Fecha getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Fecha fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	public Long getDocumentoAnt() {
		return documentoAnt;
	}
	public void setDocumentoAnt(Long documentoAnt) {
		this.documentoAnt = documentoAnt;
	}
	
	
}
