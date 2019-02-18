package br.com.gam.simulator.fields;

import org.jpos.iso.ISODate;

import java.util.Date;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field012 implements Field {
	
	/**
	 * Metodo responsável por devolver hora local, bit 12 ISO8583.
	 *
	 * @return "HHmmss"
	 */
	public String generate() {
		return ISODate.getTime(new Date());
	}
	
}
