package proyecto_libros;

public class Editorial {
	private String nombre;
	private String pais;
	private String ciudad;
	private int anoFundacion;
	private long telefono;
	private String email;
	
	public Editorial(String nombre, String pais, String ciudad, int anoFundacion, long telefono, String email) {
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.anoFundacion = anoFundacion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public void setAnoFundacion(int fundacion) {
		this.anoFundacion = fundacion;
	}
	
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getPais() {
		return this.pais;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	
	public int getAnoFunacion() {
		return this.anoFundacion;
	}
	
	public long getTelefono() {
		return this.telefono;
	}
	
	public String getEmail() {
		return this.email;
	}
}
