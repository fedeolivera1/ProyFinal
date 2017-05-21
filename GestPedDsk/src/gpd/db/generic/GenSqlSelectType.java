package gpd.db.generic;

import java.util.HashMap;

public class GenSqlSelectType {

	private String statement;
	private HashMap<Integer, Object> selectDatosCond;


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
	
	
	
	
	
}
