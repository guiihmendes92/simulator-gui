package br.com.gam.simulator.participant.create_iso_message;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.ErrorDescription;
import br.com.gam.simulator.entities.TestJson;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.BLException;
import org.jpos.transaction.Context;
import org.jpos.transaction.TxnSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static br.com.gam.simulator.constants.Constants.ISO_MESSAGE_CASES;
import static br.com.gam.simulator.constants.Constants.findConstant;

/**
 * Created by Guilherme Mendes on 01/11/2018
 */
public class ParseJsonToIsoMessage extends TxnSupport {
	
	private String jsonType;
	
	@Override
	protected int doPrepare(final long id, final Context ctx) throws Exception {
		
		final HashMap<Constants, TestJson> switchJsonCases = ctx.get(ISO_MESSAGE_CASES);
		final TestJson                     testJson        = getisoMessageCase(getJsonConstants(), switchJsonCases);
		
		final Map<String, MountMessageField> originalRecoveryData = new HashMap<>();
		
		final TestJson previousAuthorizationTestJson = switchJsonCases.get(Constants.AUTHORIZATION);
		final TestJson previousCancellationTestJson  = switchJsonCases.get(Constants.CANCELLATION);
		
		originalRecoveryData.put(null, new CreateIsoMessage());
		originalRecoveryData.put(Constants.ECHO$AUTH.toString(), new AuthorizationOriginal(previousAuthorizationTestJson));
		originalRecoveryData.put(Constants.ECHO$CANC.toString(), new CancellationOriginal(previousCancellationTestJson));
		originalRecoveryData.put(Constants.ECHO$RESP_AUTH.toString(), new AuthorizationOriginal(previousAuthorizationTestJson));
		originalRecoveryData.put(Constants.ECHO$RESP_CANC.toString(), new CancellationOriginal(previousCancellationTestJson));
		
		return Optional.of(testJson) //
		               .map(new CreateIsoMessage()) //
		               .map(putConxtext(ctx)) //
		               .orElse(ABORTED | NO_JOIN);
	}
	
	private TestJson getisoMessageCase(final Constants constants, final Map<Constants, TestJson> switchTestJson) throws BLException {
		
		return switchTestJson.entrySet() //
		                     .stream() //
		                     .filter(entry -> entry.getKey().equals(constants)) //
		                     .findFirst() //
		                     .map(Map.Entry::getValue) //
		                     .orElseThrow(() -> new BLException("invalid TestJson"));
	}
	
	private Constants getJsonConstants() throws BLException {
		return findConstant(constants -> constants.toString().equals(jsonType)).orElseThrow(
				() -> new BLException("1805", ErrorDescription.UNCONFIGURED_POINT.toString()));
	}
	
	private Function<TestJson, Integer> putConxtext(final Context ctx) {
		return isoMessageCase -> {
			
			ctx.put(isoMessageCase.getJsonConstants(), isoMessageCase);
			
			return PREPARED | NO_JOIN;
		};
	}
	
	@Override
	public void setConfiguration(final Configuration cfg) throws ConfigurationException {
		super.setConfiguration(cfg);
		
		this.jsonType = cfg.get("json-type");
		
	}
}
