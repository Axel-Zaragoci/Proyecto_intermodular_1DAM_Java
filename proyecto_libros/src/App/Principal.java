package App;
import Controllers.Navegador;
import Views.VentanaPrincipal;

public class Principal {

	public static void main(String[] args) {
		VentanaPrincipal v = new VentanaPrincipal();
		Navegador.agregarVentanas(v);
		Navegador.dispatcher("Menu", true);
	}
}
