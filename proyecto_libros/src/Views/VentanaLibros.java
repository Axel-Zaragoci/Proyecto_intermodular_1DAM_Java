package Views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controllers.AutorController;
import Controllers.Navegador;
import Models.Autor;
import Models.Editorial;
import Models.Libro;


public class VentanaLibros extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();

	public VentanaLibros() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1400, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Libros");
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 419, 39);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton createButton = new JButton("Crear libro");
		createButton.addActionListener(e -> {
				Navegador.agregarVentanas(new VentanaLibrosCrear());
				Navegador.dispatcher("Crear libro", true);
				Navegador.dispatcher(getTitle(), false);
				((VentanaLibrosCrear) Navegador.obtenerVentana("Crear libro")).actualizarListas();
		});
		createButton.setBounds(10, 11, 117, 23);
		panel.add(createButton);
		
		JButton deleteButton = new JButton("Eliminar libro");
		deleteButton.setBounds(292, 11, 117, 23);
		panel.add(deleteButton);
		deleteButton.addActionListener(e -> {
			VentanaLibros.this.eliminarAutor();
		});
		
		JButton updateButton = new JButton("Editar libro");
		updateButton.setBounds(150, 11, 117, 23);
		panel.add(updateButton);
		updateButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row != -1) {
				Navegador.agregarVentanas(new VentanaAutoresActualizar());
				Navegador.dispatcher("Actualizar autor", true);
				Navegador.dispatcher(getTitle(), false);
				//((VentanaLibrosActualizar) Navegador.obtenerVentana("Actualizar libro")).actualizarLista();
				//((VentanaLibrosActualizar) Navegador.obtenerVentana("Actualizar libro")).setDatos(Libro.obtenerLibro(row));
				return;
			}
			Navegador.mostrarMensajeError(VentanaLibros.this, "Error", "Selecciona el autor a actualizar");
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 61, 1358, 593);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		table.setDefaultEditor(Object.class, null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Menu", true);
				Navegador.obtenerVentana("Menu").setLocationRelativeTo(VentanaLibros.this);
			}
		});
	}
	
	public void actualizarTabla() {
		ArrayList<Libro> libros = Libro.actualizarLista();
		Autor.actualizarLista();
		Editorial.actualizarLista();
		model.setRowCount(0);
		model.setColumnCount(0);
		
		model.addColumn("ID");
		model.addColumn("Título");
		model.addColumn("Editorial");
		model.addColumn("Autores");
		model.addColumn("Páginas");
		model.addColumn("Año de publicación");
		model.addColumn("Precio");
		model.addColumn("ISBN");
		model.addColumn("Idioma");
		
		for (Libro libro : libros) {
			String autores = "";
			for (Integer id : libro.getAutor()) {
				autores = autores + Autor.obtenerAutor(id-1).getNombre() + ", ";
			}
			Object paginas = libro.getPaginas() == 0 ? "" : libro.getPaginas();
			Object publicacion = libro.getPublicacion() == 0 ? "" : libro.getPublicacion();
			Object precio = libro.getPrecio() == 0 ? "" : libro.getPrecio();
			Object isbn = libro.getIsbn() == 0 ? "" : libro.getIsbn();
			Object[] a = {libro.getId(), libro.getTitulo(), (Editorial.obtenerEditorial(libro.getEditorial()-1)).getNombre(), autores.substring(0, autores.length() - 2), paginas, publicacion, precio, isbn, libro.getIdioma()};
			model.addRow(a);
		}
	}
	
	public void eliminarAutor() {
		int seleccionado = table.getSelectedRow();
		if (seleccionado == -1) {
			Navegador.mostrarMensajeError(this, "Error", "Debes seleccionar una o más líneas");
			return;
		}
		if (Navegador.mostrarMensajePregunta(this, "Confirmación", "Seguro que quieres eliminar el autor " + model.getValueAt(seleccionado, 1)) == JOptionPane.OK_OPTION) {
			if (!AutorController.eliminar((int) model.getValueAt(seleccionado, 0))) {
				Navegador.mostrarMensajeError(this, "Error", "No se ha podido eliminar el autor " + model.getValueAt(seleccionado, 1));
			}
			else {
				Navegador.mostrarMensajeInformacion(this, "Completado", "Se ha eliminado correctamente el autor");
			}
		}
	}
}
