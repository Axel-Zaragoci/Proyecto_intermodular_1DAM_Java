package App;
import Controllers.Navegador;
import Views.VentanaLogin;


public class Principal {

	public static void main(String[] args) {
		Navegador.agregarVentanas(new VentanaLogin());
		Navegador.dispatcher("Iniciar sesión", true);
	}
}
