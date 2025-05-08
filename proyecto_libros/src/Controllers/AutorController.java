package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Autor;
import Views.VentanaAutores;
import Views.*;

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
			if (autor.getSeudonimo() == -1) {
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
			if (autor.getSeudonimo() == -1) {
				stmt.setObject(5, null);
			}
			else {
				stmt.setObject(5, autor.getSeudonimo(), java.sql.Types.INTEGER);
			}
			
			stmt.execute();
			
			Navegador.mostrarMensajeInformacion(Navegador.obtenerVentana("Crear autor"), "Confirmado", "Se ha insertado el autor");
			((VentanaAutoresCrear) Navegador.obtenerVentana("Crear autor")).actualizarLista();
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
			stmt.setDate(2, java.sql.Date.valueOf(autorEditado.getFechaNacimiento()));
			stmt.setString(3, autorEditado.getNacionalidad());
			stmt.setString(4, autorEditado.isVivo()?"Vivo":"Fallecido");
			if (autorEditado.getSeudonimo() == -1) {
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
}
