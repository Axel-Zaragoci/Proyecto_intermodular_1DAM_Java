package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import Controllers.AutorController;
import Controllers.Database;
import Controllers.FilesController;
import Controllers.Navegador;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Autor {
	private int id;
	private String nombre;
	private String fechaNacimiento;
	private String nacionalidad;
	private boolean vivo;
	private Integer seudonimo;
	private static ArrayList<Autor> autores = new ArrayList<>();
	
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
	
	public static Autor obtenerAutor(int id) {
		return autores.get(id);
	}
	
	public static String obtenerVida(boolean v) {
		return v ? "Vivo" : "Fallecido";
	}
	
	public static String obtenerSeudonimo(Integer id) {
		for (Autor autor : autores) {
			if (autor.getId() == id) {
				return autor.getNombre();
			}
		}
		return null;
	}
	
	public static ArrayList<Autor> actualizarLista() {
		autores = AutorController.ver();
		autores.sort(Comparator.comparing(Autor::getId));
		return autores;
	}

	public static boolean exportar(Autor a) {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File (FilesController.obtenerRuta(Navegador.obtenerVentana("Autores")))));
			buffer.write(a.getId() + "");
			buffer.write("|");
			buffer.write(a.getNombre());
			buffer.write("|");
			buffer.write(a.getFechaNacimiento());
			buffer.write("|");
			buffer.write(a.getNacionalidad());
			buffer.write("|");
			buffer.write(a.isVivo() + "");
			buffer.write("|");
			buffer.write(a.getSeudonimo() + "");
			buffer.newLine();
			buffer.flush();
			buffer.close();
			return true;
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean exportarTodo() {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File (FilesController.obtenerRuta(Navegador.obtenerVentana("Autores")))));
			for (Autor a : autores) {
				buffer.write(a.getId() + "");
				buffer.write("|");
				buffer.write(a.getNombre());
				buffer.write("|");
				buffer.write(a.getFechaNacimiento());
				buffer.write("|");
				buffer.write(a.getNacionalidad());
				buffer.write("|");
				buffer.write(a.isVivo() + "");
				buffer.write("|");
				buffer.write(a.getSeudonimo() + "");
				buffer.newLine();
			}
			buffer.flush();
			buffer.close();
			return true;
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean importar() {
		Path file = Path.of(FilesController.obtenerRuta(Navegador.obtenerVentana("Autores")));
		
		try (Stream<String> lineas = Files.lines(file)) {
			lineas.forEach(l -> {
				String[] data = l.split("|");
				
				Autor temp = new Autor(Integer.parseInt(data[0]), data[1], data[2], data[3], (data[4].equals("true") ? true : false), Integer.parseInt(data[5]));
				if (Database.revisarAutor(temp, Navegador.obtenerVentana("Autores"))) {
					AutorController.crear(temp);
				}
			});
		}
		catch (IOException ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Autores"), "Error", "Ha ocurrido un error al interactuar con el archivo");
			ex.printStackTrace();
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Autores"), "Error", "Uno de los datos ha sido modificado y es inválido");
		}
		return true;
	}
}