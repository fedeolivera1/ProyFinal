package gpd.dominio.producto;

public enum EstadoProd {
	A("Activo", 1),
	E("Eliminado", 0);
	
	private final String estadoProd;
	private final int asInt;
	
	EstadoProd(String estadoProd, int asInt) {
		this.estadoProd = estadoProd;
		this.asInt = asInt;
	}

	public String getEstadoProd() {
		return estadoProd;
	}
	
	public int getAsInt() {
		return asInt;
	}
	
	public static EstadoProd getEstadoProdPorInt(final int nro) {
        for (EstadoProd estProd : EstadoProd.values()) {
            if (estProd.getAsInt() == nro)
                return estProd;
    	}
        return null;
    }
}
