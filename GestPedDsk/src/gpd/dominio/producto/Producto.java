package gpd.dominio.producto;

import java.io.Serializable;

import gpd.dominio.util.Sinc;
import gpd.types.Fecha;

public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idProducto;
	private TipoProd tipoProd;
	private String codigo;
	private String nombre;
	private String descripcion;
	private Float stockMin;
	private Double precio;
	private Sinc sinc;
	private Fecha ultAct;
	
	
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	public TipoProd getTipoProd() {
		return tipoProd;
	}
	public void setTipoProd(TipoProd tipoProd) {
		this.tipoProd = tipoProd;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Float getStockMin() {
		return stockMin;
	}
	public void setStockMin(Float stockMin) {
		this.stockMin = stockMin;
	}
	
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
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
