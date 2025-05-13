package Models;

import java.util.ArrayList;
import java.util.Comparator;

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
	
	public static Autor obtenerAutor(int id) {
		for (Autor a : autores) {
			if (a.id == id) {
				return a;
			}
		}
		return null;
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
	
	public static ArrayList<Autor> actualizarLista() {
		autores = AutorController.ver();
		autores.sort(Comparator.comparing(Autor::getId));
		return autores;
	}

}