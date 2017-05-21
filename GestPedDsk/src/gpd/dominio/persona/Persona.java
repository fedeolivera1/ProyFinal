package gpd.dominio.persona;

import java.io.Serializable;

public abstract class Persona implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idPersona;
	private String direccion;
	private String puerta;
	private String solar;
	private String manzana;
	private Double km;
	private String complemento;
	private String telefono;
	private String celular;
	private String email;
	private TipoPersona tipoPers;
	private Localidad localidad;
	
	
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	
	public String getSolar() {
		return solar;
	}
	public void setSolar(String solar) {
		this.solar = solar;
	}
	
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	
	public Double getKm() {
		return km;
	}
	public void setKm(Double km) {
		this.km = km;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public TipoPersona getTipoPers() {
		return tipoPers;
	}
	public void setTipoPers(TipoPersona tipoPers) {
		this.tipoPers = tipoPers;
	}
	
	public Localidad getLocalidad() {
		return localidad;
	}
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	
	
}
