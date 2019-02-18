package br.com.gam.simulator.constants;

import br.com.gam.simulator.utilities.space.CustomSpaceFactory;
import br.com.gam.simulator.utilities.space.SpaceQueue;
import org.jpos.space.Space;

/**
 * Created by guilherme.mendes on 29/10/2018.
 */
public enum TransactionManagerConstants implements SpaceQueue {
	
	MAIN("MAIN.txnmgr", SpaceCollection.MAIN), //
	NETWORK_MANAGEMENT("NETWORK MANAGEMENT.txnmgr", SpaceCollection.MAIN), //
	AUTHORIZATION("AUTHORIZATION.txnmgr", SpaceCollection.MAIN), //
	CANCELLATION("CANCELLATION.txnmgr", SpaceCollection.MAIN), //
	REVERSAL("REVERSAL.txnmgr", SpaceCollection.MAIN), //
	NOTIFICATION("NOTIFICATION.txnmgr", SpaceCollection.MAIN), //
	; //
	
	private final String          queueName;
	private final SpaceCollection spaceCollection;
	
	TransactionManagerConstants(final String queueName, final SpaceCollection spaceCollection) {
		this.queueName = queueName;
		this.spaceCollection = spaceCollection;
	}
	
	@Override
	public Space getSpace() {
		return CustomSpaceFactory.getSpace(spaceCollection);
	}
	
	@Override
	public String getQueueName() {
		return queueName;
	}
}