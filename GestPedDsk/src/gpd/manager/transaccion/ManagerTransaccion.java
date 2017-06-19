package gpd.manager.transaccion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.dominio.producto.Lote;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.util.Converters;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.producto.IPersLote;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.persistencia.transaccion.PersistenciaTranLinea;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public class ManagerTransaccion {

	private static final Logger logger = Logger.getLogger(ManagerTransaccion.class);
	private static IPersTransaccion interfaceTransaccion;
	private static IPersTranLinea interfaceTranLinea;
	private static IPersLote interfaceLote;
	
	private static IPersTransaccion getInterfaceTransaccion() {
		if(interfaceTransaccion == null) {
			interfaceTransaccion = new PersistenciaTransaccion();
		}
		return interfaceTransaccion;
	}
	private static IPersTranLinea getInterfaceTranLinea() {
		if(interfaceTranLinea == null) {
			interfaceTranLinea = new PersistenciaTranLinea();
		}
		return interfaceTranLinea;
	}
	private static IPersLote getInterfaceLote() {
		if(interfaceLote == null) {
			interfaceLote = new PersistenciaLote();
		}
		return interfaceLote;
	}
	
	
	/*****************************************************************************************************************************************************/
	/** TRANSACCION */
	/*****************************************************************************************************************************************************/
	
	public Integer generarTransaccion(Transaccion transaccion) throws PresentacionException {
		logger.info("Ingresa guardarTransaccion");
		Integer resultado = null;
		if(transaccion != null) {
			try {
				Conector.getConn();
				ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
				Float iva = new Float(0);
				try {
					iva = Converters.convertirIva(Float.valueOf(cfgDrv.getIva()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Double subTotal = new Double(0);
				Double ivaSt = new Double(0);
				Double total = new Double(0);
				List<Lote> listaLote = new ArrayList<>();
				for(TranLinea tl : transaccion.getListaTranLinea()) {
					Lote lote = new Lote();
					lote.setTranLinea(tl);
					lote.setStock(tl.getCantidad());
					listaLote.add(lote);
					subTotal += (tl.getPrecioUnit()*tl.getCantidad());
				}
				subTotal = Converters.redondearDosDec(subTotal);
				total = subTotal * iva;
				total = Converters.redondearDosDec(total);
				ivaSt = total - subTotal;
				ivaSt = Converters.redondearDosDec(ivaSt);
				Long nroTransac = Conector.obtenerSecuencia(CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				transaccion.setEstadoTran(EstadoTran.P);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				transaccion.setSubTotal(subTotal);
				transaccion.setIva(ivaSt);
				transaccion.setTotal(total);
				if(TipoTran.C.equals(transaccion.getTipoTran())) {
					resultado = getInterfaceTransaccion().guardarTransaccionCompra(transaccion);
				} else if(TipoTran.V.equals(transaccion.getTipoTran())) {
					resultado = getInterfaceTransaccion().guardarTransaccionVenta(transaccion);
				} else {
					throw new PersistenciaException("El tipo de transaccion no es compatible.");
				}
				//se persiste el estado de la transaccion con estado 'P'
				getInterfaceTransaccion().guardarTranEstado(transaccion);
				//se persisten las lineas de la transaccion
				getInterfaceTranLinea().guardarListaTranLinea(transaccion.getListaTranLinea());
				
				//se periste el lote para cada producto de las lineas
				getInterfaceLote().guardarListaLote(listaLote);
				Conector.closeConn("guardarTransaccion", null);
			} catch (ConectorException | PersistenciaException e) {
				logger.fatal("Excepcion en ManagerTransaccion > generarTransaccion: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Transaccion obtenerTransaccionPorId(Long idTransac) throws PresentacionException {
		Transaccion transac = null;
		try {
			Conector.getConn();
			transac = getInterfaceTransaccion().obtenerTransaccionPorId(idTransac);
			if(transac != null) {
				List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
				if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
					transac.setListaTranLinea(listaTranLinea);
				}
			}
			Conector.closeConn("obtenerTransaccionPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return transac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) 
			throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try {
			Conector.getConn();
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPersona(idPersona, tipoTran, estadoTran);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
			Conector.closeConn("obtenerListaTransaccionPorPersona", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) 
			 throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try {
			Conector.getConn();
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPeriodo(tipoTran, estadoTran, fechaIni, fechaFin);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
			Conector.closeConn("obtenerListaTransaccionPorPeriodo", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public Integer modificarTransaccionCompra(Transaccion transaccion, List<Lote> listaLote) throws PresentacionException {
		if(transaccion != null) {
			//transaccion de tipo "compra"
			if(transaccion.getTipoTran().equals(TipoTran.C)) {
				try {
					Conector.getConn();
					//seteo estado a "confirmado"
					transaccion.setEstadoTran(EstadoTran.C);
					getInterfaceTransaccion().guardarTranEstado(transaccion);
					getInterfaceTransaccion().modificarEstadoTransaccion(transaccion);
					for(Lote lote : listaLote) {
						getInterfaceLote().actualizarLote(lote);
					}
					Conector.closeConn("modificarTransaccionCompra", null);
				} catch (PersistenciaException e) {
					logger.fatal("Excepcion en ManagerTransaccion > modificarTransaccionCompra: " + e.getMessage(), e);
					throw new PresentacionException(e);
				}
			}
		}
		return null;
	}
	
	public Integer anularTransaccion(Transaccion transaccion) throws PresentacionException {
		try {
			Conector.getConn();
			if(transaccion != null) {
				transaccion.setEstadoTran(EstadoTran.A);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				getInterfaceTransaccion().guardarTranEstado(transaccion);
				getInterfaceTransaccion().modificarEstadoTransaccion(transaccion);
			}
			Conector.closeConn("anularTransaccion", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > anularTransaccion: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
			return null;
	}
	
}
