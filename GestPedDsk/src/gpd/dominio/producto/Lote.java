package gpd.dominio.producto;

import java.io.Serializable;

import gpd.types.Fecha;

public class Lote implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLote;
	private Producto producto;
	private Fecha venc;
	private Deposito deposito;
	private Utilidad utilidad;
	private Integer stock;
	
	
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Fecha getVenc() {
		return venc;
	}
	public void setVenc(Fecha venc) {
		this.venc = venc;
	}
	
	public Deposito getDeposito() {
		return deposito;
	}
	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
	
	public Utilidad getUtilidad() {
		return utilidad;
	}
	public void setUtilidad(Utilidad utilidad) {
		this.utilidad = utilidad;
	}
	
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	
}
