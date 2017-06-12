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
	private IPersTransaccion interfaceTransaccion;
	private IPersTranLinea interfaceTranLinea;
	private IPersLote interfaceLote;
	
	
	public Integer generarTransaccion(Transaccion transaccion) {
		logger.info("Ingresa guardarTransaccion");
		Integer resultado = null;
		if(transaccion != null) {
			interfaceTransaccion = new PersistenciaTransaccion();
			interfaceTranLinea = new PersistenciaTranLinea();
			interfaceLote = new PersistenciaLote();
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
					resultado = interfaceTransaccion.guardarTransaccionCompra(transaccion);
				} else if(TipoTran.V.equals(transaccion.getTipoTran())) {
					resultado = interfaceTransaccion.guardarTransaccionVenta(transaccion);
				} else {
					throw new PersistenciaException("El tipo de transaccion no es compatible.");
				}
				//se persiste el estado de la transaccion con estado 'P'
				interfaceTransaccion.guardarTranEstado(transaccion);
				//se persisten las lineas de la transaccion
				interfaceTranLinea.guardarListaTranLinea(transaccion.getListaTranLinea());
				
				/*a modo de prueba*/ //throw new PersistenciaException(); /**/
				
				//se periste el lote para cada producto de las lineas
				interfaceLote.guardarListaLote(listaLote);
			} catch (ConectorException | PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Conector.closeConn("guardarTransaccion", null);
			}
		}
		return resultado;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) {
		List<Transaccion> listaTransac = null;
		interfaceTransaccion = new PersistenciaTransaccion();
		interfaceTranLinea = new PersistenciaTranLinea();
		try {
			Conector.getConn();
			listaTransac = interfaceTransaccion.obtenerListaTransaccionPorPersona(idPersona, tipoTran, estadoTran);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = interfaceTranLinea.obtenerListaTranLinea(transac.getNroTransac());
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} catch (Exception e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerListaTransaccionPorPersona", null);
		}
		return listaTransac;
	}
	
	public Integer modificarTransaccion(Transaccion transaccion) {
		return null;
	}
	
	public Integer anularTransaccion(Transaccion transaccion) {
		return null;
	}
	
}
