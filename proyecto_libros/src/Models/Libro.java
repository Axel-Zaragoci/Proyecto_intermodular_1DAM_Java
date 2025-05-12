package Models;

import java.util.ArrayList;
import java.util.Comparator;

import Controllers.LibroController;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Libro {
	private int id;
	private String titulo;
	private int editorial;
	private ArrayList<Integer> autor = new ArrayList<>();
	private Integer paginas;
	private Integer publicacion;
	private Double precio;
	private Long isbn;
	private String idioma;
	private static ArrayList<Libro> libros = new ArrayList<>();
	
	public static Libro obtenerLibro(int id) {
		for (Libro l : libros) {
			if (l.id == id) {
				return l;
			}
		}
		return null;
	}
	
	public static ArrayList<Libro> actualizarLista() {
		libros = LibroController.ver();
		libros.sort(Comparator.comparing(Libro::getId));
		return libros;
	}
	
	public Libro(String titulo, int editorial, Integer[] autor, Integer paginas, Integer publicacion, Double precio, Long isbn, String idioma) {
		this.titulo = titulo;
		this.editorial = editorial;
		for(Integer a : autor) {
			this.autor.add(a);
		}
		this.paginas = paginas;
		this.publicacion = publicacion;
		this.precio = precio;
		this.isbn = isbn;
		this.idioma = idioma;
	}
	

	public Libro(int id, String titulo, int editorial, Integer[] autores ,Integer paginas, Integer publicacion, Double precio, Long isbn, String idioma) {
		this.id = id;
		this.titulo = titulo;
		this.editorial = editorial;
		for(Integer a : autores) {
			this.autor.add(a);
		}
		this.paginas = paginas;
		this.publicacion = publicacion;
		this.precio = precio;
		this.isbn = isbn;
		this.idioma = idioma;
	}
}


