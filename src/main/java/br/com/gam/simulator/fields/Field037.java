package br.com.gam.simulator.fields;

import org.jpos.iso.ISOUtil;

import java.util.Random;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field037 implements Field {
	
	public String generate() {
		
		return ISOUtil.getRandomDigits(new Random(), 12, 9);
		
	}
}
