package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Editorial;

public class EditorialController {
	public static void crear(Editorial editorial) {
		String sql = "SELECT * FROM editorial";
		try (Connection con = Database.conectar();) {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery(sql);
			
			boolean exist = false;
			while (rs.next()) {
				Integer ano = editorial.getAnoFundacion() == null ? 0 : editorial.getAnoFundacion();
				if (rs.getString("nombre").equalsIgnoreCase(editorial.getNombre()) && rs.getString("pais").equalsIgnoreCase(editorial.getPais()) && rs.getLong("telefono") == editorial.getTelefono() && rs.getString("email").equalsIgnoreCase(editorial.getEmail()) && rs.getString("ciudad").equalsIgnoreCase(editorial.getCiudad()) && rs.getInt("ano_fundacion") == ano) {
					exist = true;
				}
			}
			
			if (!exist) {
				String sqlInsert = "INSERT INTO editorial (nombre, pais, telefono, email, ciudad, ano_fundacion) VALUES (?, ?, ?, ?, ?, ?);";
				PreparedStatement stmt = con.prepareStatement(sqlInsert);
				stmt.setString(1, editorial.getNombre());
				stmt.setString(2, editorial.getPais());
				stmt.setString(3, editorial.getTelefono() == 0 ? null : editorial.getTelefono()+"");
				stmt.setString(4, editorial.getEmail());
				stmt.setString(5, editorial.getCiudad());;
				if (editorial.getAnoFundacion() == null) {
					stmt.setObject(6, null);
				}
				else {
					stmt.setObject(6, editorial.getAnoFundacion(), java.sql.Types.INTEGER);
				}
				
				stmt.execute();
				Navegador.mostrarMensajeInformacion(Navegador.obtenerVentana("Crear editorial"), "Completado", "Se ha insertado la editorial correctamente");
			}
			else {
				Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear editorial"), "Error", "Ya existe una editorial con estos datos");
			}
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
				Integer anoFundacion = rs.getInt("ano_fundacion") == 0 ? null : rs.getInt("ano_fundacion");
				Editorial editorial = new Editorial(rs.getInt("id"), rs.getString("nombre"), rs.getString("pais"), rs.getString("ciudad"), anoFundacion, rs.getLong("telefono"), rs.getString("email"));
				lista.add(editorial);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static void actualizar(Editorial editorialEditada) {
		String sql = "UPDATE editorial SET nombre = ?, pais = ?, ciudad = ?, ano_fundacion = ?, telefono = ?, email = ? WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, editorialEditada.getNombre());
			stmt.setString(2, editorialEditada.getPais());
			stmt.setString(3, editorialEditada.getCiudad());
			stmt.setInt(4, editorialEditada.getAnoFundacion());
			stmt.setLong(5, editorialEditada.getTelefono());
			stmt.setString(6, editorialEditada.getEmail());
			stmt.setInt(7, editorialEditada.getId());
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void eliminar(int id) {
		String sql = "DELETE FROM editorial WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql);) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
