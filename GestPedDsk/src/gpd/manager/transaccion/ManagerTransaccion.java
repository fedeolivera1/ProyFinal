package gpd.manager.transaccion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.dominio.producto.Lote;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
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

public class ManagerTransaccion {

	private static final Logger logger = Logger.getLogger(ManagerTransaccion.class);
	private IPersTransaccion interfaceTransaccion;
	private IPersTranLinea interfaceTranLinea;
	private IPersLote interfaceLote;
	
	//FIXME revisar y probar este metodo
	public Integer generarTransaccion(Transaccion transaccion) {
		logger.info("Ingresa guardarTransaccion");
		Integer resultado = null;
		if(transaccion != null) {
			interfaceTransaccion = new PersistenciaTransaccion();
			interfaceTranLinea = new PersistenciaTranLinea();
			interfaceLote = new PersistenciaLote();
			try {
				Conector.getConn();
				Long nroTransac = Conector.obtenerSecuencia(CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				transaccion.setSubTotal(new Double(0));
				transaccion.setIva(new Float(0));
				transaccion.setTotal(new Double(0));
				if(TipoTran.C.equals(transaccion.getTipoTran())) {
					resultado = interfaceTransaccion.guardarTransaccionCompra(transaccion);
				} else if(TipoTran.V.equals(transaccion.getTipoTran())) {
					resultado = interfaceTransaccion.guardarTransaccionVenta(transaccion);
				} else {
					throw new PersistenciaException("El tipo de transaccion no es compatible.");
				}
				List<Lote> listaLote = new ArrayList<>();
				for(TranLinea tl : transaccion.getListaTranLinea()) {
					Lote lote = new Lote();
					lote.setProducto(tl.getProducto());
					lote.setStock(tl.getCantidad());
					listaLote.add(lote);
				}
				interfaceTranLinea.guardarListaTranLinea(transaccion.getListaTranLinea());
				//a modo de prueba
				//throw new PersistenciaException();
				interfaceLote.guardarListaLote(listaLote);
			} catch (ConectorException | PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			} finally {
				Conector.closeConn("guardarTransaccion", null);
			}
		}
		return resultado;
	}
}
