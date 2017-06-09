package gpd.dominio.transaccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gpd.dominio.persona.Persona;
import gpd.types.Fecha;

public class Transaccion implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long nroTransac;
	private Persona persona;
	private TipoTran tipoTran;
	private Fecha fechaHora;
	private Double subTotal;
	private Float iva;
	private Double total;
	private List<TranLinea> listaTranLinea;
	
	
	public Transaccion(TipoTran tipoTran) {
		super();
		this.tipoTran = tipoTran;
	}
	
	public Long getNroTransac() {
		return nroTransac;
	}
	public void setNroTransac(Long nroTransac) {
		this.nroTransac = nroTransac;
	}
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public TipoTran getTipoTran() {
		return tipoTran;
	}
	public void setTipoTran(TipoTran tipoTran) {
		this.tipoTran = tipoTran;
	}
	
	public Fecha getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	public Float getIva() {
		return iva;
	}
	public void setIva(Float iva) {
		this.iva = iva;
	}
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public List<TranLinea> getListaTranLinea() {
		if(listaTranLinea == null) {
			listaTranLinea = new ArrayList<>();
		}
		return listaTranLinea;
	}
	public void setListaTranLinea(List<TranLinea> listaTranLinea) {
		this.listaTranLinea = listaTranLinea;
	}
	
}
