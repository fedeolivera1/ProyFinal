package gpd.dominio.util;

public enum Origen {
	W("Web", 'W'), 
	D("Dsk", 'D');
	
	private final String origen;
	private final char asChar;
	
	Origen(String origen, char asChar) {
		this.origen = origen;
		this.asChar = asChar;
	}

	public String getOrigen() {
		return origen;
	}
	
	public char getAsChar() {
		return asChar;
	}
	
	public static Origen getOrigenPorChar(final char name) {
        for (Origen origen : Origen.values()) {
            if (origen.getAsChar() == name) { 
            	return origen; 
            }
    	}
        return null;
    }
}
