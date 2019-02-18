package br.com.gam.simulator.participant;

import org.jpos.transaction.GroupSelector;
import org.jpos.transaction.TxnSupport;

import java.io.Serializable;

/**
 * Created by guilherme.mendes on 11/01/2017.
 */
public class GoToGroups extends TxnSupport implements GroupSelector {
	
	public int prepare(final long id, final Serializable context) {
		
		return PREPARED | READONLY | NO_JOIN;
	}
	
	public String select(final long id, final Serializable ser) {
		return cfg.get("groups", null);
	}
}

