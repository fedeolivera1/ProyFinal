package gpd.dominio.transaccion;

public enum EstadoTran {
	P("Pendiente", 'P'),
	C("Confirmada", 'C'),
	A("Anulada", 'A');
	
	private final String estadoTran;
	private final char asChar;
	
	EstadoTran(String estadoTran, char asChar) {
		this.estadoTran = estadoTran;
		this.asChar = asChar;
	}

	public String getEstadoTran() {
		return estadoTran;
	}
	
	public char getAsChar() {
		return asChar;
	}

	public static EstadoTran getEstadoTranPorChar(final char name) {
        for (EstadoTran estado : EstadoTran.values()) {
            if (estado.getAsChar() == name)
                return estado;
    	}
        return null;
    }
	
	 @Override
	 public String toString() {
		 return estadoTran;
	 }
}
