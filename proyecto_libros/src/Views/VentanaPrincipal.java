package Views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controllers.Navegador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Menu");
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 418, 243);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel tituloLabel = new JLabel("Elige que quieres ver");
		tituloLabel.setBounds(0, 39, 418, 23);
		tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
		tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(tituloLabel);		
		
		Font fuenteButton = new Font("Arial", Font.BOLD, 14);
		
		JButton LibroButton = new JButton("Libros");
		LibroButton.setBounds(10, 120, 110, 27);
		LibroButton.setFont(fuenteButton);
		panel.add(LibroButton);
		LibroButton.addActionListener(e -> {
				VentanaLibros v = new VentanaLibros();
				Navegador.agregarVentanas(v);
				Navegador.dispatcher("Libros", true);
				Navegador.dispatcher("Menu", false);
		});
		
		JButton AutoresButton = new JButton("Autores");
		AutoresButton.setBounds(157, 120, 110, 27);
		AutoresButton.setFont(fuenteButton);
		panel.add(AutoresButton);
		AutoresButton.addActionListener(e -> {
				VentanaAutores v = new VentanaAutores();
				Navegador.agregarVentanas(v);
				v.actualizarTabla();
				Navegador.dispatcher("Autores", true);
				Navegador.dispatcher("Menu", false);
		});
		
		JButton EditorialButton = new JButton("Editoriales");
		EditorialButton.setBounds(298, 120, 110, 27);
		EditorialButton.setFont(fuenteButton);
		panel.add(EditorialButton);
		EditorialButton.addActionListener(e -> {
				VentanaEditoriales v = new VentanaEditoriales();
				Navegador.agregarVentanas(v);
				Navegador.dispatcher("Editoriales", true);
				Navegador.dispatcher("Menu", false);
		});
	}
}
