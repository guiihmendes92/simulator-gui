package br.com.gam.simulator.fields;

import br.com.gam.simulator.entities.Card;
import lombok.Setter;

import java.util.Optional;

/**
 * Created by Guilherme Mendes on 29/10/2018
 */
public class Field002 implements Field {
	
	private Card card;
	
	public String generate() {
		return Optional.ofNullable(card) //
		               .map(Card::getPan) //
		               .orElse(null);
	}
	
	@Override
	public Field setCard(final Card card) {
		this.card = card;
		return this;
	}
}
