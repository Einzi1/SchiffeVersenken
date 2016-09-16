package de.verbund.sv.netz.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.verbund.sv.netz.Befehl;
import de.verbund.sv.netz.Empfaenger;
import de.verbund.sv.netz.SocketClientServiceImpl;

public class ClientGUITester {

	private JFrame frame;
	private JLabel lIP;
	private JLabel lPort;
	private JButton buttonVerbinden;
	private JTextArea taKonsole;

	private SocketClientServiceImpl client;
	private JButton buttonActionSenden;

	private JTextField taBefehl;

	public static void main(String[] args) {
		new ClientGUITester();
	}

	public ClientGUITester() {
		initKomponenten();
	}

	private void initKomponenten() {
		frame = new JFrame("ClientGUI");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(400, 300);
		frame.setLayout(new GridBagLayout());

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		lIP = new JLabel("IP: localhost");
		lPort = new JLabel("Port: 4448");
		buttonVerbinden = new JButton("Verbinden");
		buttonVerbinden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verbinden();
			}
		});

		panel1.add(lIP);
		panel1.add(lPort);
		panel1.add(buttonVerbinden);

		taBefehl = new JTextField(8);
		buttonActionSenden = new JButton("Action senden");

		buttonActionSenden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				befehlSenden();
			}
		});

		panel2.add(taBefehl);
		panel2.add(buttonActionSenden);

		taKonsole = new JTextArea("     ");
		panel3.add(taKonsole);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(panel1, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(panel2, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(panel3, gbc);

		frame.setVisible(true);
	}

	protected void befehlSenden() {
		String anweisung = taBefehl.getText();
		Befehl befehl = new Befehl();
		befehl.setBefehl(anweisung);
		try {
			client.sende(befehl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void verbinden() {
		taKonsole.append("Verbinde mit Server...\n");
		client = SocketClientServiceImpl.getInstance();
		try {
			client.starten("192.168.36.73", 4448);
			// client.starten("localhost", 4448);

			client.addEmpfeanger(new Empfaenger() {
				@Override
				public void empfange(Befehl befehl) {
					befehlAusgeben(befehl);
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void befehlAusgeben(Befehl befehl) {
		taKonsole.setText(befehl.getBefehl());
		System.out.println("befehl angekommen");
	}
}
