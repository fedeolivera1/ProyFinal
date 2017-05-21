package gpd.dominio.persona;

public enum TipoPersona {
	C("Cliente", 'C'), 
	P("Proveedor", 'P');
	
	private final String tipoPers;
	private final char asChar;
	
	TipoPersona(String tipoPers, char asChar) {
		this.tipoPers = tipoPers;
		this.asChar = asChar;
	}

	public String getTipoPers() {
		return tipoPers;
	}
	
	public char getAsChar() {
		return asChar;
	}
}
