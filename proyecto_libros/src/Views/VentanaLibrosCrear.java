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
import Models.Editorial;
import Models.Libro;

public class VentanaLibrosCrear extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titleTextField;
	private JTextField pageTextField;
	private JTextField yearTextField;
	private JTextField costTextField;
	private JTextField isbnTextField;
	private JTextField codeTextField;
	private DefaultListModel<String> writers = new DefaultListModel<>();
	private DefaultListModel<String> editors = new DefaultListModel<>();
	private JList<String> writerList;
	private JList<String> editorList;

	public VentanaLibrosCrear() {
		setLocationRelativeTo(Navegador.obtenerVentana("Libros"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1050, 400);
		setTitle("Crear libro");
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
	
		JScrollPane editorScrollPane = new JScrollPane();
		editorScrollPane.setBounds(660, 11, 260, 343);
		contentPane.add(editorScrollPane);
		editorList = new JList<>(editors);
		editorScrollPane.setViewportView(editorList);
		
		JButton createButton = new JButton("Crear libro");
		createButton.setBounds(10, 182, 104, 23);
		formPanel.add(createButton);
		createButton.addActionListener( e -> {
			crearLibro();
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Libros", true);
				Navegador.obtenerVentana("Libros").setLocationRelativeTo(VentanaLibrosCrear.this);
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
		
		ArrayList<Editorial> editoriales = Editorial.actualizarLista();
		editors.clear();
		for (Editorial e : editoriales) {
			editors.addElement(e.getId() + " - " + e.getNombre());
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
	
	public void crearLibro() {
		String titulo = titleTextField.getText().trim();
		Integer paginas = 0;
		try {
			if (!(pageTextField.getText().trim().isBlank() || pageTextField.getText().trim().isEmpty() || pageTextField.getText().trim().equals(""))) {
				paginas = Integer.parseInt(pageTextField.getText().trim());
			}
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "El número de páginas debe de ser un número");
			return;
		}
		Integer ano = 0;
		try {
			if (!(yearTextField.getText().trim().isBlank() || yearTextField.getText().trim().isEmpty() || yearTextField.getText().trim().equals(""))) {
				ano = Integer.parseInt(yearTextField.getText().trim());
			}
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "El año de publicación debe de ser un número");
			return;
		}
		Double precio = 0D;
		try {
			if (!(costTextField.getText().trim().isBlank() || costTextField.getText().trim().isEmpty() || costTextField.getText().trim().equals(""))) {
				precio = Double.parseDouble(costTextField.getText().trim());
			}
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "El precio debe de ser un número");
			return;
		}
		Long isbn = 0L;
		try {
			if (!(isbnTextField.getText().trim().isBlank() || isbnTextField.getText().trim().isEmpty() || isbnTextField.getText().trim().equals(""))) {
				isbn = Long.parseLong(isbnTextField.getText().trim());
			}
		}
		catch (Exception ex) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "El ISBN debe de ser solo números");
			return;
		}
		
		String idioma = codeTextField.getText().trim();
		
		int[] autoresTemp = writerList.getSelectedIndices();
		if (autoresTemp.length == 0) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "Debe de haber al menos 1 autor seleccionado");
			return;
		}
		ArrayList<Integer> autores = new ArrayList<>();
		for (int i = 0; i < autoresTemp.length; i++) {
			autores.add(Integer.parseInt(writers.getElementAt(autoresTemp[i]).split(" - ")[0]));
		}
		
		int editorial = editorList.getSelectedIndex();
		if (editorial == -1) {
			Navegador.mostrarMensajeError(VentanaLibrosCrear.this, "Error", "Debe de haber una editorial seleccionada");
			return;
		}
		editorial = Integer.parseInt(editors.getElementAt(editorial).split(" - ")[0]);
		
		Libro temp = new Libro(titulo, editorial, autores.toArray(new Integer[0]), paginas, ano, precio, isbn, idioma);
		if (Database.revisarLibro(temp, VentanaLibrosCrear.this)) {
			try {
				LibroController.crearLibro(temp);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Navegador.mostrarMensajeInformacion(VentanaLibrosCrear.this, "Completado", "Libro creado");
			limpiar();
		}
	}
}
