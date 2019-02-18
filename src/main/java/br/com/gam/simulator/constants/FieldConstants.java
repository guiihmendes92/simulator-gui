package br.com.gam.simulator.constants;

import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.fields.*;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Guilherme Mendes on 31/10/2018
 */
public enum FieldConstants {
	
	PAN(new Field002()), //
	AMOUNT(new Field004()), //
	LOCAL_DATE_TIME(new Field007()), //
	UTC_DATE_TIME(new Field007UTC()), //
	NSU(new Field011()), //
	LOCAL_TIME(new Field012()), //
	LOCAL_DATE(new Field013()), //
	EXPIRATION_DATE(new Field014()), //
	PSN(new Field023()), //
	TRACK1(new Field035()), //
	RRN(new Field037()), //
	TERMINAL(new Field041()), //
	TRACK2(new Field045()), //
	REAL_CURRENCY(new Field049()), //
	DOLLAR_CURRENCY(new Field050()), //
	;
	
	@Getter
	private Field field;
	
	FieldConstants(final Field field) {
		this.field = field;
	}
	
	public static Optional<Field> mountField(final String key, final String value, final Card card) {
		
		return Optional.of(key.matches("\\d+")) //
		               .filter(Boolean::booleanValue) //
		               .flatMap(isBit -> Stream.of(values()) //
		                                       .filter(fieldConstants -> fieldConstants.name().equals(value)) //
		                                       .findFirst() //
		                                       .map(FieldConstants::getField)//
		                                       .map(field -> field.setCard(card)));
	}}
