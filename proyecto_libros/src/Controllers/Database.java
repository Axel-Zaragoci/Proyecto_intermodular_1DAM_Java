package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import Models.Autor;
import Models.Editorial;
import Models.Libro;
import Config.config;

public class Database {
    public static Connection conectar() {
    	try {
			return DriverManager.getConnection(config.getUrl(), config.getUsuario(), config.getContraseña());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static boolean revisarLibro(Libro libro, JFrame ventana) {
    	if (libro.getTitulo() == null || libro.getTitulo().length() > 75) {
    		Navegador.mostrarMensajeError(ventana, "Error", "El titulo no puede estar vacío ni tener más de 75 caracteres. Ahora mismo tiene " + libro.getTitulo().length());
    		return false;
    	}
    	if (libro.getPaginas() < 0 || libro.getPaginas() > 9999) {
    		Navegador.mostrarMensajeError(ventana, "Error", "El libro debe de tener más de 0 páginas y menos de 9999 páginas");
    		return false;
    	}
    	if (libro.getPublicacion() > 9999) {
    		Navegador.mostrarMensajeError(ventana, "Error", "El libro debe de tener un año de publicación válido");
    		return false;
    	}
    	String sqlAutorID = "SELECT MAX(id) AS id FROM autor";
		try (Connection con = Database.conectar();
			 PreparedStatement stmtAutorID = con.prepareStatement(sqlAutorID)) {
			ResultSet rsAutorID = stmtAutorID.executeQuery();
			if (libro.getAutor() == null || libro.getAutor().size() == 0) {
				Navegador.mostrarMensajeError(ventana, "Error", "El libro debe de tener al menos un autor");
				return false;
			}
			else {
				while(rsAutorID.next()) {
					for (Integer autor : libro.getAutor()) {
						if (autor < 1 || autor > rsAutorID.getInt("id")) {
							Navegador.mostrarMensajeError(ventana, "Error", "El autor indicado no existe. Indica uno existente o crea un nuevo autor antes de añadir el libro");
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
					Navegador.mostrarMensajeError(ventana, "Error", "La editorial indicada no existe. Indica una editorial existente o crea una editorial antes de añadir el libro");
					return false;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (libro.getPrecio() < 0 || libro.getPrecio() > 10000) {
			Navegador.mostrarMensajeError(ventana, "Error", "El precio debe de ser mayor de 0 y menor que 10000");
			return false;
		}
		
		if (libro.getIsbn() != 0) {
			String isbn = libro.getIsbn() + "";
			if (isbn.length() != 9 && isbn.length() != 10 && isbn.length() != 13) {
				Navegador.mostrarMensajeError(ventana, "Error", "El ISBN es invalido. Si tiene una letra debes quitarla");
				return false;
			}
		}
		
		if (libro.getIdioma() == null || libro.getIdioma().length() > 4) {
			Navegador.mostrarMensajeError(ventana, "Error", "El idioma debe de ser un código de hasta 3 letras");
		}
    	return true;
    }

    public static boolean revisarAutor(Autor autor, JFrame ventana) {
    	if(autor.getNombre().length() > 50 || autor.getNombre().length() < 1) {
    		Navegador.mostrarMensajeError(ventana, "Error", "El autor debe de tener un nombre. El nombre debe ser de máximo 50 caracteres. Actualmente tiene " + autor.getNombre().length());
    		return false;
    	}
    	
		if (autor.getFechaNacimiento() != null) {
			try {
				java.sql.Date.valueOf(autor.getFechaNacimiento());
			}
			catch (Exception ex) {
	    		Navegador.mostrarMensajeError(ventana, "Error", "La fecha de nacimiento es inválida");
	    		return false;
			}
		}	
    	
    	if(autor.getNacionalidad().length() > 5 || autor.getNacionalidad().length() < 0) {
    		Navegador.mostrarMensajeError(ventana, "Error", "El autor debe de tener una nacionalidad. Esta se muestra mediante un código de hasta 4 letras");
    		return false;
    	}
    	
    	if(autor.getSeudonimo() != null && autor.getSeudonimo() != -1) {
        	String sqlAutorID = "SELECT MAX(id) AS id FROM autor";
    		try (Connection con = Database.conectar();
    			 PreparedStatement stmtAutorID = con.prepareStatement(sqlAutorID)) {
    			ResultSet rsAutorID = stmtAutorID.executeQuery();
    			while (rsAutorID.next()) {
    				if (autor.getSeudonimo() < 1 || autor.getSeudonimo() > rsAutorID.getInt("id")) {
    					Navegador.mostrarMensajeError(ventana, "Error", "Error. Debes añadir un autor válido para el pseudónimo");
    					return false;
    				}
    			}
    		}
    		catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return true;
    }

    public static boolean revisarEditorial(Editorial editorial, JFrame ventana) {
    	if (editorial.getNombre() != null) {
    		if (editorial.getNombre().length() > 50 || editorial.getNombre().length() < 1) {
        		Navegador.mostrarMensajeError(ventana, "Error", "La editorial debe de tener un máximo de 50 caracteres. Actualmente tiene " + editorial.getNombre().length());
        		return false;
        	}
    	}
    	else {
    		Navegador.mostrarMensajeError(ventana, "Error", "La editorial debe tener un nombre");
    		return false;
    	}
    	
    	if (!editorial.getPais().equals("")) {
    		if (editorial.getPais().length() > 30 || editorial.getPais().length() < 1) {
        		Navegador.mostrarMensajeError(ventana, "Error", "El país de la editorial debe tener un máximo de 30 caracteres. Actualmente tiene " + editorial.getPais().length());
        		return false;
        	}
    	}
    	
    	if ((editorial.getTelefono() + "").length() > 13 || (editorial.getTelefono() + "").length() < 1) {
    		Navegador.mostrarMensajeError(ventana, "Error", "La editorial debe de tener un teléfono asignado de hasta 13 números");
    		return false;
    	}
    	
    	if (editorial.getCiudad() != null) {
    		if (editorial.getCiudad().length() > 50 || editorial.getCiudad().length() < 0) {
        		Navegador.mostrarMensajeError(ventana, "Error", "La editorial debe tener una ciudad asignada para su central y debe tener un máximo de 50 caracteres. Actualmente tiene " + editorial.getPais().length());
        		return false;
    		}
    	}
    	
    	if (editorial.getAnoFundacion() != 0) {
    		if (editorial.getAnoFundacion() > 10000 || editorial.getAnoFundacion() < -10000) {
        		Navegador.mostrarMensajeError(ventana, "Error", "La editorial debe tener un año de fundación válido");
        		return false;
        	}
    	}
    	return true;
    }
}
