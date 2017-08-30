package gpd.dominio.producto;

import java.io.Serializable;

import gpd.dominio.util.Estado;
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
	private Unidad unidad;
	private Float cantUnidad;
	private AplicaIva aplIva;
	private Double precio;
	private Sinc sinc;
	private Fecha ultAct;
	private Estado estadoProd;
	
	
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
	
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	
	public Float getCantUnidad() {
		return cantUnidad;
	}
	public void setCantUnidad(Float cantUnidad) {
		this.cantUnidad = cantUnidad;
	}
	
	public AplicaIva getAplIva() {
		return aplIva;
	}
	public void setAplIva(AplicaIva aplIva) {
		this.aplIva = aplIva;
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
	
	public Estado getEstadoProd() {
		return estadoProd;
	}
	public void setEstadoProd(Estado estadoProd) {
		this.estadoProd = estadoProd;
	}
	
	@Override
	public String toString() {
		return codigo + " | " + nombre + " | iva: " + aplIva.getAplIvaDesc();
	}
	
}
