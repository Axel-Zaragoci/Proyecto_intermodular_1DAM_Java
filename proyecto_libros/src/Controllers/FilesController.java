package Controllers;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FilesController {
	public static String obtenerRuta(JFrame ventana) {
		File current = new File("C:\\Users\\axelz\\OneDrive\\Escritorio\\DAM\\Proyecto intermodular\\Proyecto 3er trimestre\\proyecto_java\\proyecto_libros\\data");
		JFileChooser chooser = new JFileChooser(current);
		FileNameExtensionFilter filterTXT = new FileNameExtensionFilter("archivos .txt", ".txt");
		chooser.addChoosableFileFilter(filterTXT);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int chooserResult = chooser.showOpenDialog(ventana);
		if (chooserResult == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
}
