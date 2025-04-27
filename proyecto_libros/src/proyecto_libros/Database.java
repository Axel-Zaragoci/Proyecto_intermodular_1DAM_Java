package proyecto_libros;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
