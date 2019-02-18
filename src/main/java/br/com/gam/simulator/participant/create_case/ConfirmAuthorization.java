package br.com.gam.simulator.participant.create_case;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.TransactionManagerConstants;
import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;

import java.util.Map;

import static br.com.gam.simulator.constants.Constants.CONFIRM_AUTHORIZATION;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
class ConfirmAuthorization extends TestJson {
	
	ConfirmAuthorization(final Constants jsonConstants, final Card card, final Map<String, String> jsonMap) {
		super(jsonConstants, card, jsonMap);
		build(1.1, CONFIRM_AUTHORIZATION, TransactionManagerConstants.NOTIFICATION);
	}
	
}
