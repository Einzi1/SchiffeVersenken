package de.verbund.sv.netz;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ServerGUI {

	private JFrame frame;
	private JButton startbutton;
	private JButton restartbutton;
	private JButton closebutton;
	private JTextArea textArea;

	public static void main(String[] args) {
		new ServerGUI();
	}

	public ServerGUI() {

		initKomponenten();
	}

	private void initKomponenten() {
		frame = new JFrame("Battleship Destroyer Server");

		JPanel panel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(400, 300);
		frame.setLayout(new GridLayout(2, 1));

		startbutton = new JButton("Start");

		startbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				serverStarten();
			}
		});

		restartbutton = new JButton("Neustarten");
		closebutton = new JButton("Beenden");
		textArea = new JTextArea();

		// JScrollPane scroll = new JScrollPane(textArea,
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		// JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.add(startbutton);
		panel.add(restartbutton);
		panel.add(closebutton);
		frame.add(panel);
		frame.add(textArea);
		frame.setVisible(true);
	}

	protected void serverStarten() {
		textArea.setText("Server starten! \n");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Server();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		textArea.append("Warten auf Clients... \n");
	}

}
