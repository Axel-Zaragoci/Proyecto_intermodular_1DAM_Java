package Models;

import java.util.ArrayList;
import java.util.Comparator;

import Controllers.EditorialController;
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
	private long telefono;
	private String email;
	private static ArrayList<Editorial> editoriales = new ArrayList<>();
	
	public static Editorial obtenerEditorial(int id) {
		for (Editorial e : editoriales) {
			if (e.id == id) {
				return e;
			}
		}
		return null;
	}
	
	public Editorial(String nombre, String pais, String ciudad, Integer anoFundacion, long telefono, String email) {
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.anoFundacion = anoFundacion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public Editorial(int id, String nombre, String pais, String ciudad, Integer anoFundacion, long telefono, String email) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.anoFundacion = anoFundacion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public static ArrayList<Editorial> actualizarLista() {
		editoriales = EditorialController.ver();
		editoriales.sort(Comparator.comparing(Editorial::getId));
		return editoriales;
	}
	
	public Integer getAnoFundacion() {
		return anoFundacion == null ? 0 : anoFundacion;
	}
}
