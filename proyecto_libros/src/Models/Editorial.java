package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Editorial {
	private int id;
	private String nombre;
	private String pais;
	private String ciudad;
	private Integer anoFundacion;
	private Integer telefono;
	private String email;
	
	public Editorial(String nombre, String pais, String ciudad, Integer anoFundacion, Integer telefono, String email) {
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.anoFundacion = anoFundacion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public Editorial(int id, String nombre, String pais, String ciudad, Integer anoFundacion, Integer telefono, String email) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.anoFundacion = anoFundacion;
		this.telefono = telefono;
		this.email = email;
	}
}
