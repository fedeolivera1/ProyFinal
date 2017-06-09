package gpd.db.constantes;

public interface CnstQryDepLoc {

	public static final String QRY_SELECT_DEP = "SELECT id_dep, nombre FROM departamento order by id_dep";
	
	public static final String QRY_SELECT_DEP_XID = "SELECT id_dep, nombre FROM departamento WHERE id_dep = ?";
	
	public static final String QRY_SELECT_LOC_XDEP = "SELECT id_loc, nombre, id_dep FROM localidad WHERE id_dep = ? order by id_loc";
	
	public static final String QRY_SELECT_LOC_XID = "SELECT id_loc, nombre, id_dep FROM localidad WHERE id_loc = ?";
	
}
