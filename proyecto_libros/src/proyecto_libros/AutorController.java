package proyecto_libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutorController {	
	
	public static void crear(Autor autor) {
		String sql = "INSERT INTO autor (nombre, fecha_nacimiento, nacionalidad, estado, seudonimo) VALUES (?, ?, ?, ?, ?);";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, autor.getNombre());
			stmt.setDate(2, java.sql.Date.valueOf("1964-06-05"));
			stmt.setString(3, autor.getNacionalidad());
			stmt.setString(4, autor.isVivo()?"Vivo":"Fallecido");
			stmt.setString(5, autor.getSeudonimo());
			
			stmt.execute();
			System.out.println("Autor añadido");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
