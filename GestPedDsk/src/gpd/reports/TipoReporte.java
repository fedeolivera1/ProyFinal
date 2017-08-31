
package gpd.reports;

public enum TipoReporte {
	C("Listado de Compras"),
	V("Listado de Ventas"),
	P("Listado de Pedidos"),
	PF("Listado de Clientes"),
	PJ("Listado de Empresas"),
	PR("Inventario de Productos");
	
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
