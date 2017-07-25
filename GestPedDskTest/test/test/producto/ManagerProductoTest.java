package test.producto;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PresentacionException;
import gpd.manager.producto.ManagerProducto;

public class ManagerProductoTest {

	ManagerProducto mgr = new ManagerProducto();
	
	
	@Test
	public void testObtenerProductoPorId() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerListaProductoPorTipoProd() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGuardarProducto() {
		fail("Not yet implemented");
	}

	@Test
	public void testModificarProducto() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminarProducto() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerTipoProdPorId() {
		TipoProd tp = null;
		try {
			tp = mgr.obtenerTipoProdPorId(1);
		} catch (PresentacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(tp != null);
	}

	@Test
	public void testObtenerListaTipoProd() {
		List<TipoProd> lista = null;
		try {
			lista = mgr.obtenerListaTipoProd();
		} catch (PresentacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(lista != null && !lista.isEmpty());
	}

	@Test
	public void testGuardarTipoProd() {
		TipoProd tp = new TipoProd();
		tp.setDescripcion("desc3");
		try {
			assertTrue(mgr.guardarTipoProd(tp)>0);
		} catch (PresentacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testModificarTipoProd() {
		TipoProd tp;
		try {
			tp = mgr.obtenerTipoProdPorId(1);
			tp.setDescripcion("desc3 modif");
			mgr.modificarTipoProd(tp);
			assertTrue(tp.getDescripcion().equals("desc3 modif"));
		} catch (PresentacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEliminarTipoProd() {
		fail("Not yet implemented");
	}

}
