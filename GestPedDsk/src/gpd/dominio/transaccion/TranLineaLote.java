package gpd.dominio.transaccion;

import gpd.dominio.producto.Lote;

public class TranLineaLote {
	
	private TranLinea tranLinea;
	private Lote lote;
	private Integer cantidad;

	
	public TranLineaLote(TranLinea tranLinea, Lote lote, Integer cantidad) {
		this.tranLinea = tranLinea;
		this.lote = lote;
		this.cantidad = cantidad;
	}


	public TranLinea getTranLinea() {
		return tranLinea;
	}
	public void setTranLinea(TranLinea tranLinea) {
		this.tranLinea = tranLinea;
	}

	public Lote getLote() {
		return lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	
}
