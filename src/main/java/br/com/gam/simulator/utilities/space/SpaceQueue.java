package br.com.gam.simulator.utilities.space;

import org.jpos.space.Space;

/**
 * Created by guilherme.mendes on 29/10/2018.
 */
public interface SpaceQueue {
	
	Space getSpace();
	
	String getQueueName();
}