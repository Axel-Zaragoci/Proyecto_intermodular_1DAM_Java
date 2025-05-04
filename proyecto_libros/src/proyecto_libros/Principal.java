package proyecto_libros;

public class Principal {

	public static void main(String[] args) {
		VentanaPrincipal v = new VentanaPrincipal();
		Navegador.argegarVentanas(v);
		Navegador.dispatcher("Menu", true);
	}

}
