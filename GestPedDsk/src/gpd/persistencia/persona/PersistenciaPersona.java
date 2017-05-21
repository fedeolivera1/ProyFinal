package gpd.persistencia.persona;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPersonas;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.Cliente;
import gpd.dominio.persona.Proveedor;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.conector.Conector;

public class PersistenciaPersona extends Conector implements IPersPersona  {
	
	private static final Logger logger = Logger.getLogger(Conector.class.getName());
	private Integer resultado;
	
	@Override
	public Integer guardarCliente(Cliente cliente) {
		try {
			Conector.getConn();
			Integer documento = cliente.getDocumento();
			
			guardarPersona(documento, cliente.getDireccion(), cliente.getPuerta(), cliente.getSolar(), cliente.getManzana(),
					cliente.getKm(), cliente.getComplemento(), cliente.getTelefono(), cliente.getCelular(), cliente.getEmail(),
					cliente.getTipoPers(), cliente.getLocalidad().getIdLocalidad());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersonas.QUERY_INSERT_CLIENTE);
			genExec.getExecuteDatosCond().put(1, documento);
			genExec.getExecuteDatosCond().put(2, cliente.getTipoDoc().getIdTipoDoc());
			genExec.getExecuteDatosCond().put(3, cliente.getApellido1());
			genExec.getExecuteDatosCond().put(4, cliente.getApellido2());
			genExec.getExecuteDatosCond().put(5, cliente.getNombre1());
			genExec.getExecuteDatosCond().put(6, cliente.getNombre2());
			genExec.getExecuteDatosCond().put(7, cliente.getFechaNac());
			genExec.getExecuteDatosCond().put(8, cliente.getSexo().getAsChar());
			genExec.getExecuteDatosCond().put(9, Origen.D.getAsChar());
			genExec.getExecuteDatosCond().put(10, Sinc.N.getAsChar());
			genExec.getExecuteDatosCond().put(11, cliente.getUltAct());
			resultado = (Integer) runGeneric(genExec);
			
			Conector.closeConn(null, "guardarCliente");
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarCliente: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer guardarProveedor(Proveedor proveedor) {
		Integer secProveedor = null;
		try {
			Conector.getConn();
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryPersonas.QUERY_SELECT_SEQ_PER);
			ResultSet rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				secProveedor = rs.getInt(1);
			}
			
			guardarPersona(secProveedor, proveedor.getDireccion(), proveedor.getPuerta(), proveedor.getSolar(), proveedor.getManzana(),
					proveedor.getKm(), proveedor.getComplemento(), proveedor.getTelefono(), proveedor.getCelular(), proveedor.getEmail(),
					proveedor.getTipoPers(), proveedor.getLocalidad().getIdLocalidad());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersonas.QUERY_INSERT_PROVEEDOR);
			genExec.getExecuteDatosCond().put(1, secProveedor);
			genExec.getExecuteDatosCond().put(2, proveedor.getRazonSocial());
			genExec.getExecuteDatosCond().put(3, proveedor.getRut());
			genExec.getExecuteDatosCond().put(4, proveedor.getBps());
			genExec.getExecuteDatosCond().put(5, proveedor.getBse());
			resultado = (Integer) runGeneric(genExec);
			
			Conector.closeConn(null, "guardarProveedor");
			} catch (ConectorException | SQLException e) {
				Conector.rollbackConn();
				logger.log(Level.FATAL, "Excepcion al guardarProveedor: " + e.getMessage(), e);
			}
		return resultado;
	}
	
	private Integer guardarPersona(Integer idPersona, String direccion, String puerta, String solar, String manzana,
			Double km, String complemento, String telefono, String celular, String email, TipoPersona tipoPers, 
			Integer idLoc) throws ConectorException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersonas.QUERY_INSERT_PERSONA);
			genExec.getExecuteDatosCond().put(1, idPersona);
			genExec.getExecuteDatosCond().put(2, direccion);
			genExec.getExecuteDatosCond().put(3, puerta);
			genExec.getExecuteDatosCond().put(4, solar);
			genExec.getExecuteDatosCond().put(5, manzana);
			genExec.getExecuteDatosCond().put(6, km);
			genExec.getExecuteDatosCond().put(7, complemento);
			genExec.getExecuteDatosCond().put(8, telefono);
			genExec.getExecuteDatosCond().put(9, celular);
			genExec.getExecuteDatosCond().put(10, email);
			genExec.getExecuteDatosCond().put(11, tipoPers.getAsChar());
			genExec.getExecuteDatosCond().put(12, idLoc);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			throw e;
		}
		return resultado;
	}

}
