package de.verbund.sv.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import de.verbund.sv.SpielbrettManager;
import de.verbund.sv.SpielfeldManagerImplementation;
import de.verbund.sv.guitools.RessourceSingleton;
import de.verbund.sv.guitools.SVButton;
import de.verbund.sv.guitools.Timer;
import de.verbund.sv.tools.Koordinaten;

import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

// Simon und Taner

public class NeuesSpielGUI {

	private JDialog dialog;
	private SpielbrettManager spielfeldEigenes;
	private SpielbrettManager spielfeldGegner;

	private JPanel spielfeldPanel;
	private JRadioButton rdbtnSP;
	private JRadioButton rdbtnMP;
	private JTextField txtServerPort;
	private JTextField txtServerIP;
	private JPanel panel_5;
	private JLabel lblServerIP;
	private JLabel lblServerPort;
	private SVButton[][] buttonArray = new SVButton[10][10];
	private Koordinaten[] setzeSchiffKoordinaten;
	private int zaehler = 0;
	private BufferedImage imgCursor;

	public NeuesSpielGUI() {
		imgCursor = RessourceSingleton.getInstance().getImgCursor();

		initialize();

		dialog.setVisible(true);
	}

	private void initialize() {
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setBounds(100, 100, 600, 600);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		dialog.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblNeuesSpiel = new JLabel("Neues  Spiel");
		lblNeuesSpiel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblNeuesSpiel);

