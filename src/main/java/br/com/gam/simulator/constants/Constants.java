package br.com.gam.simulator.constants;

import br.com.gam.simulator.entities.SwitchJsonCase;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Guilherme Mendes on 01/11/2018
 */
public enum Constants {
	
	SWITCH_CASE, //
	CARD_SWITCH_CASE, //
	ISO_MESSAGE_CASES, //
	TIMESTAMP, REQUEST, //
	RESPONSE, //
	DESTINATION, //
	HOST_DURATION, //
	APPROVED_TRANSACTION("00"), //
	PREFIX_SAF("saf."), //
	PREFIX_MUX("mux."), //
	
	// JSON
	
	ECHO$AUTH, //
	ECHO$CANC, //
	ECHO$RESP_AUTH, //
	ECHO$RESP_CANC, //
	
	CASE_NAME(SwitchJsonCase.CASE_NAME), //
	CARD(SwitchJsonCase.CARD), //
	AUTHORIZATION(SwitchJsonCase.AUTHORIZATION), //
	CANCELLATION(SwitchJsonCase.CANCELLATION), //
	UNDO_AUTHORIZATION(SwitchJsonCase.UNDO_AUTHORIZATION), //
	UNDO_CANCELLATION(SwitchJsonCase.UNDO_CANCELLATION), //
	CONFIRM_AUTHORIZATION(SwitchJsonCase.CONFIRM_AUTHORIZATION), //
	CONFIRM_CANCELLATION(SwitchJsonCase.CONFIRM_CANCELLATION), //
	PROBE_RESPONSE(SwitchJsonCase.PROBE_RESPONSE), //
	NETWORK_MANAGEMENT(SwitchJsonCase.NETWORK_MANAGEMENT), //
	
	;
	
	private String value;
	
	Constants() {
	}
	
	Constants(final String value) {
		this.value = value;
	}
	
	public static Optional<Constants> findConstant(final Predicate<Constants> predicate) {
		
		return Stream.of(Constants.values()) //
		             .filter(predicate) //
		             .findFirst();
	}
	
	@Override
	public String toString() {
		return Optional.ofNullable(value) //
		               .orElse(name());
	}}
