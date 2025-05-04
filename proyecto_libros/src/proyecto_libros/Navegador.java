package proyecto_libros;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Navegador {
	private static ArrayList<JFrame> ventanas = new ArrayList<>();
	
	public static void agregarVentanas(JFrame v) {
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
	
	public static void mostrarMensajeError(JFrame v,String titulo, String mensaje) {
		JOptionPane.showMessageDialog(v, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}
	
	public static int mostrarMensajePregunta(JFrame v, String titulo, String mensaje) {
		return JOptionPane.showConfirmDialog(v, mensaje, titulo, JOptionPane.YES_NO_OPTION);
	}
	
	public static void mostrarMensajeInformacion(JFrame v, String titulo, String mensaje) {
		JOptionPane.showMessageDialog(v, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
}
