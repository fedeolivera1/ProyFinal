package gpd.db.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenSqlExecType {

	private String statement;
	private HashMap<Integer, Object> executeDatosCond;
	private List<HashMap<Integer, Object>> listaExecuteDatosCond;
	
	
	public GenSqlExecType(String statement) {
		super();
		this.statement = statement;
	}
	
	public GenSqlExecType(String statement, HashMap<Integer, Object> executeDatosCond) {
		super();
		this.statement = statement;
		this.executeDatosCond = executeDatosCond;
	}
	
	public GenSqlExecType(String statement, List<HashMap<Integer, Object>> listaExecuteDatosCond) {
		super();
		this.statement = statement;
		this.listaExecuteDatosCond = listaExecuteDatosCond;
	}

	
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public HashMap<Integer, Object> getExecuteDatosCond() {
		if(this.executeDatosCond == null) {
			this.executeDatosCond = new HashMap<Integer, Object>();
		}
		return executeDatosCond;
	}
	public void setExecuteDatosCond(HashMap<Integer, Object> executeDatosCond) {
		this.executeDatosCond = executeDatosCond;
	}

	public List<HashMap<Integer, Object>> getListaExecuteDatosCond() {
		if(this.listaExecuteDatosCond == null) {
			this.listaExecuteDatosCond = new ArrayList<HashMap<Integer, Object>>();
		}
		return listaExecuteDatosCond;
	}
	public void setListaExecuteDatosCond(List<HashMap<Integer, Object>> listaExecuteDatosCond) {
		this.listaExecuteDatosCond = listaExecuteDatosCond;
	}
}
