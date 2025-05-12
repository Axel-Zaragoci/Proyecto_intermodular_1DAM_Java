package Views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Config.config;
import Controllers.Navegador;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JTextField passwdTextField;

	public VentanaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Iniciar sesión");
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 368, 243);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel titleLabel = new JLabel("Inicia sesión para continuar");
		titleLabel.setBounds(10, 26, 232, 14);
		panel.add(titleLabel);
		
		JLabel userLabel = new JLabel("Nombre de usuario:");
		userLabel.setBounds(10, 62, 130, 14);
		panel.add(userLabel);
		
		userTextField = new JTextField();
		userTextField.setBounds(10, 76, 204, 20);
		panel.add(userTextField);
		userTextField.setColumns(10);
		
		JLabel passwdLabel = new JLabel("Contraseña:");
		passwdLabel.setBounds(10, 107, 130, 14);
		panel.add(passwdLabel);
		
		passwdTextField = new JTextField();
		passwdTextField.setBounds(10, 121, 204, 20);
		panel.add(passwdTextField);
		passwdTextField.setColumns(10);
		
		JButton loginButton = new JButton("Iniciar sesión");
		loginButton.addActionListener( e -> {
			if (userTextField.getText().trim().isBlank() || userTextField.getText().trim().isEmpty() || userTextField.getText().trim().equals("")) {
				Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Debes indicar un usuario");
				return;
			}
			if (passwdTextField.getText().trim().isBlank() || passwdTextField.getText().trim().isEmpty() || passwdTextField.getText().trim().equals("")) {
				Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Debes indicar una contraseña");
				return;
			}
			if (userTextField.getText().trim().equalsIgnoreCase(config.getUsuario()) && passwdTextField.getText().trim().equalsIgnoreCase(config.getContraseña())) {
				Navegador.agregarVentanas(new VentanaPrincipal());
				Navegador.dispatcher("Menu", true);
				Navegador.dispatcher(getTitle(), false);
				return;
			}
			Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Datos incorrectos");
		});
		loginButton.setBounds(10, 174, 130, 23);
		panel.add(loginButton);
	}
}
