package gpd.db.generic;

import java.util.HashMap;

public class GenSqlSelectType {

	private String statement;
	private HashMap<Integer, Object> selectDatosCond;
	private int cnt = 0;


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

	public void setParamEmptyAsNull(Object obj) {
		cnt++;
		if(obj instanceof String) {
			obj = String.valueOf(obj).equals("") ? null : obj;
		}
		this.getSelectDatosCond().put(cnt, obj);
	}

	public void setParamEmptyAsNumber(Object obj) {
		cnt++;
		if(obj == null || obj instanceof String) {
			obj = (obj == null || String.valueOf(obj).equals("")) ? -1 : obj;
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
