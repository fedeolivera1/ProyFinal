package gpd.dominio.persona;

public class PersonaJuridica extends Persona {

	private Long rut;
	private String nombre;
	private String razonSocial;
	private String bps;
	private String bse;
	private Boolean esProv;
	
	
	public Long getRut() {
		return rut;
	}
	public void setRut(Long rut) {
		this.rut = rut;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getBps() {
		return bps;
	}
	public void setBps(String bps) {
		this.bps = bps;
	}
	
	public String getBse() {
		return bse;
	}
	public void setBse(String bse) {
		this.bse = bse;
	}
	
	public Boolean getEsProv() {
		return esProv;
	}
	public void setEsProv(Boolean esProv) {
		this.esProv = esProv;
	}
	
	
	
}
