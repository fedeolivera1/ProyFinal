package gpd.dominio.pedido;

import java.io.Serializable;

import gpd.dominio.producto.Producto;
import gpd.dominio.util.Sinc;
import gpd.types.Fecha;

public class PedidoLinea implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private Producto producto;
	private Integer cantidad;
	private Sinc sinc;
	private Fecha ultAct;
	
	
	public PedidoLinea(Pedido pedido) {
		super();
		this.pedido = pedido;
	}
	
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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
