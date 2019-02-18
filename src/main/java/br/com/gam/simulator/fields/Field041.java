package br.com.gam.simulator.fields;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field041 implements Field {
	
	/**
	 * obtem as tres primeiras letras do usuario e adiciona o ano corrente.
	 *
	 * @return numero do terminal com 8 posicoes
	 */
	public String generate() {
		
		final String gam = "GAM";
		
		return Optional.of(System.getProperty("user.name", gam)) //
		               .filter(userName -> userName.length() >= 3) //
		               .map(userName -> userName.substring(0, 3)) //
		               .map(String::toUpperCase) //
		               .map(name -> name + getYear()) //
		               .orElse(gam + getYear());
	}
	
	private String getYear() {
		return "-" + LocalDate.now().getYear();
	}
	
}
