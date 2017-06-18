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
	private EstadoTran estadoTran;
	private Fecha fechaHora;
	private Double subTotal;
	private Double iva;
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
	
	public EstadoTran getEstadoTran() {
		return estadoTran;
	}
	public void setEstadoTran(EstadoTran estadoTran) {
		this.estadoTran = estadoTran;
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
	
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
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
	
	
	@Override
	public String toString() {
		return "Tx: " + nroTransac + " |Tipo: " + tipoTran.getTipoTran() + " |Est: " + estadoTran.getEstadoTran()
				+ " |Fec: " + fechaHora.toString(Fecha.AMDHMS);
	}

	public String toStringLineas() {
		StringBuilder strBld = new StringBuilder();
		if(this.listaTranLinea != null && !listaTranLinea.isEmpty()) {
			for(TranLinea tl : listaTranLinea) {
				strBld.append(tl.toString()).append(" || ");
			}
			strBld.delete(strBld.length()-4, strBld.length());
		}
		return strBld.toString();
	}
	
}
