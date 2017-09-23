package gpd.dominio.producto;

import java.io.Serializable;

import gpd.dominio.transaccion.TranLinea;
import gpd.types.Fecha;

public class Lote implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLote;
	private TranLinea tranLinea;
	private Fecha venc;
	private Deposito deposito;
	private Utilidad utilidad;
	private Integer stock;
	private Integer stockIni;
	
	
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	
	public TranLinea getTranLinea() {
		return tranLinea;
	}
	public void setTranLinea(TranLinea tranLinea) {
		this.tranLinea = tranLinea;
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
	
	public Integer getStockIni() {
		return stockIni;
	}
	public void setStockIni(Integer stockIni) {
		this.stockIni = stockIni;
	}
	
	@Override
	public String toString() {
		return String.valueOf(idLote);
	}
	
}
