package br.com.gam.simulator.participant;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.entities.TestJson;
import org.jpos.transaction.Context;
import org.jpos.transaction.GroupSelector;
import org.jpos.transaction.TxnSupport;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by Guilherme Mendes on 02/11/2018
 */
public class NextExecutionCase extends TxnSupport implements GroupSelector {
	
	@Override
	protected int doPrepare(final long id, final Context ctx) {
		
		return PREPARED | NO_JOIN | READONLY;
		
	}
	
	/**
	 * Verifica se o caso ja foi executado e qual é o proximo a ser executado.
	 *
	 * @param id
	 * @param context
	 * @return próximo caso a ser executado
	 */
	@Override
	public String select(final long id, final Serializable context) {
		
		final Context ctx = (Context) context;
		
		final Set<TestJson> aTestJsons = ctx.get(Constants.ISO_MESSAGE_CASES);
		
		return aTestJsons.parallelStream() //
		                 .reduce((current, next) -> {
			
			                      if (isNext(current, next) || isNext2(current, next)) {
				
				                      return next;
			                      }
			
			                      return current;
		                      }) //
		                 .filter(executed().negate()) //
		                 .map(TestJson::getJsonConstants) //
		                 .map(Constants::toString) //
		                 .orElse("END");
	}
	
	private boolean isNext(final TestJson current, final TestJson next) {
		return current.getExecutionOrder() > next.getExecutionOrder() && !next.isExecuted();
	}
	
	private boolean isNext2(final TestJson current, final TestJson next) {
		return current.getExecutionOrder() < next.getExecutionOrder() && current.isExecuted();
	}
	
	private Predicate<TestJson> executed() {
		return TestJson::isExecuted;
	}
}
