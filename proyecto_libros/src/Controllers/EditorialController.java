package Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

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
	
	public static boolean actualizar(Editorial editorialEditada) {
		String sql = "UPDATE editorial SET nombre = ?, pais = ?, ciudad = ?, ano_fundacion = ?, telefono = ?, email = ? WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, editorialEditada.getNombre());
			stmt.setString(2, editorialEditada.getPais());
			stmt.setString(3, editorialEditada.getCiudad());
			if (editorialEditada.getAnoFundacion() == null) {
				stmt.setObject(4, null);
			}
			else {
				stmt.setObject(4, editorialEditada.getAnoFundacion(), java.sql.Types.INTEGER);
			}
			stmt.setString(5, editorialEditada.getTelefono() == 0 ? null : editorialEditada.getTelefono()+"");
			stmt.setString(6, editorialEditada.getEmail());
			stmt.setInt(7, editorialEditada.getId());
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Navegador.mostrarMensajeInformacion(Navegador.obtenerVentana("Actualizar editorial"), "Completado", "Se ha actualizado correctamente la editorial");
		Editorial.actualizarLista();
		return true;
	}

	public static boolean eliminar(int id) {
		String sql = "DELETE FROM editorial WHERE id = ?";
		try (Connection con = Database.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql);) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean exportar(Editorial e) {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File (FilesController.obtenerRuta(Navegador.obtenerVentana("Editorial"))), true));
			buffer.write(e.getId() + "");
			buffer.write("|");
			buffer.write(e.getNombre());
			buffer.write("|");
			buffer.write(e.getPais());
			buffer.write("|");
			buffer.write(e.getCiudad());
			buffer.write("|");
			buffer.write(e.getAnoFundacion() + "");
			buffer.write("|");
			buffer.write(e.getTelefono() + "");
			buffer.write("|");
			buffer.write(e.getEmail());
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
			for (Editorial e : Editorial.actualizarLista()) {
				buffer.write(e.getId() + "");
				buffer.write("|");
				buffer.write(e.getNombre());
				buffer.write("|");
				buffer.write(e.getPais() == null ? "" : e.getPais());
				buffer.write("|");
				buffer.write(e.getCiudad() == null ? "" : e.getCiudad());
				buffer.write("|");
				buffer.write(e.getAnoFundacion() == null ? "" : e.getAnoFundacion()+"");
				buffer.write("|");
				buffer.write(e.getTelefono() == 0 ? "" : e.getTelefono()+"");
				buffer.write("|");
				buffer.write(e.getEmail() == null ? "" : e.getEmail());
				buffer.write("|");
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
		Path file = Path.of(FilesController.obtenerRuta(Navegador.obtenerVentana("Editoriales")));
		
		try (Stream<String> lineas = Files.lines(file)) {
			lineas.forEach(l -> {
				String[] data = l.split("\\|", -1);
				for(String d : data) {
					System.out.println(d);
				}
				Editorial temp = new Editorial(Integer.parseInt(data[0]), data[1], data[2], data[3], Integer.parseInt(data[4]), Long.parseLong((data[5].equals("") ? "0" : data[5])), data[6]);
				if (Database.revisarEditorial(temp, Navegador.obtenerVentana("Editoriales"))) {
					EditorialController.crear(temp);
				}
				else {
					return;
				}
			});
			return true;
		}
		catch (IOException ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Editorial"), "Error", "Ha ocurrido un error al interactuar con el archivo");
			ex.printStackTrace();
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Editorial"), "Error", "Uno de los datos ha sido modificado y es inválido");
			ex.printStackTrace();
		}
		return false;
	}
}