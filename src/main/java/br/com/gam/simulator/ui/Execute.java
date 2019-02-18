package br.com.gam.simulator.ui;

import org.jpos.q2.Q2;

/**
 * Created by Guilherme Mendes on 31/10/2018
 */
public class Execute {
	
	public static void main(String[] args) {
		
		final String[] commandLineParser = { "-d", "src/disc", "-r"};
		
		final Q2 q2 = new Q2(commandLineParser);
		
		q2.start();
		
	}
}
