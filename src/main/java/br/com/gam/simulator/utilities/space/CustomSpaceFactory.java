package br.com.gam.simulator.utilities.space;

import br.com.gam.simulator.constants.SpaceCollection;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by guilherme.mendes on 29/10/2018.
 */

public class CustomSpaceFactory {
	
	private final static String SPACE_DEFAULT_DIR = "data/";
	private final static String SPACE_DELIMITER   = ":";
	
	/**
	 * Exemplo: "tipo-space:nome-do-meu-space" (memory space)
	 * <p>
	 * Exemplo do resultado: tipo-space:nome-do-meu-space:pasta-default/nome-do-meu-space (memory persistent)
	 * Exemplo real: "je:replication:data/replication
	 *
	 * @param spaceCollection
	 * @return space
	 */
	public static Space getSpace(final SpaceCollection spaceCollection) {
		
		return Optional.of(spaceCollection) //
		               .filter(SpaceCollection::isPersistentSpace) //
		               .map(persistentSpace()) //
		               .map(SpaceFactory::getSpace) //
		               .orElseGet(memorySpace(spaceCollection));
	}
	
	private static Supplier<Space> memorySpace(final SpaceCollection spaceCollection) {
		return () -> {
			
			final String uri = formatUriMemorySpace(spaceCollection.getSpaceType(), spaceCollection.getNameSpace());
			
			return SpaceFactory.getSpace(uri);
		};
	}
	
	private static Function<SpaceCollection, String> persistentSpace() {
		return spaceCollection -> formatUriPersistentSpace(spaceCollection.getSpaceType(), //
		                                                   spaceCollection.getNameSpace(), //
		                                                   spaceCollection.getNameSpace());
	}
	
	private static String formatUriMemorySpace(final String type, final String spaceName) {
		
		return type +//
		       SPACE_DELIMITER +//
		       spaceName;
	}
	
	private static String formatUriPersistentSpace(final String type, final String spaceName, final String fileName) {
		
		return type +//
		       SPACE_DELIMITER +//
		       spaceName +//
		       SPACE_DELIMITER +//
		       CustomSpaceFactory.SPACE_DEFAULT_DIR +//
		       fileName;
	}
}