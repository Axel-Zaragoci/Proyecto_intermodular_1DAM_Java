package Config;

public class config {
	private static String url = "jdbc:postgresql://localhost:8081/Proyecto";
    private static String usuario;
    private static String contraseña;
    
    public static void setUsuario(String user) {
    	usuario = user;
    }
    
    public static void setPasswd(String passwd) {
    	contraseña = passwd;
    }
    
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
