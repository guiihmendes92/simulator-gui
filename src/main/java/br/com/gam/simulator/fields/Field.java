package br.com.gam.simulator.fields;

import br.com.gam.simulator.entities.Card;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public interface Field {
	
	String generate();
	
	default Field setCard(final Card card) {
		return this;
	}
}
