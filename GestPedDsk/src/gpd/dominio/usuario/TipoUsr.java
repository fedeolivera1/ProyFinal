package gpd.dominio.usuario;

public enum TipoUsr {
	A("Admin", 'A'),
	V("Vendedor", 'V');
	
	private final String tipoUsr;
	private final char asChar;
	
	//Constructor
	TipoUsr(String tipoUsr, char asChar) {
		this.tipoUsr = tipoUsr;
		this.asChar = asChar;
	}
	
	//gets

	public String getTipoUsr() {
		return tipoUsr;
	}
	
	public char getAsChar() {
		return asChar;
	}

	public static TipoUsr getTipoUsrPorChar(final char name)
    {
        for (TipoUsr tipo : TipoUsr.values()) {
            if (tipo.getAsChar() == name)
                return tipo;
    	}
        return null;
    }
	
	//Otros métodos
	
	@Override
	public String toString() {
	    return this.tipoUsr;
	}
}
