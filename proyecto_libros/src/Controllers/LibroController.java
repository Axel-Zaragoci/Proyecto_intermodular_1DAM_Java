package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Libro;

public class LibroController {
	
	public static void crearLibro(Libro libro) throws SQLException {
		Connection con = Database.conectar();
		try {
			con.setAutoCommit(false);
			
			String sqlBusqueda = "SELECT id FROM libro WHERE titulo = ? AND ano_publicacion = ? AND paginas = ?";
			PreparedStatement stmtBusqueda = con.prepareStatement(sqlBusqueda);
			stmtBusqueda.setString(1, libro.getTitulo());
			stmtBusqueda.setInt(2, libro.getPublicacion());
			stmtBusqueda.setInt(3, libro.getPaginas());
			
			ResultSet rsBusqueda = stmtBusqueda.executeQuery();
			int libroID;
			
			if(rsBusqueda.next()) {
				libroID = rsBusqueda.getInt("id");
			}
			else {
				String sqlInsertLibro = "INSERT INTO libro (titulo, paginas, ano_publicacion) VALUES (?, ?, ?) RETURNING ID";
				PreparedStatement stmtInsertLibro = con.prepareStatement(sqlInsertLibro);
				stmtInsertLibro.setString(1, libro.getTitulo());
				stmtInsertLibro.setInt(2, libro.getPaginas());
				stmtInsertLibro.setInt(3, libro.getPublicacion());
				
				ResultSet rsInsertLibro = stmtInsertLibro.executeQuery();
				
				if (rsInsertLibro.next()) {
					libroID = rsInsertLibro.getInt("id");
				}
				else {
					throw new SQLException("No se pudo insertar el libro");
				}
			}
			
			String sqlInsertEditar = "INSERT INTO editar (libro, editorial, precio, isbn, idioma) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmtInsertEditar = con.prepareStatement(sqlInsertEditar);
	        stmtInsertEditar.setInt(1, libroID);
	        stmtInsertEditar.setInt(2, libro.getEditorial());
	        stmtInsertEditar.setDouble(3, libro.getPrecio());
	        stmtInsertEditar.setLong(4, libro.getIsbn());
	        stmtInsertEditar.setString(5, libro.getIdioma());
	        stmtInsertEditar.executeUpdate();
	        
	        String sqlDeleteEscribir = "DELETE FROM escribir WHERE libro = ?";
	        PreparedStatement stmtDeleteEscribir = con.prepareStatement(sqlDeleteEscribir);
	        stmtDeleteEscribir.setInt(1, libroID);
	        stmtDeleteEscribir.executeUpdate();
	        
	        String sqlInsertsEscribir = "INSERT INTO escribir (libro, autor) VALUES (?, ?)";
	        PreparedStatement stmtInsertEscribir = con.prepareStatement(sqlInsertsEscribir);
	        for (int autor : libro.getAutor()) {
	        	stmtInsertEscribir.setInt(1, libroID);
	        	stmtInsertEscribir.setInt(2, autor);
	        	stmtInsertEscribir.addBatch();
	        }
	        stmtInsertEscribir.executeBatch();
	        
	        con.commit();
		}
		catch (SQLException ex) {
			con.rollback();
			ex.printStackTrace();
		}
		con.setAutoCommit(true);
		con.close();
	}
	
