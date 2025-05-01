package proyecto_libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
