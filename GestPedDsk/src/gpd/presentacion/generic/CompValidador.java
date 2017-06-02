package gpd.presentacion.generic;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CompValidador {

	public void addBorder(Component comp) {
		Border line = BorderFactory.createLineBorder(Color.RED, 1);
		String nombreClase = comp.getClass().getName();
		if (nombreClase.equals("javax.swing.JTextField")) {
			((javax.swing.JTextField) comp).setBorder(line);
			((javax.swing.JTextField) comp).addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					removeBorder(comp);
				}
			});
		} else if (nombreClase.equals("javax.swing.JFormattedTextField")) {
			((javax.swing.JFormattedTextField) comp).setBorder(line);
			((javax.swing.JFormattedTextField) comp).addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					removeBorder(comp);
				}
			});
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			((javax.swing.JComboBox<?>) comp).setBorder(line);
			((javax.swing.JComboBox<?>) comp).addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					removeBorder(comp);
				}
			});
		} else if (nombreClase.equals("javax.swing.JTextArea")) {
			((javax.swing.JTextArea) comp).setBorder(line);
			((javax.swing.JTextArea) comp).addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					removeBorder(comp);
				}
			});
		} else if (nombreClase.equals("javax.swing.JList")) {
			((javax.swing.JList<?>) comp).setBorder(line);
			((javax.swing.JList<?>) comp).addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					removeBorder(comp);
				}
			});
		}
	}
	
	public void removeBorder(Component comp) {
		Border empty = BorderFactory.createLineBorder(Color.lightGray, 1);
		String nombreClase = comp.getClass().getName();
		if (nombreClase.equals("javax.swing.JTextField")) {
			((javax.swing.JTextField) comp).setBorder(empty);
		} else if (nombreClase.equals("javax.swing.JFormattedTextField")) {
			((javax.swing.JFormattedTextField) comp).setBorder(empty);
		} else if (nombreClase.equals("javax.swing.JComboBox")) {
			((javax.swing.JComboBox<?>) comp).setBorder(empty);
		} else if (nombreClase.equals("javax.swing.JTextArea")) {
			((javax.swing.JTextArea) comp).setBorder(empty);
		} else if (nombreClase.equals("javax.swing.JList")) {
			((javax.swing.JList<?>) comp).setBorder(empty);
		}
	}
	
	
	
	
	
}
