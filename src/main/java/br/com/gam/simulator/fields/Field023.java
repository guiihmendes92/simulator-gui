package br.com.gam.simulator.fields;

import br.com.gam.simulator.entities.Card;

import java.util.Optional;

/**
 * Created by Guilherme Mendes on 31/10/2018
 */
public class Field023 implements Field {
	
	private Card card;
	
	public String generate() {
		
		setCard(card);
		return Optional.ofNullable(card) //
		               .map(Card::getPsn) //
		               .orElse(null);
	}
	
	@Override
	public Field setCard(final Card card) {
		this.card = card;
		return this;
	}
}
