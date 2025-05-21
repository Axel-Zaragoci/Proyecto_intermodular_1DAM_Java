package Views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Config.config;
import Controllers.Database;
import Controllers.Navegador;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwdTextField;

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
		titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
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
		
		passwdTextField = new JPasswordField();
		passwdTextField.setBounds(10, 121, 204, 20);
		panel.add(passwdTextField);
		passwdTextField.setColumns(10);
		
		JButton loginButton = new JButton("Iniciar sesión");
		loginButton.addActionListener( e -> {
			revision();
		});
		loginButton.setBounds(10, 174, 130, 23);
		panel.add(loginButton);
	}
	
	private void revision() {
		if (userTextField.getText().trim().isBlank() || userTextField.getText().trim().isEmpty() || userTextField.getText().trim().equals("")) {
			Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Debes indicar un usuario");
			return;
		}
		if (passwdTextField.getPassword().length == 0) {
			Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Debes indicar una contraseña");
			return;
		}
		String passwd = String.valueOf(passwdTextField.getPassword());
		if (Database.revisarConexion(userTextField.getText().trim().toLowerCase(), passwd)) {
			Navegador.agregarVentanas(new VentanaPrincipal());
			Navegador.dispatcher("Menu", true);
			Navegador.dispatcher(getTitle(), false);
			config.setUsuario(userTextField.getText().trim().toLowerCase());
			config.setPasswd(passwd);
			return;
		}
		Navegador.mostrarMensajeError(VentanaLogin.this, "Error", "Datos incorrectos");
	}
}
