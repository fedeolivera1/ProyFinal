package gpd.dominio.util;

public enum Sinc {
	S("Si", 'S'), 
	N("No", 'N');
	
	private final String sinc;
	private final char asChar;
	
	Sinc(String sinc, char asChar) {
		this.sinc = sinc;
		this.asChar = asChar;
	}

	public String getSinc() {
		return sinc;
	}
	
	public char getAsChar() {
		return asChar;
	}
	
	public static Sinc getSincPorChar(final char name) {
        for (Sinc sinc : Sinc.values()) {
            if (sinc.getAsChar() == name) { 
            	return sinc; 
            }
    	}
        return null;
    }
}
