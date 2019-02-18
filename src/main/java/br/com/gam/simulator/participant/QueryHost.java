package br.com.gam.simulator.participant;

import br.com.gam.simulator.constants.Constants;
import br.com.gam.simulator.constants.ErrorDescription;
import br.com.gam.simulator.entities.TestJson;
import org.jpos.core.Configuration;
import org.jpos.ee.BLException;
import org.jpos.iso.*;
import org.jpos.transaction.Context;
import org.jpos.transaction.TxnSupport;
import org.jpos.util.Chronometer;
import org.jpos.util.NameRegistrar;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static br.com.gam.simulator.constants.Constants.DESTINATION;

/**
 * Created by Guilherme Mendes on 06/11/2018
 */
public class QueryHost extends TxnSupport implements ISOResponseListener {
	
	private static final long DEFAULT_TIMEOUT      = 20000L;
	private static final long DEFAULT_WAIT_TIMEOUT = 12000L;
	
	private long      timeout;
	private long      waitTimeout;
	private Constants requestName;
	private Constants responseName;
	private boolean   continuations;
	
	private Chronometer chronometer;
	
	@Override
	protected int doPrepare(final long id, final Context ctx) throws Exception {
		
		final String destination = checkDestination(ctx.getString(DESTINATION));
		final MUX    mux         = checkMux(destination);
		final ISOMsg request     = checkRequest(ctx);
		
		chronometer = new Chronometer();
		
		assertTrue(isConnected(mux), "1891", ErrorDescription.HOST_UNREACHABLE.toString());
		
		long responseTime = Math.max(timeout - chronometer.elapsed(), 1000L); // give at least a second to catch a response
		
		return Optional.of(continuations) //
		               .filter(Boolean::booleanValue) //
		               .map(asynchronousSend(ctx, mux, request, responseTime)) //
		               .orElseGet(synchronousSend(ctx, mux, request, responseTime));
	}
	
	@Override
	public void responseReceived(final ISOMsg response, final Object handBack) {
		
		final Context ctx = (Context) handBack;
		
		final TestJson aTestJson = ctx.get(requestName);
		
		aTestJson.setExecuted(true);
		aTestJson.setIsoMessageResponse(response);
		
		//		ctx.put(requestName, aTestJson);
		//		ctx.put(responseName, response);
		ctx.put(Constants.HOST_DURATION, getHostDuration());
		ctx.resume();
	}
	
	private int getHostDuration() {
		return Optional.of(chronometer) //
		               .map(Chronometer::elapsed) //
		               .map(Long::intValue) //
		               .orElse(null);
	}
	
	@Override
	public void expired(final Object handBack) {
		
		final Context ctx = (Context) handBack;
		
		try {
			
			final ISOMsg request  = ctx.get(requestName);
			final ISOMsg response = (ISOMsg) request.clone();
			
			if (response.isRequest()) {
				response.setResponseMTI();
			}
			response.set(39, "91");
			
			ctx.put(responseName, response);
			ctx.put(Constants.HOST_DURATION, getHostDuration());
			
		} catch (ISOException e) {
			warn(e);
		}
		
		ctx.resume();
	}
	
	private ISOMsg checkRequest(final Context ctx) throws BLException {
		
		final TestJson aTestJson = ctx.get(requestName);
		final ISOMsg   request   = aTestJson.getIsoMessageRequest();
		
		assertNotNull(request, "1800", ErrorDescription.INVALID_REQUEST.toString());
		return request;
	}
	
	private MUX checkMux(final String destination) throws BLException {
		
		final String muxName = cfg.get("mux-name", Constants.PREFIX_MUX.toString() + destination);
		final MUX    mux     = NameRegistrar.getIfExists(muxName);
		
		assertNotNull(mux, "1891", ErrorDescription.UNCONFIGURED_POINT.toString());
		
		return mux;
	}
	
	private String checkDestination(final String destination) throws BLException {
		final String destinationFinal = cfg.get("destination", destination);
		
		assertNotNull(destinationFinal, "1891", ErrorDescription.DESTINATION_NOT_FOUND.toString());
		
		return destinationFinal;
	}
	
	private Function<Boolean, Integer> asynchronousSend(final Context ctx, final MUX mux, final ISOMsg request, final long t) {
		return send -> {
			try {
				mux.request(request, t, this, ctx);
				return PREPARED | READONLY | PAUSE | NO_JOIN;
			} catch (ISOException e) {
				error(e);
				return ABORTED;
			}
		};
	}
	
	private Supplier<Integer> synchronousSend(final Context ctx, final MUX mux, final ISOMsg request, final long t) {
		return () -> {
			try {
				return Optional.ofNullable(mux.request(request, t)) //
				               .map(response -> {
					               ctx.put(responseName, response);
					               return PREPARED | NO_JOIN;
				               }) //
				               .orElseThrow(() -> new BLException("1891", ErrorDescription.HOST_UNREACHABLE.toString()));
			} catch (Exception e) {
				error(e);
				return ABORTED;
			}
		};
	}
	
	@Override
	public void setConfiguration(final Configuration cfg) {
		this.cfg = cfg;
		timeout = cfg.getLong("timeout", DEFAULT_TIMEOUT);
		waitTimeout = cfg.getLong("wait-timeout", DEFAULT_WAIT_TIMEOUT);
		
		requestName = Constants.findConstant(constants -> constants.toString().equals(cfg.get("request"))).orElse(Constants.REQUEST);
		
		responseName = Optional.ofNullable(cfg.get("response", null)) //
		                       .map(Constants::valueOf) //
		                       .orElse(Constants.RESPONSE);
		
		continuations = cfg.getBoolean("continuations", true);
	}
	
	private boolean isConnected(final MUX mux) {
		if (mux.isConnected()) {
			return true;
		}
		long timeout = System.currentTimeMillis() + waitTimeout;
		while (System.currentTimeMillis() < timeout) {
			if (mux.isConnected()) {
				return true;
			}
			ISOUtil.sleep(500);
		}
		return false;
	}
}
