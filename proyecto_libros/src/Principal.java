import Views.VentanaPrincipal;
import Models.Navegador;

public class Principal {

	public static void main(String[] args) {
		VentanaPrincipal v = new VentanaPrincipal();
		Navegador.agregarVentanas(v);
		Navegador.dispatcher("Menu", true);
	}

}
