package proyecto_libros;

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
	
	public void setSeudonimo(Integer seudonimo) {
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
	
	public Integer getSeudonimo() {
		return this.seudonimo;
	}
	
	public int getId() {
		return this.id;
	}
}
