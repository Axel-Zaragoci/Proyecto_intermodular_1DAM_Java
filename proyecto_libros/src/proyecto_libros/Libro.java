package proyecto_libros;

public class Libro {
	private String titulo;
	private String editorial;
	private String autor;
	private String saga;
	private int paginas;
	private int publicacion;
	
	public Libro(String titulo, String editorial, String autor, String saga, int paginas, int publicacion) {
		this.titulo = titulo;
		this.editorial = editorial;
		this.autor = autor;
		this.saga = saga;
		this.paginas = paginas;
		this.publicacion = publicacion;
	}
}
