package proyecto_libros;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Libro {
	private int id;
	private String titulo;
	private int editorial;
	private ArrayList<Integer> autor = new ArrayList<>();
	private int paginas;
	private int publicacion;
	private double precio;
	private long isbn;
	private String idioma;
	
	public Libro(String titulo, int editorial, Integer[] autor, int paginas, int publicacion, double precio, long isbn, String idioma) {
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
	
	public Libro(int id, String titulo, int editorial, int paginas, int publicacion, double precio, long isbn, String idioma) {
		this.id = id;
		this.titulo = titulo;
		this.editorial = editorial;
		this.paginas = paginas;
		this.publicacion = publicacion;
		this.precio = precio;
		this.isbn = isbn;
		this.idioma = idioma;
	}
}


