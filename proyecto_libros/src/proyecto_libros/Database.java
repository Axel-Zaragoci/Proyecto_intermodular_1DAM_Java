package proyecto_libros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static String url = "jdbc:postgresql://localhost:5432/Proyecto";
    private static String usuario = "postgres";
    private static String contraseña = "1234";
    
    public static Connection conectar() {
    	try {
			return DriverManager.getConnection(url, usuario, contraseña);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static boolean revisarLibro(Libro libro) {
    	if (libro.getTitulo() == null || libro.getTitulo().length() > 75) {
    		Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El titulo no puede estar vacío ni tener más de 75 caracteres. Ahora mismo tiene " + libro.getTitulo().length());
    		return false;
    	}
    	if (libro.getPaginas() < 0 || libro.getPaginas() > 9999) {
    		Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El libro debe de tener más de 0 páginas y menos de 9999 páginas");
    		return false;
    	}
    	if (libro.getPublicacion() > 9999) {
    		Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El libro debe de tener un año de publicación válido");
    		return false;
    	}
    	String sqlAutorID = "SELECT MAX(id) AS id FROM autor";
		try (Connection con = Database.conectar();
			 PreparedStatement stmtAutorID = con.prepareStatement(sqlAutorID)) {
			ResultSet rsAutorID = stmtAutorID.executeQuery();
			if (libro.getAutor() == null || libro.getAutor().size() == 0) {
				Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El libro debe de tener al menos un autor");
				return false;
			}
			else {
				while(rsAutorID.next()) {
					for (Integer autor : libro.getAutor()) {
						if (autor < 1 || autor > rsAutorID.getInt("id")) {
							Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El autor indicado no existe. Indica uno existente o crea un nuevo autor antes de añadir el libro");
							return false;
						}
				}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
    	String sqlEditorialID = "SELECT MAX(id) AS id FROM autor";
		try (Connection con = Database.conectar();
			 PreparedStatement stmtEditorialID = con.prepareStatement(sqlEditorialID)) {
			ResultSet rsEditorialID = stmtEditorialID.executeQuery();
			while (rsEditorialID.next()) {
				if (libro.getEditorial() < 1 || libro.getEditorial() > rsEditorialID.getInt("id")) {
					Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "La editorial indicada no existe. Indica una editorial existente o crea una editorial antes de añadir el libro");
					return false;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (libro.getPrecio() < 0 || libro.getPrecio() > 10000) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El precio debe de ser mayor de 0 y menor que 10000");
			return false;
		}
		
		String isbn = libro.getIsbn() + "";
		if (isbn.length() != 9 && isbn.length() != 10 && isbn.length() != 13) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El ISBN es invalido. Si tiene una letra debes quitarla");
			return false;
		}
		
		if (libro.getIdioma() == null || libro.getIdioma().length() > 4) {
			Navegador.mostrarMensajeError(Navegador.obtenerVentana("Crear libro"), "Error", "El idioma debe de ser un código de hasta 3 letras");
		}
    	return true;
    }

}