	public static ArrayList<Libro> ver() {
		ArrayList<Libro> libros = new ArrayList<>();
		try {
			Connection con = Database.conectar();
			String sqlLibro = "SELECT l.id, l.titulo, e.editorial, l.paginas, l.ano_publicacion, e.precio, e.isbn, e.idioma FROM libro l, editar e WHERE l.id = e.libro";
			PreparedStatement stmtLibro = con.prepareStatement(sqlLibro);
			ResultSet rs = stmtLibro.executeQuery();
			
			while (rs.next()) {
				int libroID = rs.getInt("id");
				
				String sqlAutor = "SELECT autor FROM escribir WHERE libro = ?";
				PreparedStatement stmtAutor = con.prepareStatement(sqlAutor);
				stmtAutor.setInt(1, libroID);
				ResultSet rsAutores = stmtAutor.executeQuery();
				
				ArrayList<Integer> autores = new ArrayList<>();
				while(rsAutores.next()) {
					autores.add(rsAutores.getInt("autor"));
				}
				
				Libro libro = new Libro(libroID, rs.getString("titulo"), rs.getInt("editorial"), autores.toArray(new Integer[0]),rs.getInt("paginas"), rs.getInt("ano_publicacion"), rs.getDouble("precio"), rs.getLong("isbn"), rs.getString("idioma"));
				libro.setAutor(autores);
				libros.add(libro);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return libros;
	}
	
	public static void actualizarLibro(Libro libro) throws SQLException {
	    Connection con = Database.conectar();
	    try {
	        con.setAutoCommit(false);

	        String sqlLibro = "UPDATE libro SET titulo = ?, paginas = ? WHERE id = ?";
	        PreparedStatement stmtLibro = con.prepareStatement(sqlLibro);
	        stmtLibro.setString(1, libro.getTitulo());
	        stmtLibro.setInt(2, libro.getPaginas());
	        stmtLibro.setInt(3, libro.getId());
	        stmtLibro.executeUpdate();
	        
	        String sqlEditar = "UPDATE editar SET editorial = ?, precio = ?, isbn = ?, idioma = ? WHERE libro = ? ";
	        PreparedStatement stmtEditar = con.prepareStatement(sqlEditar);
	        stmtEditar.setInt(1, libro.getEditorial());
	        stmtEditar.setDouble(2, libro.getPrecio());
	        stmtEditar.setLong(3, libro.getIsbn());
	        stmtEditar.setString(4, libro.getIdioma());
	        stmtEditar.setInt(5, libro.getId());
	        stmtEditar.executeUpdate();

	        PreparedStatement stmtDeleteAutores = con.prepareStatement("DELETE FROM escribir WHERE libro = ?");
	        stmtDeleteAutores.setInt(1, libro.getId());
	        stmtDeleteAutores.executeUpdate();

	        String sqlEscribir = "INSERT INTO escribir (libro, autor) VALUES (?, ?)";
	        PreparedStatement stmtEscribir = con.prepareStatement(sqlEscribir);
	        for (int autorId : libro.getAutor()) {
	            stmtEscribir.setInt(1, libro.getId());
	            stmtEscribir.setInt(2, autorId);
	            stmtEscribir.addBatch();
	        }
	        stmtEscribir.executeBatch();

	        con.commit();
	    } catch (SQLException e) {
	        con.rollback();
	        throw e;
	    }
        con.setAutoCommit(true);
        con.close();
	}

	public static boolean eliminar(int id) throws SQLException {
		Connection con = Database.conectar();
		try {
			con.setAutoCommit(false);
			
			String sqlAutor = "DELETE FROM escribir WHERE libro = ?";
			PreparedStatement stmtAutor = con.prepareStatement(sqlAutor);
			stmtAutor.setInt(1, id);
			stmtAutor.executeUpdate();
			
			String sqlEditar = "DELETE FROM editar WHERE libro = ?";
			PreparedStatement stmtEditar = con.prepareStatement(sqlEditar);
			stmtEditar.setInt(1, id);
			stmtEditar.executeUpdate();
			
			String sqlLibro = "DELETE FROM libro WHERE id = ?";
			PreparedStatement stmtLibro = con.prepareStatement(sqlLibro);
			stmtLibro.setInt(1, id);
			stmtLibro.executeUpdate();
			
			con.commit();
		}
		catch (SQLException ex) {
			con.rollback();
			ex.printStackTrace();
			return false;
		}
		con.setAutoCommit(true);
		con.close();
		return true;
	}
}
