package br.com.gam.simulator.fields;

import org.jpos.iso.ISOUtil;

import java.util.Random;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field011 implements Field {
	
	/**
	 * Metodo responsável por criar um número aleatorio de 6 posições
	 */
	public String generate() {
		return ISOUtil.getRandomDigits(new Random(), 6, 9);
	}
	
}
