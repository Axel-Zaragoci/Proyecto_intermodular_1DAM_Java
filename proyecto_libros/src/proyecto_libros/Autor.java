package proyecto_libros;

public class Autor {
	private String nombre;
	private String fechaNacimiento;
	private String nacionalidad;
	private boolean vivo;
	private String seudonimo;
	
	public Autor(String nombre, String fechaNacimiento, String nacionalidad, boolean vivo, String seudonimo) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.vivo = vivo;
		this.seudonimo = seudonimo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setFechaNacimiento(String fecha) {
		this.fechaNacimiento = fecha;
	}
	
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	
	public void setSeudonimo(String seudonimo) {
		this.seudonimo = seudonimo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	
	public String getNacionalidad() {
		return this.nacionalidad;
	}
	
	public boolean isVivo() {
		return this.vivo;
	}
	
	public String getSeudonimo() {
		return this.seudonimo;
	}
}
