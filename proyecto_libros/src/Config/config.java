package Config;

public class config {
	  private static String url = "jdbc:postgresql://localhost:5432/Proyecto";
    private static String usuario = "postgres";
    private static String contraseña = "1234";
    
    public static String getUrl() {
    	return url;
    }
    
    public static String getUsuario() {
    	return usuario;
    }
    
    public static String getContraseña() {
		return contraseña;
    }
}
