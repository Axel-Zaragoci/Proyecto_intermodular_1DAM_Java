package Views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controllers.Database;
import Controllers.EditorialController;
import Controllers.Navegador;
import Models.Editorial;

public class VentanaEditorialesCrear extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField nationTextField;
	private JTextField cityTextField;
	private JTextField yearTextField;
	private JTextField phoneTextField;
	private JTextField emailTextField;
	
	public VentanaEditorialesCrear() {
		setLocationRelativeTo(Navegador.obtenerVentana("Editoriales"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Crear editorial");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setBounds(10, 11, 402, 227);
		contentPane.add(formPanel);
		formPanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("Indica el nombre: ");
		nameLabel.setBounds(10, 11, 174, 14);
		formPanel.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(194, 8, 110, 20);
		formPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel nationLabel = new JLabel("Indica el país con la sede: ");
		nationLabel.setBounds(10, 36, 174, 14);
		formPanel.add(nationLabel);
		
		nationTextField = new JTextField();
		nationTextField.setBounds(194, 33, 110, 20);
		formPanel.add(nationTextField);
		nationTextField.setColumns(10);
		
		JLabel cityLabel = new JLabel("Indica la ciudad con la sede: ");
		cityLabel.setBounds(10, 61, 174, 14);
		formPanel.add(cityLabel);
		
		cityTextField = new JTextField();
		cityTextField.setBounds(194, 58, 110, 20);
		formPanel.add(cityTextField);
		cityTextField.setColumns(10);
		
		JLabel yearLabel = new JLabel("Indica el año de fundación: ");
		yearLabel.setBounds(10, 86, 174, 14);
		formPanel.add(yearLabel);
		
		yearTextField = new JTextField();
		yearTextField.setBounds(194, 83, 51, 20);
		formPanel.add(yearTextField);
		yearTextField.setColumns(10);
		
		JLabel phoneLabel = new JLabel("Indica el teléfono de contacto: ");
		phoneLabel.setBounds(10, 111, 186, 14);
		formPanel.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(194, 108, 110, 20);
		formPanel.add(phoneTextField);
		phoneTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("Indica el email de contacto: ");
		emailLabel.setBounds(10, 136, 174, 14);
		formPanel.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(194, 133, 200, 20);
		formPanel.add(emailTextField);
		emailTextField.setColumns(10);
		
		JButton CreateButton = new JButton("Crear editorial");
		CreateButton.setBounds(13, 193, 128, 23);
		formPanel.add(CreateButton);
		CreateButton.addActionListener( e -> {
			Integer anoFundacion = null;
			long telefono = 0;
			try {
				if (!(yearTextField.getText().trim().isBlank() || yearTextField.getText().trim().isEmpty() || yearTextField.getText().trim().equals(""))) {
					anoFundacion = Integer.parseInt(yearTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaEditorialesCrear.this, "Error", "Año de fundación no válido");
				return;
			}
			try {
				if (!(phoneTextField.getText().trim().isBlank() || phoneTextField.getText().trim().isEmpty() || phoneTextField.getText().trim().equals(""))) {
					telefono = Long.parseLong(phoneTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaEditorialesCrear.this, "Error", "Número de teléfono no válido");
				return;
			}
			Editorial temp = new Editorial(nameTextField.getText().trim(), nationTextField.getText().trim(), cityTextField.getText().trim(), anoFundacion, telefono, emailTextField.getText().trim());
			if (Database.revisarEditorial(temp, VentanaEditorialesCrear.this)) {
				EditorialController.crear(temp);
				Navegador.mostrarMensajeInformacion(Navegador.obtenerVentana("Crear editorial"), "Completado", "Se ha insertado la editorial correctamente");
				limpiar();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Editoriales", true);
				Navegador.obtenerVentana("Editoriales").setLocationRelativeTo(VentanaEditorialesCrear.this);
				((VentanaEditoriales) Navegador.obtenerVentana("Editoriales")).actualizarTabla();
			}
		});
	}
	
	public void limpiar() {
		nameTextField.setText(null);
		nationTextField.setText(null);
		cityTextField.setText(null);
		yearTextField.setText(null);
		phoneTextField.setText(null);
		emailTextField.setText(null);
	}
}
