package proyecto_libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AutorController {
	public static void main(String[] args) {
	}
	
	public static void crear(Autor autor) {
		String sql = "INSERT INTO autor (nombre, fecha_nacimiento, nacionalidad, estado, seudonimo) VALUES (?, ?, ?, ?, ?);";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, autor.getNombre());
			stmt.setDate(2, java.sql.Date.valueOf(autor.getFechaNacimiento()));
			stmt.setString(3, autor.getNacionalidad());
			stmt.setString(4, autor.isVivo()?"Vivo":"Fallecido");
			stmt.setObject(5, autor.getSeudonimo(), java.sql.Types.INTEGER);
			
			stmt.execute();
			System.out.println("Autor añadido");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public static void actualizar(Autor autorEditado) {
		String sql = "UPDATE autor SET nombre = ?, fecha_nacimiento = ?, nacionalidad = ?, estado = ?, seudonimo = ? WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, autorEditado.getNombre());
			stmt.setDate(2, java.sql.Date.valueOf(autorEditado.getFechaNacimiento()));
			stmt.setString(3, autorEditado.getNacionalidad());
			stmt.setString(4, autorEditado.isVivo()?"Vivo":"Fallecido");
			stmt.setObject(5, autorEditado.getSeudonimo(), java.sql.Types.INTEGER);
			stmt.setInt(6, autorEditado.getId());			
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
