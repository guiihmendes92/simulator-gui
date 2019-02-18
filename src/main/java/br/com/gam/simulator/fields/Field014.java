package br.com.gam.simulator.fields;

import br.com.gam.simulator.entities.Card;

import java.util.Optional;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field014 implements Field {
	
	private Card card;
	
	@Override
	public String generate() {
		return Optional.ofNullable(card) //
		               .map(Card::getExpirationDate) //
		               .orElse(null);
	}
	
	@Override
	public Field setCard(final Card card) {
		this.card = card;
		return this;
	}
	
}
