package br.com.gam.simulator.participant.create_case;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.TestJson;
import br.com.gam.simulator.entities.SwitchJsonCase;
import org.jpos.transaction.Context;
import org.jpos.transaction.TxnSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static br.com.gam.simulator.constants.Constants.*;
import static java.util.Objects.nonNull;

/**
 * Created by Guilherme Mendes on 02/11/2018
 */
public class CreateCase extends TxnSupport {
	
	@Override
	protected int doPrepare(final long id, final Context ctx) throws Exception {
		
		final SwitchJsonCase switchJsonCase = ctx.get(Constants.SWITCH_CASE);
		
		final Card card = getCard(switchJsonCase, ctx.get(Constants.CARD_SWITCH_CASE));
		
		ctx.put(ISO_MESSAGE_CASES, new SwitchJsonCase().jsonMap() //
		                                               .entrySet() //
		                                               .parallelStream() //
		                                               .filter(entry -> nonNull(entry.getValue())) //
		                                               .map(entry -> getIsoMessageCase(entry.getKey(), entry.getValue(), card))); //
		
		return PREPARED | NO_JOIN;
	}
	
	private Card getCard(final SwitchJsonCase switchJsonCase, final Card card) {
		return Optional.ofNullable(switchJsonCase) //
		               .map(SwitchJsonCase::getCard) //
		               .orElse(card);
	}
	
	private Map<Constants, TestJson> getIsoMessageCase(final Constants constants, final Map<String, String> jsonCase, final Card card) {
		
		final HashMap<Constants, TestJson> isoMessageCases = new HashMap<>();
		
		isoMessageCases.put(CANCELLATION, new Cancellation(constants, card, jsonCase));
		isoMessageCases.put(AUTHORIZATION, new Authorization(constants, card, jsonCase));
		isoMessageCases.put(PROBE_RESPONSE, new Probe(constants, card, jsonCase));
		isoMessageCases.put(UNDO_CANCELLATION, new UndoCancellation(constants, card, jsonCase));
		isoMessageCases.put(NETWORK_MANAGEMENT, new NetworkManagement(constants, card, jsonCase));
		isoMessageCases.put(UNDO_AUTHORIZATION, new UndoAuthorization(constants, card, jsonCase));
		isoMessageCases.put(CONFIRM_CANCELLATION, new ConfirmCancellation(constants, card, jsonCase));
		isoMessageCases.put(CONFIRM_AUTHORIZATION, new ConfirmAuthorization(constants, card, jsonCase));
		
		return isoMessageCases;
	}
}