		JPanel panel_1 = new JPanel();
		dialog.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);

		ButtonGroup radiobuttons = new ButtonGroup();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rdbtnSP = new JRadioButton("SinglePlayer");
		rdbtnSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zeigeVerbindung(false);
			}
		});
		rdbtnSP.setSelected(true);
		radiobuttons.add(rdbtnSP);
		panel_2.add(rdbtnSP);

		rdbtnMP = new JRadioButton("Multiplayer");
		rdbtnMP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zeigeVerbindung(true);
			}
		});
		radiobuttons.add(rdbtnMP);
		panel_2.add(rdbtnMP);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);

		JPanel panel_6 = new JPanel();

		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 223, 137, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		JButton btnRndSetzeSchiff = new JButton("Schiffe zufällig setzen");
		btnRndSetzeSchiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel tempPanel = initSpielfeldRandom();
				panel_6.remove(0);
				panel_6.add(tempPanel, gbc_panel_6);
				panel_6.validate();
			}
		});
		GridBagConstraints gbc_btnRndSetzeSchiff = new GridBagConstraints();
		gbc_btnRndSetzeSchiff.insets = new Insets(5, 0, 20, 5);
		gbc_btnRndSetzeSchiff.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRndSetzeSchiff.gridx = 1;
		gbc_btnRndSetzeSchiff.gridy = 0;
		panel_5.add(btnRndSetzeSchiff, gbc_btnRndSetzeSchiff);

		lblServerIP = new JLabel("Server-IP:");
		GridBagConstraints gbc_lblServerip = new GridBagConstraints();
		gbc_lblServerip.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerip.anchor = GridBagConstraints.EAST;
		gbc_lblServerip.gridx = 0;
		gbc_lblServerip.gridy = 1;
		lblServerIP.setVisible(false);
		panel_5.add(lblServerIP, gbc_lblServerip);

		txtServerIP = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 15, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		txtServerIP.setVisible(false);
		panel_5.add(txtServerIP, gbc_textField);
		txtServerIP.setColumns(16);

		lblServerPort = new JLabel("Server-Port:");
		GridBagConstraints gbc_lblServerport = new GridBagConstraints();
		gbc_lblServerport.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerport.anchor = GridBagConstraints.EAST;
		gbc_lblServerport.gridx = 0;
		gbc_lblServerport.gridy = 2;
		lblServerPort.setVisible(false);
		panel_5.add(lblServerPort, gbc_lblServerport);

		txtServerPort = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 15, 0, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		txtServerPort.setVisible(false);
		panel_5.add(txtServerPort, gbc_textField_1);
		txtServerPort.setColumns(16);

		panel_6.add(initButtons());

		panel_4.add(panel_6, gbc_panel_6);

		JPanel panel_3 = new JPanel();
		dialog.getContentPane().add(panel_3, BorderLayout.SOUTH);

		JButton btnSpielBeginnen = new JButton("Spiel beginnen");
		btnSpielBeginnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spielBeginnen();
			}
		});
		panel_3.add(btnSpielBeginnen);
	}

	protected void zeigeVerbindung(boolean visible) {
		lblServerIP.setVisible(visible);
		txtServerIP.setVisible(visible);
		lblServerPort.setVisible(visible);
		txtServerPort.setVisible(visible);
	}

	protected void spielBeginnen() {
		if (rdbtnSP.isSelected()) {
			spielfeldGegner = new SpielfeldManagerImplementation(true);
			new SpielGUI(spielfeldGegner, spielfeldEigenes);
		} else {
			try {
				String ip = txtServerIP.getText();
				int port = Integer.parseInt(txtServerPort.getText());
				// TODO Behandlung der Numberformatexception, Label rot,
				// JOPtionPane
				// TODO empfangen des gegnerspielfelds und verbindungsaufbau
				try {
					spielfeldGegner = new SpielfeldManagerImplementation();
					spielfeldEigenes.verbinden(true, ip, port);
					spielfeldGegner.verbinden(false, ip, port);
				} catch (Exception e) {
					System.out.println("Verbindung hat Exception geworfen!");
					e.printStackTrace();
				}

				new SpielGUI(spielfeldGegner, spielfeldEigenes);
			} catch (NumberFormatException e) {

			}
		}
		dialog.dispose();
	}

	private JPanel initSpielfeldRandom() {
		spielfeldEigenes = new SpielfeldManagerImplementation();
		spielfeldEigenes.fuelleSpielfeldRandom();

		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridBagLayout());

		for (int x = 0; x < this.spielfeldEigenes.getGroesse(); x++) {
			for (int y = 0; y < this.spielfeldEigenes.getGroesse(); y++) {
				Koordinaten koordinaten = new Koordinaten(x, y);
				int status = spielfeldEigenes.getStatus(new Koordinaten(x, y));

				buttonArray[x][y] = new SVButton(status);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;

				buttonArray[x][y].setCursor(null);
				tempPanel.add(buttonArray[x][y], gbc);
			}
		}
		return tempPanel;
	}

	private JPanel initButtons() {
		spielfeldEigenes = new SpielfeldManagerImplementation();
		spielfeldPanel = new JPanel();
		spielfeldPanel.setLayout(new GridBagLayout());
		setzeSchiffKoordinaten = new Koordinaten[2];

		for (int x = 0; x < this.spielfeldEigenes.getGroesse(); x++) {
			for (int y = 0; y < this.spielfeldEigenes.getGroesse(); y++) {
				Koordinaten koordinaten = new Koordinaten(x, y);
				int status = 0;

				buttonArray[x][y] = new SVButton(status);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;
				Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(imgCursor,
						new Point(imgCursor.getHeight() / 2, imgCursor.getWidth() / 2), "Schiff");

				buttonArray[x][y].setCursor(cursor);

				buttonArray[x][y].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setzeSchiffAuf(koordinaten);
					}
				});
				spielfeldPanel.add(buttonArray[x][y], gbc);
			}
		}
		return spielfeldPanel;
	}

	protected void setzeSchiffAuf(Koordinaten koordinaten) {
		if (zaehler == 0) {
			setzeSchiffKoordinaten[0] = koordinaten;
			buttonArray[koordinaten.getX()][koordinaten.getY()]
					.setIcon(new ImageIcon("Bilder/einzelschiff_wasser_32px.png"));
			zaehler++;
		} else {
			setzeSchiffKoordinaten[1] = koordinaten;
			spielfeldEigenes.schiffStartEndKoordinaten(setzeSchiffKoordinaten[0], setzeSchiffKoordinaten[1]);

			for (int x = 0; x < spielfeldEigenes.getGroesse(); x++) {
				for (int y = 0; y < spielfeldEigenes.getGroesse(); y++) {
					int status = spielfeldEigenes.getStatus(new Koordinaten(x, y));
					if (status == 2) {
						buttonArray[x][y].setIcon(new ImageIcon("Bilder/einzelschiff_wasser_32px.png"));
					} else {
						buttonArray[x][y].setIcon(new ImageIcon("Bilder/Meer_64px.png"));
					}
				}
			}
			zaehler = 0;
		}
	}
}