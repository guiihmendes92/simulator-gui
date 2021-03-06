package br.com.gam.simulator.fields;

import org.jpos.iso.ISODate;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field007UTC implements Field {
	
	/**
	 * Metodo responsável por devolver data e hora GMT, bit 07 ISO8583.
	 *
	 * @return "MMddHHmmss"
	 */
	public String generate() {
		return ISODate.formatDate(new Date(), "MMddHHmmss", TimeZone.getTimeZone("GMT"));
	}
	
}
