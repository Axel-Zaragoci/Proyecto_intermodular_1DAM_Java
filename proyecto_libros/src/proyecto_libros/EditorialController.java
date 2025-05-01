package proyecto_libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditorialController {
	public static void crear(Editorial editorial) {
		String sql = "INSERT INTO editorial"
				+ " (nombre, pais, telefono, email, ciudad, ano_fundacion) VALUES (?, ?, ?, ?, ?, ?);";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, editorial.getNombre());
			stmt.setString(2, editorial.getPais());
			stmt.setLong(3, editorial.getTelefono());
			stmt.setString(4, editorial.getEmail());
			stmt.setString(5, editorial.getCiudad());;
			stmt.setInt(6, editorial.getAnoFunacion());
			
			stmt.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Editorial> ver() {
		ArrayList<Editorial> lista = new ArrayList<>();
		String sql = "SELECT * FROM editorial";
		
		try (Connection con = Database.conectar();
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				Editorial editorial = new Editorial(rs.getInt("id"), rs.getString("nombre"), rs.getString("pais"), rs.getString("ciudad"), rs.getInt("ano_fundacion"), rs.getLong("telefono"), rs.getString("email"));
				lista.add(editorial);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

}
