package de.verbund.sv.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.StringWriter;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Taner

public class RegelwerkGUI {

	private JDialog dialog;
	private Component parent;

	public RegelwerkGUI() {
		this(true);
	}

	public RegelwerkGUI(boolean modal) {
		initialize();
		dialog.setModal(modal);
		dialog.setVisible(true);
	}

	public RegelwerkGUI(Component parent, boolean modal) {
		this(modal);
		this.parent = parent;
	}

	private void initialize() {
		dialog = new JDialog();
		dialog.setResizable(false);
		dialog.setBounds(100, 100, 450, 300);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));
		dialog.setLocationRelativeTo(parent);
		dialog.setTitle("Regeln");

		/*
		 * JTextPane txtpnRegeln = new JTextPane();
		 * txtpnRegeln.setEditable(false); txtpnRegeln.setText("Regeln");
		 * dialog.getContentPane().add(txtpnRegeln, BorderLayout.NORTH);
		 */

		JLabel jlRegelwerk = new JLabel();
		jlRegelwerk.setText(getText());
		dialog.getContentPane().add(jlRegelwerk, BorderLayout.NORTH);

		ImageIcon schiff = new ImageIcon("Bilder/einzelschiff_32px_neu.png");
		JLabel jlSchiff = new JLabel("");
		jlSchiff.setIcon(schiff);

		JPanel anzSchiffe = new JPanel();
		anzSchiffe.add(jlSchiff, BorderLayout.CENTER);
		dialog.getContentPane().add(anzSchiffe, BorderLayout.CENTER);

	}
	
	public String getText() {
		StringBuilder text = new StringBuilder();

		text.append("<html><h1 align=\"center\">Regeln</h1>");
		text.append("Jeder hat seine Schiffe in seinem Feld. ");
		text.append("<p>Du kannst die Schiffe des Gegners nicht sehen, und er deine auch nicht.");
		text.append("Mit einem Mausklick tickt man dann auf das gegnerische Feld.");
		text.append("Gibt es einen Treffer, dann wird der angezeigt, wenn nicht, gibt es nur ein Loch im Wasser.");
		text.append("Gewonnen hat der, der alle gegnerischen Schiffe versenkt hat.</p><br/>");
		text.append("Es gibt folgende Schiffe: </html>");
		
		return text.toString();
	} 
}