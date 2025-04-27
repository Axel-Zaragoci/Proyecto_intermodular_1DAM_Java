package proyecto_libros;

import java.util.ArrayList;

public class Libro {
	private String titulo;
	private ArrayList<String> editorial = new ArrayList<>();
	private ArrayList<String> autor = new ArrayList<>();
	private int paginas;
	private int publicacion;
	private double precio;
	private long isbn;
	private String idioma;
	
	public Libro(String titulo, String[] editorial, String[] autor, String saga, int paginas, int publicacion, double precio, long isbn, String idioma) {
		this.titulo = titulo;
		for(String e : editorial) {
			this.editorial.add(e);
		}
		for(String a : autor) {
			this.autor.add(a);
		}
		this.paginas = paginas;
		this.publicacion = publicacion;
		this.precio = precio;
		this.isbn = isbn;
		this.idioma = idioma;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	
	public void setPublicacion(int publicacion) {
		this.publicacion = publicacion;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public int getPaginas() {
		return this.paginas;
	}
	
	public int getPublicacion() {
		return this.publicacion;
	}
	
	public double getPrecio() {
		return this.precio;
	}
	
	public long getIsbn() {
		return this.isbn;
	}
	
	public String getIdioma() {
		return this.idioma;
	}
}
