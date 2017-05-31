package gpd.presentacion.generic;

import java.awt.Component;
import java.util.HashMap;

public class GenCompType {

	private HashMap<Integer, Component> hsComp;
	private int cnt;
	

	public HashMap<Integer, Component> getHsComp() {
		if(this.hsComp == null) {
			this.hsComp = new HashMap<>();
		}
		return hsComp;
	}

	public void setComp(Component comp) {
		cnt++;
		this.getHsComp().put(cnt, comp);
	}
}
