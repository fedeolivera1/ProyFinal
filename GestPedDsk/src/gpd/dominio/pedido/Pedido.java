package gpd.dominio.pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gpd.dominio.persona.Persona;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.types.Fecha;

public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	private Persona persona;
	private Fecha fechaHora;
	private EstadoPedido estado;
	private Fecha fechaProg;
	private Fecha horaProg;
	private Origen origen;
	private Double subTotal;
	private Double iva;
	private Double total;
	private UsuarioDsk usuario;
	private Transaccion transaccion;
	private Sinc sinc;
	private Fecha ultAct;
	private List<PedidoLinea> listaPedidoLinea;
	
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public Fecha getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
	public Fecha getFechaProg() {
		return fechaProg;
	}
	public void setFechaProg(Fecha fechaProg) {
		this.fechaProg = fechaProg;
	}
	
	public Fecha getHoraProg() {
		return horaProg;
	}
	public void setHoraProg(Fecha horaProg) {
		this.horaProg = horaProg;
	}
	
	public Origen getOrigen() {
		return origen;
	}
	public void setOrigen(Origen origen) {
		this.origen = origen;
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
	
	public UsuarioDsk getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDsk usuario) {
		this.usuario = usuario;
	}
	
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
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
	
	public List<PedidoLinea> getListaPedidoLinea() {
		if(listaPedidoLinea == null) {
			listaPedidoLinea = new ArrayList<>();
		}
		return listaPedidoLinea;
	}
	public void setListaPedidoLinea(List<PedidoLinea> listaPedidoLinea) {
		this.listaPedidoLinea = listaPedidoLinea;
	}
	@Override
	public String toString() {
		return persona + " | estado: " + estado.name() + " | orig: " + origen;
	}

}
