package br.com.gam.simulator.entities;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.TransactionManagerConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jpos.iso.ISOMsg;

import java.util.Map;
import java.util.Optional;

import static br.com.gam.simulator.entities.SwitchJsonCase.EXECUTION_ORDER;

/**
 * Created by Guilherme Mendes on 02/11/2018
 */

@Getter
@Setter
@Builder
public abstract class TestJson {
	
	protected TransactionManagerConstants transactionManagerConstants;
	protected Constants                   jsonConstants;
	protected ISOMsg                      isoMessageRequest;
	protected ISOMsg                      isoMessageResponse;
	protected Card                        card;
	protected Map<String, String>         jsonMap;
	protected boolean                     executed;
	protected Double                      executionOrder;
	
	public TestJson(final Constants jsonConstants, final Card card, final Map<String, String> jsonMap) {
		this.jsonConstants = jsonConstants;
		this.card = card;
		this.jsonMap = jsonMap;
	}
	
	public static Optional<TestJson> findIsoMessageCase(final Map<Constants, TestJson> aTestJsons, final Constants jsonConstants) {
		return aTestJsons.parallelStream() //
		                 .filter(isoMessageCase -> jsonConstants.equals(isoMessageCase.getJsonConstants())) //
		                 .findFirst(); //
	}
	
	protected TestJson build(final double executeOrder, final Constants jsonConstants,
	                         final TransactionManagerConstants transactionManagerConstants) {
		
		TestJson.builder() //
		        .executionOrder(getExecutionOrder(executeOrder, jsonMap)) //
		        .jsonConstants(jsonConstants) //
		        .transactionManagerConstants(transactionManagerConstants) //
		        .isoMessageRequest(new ISOMsg()) //
		        .card(card) //
		        .jsonMap(jsonMap) //
		        .build();
		
		return this;
	}
	
	private double getExecutionOrder(final Double executionOrder, final Map<String, String> jsonCase) {
		
		return Optional.ofNullable(jsonCase.get(EXECUTION_ORDER)) //
		               .map(Double::valueOf) //
		               .orElse(executionOrder);
	}
}