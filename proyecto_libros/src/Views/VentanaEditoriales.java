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

import Controllers.EditorialController;
import Controllers.Navegador;
import Models.Editorial;

public class VentanaEditoriales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();

	public VentanaEditoriales() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1250, 700);
		contentPane = new JPanel();
		setTitle("Editoriales");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 1218, 643);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(10, 11, 685, 41);
		panel.add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		JButton createButton = new JButton("Crear editorial");
		createButton.setBounds(10, 11, 145, 23);
		createButton.addActionListener(e -> {
			Navegador.agregarVentanas(new VentanaEditorialesCrear());
			((VentanaEditorialesCrear) Navegador.obtenerVentana("Crear editorial")).setVisible(true);
			VentanaEditoriales.this.setVisible(false);
		});
		buttonsPanel.add(createButton);
		
		JButton updateButton = new JButton("Actualizar editorial");
		updateButton.setBounds(165, 11, 145, 23);
		updateButton.addActionListener(e -> {
			
		});
		buttonsPanel.add(updateButton);
		
		JButton deleteButton = new JButton("Eliminar editorial");
		deleteButton.setBounds(320, 11, 145, 23);
		deleteButton.addActionListener(e -> {
			int seleccionado = table.getSelectedRow();
			if (seleccionado == -1) {
				Navegador.mostrarMensajeError(VentanaEditoriales.this, "Error", "Debes seleccionar una o más líneas");
				return;
			}
			if (Navegador.mostrarMensajePregunta(VentanaEditoriales.this, "Confirmación", "Seguro que quieres eliminar la editorial " + model.getValueAt(seleccionado, 1)) == JOptionPane.OK_OPTION) {
				if (!EditorialController.eliminar((int) model.getValueAt(seleccionado, 0))) {
					Navegador.mostrarMensajeError(VentanaEditoriales.this, "Error", "No se ha podido eliminar la editorial " + model.getValueAt(seleccionado, 1));
				}
				else {
					Navegador.mostrarMensajeInformacion(VentanaEditoriales.this, "Completado", "Se ha eliminado correctamente la editorial");
				}
			}
			actualizarTabla();
		});
		buttonsPanel.add(deleteButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 1198, 555);
		panel.add(scrollPane);
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Menu", true);
				Navegador.obtenerVentana("Menu").setLocationRelativeTo(VentanaEditoriales.this);
			}
		});
	}
	
	public void actualizarTabla() {
		ArrayList<Editorial> editoriales = Editorial.actualizarLista();
		model.setRowCount(0);
		model.setColumnCount(0);
		
		model.addColumn("ID");
		model.addColumn("Nombre");
		model.addColumn("País");
		model.addColumn("Ciudad");
		model.addColumn("Año de fundación");
		model.addColumn("Teléfono");
		model.addColumn("Email");
		
		for (Editorial e : editoriales) {
			Object[] a = {e.getId(), e.getNombre(), e.getPais(), e.getCiudad(), e.getAnoFundacion() == 0 ? "" : e.getAnoFundacion(), e.getTelefono() == 0 ? "" : e.getTelefono(), e.getEmail()};
			model.addRow(a);
		}
	}
}
