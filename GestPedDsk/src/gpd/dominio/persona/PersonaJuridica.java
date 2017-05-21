package gpd.dominio.persona;

public abstract class PersonaJuridica extends Persona {

	private Integer rut;
	private String nombre;
	private String razonSocial;
	private String bps;
	private String bse;
	
	
	public Integer getRut() {
		return rut;
	}
	public void setRut(Integer rut) {
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
	
	
	
}
