package gpd.dominio.persona;

public enum TipoPersona {
	F("Persona Fisica", 'F'), 
	J("Persona Juridica", 'J');
	
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
