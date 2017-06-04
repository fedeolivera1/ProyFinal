package gpd.dominio.transaccion;

public enum TipoTran {
	V("Venta", 'V'),
	C("Compra", 'C');
	
	private final String tipoTran;
	private final char asChar;
	
	TipoTran(String tipoTran, char asChar) {
		this.tipoTran = tipoTran;
		this.asChar = asChar;
	}

	public String getTipoTran() {
		return tipoTran;
	}
	
	public char getAsChar() {
		return asChar;
	}

	public static TipoTran getTipoTranPorChar(final char name) {
        for (TipoTran tipo : TipoTran.values()) {
            if (tipo.getAsChar() == name)
                return tipo;
    	}
        return null;
    }
	
}
