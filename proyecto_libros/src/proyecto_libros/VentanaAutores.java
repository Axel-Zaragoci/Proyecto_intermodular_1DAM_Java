package proyecto_libros;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Navegador.agregarVentanas(new VentanaAutoresCrear());
				Navegador.dispatcher("Crear autor", true);
				Navegador.dispatcher(getTitle(), false);
			}
		});
		createButton.setBounds(10, 11, 117, 23);
		panel.add(createButton);
		
		JButton deleteButton = new JButton("Eliminar autor");
		deleteButton.setBounds(292, 11, 117, 23);
		panel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAutores.this.eliminarAutor();
			}
		});
		
		JButton updateButton = new JButton("Editar autor");
		updateButton.setBounds(150, 11, 117, 23);
		panel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Navegador.agregarVentanas(new VentanaAutoresActualizar());
				Navegador.dispatcher("Crear autor", true);
				Navegador.dispatcher(getTitle(), false);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 61, 1208, 593);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);
		table.setModel(model);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Menu", true);
			}
		});
	}
	
	public void actualizarTabla() {
		ArrayList<Autor> autores = AutorController.ver();
		model.setRowCount(0);
		model.setColumnCount(0);
		
		model.addColumn("id");
		model.addColumn("Nombre");
		model.addColumn("Fecha de nacimiento");
		model.addColumn("Nacionalidad");
		model.addColumn("Vivo");
		model.addColumn("Seudónimo");
		
		Autor.actualizarLista();
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
