package br.com.gam.simulator.participant.create_case;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.TransactionManagerConstants;
import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;

import java.util.Map;

import static br.com.gam.simulator.constants.Constants.PROBE_RESPONSE;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */
class Probe extends TestJson {
	
	Probe(final Constants jsonConstants, final Card card, final Map<String, String> jsonMap) {
		super(jsonConstants, card, jsonMap);
		build(1.0, PROBE_RESPONSE, TransactionManagerConstants.AUTHORIZATION); // FIXME: 17/02/2019 falta implementar
	}
	
}
