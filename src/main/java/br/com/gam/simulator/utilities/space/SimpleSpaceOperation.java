package br.com.gam.simulator.utilities.space;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * Created by maycon.lima on 26/07/2018.
 */
public final class SimpleSpaceOperation implements SpaceOperation<Serializable> {
	
	@Override
	public void queue(final SpaceQueue spaceQueue, final Serializable data) {
		
		validateInput(spaceQueue, data);
		
		queue(spaceQueue, data, 0L);
	}
	
	@Override
	public void queue(final SpaceQueue spaceQueue, final Serializable data, final Long timeout) {
		
		validateInput(spaceQueue, data);
		
		spaceQueue.getSpace().out(spaceQueue.getQueueName(), data, timeout);
	}
	
	private void validateInput(final SpaceQueue spaceQueue, final Serializable data) {
		
		requireNonNull(spaceQueue, "The SpaceQueue can not be null");
		requireNonNull(data, "The data object serializable can not be null");
	}
	
	@Override
	public Serializable dequeue(final SpaceQueue spaceQueue) {
		return (Serializable) spaceQueue.getSpace().inp(spaceQueue.getQueueName());
	}
}