package test.producto;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import gpd.dominio.producto.TipoProd;
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
		TipoProd tp = mgr.obtenerTipoProdPorId(1);
		assertTrue(tp != null);
	}

	@Test
	public void testObtenerListaTipoProd() {
		List<TipoProd> lista = mgr.obtenerListaTipoProd();
		assertTrue(lista != null && !lista.isEmpty());
	}

	@Test
	public void testGuardarTipoProd() {
		TipoProd tp = new TipoProd();
		tp.setDescripcion("desc3");
		assertTrue(mgr.guardarTipoProd(tp)>0);
	}

	@Test
	public void testModificarTipoProd() {
		TipoProd tp = mgr.obtenerTipoProdPorId(1);
		tp.setDescripcion("desc3 modif");
		mgr.modificarTipoProd(tp);
		assertTrue(tp.getDescripcion().equals("desc3 modif"));
	}

	@Test
	public void testEliminarTipoProd() {
		fail("Not yet implemented");
	}

}
