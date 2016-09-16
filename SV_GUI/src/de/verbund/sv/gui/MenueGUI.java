package de.verbund.sv.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import de.verbund.sv.gui.RegelwerkGUI;
import de.verbund.sv.guitools.RessourceSingleton;
import de.verbund.sv.gui.NeuesSpielGUI;

// Taner

public class MenueGUI {

	private JFrame frmMen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenueGUI window = new MenueGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenueGUI() {
		initialize();
		frmMen.setVisible(true);
		// läd dann schon einmal im Hintergrund die Bilder.
		RessourceSingleton.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// aendereLookAndFeel();
		frmMen = new JFrame();
		frmMen.setTitle("Men\u00FC");
		frmMen.setBounds(100, 100, 600, 600);
		frmMen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMen.setLocationRelativeTo(null);
		frmMen.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmMen.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblHeader = new JLabel("BATTLESHIP DESTROYER ");
		lblHeader.setForeground(Color.BLUE);
		lblHeader.setFont(new Font("Andalus", Font.BOLD, 24));
		panel.add(lblHeader);

		JPanel panel_2 = new JPanel();
		frmMen.getContentPane().add(panel_2, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JButton btnNeuesSpiel = new JButton("Neues Spiel");
		btnNeuesSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							neuesSpiel();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		GridBagConstraints gbc_btnNeuesSpiel = new GridBagConstraints();
		gbc_btnNeuesSpiel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNeuesSpiel.gridwidth = 3;
		gbc_btnNeuesSpiel.insets = new Insets(0, 0, 5, 5);
		gbc_btnNeuesSpiel.gridx = 0;
		gbc_btnNeuesSpiel.gridy = 0;
		panel_1.add(btnNeuesSpiel, gbc_btnNeuesSpiel);

		JButton btnSpielLaden = new JButton("Spiel laden");
		GridBagConstraints gbc_btnSpielLaden = new GridBagConstraints();
		gbc_btnSpielLaden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSpielLaden.gridwidth = 3;
		gbc_btnSpielLaden.insets = new Insets(0, 0, 5, 5);
		gbc_btnSpielLaden.gridx = 0;
		gbc_btnSpielLaden.gridy = 1;
		panel_1.add(btnSpielLaden, gbc_btnSpielLaden);

		JButton btnHighscores = new JButton("Highscores");
		GridBagConstraints gbc_btnHighscores = new GridBagConstraints();
		gbc_btnHighscores.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHighscores.gridwidth = 3;
		gbc_btnHighscores.insets = new Insets(0, 0, 5, 5);
		gbc_btnHighscores.gridx = 0;
		gbc_btnHighscores.gridy = 2;
		panel_1.add(btnHighscores, gbc_btnHighscores);

		JButton btnRegelwerk = new JButton("Regelwerk");
		btnRegelwerk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regelwerk();
			}
		});
		GridBagConstraints gbc_btnRegelwerk = new GridBagConstraints();
		gbc_btnRegelwerk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegelwerk.gridwidth = 3;
		gbc_btnRegelwerk.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegelwerk.gridx = 0;
		gbc_btnRegelwerk.gridy = 3;
		panel_1.add(btnRegelwerk, gbc_btnRegelwerk);

		JButton btnBeenden = new JButton("Spiel Beenden");
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		GridBagConstraints gbc_btnBeenden = new GridBagConstraints();
		gbc_btnBeenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBeenden.gridwidth = 3;
		gbc_btnBeenden.insets = new Insets(0, 0, 5, 5);
		gbc_btnBeenden.gridx = 0;
		gbc_btnBeenden.gridy = 4;
		panel_1.add(btnBeenden, gbc_btnBeenden);
	}

	protected void neuesSpiel() {
		new NeuesSpielGUI();
	}

	protected void regelwerk() {
		new RegelwerkGUI();
	}

	protected void aendereLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}