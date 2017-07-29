package gpd.dominio.util;

public enum Estado {
	A("Activo", 1),
	E("Eliminado", 0);
	
	private final String estado;
	private final int asInt;
	
	Estado(String estado, int asInt) {
		this.estado = estado;
		this.asInt = asInt;
	}

	public String getEstadoProd() {
		return estado;
	}
	
	public int getAsInt() {
		return asInt;
	}
	
	public static Estado getEstadoProdPorInt(final int nro) {
        for (Estado estado : Estado.values()) {
            if (estado.getAsInt() == nro)
                return estado;
    	}
        return null;
    }
}
