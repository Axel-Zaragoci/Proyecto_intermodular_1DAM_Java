package Views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controllers.AutorController;
import Controllers.Database;
import Controllers.Navegador;
import Models.Autor;

public class VentanaAutoresActualizar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField yearTextField;
	private JTextField monthTextField;
	private JTextField dayTextField;
	private JTextField nationTextField;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JCheckBox liveCheckBox;
	private JList<String> SeudoList;
	private int id;

	public VentanaAutoresActualizar() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Actualizar autor");
		setBounds(100, 100, 750, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setBounds(10, 11, 430, 110);
		contentPane.add(formPanel);
		formPanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("Indica el nombre: ");
		nameLabel.setBounds(10, 11, 106, 14);
		formPanel.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(124, 8, 96, 20);
		formPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel yearLabel = new JLabel("Indica la fecha de nacimiento (yyyy/mm/dd): ");
		yearLabel.setBounds(10, 36, 255, 14);
		formPanel.add(yearLabel);
		
		yearTextField = new JTextField();
		yearTextField.setBounds(275, 33, 51, 20);
		formPanel.add(yearTextField);
		yearTextField.setColumns(5);
		
		JLabel interLabel = new JLabel("/");
		interLabel.setBounds(383, 33, 12, 20);
		formPanel.add(interLabel);
		
		monthTextField = new JTextField();
		monthTextField.setBounds(347, 33, 26, 20);
		formPanel.add(monthTextField);
		monthTextField.setColumns(5);
		
		JLabel interLabel2 = new JLabel("/");
		interLabel2.setBounds(336, 33, 12, 20);
		formPanel.add(interLabel2);
		
		dayTextField = new JTextField();
		dayTextField.setBounds(394, 33, 26, 20);
		formPanel.add(dayTextField);
		dayTextField.setColumns(5);
		
		JLabel nationLabel = new JLabel("Indica el pais de origen (cod de hasta 4 letras):");
		nationLabel.setBounds(10, 61, 271, 14);
		formPanel.add(nationLabel);
		
		nationTextField = new JTextField();
		nationTextField.setBounds(285, 58, 51, 20);
		formPanel.add(nationTextField);
		nationTextField.setColumns(10);
		
		liveCheckBox = new JCheckBox("Sigue con vida");
		liveCheckBox.setBounds(7, 82, 126, 23);
		formPanel.add(liveCheckBox);
		
		JScrollPane TextScrollPane = new JScrollPane();
		TextScrollPane.setBounds(20, 132, 350, 90);
		TextScrollPane.setBorder(null);
		contentPane.add(TextScrollPane);
		
		JTextArea akaLabel = new JTextArea("Si quieres asignar un seudónimo al autor, seleccionalo en la lista lateral. Ten en cuenta que un autor solo puede tener un pesuónimo. Si no aparece el pseudónimo, debes crear un autor con el seudónimo por nombre y luego crear este autor y asignar el anterior como pseudónimo");
		akaLabel.setEditable(false);
		akaLabel.setBounds(289, 148, 5, 22);
		akaLabel.setFont(nameLabel.getFont());
		akaLabel.setLineWrap(true);
		akaLabel.setWrapStyleWord(true);
		akaLabel.setBorder(null);
		akaLabel.setBackground(null);
		TextScrollPane.setViewportView(akaLabel);
		
		JScrollPane SeudoScrollPane = new JScrollPane();
		SeudoScrollPane.setBounds(450, 11, 278, 343);
		contentPane.add(SeudoScrollPane);
		
		SeudoList = new JList<>(model);
		SeudoScrollPane.setViewportView(SeudoList);
		SeudoList.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBounds(10, 233, 368, 121);
		contentPane.add(ButtonPanel);
		ButtonPanel.setLayout(null);
		
		JButton CreateButton = new JButton("Actualizar autor");
		CreateButton.addActionListener( e -> {
			boolean year = !(yearTextField.getText().trim().isBlank() || yearTextField.getText().trim().isEmpty() || yearTextField.getText().trim().equals(""));
			boolean month = !(monthTextField.getText().trim().isBlank() || monthTextField.getText().trim().isEmpty() || monthTextField.getText().trim().equals(""));
			boolean day = !(dayTextField.getText().trim().isBlank() || dayTextField.getText().trim().isEmpty() || dayTextField.getText().trim().equals(""));
			
			if (!(year == month && month == day)) {
				Navegador.mostrarMensajeError(VentanaAutoresActualizar.this, "Error", "O todas las partes de la fecha deben tener datos o ninguna");
				return;
			}
			String date = yearTextField.getText().trim() + "-" + monthTextField.getText().trim() + "-" + dayTextField.getText().trim();
			if (year == false) {
				date = null;
			}
			
			Integer idSeudo = null;
			if (SeudoList.getSelectedIndex() != -1) {
				String[] iden = SeudoList.getSelectedValue().split(" - ");
				idSeudo = Integer.parseInt(iden[0]);
			}
			Autor temp = new Autor(id, nameTextField.getText().trim(), date, nationTextField.getText().trim(), liveCheckBox.isSelected(), idSeudo);
			
			if (Database.revisarAutor(temp, VentanaAutoresActualizar.this)) {
				AutorController.actualizar(temp);
				actualizarLista();
				salir();
			}
		});
		
		CreateButton.setBounds(10, 11, 159, 23);
		ButtonPanel.add(CreateButton);
		
		JButton deleteSeudoButton = new JButton("Eliminar seudónimo");
		deleteSeudoButton.addActionListener(e -> {
			actualizarLista();
		});
		deleteSeudoButton.setBounds(10, 45, 159, 23);
		ButtonPanel.add(deleteSeudoButton);
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Navegador.dispatcher("Autores", true);
				Navegador.obtenerVentana("Autores").setLocationRelativeTo(VentanaAutoresActualizar.this);
				((VentanaAutores) Navegador.obtenerVentana("Autores")).actualizarTabla();
			}
		});
	}

	public void actualizarLista() {
		ArrayList<Autor> autores = Autor.actualizarLista();
		model.setSize(0);;
		for (Autor a : autores) {
			model.addElement(a.getId() + " - " + a.getNombre());
		}
	}

	public void salir() {
		this.setVisible(false);
		Navegador.dispatcher("Autores", true);
		Navegador.obtenerVentana("Autores").setLocationRelativeTo(VentanaAutoresActualizar.this);
		((VentanaAutores) Navegador.obtenerVentana("Autores")).actualizarTabla();
	}
	
	public void setDatos(Autor a) {
		id = a.getId();
		nameTextField.setText(a.getNombre());
		String[] date = a.getFechaNacimiento().split("-");
		yearTextField.setText(date[0]);
		monthTextField.setText(date[1]);
		dayTextField.setText(date[2]);
		nationTextField.setText(a.getNacionalidad());
		liveCheckBox.setSelected(a.isVivo());
		SeudoList.setSelectedIndex(a.getSeudonimo()-1);
	}
}
