package test.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Test;

import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.exceptions.ConectorException;
import gpd.persistencia.conector.Conector;
import gpd.util.ConfigDriver;

public class ConectorTest extends Conector {

	@Test
	public void testGetConn() {
		Conector.getConn();
	}

	@Test
	public void testSelectGeneric() {
		ConfigDriver cfg = new ConfigDriver();
		cfg.logConfig();
		Connection conn = Conector.getConn();
//		HashMap<Integer, Object> hashDatos = new HashMap<>();
//		hashDatos.put(new Integer(1), 1);
//		hashDatos.put(new Integer(2), "desc");
		String statement = "select * from PRUEBA p where p.CODIGO=? and p.DESCRIPCION=?";
		GenSqlSelectType genSelect = new GenSqlSelectType(statement);
		genSelect.getSelectDatosCond().put(1, 1);
		genSelect.getSelectDatosCond().put(2, "desc");
//		GenSqlSelectType genSelect = new GenSqlSelectType(statement, hashDatos);
		ResultSet rs = null;
		try {
			rs = Conector.selectGeneric(conn, genSelect);
			while(rs.next()) {
				System.out.println("id: " + rs.getInt(1) + " descripcion: " + rs.getString(2));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConectorException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testRunGeneric1() {
		Connection conn = Conector.getConn();
//		String statement = "select * from PRUEBA p where p.CODIGO=?";
		String statement = "select * from PRUEBA p";
		GenSqlSelectType genSelect = new GenSqlSelectType(statement);
//		genSelect.getSelectDatosCond().put(1, 4);
		try {
			ResultSet rs = (ResultSet) Conector.runGeneric(conn, genSelect);
			while(rs.next()) {
				System.out.println("id: " + rs.getInt(1) + " descripcion: " + rs.getString(2) + " numero: " + rs.getFloat(3));
			}
		} catch (ConectorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRunGeneric2() {
		Connection conn = Conector.getConn();
		String statement = "insert into PRUEBA (CODIGO, DESCRIPCION, NUMERO) values (?,?,?)";
		GenSqlExecType genExec = new GenSqlExecType(statement);
		genExec.getExecuteDatosCond().put(1, 4);
		genExec.getExecuteDatosCond().put(2, "xyz");
		genExec.getExecuteDatosCond().put(3, 0);
		try {
			Integer rs = (Integer) Conector.runGeneric(conn, genExec);
			System.out.println("nro: " + rs);
			
		} catch (ConectorException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	@After
	public void testExecuteNonQuery() {
		Connection conn = Conector.getConn();
		String statement = "insert into PRUEBA (CODIGO, DESCRIPCION, NUMERO) values (?,?,?)";
//		HashMap<Integer, Object> hashDatos = new HashMap<>();
//		hashDatos.put(new Integer(1), 10);
//		hashDatos.put(new Integer(2), "desc por mini fwk");
//		hashDatos.put(new Integer(3), new Double(100.25));
		GenSqlExecType genExec = new GenSqlExecType(statement);
//		GenSqlExecType genExec = new GenSqlExecType(statement, hashDatos);
		genExec.getExecuteDatosCond().put(1, 11);
		genExec.getExecuteDatosCond().put(2, null);
		genExec.getExecuteDatosCond().put(3, null);
		Integer resultado = 0;
		try {
			resultado = Conector.executeNonQuery(conn, genExec);
		} catch (ConectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		genExec = new GenSqlExecType(statement);
		System.out.println("el resultado es: " + resultado);
		genExec.getExecuteDatosCond().put(1, 12);
		genExec.getExecuteDatosCond().put(2, "");
		genExec.getExecuteDatosCond().put(3, null);
		try {
			resultado = Conector.executeNonQuery(conn, genExec);
		} catch (ConectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Conector.commitConn(conn);
	}
	
	@Test
	@After
	public void testExecuteNonQueryLista() {
		Connection conn = Conector.getConn();
		String statement = "insert into PRUEBA (CODIGO, DESCRIPCION, NUMERO) values (?,?,?)";
		GenSqlExecType genExec = new GenSqlExecType(statement);
		HashMap<Integer, Object> hashDatos = new HashMap<>();
		hashDatos.put(new Integer(1), 13);
		hashDatos.put(new Integer(2), "abc");
		hashDatos.put(new Integer(3), null);
		genExec.getListaExecuteDatosCond().add(hashDatos);
		hashDatos = new HashMap<>();
		hashDatos.put(new Integer(1), 14);
		hashDatos.put(new Integer(2), "def");
		hashDatos.put(new Integer(3), null);
		genExec.getListaExecuteDatosCond().add(hashDatos);

		Integer resultado = 0;
		try {
			resultado = Conector.executeNonQueryList(conn, genExec);
		} catch (ConectorException e) {
			e.printStackTrace();
		}
		System.out.println("cantidad ejecutados: " + resultado);
		Conector.commitConn(conn);
	}
	

}
