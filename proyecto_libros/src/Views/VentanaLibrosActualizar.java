package Views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controllers.Database;
import Controllers.LibroController;
import Controllers.Navegador;
import Models.Autor;
import Models.Libro;

public class VentanaLibrosActualizar extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titleTextField;
	private JTextField pageTextField;
	private JTextField yearTextField;
	private JTextField costTextField;
	private JTextField isbnTextField;
	private JTextField codeTextField;
	private DefaultListModel<String> writers = new DefaultListModel<>();
	private JList<String> writerList;
	private int id;
	private int editorial;


	public VentanaLibrosActualizar() {
		setLocationRelativeTo(Navegador.obtenerVentana("Libros"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 750, 400);
		setTitle("Actualizar libro");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setBounds(10, 11, 385, 241);
		contentPane.add(formPanel);
		formPanel.setLayout(null);
		
		JLabel titleLabel = new JLabel("Indica el título: ");
		titleLabel.setBounds(10, 11, 104, 14);
		formPanel.add(titleLabel);
		
		titleTextField = new JTextField();
		titleTextField.setBounds(124, 8, 175, 20);
		formPanel.add(titleTextField);
		titleTextField.setColumns(10);
		
		JLabel pageLabel = new JLabel("Páginas: ");
		pageLabel.setBounds(10, 36, 60, 14);
		formPanel.add(pageLabel);
		
		pageTextField = new JTextField();
		pageTextField.setBounds(80, 33, 96, 20);
		formPanel.add(pageTextField);
		pageTextField.setColumns(10);
		
		JLabel yearLabel = new JLabel("Indica el año de publicación: ");
		yearLabel.setBounds(10, 61, 175, 14);
		formPanel.add(yearLabel);
		
		yearTextField = new JTextField();
		yearTextField.setBounds(195, 58, 51, 20);
		formPanel.add(yearTextField);
		yearTextField.setColumns(10);
		
		JLabel costLabel = new JLabel("Indica el precio: ");
		costLabel.setBounds(10, 85, 96, 14);
		formPanel.add(costLabel);
		
		costTextField = new JTextField();
		costTextField.setBounds(125, 82, 51, 20);
		formPanel.add(costTextField);
		costTextField.setColumns(10);
		
		JLabel isbnLabel = new JLabel("Indica el ISBN: ");
		isbnLabel.setBounds(10, 110, 96, 14);
		formPanel.add(isbnLabel);
		
		isbnTextField = new JTextField();
		isbnTextField.setBounds(116, 107, 96, 20);
		formPanel.add(isbnTextField);
		isbnTextField.setColumns(10);
		
		JLabel codeLabel = new JLabel("Indica el idioma (código de hasta 3 letras): ");
		codeLabel.setBounds(10, 135, 257, 14);
		formPanel.add(codeLabel);
		
		codeTextField = new JTextField();
		codeTextField.setBounds(277, 132, 96, 20);
		formPanel.add(codeTextField);
		codeTextField.setColumns(10);
		
		JScrollPane writerScrollPane = new JScrollPane();
		writerScrollPane.setBounds(405, 11, 245, 343);
		contentPane.add(writerScrollPane);
		writerList = new JList<>(writers);
		writerScrollPane.setViewportView(writerList);
		
		JButton updateButton = new JButton("Actualizar libro");
		updateButton.setBounds(10, 182, 104, 23);
		formPanel.add(updateButton);
		updateButton.addActionListener( e -> {
			String titulo = titleTextField.getText().trim();
			Integer paginas = null;
			try {
				if (!(pageTextField.getText().trim().isBlank() || pageTextField.getText().trim().isEmpty() || pageTextField.getText().trim().equals(""))) {
					paginas = Integer.parseInt(pageTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaLibrosActualizar.this, "Error", "El número de páginas debe de ser un número");
				return;
			}
			Integer ano = null;
			try {
				if (!(yearTextField.getText().trim().isBlank() || yearTextField.getText().trim().isEmpty() || yearTextField.getText().trim().equals(""))) {
					ano = Integer.parseInt(yearTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaLibrosActualizar.this, "Error", "El año de publicación debe de ser un número");
				return;
			}
			Double precio = null;
			try {
				if (!(costTextField.getText().trim().isBlank() || costTextField.getText().trim().isEmpty() || costTextField.getText().trim().equals(""))) {
					precio = Double.parseDouble(costTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaLibrosActualizar.this, "Error", "El precio debe de ser un número");
				return;
			}
			Long isbn = null;
			try {
				if (!(isbnTextField.getText().trim().isBlank() || isbnTextField.getText().trim().isEmpty() || isbnTextField.getText().trim().equals(""))) {
					isbn = Long.parseLong(isbnTextField.getText().trim());
				}
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaLibrosActualizar.this, "Error", "El ISBN debe de ser solo números");
				return;
			}
			
			String idioma = null;
			
			if (!(codeTextField.getText().trim().isBlank() || codeTextField.getText().trim().isEmpty() || codeTextField.getText().trim().equals(""))) {
				idioma = codeTextField.getText().trim();
			}
			
			int[] autoresTemp = writerList.getSelectedIndices();
			ArrayList<Integer> autoresIDs = new ArrayList<>();
			for (int autor : autoresTemp) {
				autoresIDs.add(Integer.parseInt(writers.getElementAt(autor).split(" - ")[0]));
			}
			
			Libro temp = new Libro(id, titulo, editorial, autoresIDs.toArray(new Integer[0]), paginas, ano, precio, isbn, idioma);
			if (Database.revisarLibro(temp, VentanaLibrosActualizar.this)) {
				try {
					LibroController.actualizarLibro(temp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				Navegador.mostrarMensajeInformacion(VentanaLibrosActualizar.this, "Completado", "Libro actualizado");
				limpiar();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Libros", true);
				Navegador.obtenerVentana("Libros").setLocationRelativeTo(VentanaLibrosActualizar.this);
				((VentanaLibros) Navegador.obtenerVentana("Libros")).actualizarTabla();
			}
		});
	}
	
	public void actualizarListas() {
		ArrayList<Autor> autores = Autor.actualizarLista();
		writers.clear();
		for (Autor a : autores) {
			writers.addElement(a.getId() + " - " + a.getNombre());
		}
	}
	
	public void limpiar() {
		titleTextField.setText(null);
		pageTextField.setText(null);
		yearTextField.setText(null);
		costTextField.setText(null);
		isbnTextField.setText(null);
		codeTextField.setText(null);
		actualizarListas();
	}

	public void obtenerDatos(Libro l) {
		titleTextField.setText(l.getTitulo());
		pageTextField.setText(l.getPaginas() + "");
		yearTextField.setText(l.getPublicacion() + "");
		costTextField.setText(l.getPrecio() + "");
		isbnTextField.setText(l.getIsbn() + "");
		codeTextField.setText(l.getIdioma() + "");
		Integer[] autores = l.getAutor().toArray(new Integer[0]);
		int[] autoresArr = new int[autores.length];
		for (int i = 0; i < autores.length; i++) {
			autoresArr[i] = autores[i]-1;
		}
		writerList.setSelectedIndices(autoresArr);
		id = l.getId();
		editorial = l.getEditorial();
	}
}
