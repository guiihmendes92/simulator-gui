package br.com.gam.simulator.ui;

import org.jpos.q2.Q2;

import javax.swing.*;

public class Test {
	
	public static void main(final String[] args) {
		
		final String[] commandLineParser = { "-d", "disc", "-r" };
		
		final Q2 q2 = new Q2(commandLineParser);
		
		q2.start();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			//			JFrame console   = new Console();
			final JFrame frame     = new JFrame("Simulator-GUI-PLUS");
			final Main   main      = new Main();
			final JPanel principal = main.getMainPanel();
			
			frame.setContentPane(principal);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame.setResizable(false);
			//			frame.setLocationRelativeTo(console);
			frame.pack();
			frame.setVisible(true);
			
		} catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}
