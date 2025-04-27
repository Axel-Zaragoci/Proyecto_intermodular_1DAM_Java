package proyecto_libros;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Navegador {
	private static ArrayList<JFrame> ventanas = new ArrayList<>();
	
	public static void argegarVentanas(JFrame v) {
		if(!ventanas.contains(v)) {
			ventanas.add(v);
		}
	}
	
	public static JFrame obtenerVentana(String titulo) {
		for (JFrame v : ventanas) {
			if (v.getTitle().equalsIgnoreCase(titulo)) {
				return v;
			}
		}
		return null;
	}
	
	public static void dispatcher(String titulo, boolean visibilidad) {
		JFrame v = obtenerVentana(titulo);
		if (v != null) {
			v.setVisible(visibilidad);
			return;
		}
		System.out.println("Ventana no encontrada");
	}
}
