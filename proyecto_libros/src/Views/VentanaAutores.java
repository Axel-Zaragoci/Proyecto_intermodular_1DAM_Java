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
import Models.Autor;
import Controllers.Navegador;


public class VentanaAutores extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();

	public VentanaAutores() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1250, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Autores");
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 419, 39);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton createButton = new JButton("Crear autor");
		createButton.addActionListener(e -> {
				Navegador.agregarVentanas(new VentanaAutoresCrear());
				Navegador.dispatcher("Crear autor", true);
				Navegador.dispatcher(getTitle(), false);
				((VentanaAutoresCrear) Navegador.obtenerVentana("Crear autor")).actualizarLista();
		});
		createButton.setBounds(10, 11, 117, 23);
		panel.add(createButton);
		
		JButton deleteButton = new JButton("Eliminar autor");
		deleteButton.setBounds(292, 11, 117, 23);
		panel.add(deleteButton);
		deleteButton.addActionListener(e -> {
			VentanaAutores.this.eliminarAutor();
		});
		
		JButton updateButton = new JButton("Editar autor");
		updateButton.setBounds(150, 11, 117, 23);
		panel.add(updateButton);
		updateButton.addActionListener(e -> {
			try {
				int id = (int) model.getValueAt(table.getSelectedRow(), 0);
				Navegador.agregarVentanas(new VentanaAutoresActualizar());
				Navegador.dispatcher("Actualizar autor", true);
				Navegador.dispatcher(getTitle(), false);
				((VentanaAutoresActualizar) Navegador.obtenerVentana("Actualizar autor")).actualizarLista();
				((VentanaAutoresActualizar) Navegador.obtenerVentana("Actualizar autor")).setDatos(Autor.obtenerAutor(id));
				return;
			}
			catch (Exception ex) {
				Navegador.mostrarMensajeError(VentanaAutores.this, "Error", "Selecciona el autor a actualizar");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 61, 1208, 593);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(819, 11, 409, 39);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton exportButton = new JButton("Exportar autor");
		exportButton.setBounds(268, 11, 119, 23);
		panel_1.add(exportButton);
		exportButton.addActionListener(e -> {
			if (table.getSelectedRow() != -1) {
				Autor temp = Autor.obtenerAutor((int) model.getValueAt(table.getSelectedRow(), 0));
				if (AutorController.exportar(temp)) {
					Navegador.mostrarMensajeInformacion(VentanaAutores.this, "Completado", "Autor exportado correctamente");
				}
				else {
					Navegador.mostrarMensajeError(VentanaAutores.this, "Error", "Ha ocurrido un error");
				}
				return;
			}
			Navegador.mostrarMensajeError(VentanaAutores.this, "Error", "Debes seleccionar un autor para exportarlo");
		});
		
		JButton exportAll = new JButton("Exportar todo");
		exportAll.setBounds(139, 11, 119, 23);
		panel_1.add(exportAll);
		exportAll.addActionListener(e -> {
			if (AutorController.exportarTodo()) {
				Navegador.mostrarMensajeInformacion(VentanaAutores.this, "Completado", "Autores exportados correctamente");
			}
			else {
				Navegador.mostrarMensajeError(VentanaAutores.this, "Error", "Ha ocurrido un error");
			}
		});
		
		JButton importButton = new JButton("Importar");
		importButton.setBounds(10, 11, 119, 23);
		panel_1.add(importButton);
		importButton.addActionListener(e -> {
			if (AutorController.importar()) {
				Navegador.mostrarMensajeInformacion(VentanaAutores.this, "Completado", "Autor/es importado/s correctamente");
				actualizarTabla();
			}
		});
		
		table.setDefaultEditor(Object.class, null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Menu", true);
				Navegador.obtenerVentana("Menu").setLocationRelativeTo(VentanaAutores.this);
			}
		});
	}
	
	public void actualizarTabla() {
		ArrayList<Autor> autores = Autor.actualizarLista();
		model.setRowCount(0);
		model.setColumnCount(0);
		
		model.addColumn("id");
		model.addColumn("Nombre");
		model.addColumn("Fecha de nacimiento");
		model.addColumn("Nacionalidad");
		model.addColumn("Vivo");
		model.addColumn("Seudónimo");
		
		for (Autor autor : autores) {
			String vivo = Autor.obtenerVida(autor.isVivo());
			String seudonimo = Autor.obtenerSeudonimo(autor.getSeudonimo());
			if (seudonimo == null) {
				seudonimo = "";
			}
			Object[] a = {autor.getId(), autor.getNombre(), autor.getFechaNacimiento(), autor.getNacionalidad(), vivo, seudonimo};
			model.addRow(a);
		}
	}
	
	public void eliminarAutor() {
		int seleccionado = table.getSelectedRow();
		if (seleccionado == -1) {
			Navegador.mostrarMensajeError(this, "Error", "Debes seleccionar una línea");
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
