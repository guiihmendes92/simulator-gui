package br.com.gam.simulator.fields;

import org.jpos.iso.ISOUtil;

import java.time.LocalTime;
import java.util.Random;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field004 implements Field {
	
	public String generate() {
		
		final int hour   = LocalTime.now().getHour();
		final int random = new Random().nextInt(100);
		
		final int amount = hour + random;
		
		return ISOUtil.zeropad(amount, 12);
		
	}
}
