package gpd.reports;

public enum TipoReporte {
	C("Compras"),
	V("Ventas"),
	P("Pedidos"),
	PE("Personas"),
	PR("Productos");
	
	private final String tipoRep;
	
	TipoReporte(String tipoRep) {
		this.tipoRep = tipoRep;
	}
	
	public String getTipoReporte() {
		return tipoRep;
	}
	
	@Override
	public String toString() {
		return tipoRep;
	}
}
