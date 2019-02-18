package br.com.gam.simulator.fields;

import org.jpos.iso.ISODate;

import java.util.Date;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field013 implements Field {
	
	/**
	 * Metodo responsável por devolver data local, bit 13 ISO8583.
	 *
	 * @return "MMdd"
	 */
	public String generate() {
		return ISODate.getDate(new Date());
	}
	
}
