package gpd.db.generic;

import java.util.HashMap;

public class GenSqlSelectType {

	private String statement;
	private HashMap<Integer, Object> selectDatosCond;
	private int cnt = 0;
	protected static final Character EMPTY_CHAR = ' ';
	protected static final String EMPTY_STR = "";


	public GenSqlSelectType(String statement) {
		super();
		this.statement = statement;
	}

	public GenSqlSelectType(String statement, HashMap<Integer, Object> selectDatosCond) {
		super();
		this.statement = statement;
		this.selectDatosCond = selectDatosCond;
	}
	

	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public HashMap<Integer, Object> getSelectDatosCond() {
		if(this.selectDatosCond == null) {
			this.selectDatosCond = new HashMap<Integer, Object>();
		}
		return selectDatosCond;
	}

	public void setSelectDatosCond(HashMap<Integer, Object> selectDatosCond) {
		this.selectDatosCond = selectDatosCond;
	}
	
	public void setParam(Object obj) {
		cnt++;
		this.getSelectDatosCond().put(cnt, obj);
	}

	public void setParamStringIfNull(Object obj) {
		cnt++;
		obj = (obj == null) ? EMPTY_STR : obj;
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamIntegerIfString(Object obj) {
		cnt++;
		obj = (obj != null && obj instanceof String) ? Integer.valueOf((String) obj) : obj;
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamLongIfString(Object obj) {
		try {
			cnt++;
			obj = (obj != null && obj instanceof String) ? Long.valueOf((String) obj) : obj;
		} catch (NumberFormatException e) {
			obj = (new Long(-1));
		}
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamCharIfNull(Object obj) {
		cnt++;
		obj = (obj == null) ? EMPTY_CHAR : obj;
		this.getSelectDatosCond().put(cnt, obj);
	}

	public void setParamEmptyAsNumber(Object obj) {
		cnt++;
		if(obj == null || obj instanceof String) {
			obj = (obj == null || String.valueOf(obj).equals(EMPTY_STR)) ? -1 : obj;
		}
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamLikeLeft(Object obj) {
		cnt++;
		if(obj instanceof String) {
			String strObj = "%" + obj.toString();
			obj = strObj;
		}
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamLikeRight(Object obj) {
		cnt++;
		if(obj instanceof String) {
			String strObj = obj.toString() + "%";
			obj = strObj;
		}
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	public void setParamLikeBoth(Object obj) {
		cnt++;
		if(obj instanceof String) {
			String strObj = "%" + obj.toString() + "%";
			obj = strObj;
		}
		this.getSelectDatosCond().put(cnt, obj);
	}
	
	
}
