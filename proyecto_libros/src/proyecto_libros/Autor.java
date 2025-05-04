package proyecto_libros;

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
}
