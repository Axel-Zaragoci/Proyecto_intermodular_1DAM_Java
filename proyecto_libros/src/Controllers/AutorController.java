package Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import Models.Autor;
import Views.VentanaAutores;
import Views.VentanaAutoresCrear;

public class AutorController {
	public static boolean crear(Autor autor) {
		try (Connection con = Database.conectar();) {
			
			String sqlBusqueda = "SELECT id FROM autor WHERE nombre = ? AND fecha_nacimiento = ? AND nacionalidad = ? AND estado = ? AND seudonimo = ?";
			PreparedStatement stmtBusqueda = con.prepareStatement(sqlBusqueda);
			stmtBusqueda.setString(1, autor.getNombre());
			java.sql.Date time = autor.getFechaNacimiento() == null ? null : Date.valueOf(autor.getFechaNacimiento());
			stmtBusqueda.setDate(2, time);
			stmtBusqueda.setString(3, autor.getNacionalidad());
			stmtBusqueda.setString(4, autor.isVivo() ? "Vivo" : "Fallecido");
			if (autor.getSeudonimo() == null) {
				stmtBusqueda.setObject(5, null);
			}
			else {
				stmtBusqueda.setObject(5, autor.getSeudonimo(), java.sql.Types.INTEGER);
			}
			
			ResultSet rsBusqueda = stmtBusqueda.executeQuery();			
			if(rsBusqueda.next()) {
				return false;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		String sql = "INSERT INTO autor (nombre, fecha_nacimiento, nacionalidad, estado, seudonimo) VALUES (?, ?, ?, ?, ?);";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, autor.getNombre());
			java.sql.Date time = autor.getFechaNacimiento() == null ? null : Date.valueOf(autor.getFechaNacimiento());
			stmt.setDate(2, time);
			stmt.setString(3, autor.getNacionalidad());
			stmt.setString(4, autor.isVivo()?"Vivo":"Fallecido");
			if (autor.getSeudonimo() == null) {
				stmt.setObject(5, null);
			}
			else {
				stmt.setObject(5, autor.getSeudonimo(), java.sql.Types.INTEGER);
			}
			
			stmt.execute();
			
			if (Navegador.obtenerVentana("Crear autor") != null) {
				((VentanaAutoresCrear) Navegador.obtenerVentana("Crear autor")).actualizarLista();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static ArrayList<Autor> ver() {
		ArrayList<Autor> lista = new ArrayList<>();
		String sql = "SELECT * FROM autor";
		
		try (Connection con = Database.conectar();
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				boolean vivo;
				if (rs.getString("estado") != null && (rs.getString("estado").equalsIgnoreCase("Vivo") || rs.getString("estado").equalsIgnoreCase("Viva"))) {
					vivo = true;
				}
				else {
					vivo = false;
				}
				Autor autor = new Autor(rs.getInt("id"), rs.getString("nombre"), rs.getString("fecha_nacimiento"), rs.getString("nacionalidad"), vivo, rs.getInt("seudonimo"));
				lista.add(autor);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static boolean actualizar(Autor autorEditado) {
		String sql = "UPDATE autor SET nombre = ?, fecha_nacimiento = ?, nacionalidad = ?, estado = ?, seudonimo = ? WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {			
			
			stmt.setString(1, autorEditado.getNombre());
			java.sql.Date time = autorEditado.getFechaNacimiento() == null ? null : Date.valueOf(autorEditado.getFechaNacimiento());
			stmt.setDate(2, time);
			stmt.setString(3, autorEditado.getNacionalidad());
			stmt.setString(4, autorEditado.isVivo()?"Vivo":"Fallecido");
			if (autorEditado.getSeudonimo() == null) {
				stmt.setObject(5, null);
			}
			else {
				stmt.setObject(5, autorEditado.getSeudonimo(), java.sql.Types.INTEGER);
			}
			stmt.setInt(6, autorEditado.getId());			
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Navegador.mostrarMensajeInformacion(Navegador.obtenerVentana("Actualizar autor"), "Completado", "Se ha actualizado el autor");
		Autor.actualizarLista();
		return true;
	}
	
	public static boolean eliminar(int id) {
		String sqlEscribir = "DELETE FROM escribir WHERE autor = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmtEscribir = con.prepareStatement(sqlEscribir)) {
			
			stmtEscribir.setInt(1, id);
			stmtEscribir.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "DELETE FROM autor WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql);) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			((VentanaAutores) Navegador.obtenerVentana("Autores")).actualizarTabla();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean exportar(Autor a) {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File (FilesController.obtenerRuta(Navegador.obtenerVentana("Autores"))), true));
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
			for (Autor a : Autor.actualizarLista()) {
				buffer.write(a.getId() + "");
				buffer.write("|");
				buffer.write(a.getNombre());
				buffer.write("|");
				buffer.write(((a.getFechaNacimiento() == null ? "" : a.getFechaNacimiento())+"").length() == 0 ? "" : a.getFechaNacimiento());
				buffer.write("|");
				buffer.write(((a.getNacionalidad() == null ? "" : a.getNacionalidad())+"").length() == 0 ? "" : a.getNacionalidad());
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
				String[] data = l.split("\\|");
				Autor temp = new Autor(Integer.parseInt(data[0]), data[1], data[2].equals("") ? null : data[2], data[3], (data[4].equals("true") ? true : false), (Integer.parseInt(data[5]) == 0 ? null : Integer.parseInt(data[5])));
				if (Database.revisarAutor(temp, Navegador.obtenerVentana("Autores"))) {
					AutorController.crear(temp);
				}
				else {
					return;
				}
			});
			return true;
		}
		catch (IOException ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Autores"), "Error", "Ha ocurrido un error al interactuar con el archivo");
			ex.printStackTrace();
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Autores"), "Error", "Uno de los datos ha sido modificado y es inválido");
			ex.printStackTrace();
		}
		return false;
	}
}
