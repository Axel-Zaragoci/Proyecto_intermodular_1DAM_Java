package proyecto_libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static ArrayList<Libro> ver() throws SQLException {
		ArrayList<Libro> libros = new ArrayList<>();
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
			
			Libro libro = new Libro(libroID, rs.getString("titulo"), rs.getInt("editorial"), rs.getInt("paginas"), rs.getInt("ano_publicacion"), rs.getDouble("precio"), rs.getLong("isbn"), rs.getString("idioma"));
			libro.setAutor(autores);
			libros.add(libro);
		}
		return libros;
	}
}
