package de.verbund.sv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.verbund.sv.SchussSchonAbgegebenException;
import de.verbund.sv.SpielbrettManager;
import de.verbund.sv.SpielfeldManagerImplementation;
import de.verbund.sv.guitools.SVButton;
import de.verbund.sv.tools.Koordinaten;

//Taner und Simon

public class SpielGUI {

	private static final boolean EIGENES = true;
	private static final boolean GEGNER = false;
	private static final int SCHONGETROFFEN = 0;
	private static final int SCHIFFTREFFER = 1;
	private static final int WASSERTREFFER = 2;
	private static final int GEWONNEN = 3;

	private JDialog frame;

	private SpielbrettManager spielfeldGegner;
	private SpielbrettManager spielfeldEigenes;

	private JPanel spielfeldPanelEigen;
	private JPanel spielfeldPanelGegner;
	private JTextArea textarea;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// SpielGUI window = new SpielGUI();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */

	public SpielGUI() {
		initialize();

		frame.setVisible(true);
	}

	public SpielGUI(SpielbrettManager spielfeld1, SpielbrettManager spielfeld2) {
		this.spielfeldGegner = spielfeld1;
		this.spielfeldEigenes = spielfeld2;
		initialize();
		frame.setModal(true);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// aendereLookAndFeel();
		// try {
		// hintergrund = ImageIO.read(new
		// File("Bilder/Battleship_wallpaper.png"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		frame = new JDialog() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				// g.drawImage(hintergrund, 0, 0, this);
			}
		};
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 333, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Gegnerisches Gewässer");
		lblNewLabel.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(50, 0, 25, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblEignesGewsser = new JLabel("Eigenes Gewässer");
		lblEignesGewsser.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
		GridBagConstraints gbc_lblEignesGewsser = new GridBagConstraints();
		gbc_lblEignesGewsser.insets = new Insets(50, 0, 25, 0);
		gbc_lblEignesGewsser.gridx = 1;
		gbc_lblEignesGewsser.gridy = 0;
		panel.add(lblEignesGewsser, gbc_lblEignesGewsser);

		JPanel panel_1 = initGegnerSpielfeld();

		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);

		JPanel panel_2 = initEigenesSpielfeld();

		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);

		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.NORTH);

		JLabel label = new JLabel("BATTLESHIP DESTROYER ");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Andalus", Font.BOLD, 40));
		panel_3.add(label);

		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setRows(6);
		JScrollPane scrollPane = new JScrollPane(textarea);
		frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

		frame.setBounds(100, 100, 1100, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnEinstellungen = new JMenu("Einstellungen");
		menuBar.add(mnEinstellungen);

		JMenuItem mntmHauptmen = new JMenuItem("Hauptmenü");
		mntmHauptmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oeffneMenue();
			}
		});
		mnEinstellungen.add(mntmHauptmen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mnEinstellungen.add(mntmSpeichern);

		JMenuItem mntmLaden = new JMenuItem("Laden");
		mnEinstellungen.add(mntmLaden);

		JMenuItem mntmRegelwerk = new JMenuItem("Regelwerk");
		mntmRegelwerk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegelwerkGUI(frame, false);
			}
		});
		mnEinstellungen.add(mntmRegelwerk);

		JMenuItem mntmVerbindung = new JMenuItem("Verbindung");
		mnEinstellungen.add(mntmVerbindung);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mnEinstellungen.add(mntmBeenden);
	}

	protected void oeffneMenue() {
		int option = JOptionPane.showConfirmDialog(frame, "Wirklich beenden?", "Achtung", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			frame.dispose();
		}
	}

	private JPanel initEigenesSpielfeld() {
		if (spielfeldEigenes == null) {
			this.spielfeldEigenes = new SpielfeldManagerImplementation(-1);
		}
		spielfeldPanelEigen = new JPanel();
		spielfeldPanelEigen.setLayout(new GridBagLayout());
		initButtons(EIGENES);

		return spielfeldPanelEigen;
	}

	private JPanel initGegnerSpielfeld() {
		// this.spielfeldGegner = new SpielfeldManagerImplementation(0);
		spielfeldPanelGegner = new JPanel();
		spielfeldPanelGegner.setLayout(new GridBagLayout());

		initButtons(GEGNER);

		return spielfeldPanelGegner;
	}

	private void initButtons(boolean spieler) {

		for (int x = 0; x < this.spielfeldGegner.getGroesse(); x++) {
			for (int y = 0; y < this.spielfeldGegner.getGroesse(); y++) {
				Koordinaten koordinaten = new Koordinaten(x, y);
				int status = 0;
				if (spieler == EIGENES) {
					status = this.spielfeldEigenes.getStatus(koordinaten);
				}

				final SVButton button = new SVButton(status);

				final GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;

				if (spieler == GEGNER) {

					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							schussAuf(gbc.gridx, gbc.gridy, button);
						}
					});
					spielfeldPanelGegner.add(button, gbc);
				} else {
					spielfeldPanelEigen.add(button, gbc);
				}
			}
		}
	}

	protected void schussAuf(int x, int y, SVButton button) {
		if (!(spielfeldGegner.hatVerloren())) {
			try {
				int status = spielfeldGegner.schussAuf(new Koordinaten(x, y));

				switch (status) {

				case SpielbrettManager.WASSER_SCHUSS:
					button.setIcon(new ImageIcon("Bilder/explosion_wasser_32px.png"));
					schreibeText(WASSERTREFFER);
					break;
				case SpielbrettManager.SCHIFF_SCHUSS:
					button.setIcon(new ImageIcon("Bilder/explosion_schiff_32px_mitSchiff.png"));
					schreibeText(SCHIFFTREFFER);
					break;
				default:
					break;
				}
			} catch (SchussSchonAbgegebenException e) {
				schreibeText(SCHONGETROFFEN);
			}
			if (spielfeldGegner.hatVerloren()) {
				schreibeText(GEWONNEN);
				JOptionPane.showMessageDialog(null, "Du hast gewonnen", "Sieg", JOptionPane.OK_OPTION,
						new ImageIcon("Bilder/medaille_64px.png"));
			}
		}
	}

	private void schreibeText(int ereignis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date uhrzeit = new Date();
		String uhrzeitString = sdf.format(uhrzeit);
		switch (ereignis) {
		case SCHONGETROFFEN:
			textarea.append("[" + uhrzeitString + "] " + "Dieses Feld wurde schon abgeschossen!\n");
			break;
		case SCHIFFTREFFER:
			textarea.append("[" + uhrzeitString + "] " + "BAAAM! SCHIFFSTREFFER!\n");
			break;
		case WASSERTREFFER:
			textarea.append("[" + uhrzeitString + "] " + "Knapp daneben!\n");
			break;
		case GEWONNEN:
			textarea.append("[" + uhrzeitString + "] " + "Du hast gewonnen!\n");
			break;
		default:
			break;
		}
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