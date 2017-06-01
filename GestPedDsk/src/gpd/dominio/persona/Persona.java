package gpd.dominio.persona;

import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.types.Fecha;

public abstract class Persona {

	private String direccion;
	private String puerta;
	private String solar;
	private String manzana;
	private Float km;
	private String complemento;
	private String telefono;
	private String celular;
	private String email;
	private Fecha fechaReg;
	private TipoPersona tipoPers;
	private Localidad localidad;
	private Origen origen;
	private Sinc sinc;
	private Fecha ultAct;
	
	
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
	
	public Float getKm() {
		return km;
	}
	public void setKm(Float km) {
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
	
	public Fecha getFechaReg() {
		return fechaReg;
	}
	public void setFechaReg(Fecha fechaReg) {
		this.fechaReg = fechaReg;
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
	
	public Origen getOrigen() {
		return origen;
	}
	public void setOrigen(Origen origen) {
		this.origen = origen;
	}
	
	public Sinc getSinc() {
		return sinc;
	}
	public void setSinc(Sinc sinc) {
		this.sinc = sinc;
	}
	
	public Fecha getUltAct() {
		return ultAct;
	}
	public void setUltAct(Fecha ultAct) {
		this.ultAct = ultAct;
	}
	
}
