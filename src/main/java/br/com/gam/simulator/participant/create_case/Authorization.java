package br.com.gam.simulator.participant.create_case;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.TransactionManagerConstants;
import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;

import java.util.Map;

import static br.com.gam.simulator.constants.Constants.AUTHORIZATION;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
class Authorization extends TestJson {
	
	Authorization(final Constants jsonConstants, final Card card, final Map<String, String> jsonMap) {
		super(jsonConstants, card, jsonMap);
		build(1.0, AUTHORIZATION, TransactionManagerConstants.AUTHORIZATION);
	}
	
}
