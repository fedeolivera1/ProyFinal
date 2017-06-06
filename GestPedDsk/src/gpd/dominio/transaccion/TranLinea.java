package gpd.dominio.transaccion;

import java.io.Serializable;

import gpd.dominio.producto.Producto;

public class TranLinea implements Serializable {

	private static final long serialVersionUID = 1L;
	private Transaccion transaccion;
	private Producto producto;
	private Integer cantidad;
	private Double precioUnit;
	
	
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
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
	
	public Double getPrecioUnit() {
		return precioUnit;
	}
	public void setPrecioUnit(Double precioUnit) {
		this.precioUnit = precioUnit;
	}
	
}
