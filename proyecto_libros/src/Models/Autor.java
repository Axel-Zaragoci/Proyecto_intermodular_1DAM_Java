package Models;

import java.util.ArrayList;

import Controllers.AutorController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Autor {
	private int id;
	private String nombre;
	private String fechaNacimiento;
	private String nacionalidad;
	private boolean vivo;
	private Integer seudonimo;
	private static ArrayList<Autor> autores = new ArrayList<>();
	
	public Autor(String nombre, String fechaNacimiento, String nacionalidad, boolean vivo, Integer seudonimo) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.vivo = vivo;
		this.seudonimo = seudonimo;
	}
	
	public Autor(int id, String nombre, String fechaNacimiento, String nacionalidad, boolean vivo, Integer seudonimo) {
		this.id = id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.vivo = vivo;
		this.seudonimo = seudonimo;
	}
	
	public static String obtenerVida(boolean v) {
		return v ? "Vivo" : "Fallecido";
	}
	
	public static String obtenerSeudonimo(Integer id) {
		for (Autor autor : autores) {
			if (autor.getId() == id) {
				return autor.getNombre();
			}
		}
		return null;
	}
	
	public static void actualizarLista() {
		autores = AutorController.ver();
	}
}
