package gpd.dominio.persona;

public enum TipoPersona {
	F("Persona Fisica", "Cliente", 'F'), 
	J("Persona Juridica", "Empresa", 'J');
	
	private final String tipoPers;
	private final String tipoPersAux;
	private final char asChar;
	
	TipoPersona(String tipoPers, String tipoPersAux, char asChar) {
		this.tipoPers = tipoPers;
		this.tipoPersAux = tipoPersAux;
		this.asChar = asChar;
	}

	public String getTipoPers() {
		return tipoPers;
	}
	
	public String getTipoPersAux() {
		return tipoPers;
	}
	
	public char getAsChar() {
		return asChar;
	}
	
	public static TipoPersona getTipoPersonaPorChar(final char name) {
        for (TipoPersona tipoPers : TipoPersona.values()) {
            if (tipoPers.getAsChar() == name)
                return tipoPers;
    	}
        return null;
    }
	
	@Override
	 public String toString() {
		 return tipoPersAux;
	 }
}
