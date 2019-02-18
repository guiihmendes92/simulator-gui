package br.com.gam.simulator.utilities.space;

/**
 * O componente <code>SpaceOperation</code> apresenta funcionalidades para operar o Space do jPOS.
 * Esse space funciona em regime de filas, portanto esse componente pode enfilerar ou desenfilerar dados.
 * <p>
 * Created by maycon.lima on 26/07/2018.
 */
public interface SpaceOperation<Serializable> {
	
	void queue(final SpaceQueue spaceQueue, Serializable data);
	
	void queue(final SpaceQueue spaceQueue, Serializable data, Long timeout);
	
	Serializable dequeue(final SpaceQueue spaceQueue);
}