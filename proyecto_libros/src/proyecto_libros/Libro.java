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
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public void setSaga(String saga) {
		this.saga = saga;
	}
	
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	
	public void setPublicacion(int publicacion) {
		this.publicacion = publicacion;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public String getEditorial() {
		return this.editorial;
	}
	
	public String getAutor() {
		return this.autor;
	}
	
	public String getSaga() {
		return this.saga;
	}
	
	public int getPaginas() {
		return this.paginas;
	}
	
	public int getPublicacion() {
		return this.publicacion;
	}
}
