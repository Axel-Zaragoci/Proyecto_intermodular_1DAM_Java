package proyecto_libros;

import java.util.ArrayList;

public class Libro {
	private String titulo;
	private ArrayList<String> editorial = new ArrayList<>();
	private ArrayList<String> autor = new ArrayList<>();
	private String saga;
	private int paginas;
	private int publicacion;
	
	public Libro(String titulo, String[] editorial, String[] autor, String saga, int paginas, int publicacion) {
		this.titulo = titulo;
		for(String e : editorial) {
			this.editorial.add(e);
		}
		for(String a : autor) {
			this.autor.add(a);
		}
		this.saga = saga;
		this.paginas = paginas;
		this.publicacion = publicacion;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
